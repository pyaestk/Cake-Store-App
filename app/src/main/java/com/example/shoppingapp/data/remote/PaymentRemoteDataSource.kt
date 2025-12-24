package com.example.shoppingapp.data.remote

import OrderRequest
import PaymentItemResponse
import PaymentSummaryResponse
import com.example.shoppingapp.data.model.request.AddToCartRequest
import com.example.shoppingapp.data.model.response.ItemResponse
import com.example.shoppingapp.domain.model.CheckoutItem
import com.example.shoppingapp.domain.model.PaymentMethod
import com.example.shoppingapp.domain.model.ShippingOption
import com.example.shoppingapp.domain.util.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PaymentRemoteDataSource(
    private val firestore: FirebaseFirestore,
    private val firebaseDatabase: FirebaseDatabase,
    private val auth: FirebaseAuth
) {

    private fun addressDoc(userId: String) =
        firestore.collection("User")
            .document(userId)
            .collection("Address")
            .document("shipping")

    private fun checkoutDoc(userId: String) =
        firestore.collection("User")
            .document(userId)
            .collection("Checkout")
            .document("current")


    suspend fun getPaymentSummary(): Response<PaymentSummaryResponse> {

        return try {
            val userId = auth.currentUser?.uid.toString()

            // Checkout settings (shipping option + payment method)
            val checkoutRef = checkoutDoc(userId)
            val checkoutSnap = checkoutRef.get().await()

            val selectedShipping = when (checkoutSnap.getString("shippingOption")) {
                ShippingOption.Express.name -> ShippingOption.Express
                else -> ShippingOption.Standard
            }

            val selectedPaymentMethod = when (checkoutSnap.getString("paymentMethod")) {
                PaymentMethod.CashOnDelivery.name -> PaymentMethod.CashOnDelivery
                else -> PaymentMethod.Card
            }

            // Items (Option A: CartItem + join Realtime DB Items)
            val cartSnap = firestore.collection("User")
                .document(userId)
                .collection("CartItem")
                .get()
                .await()

            val cartItems = cartSnap.documents.mapNotNull {
                it.toObject(AddToCartRequest::class.java)
            }

            val itemsRef = firebaseDatabase.getReference("Items")
            val itemsSnapshot = itemsRef.get().await()
            val allItems = itemsSnapshot.children.mapNotNull {
                it.getValue(ItemResponse::class.java)
            }

            val items: List<PaymentItemResponse> = cartItems.mapNotNull { cart ->
                val matching = allItems.find { it.id == cart.itemId }
                matching?.let {
                    PaymentItemResponse(
                        id = cart.itemId.toString(),
                        title = it.title,
                        subtitle = "Size: ${cart.size}",
                        imageUrl = it.picUrl.firstOrNull(),
                        qty = cart.quantity,
                        price = it.price.toDouble()
                    )
                }
            }

            // 4) Totals
            val itemsTotal = items.sumOf { it.price * it.qty }
            val shippingFee = if (selectedShipping == ShippingOption.Express) 12.0 else 0.0
            val grandTotal = itemsTotal + shippingFee

            val paymentSummaryResponse = PaymentSummaryResponse(
                items = items,
                selectedShipping = selectedShipping,
                selectedPaymentMethod = selectedPaymentMethod,
                itemsTotal = itemsTotal,
                shippingFee = shippingFee,
                grandTotal = grandTotal
            )

            Response.Success(paymentSummaryResponse)
        } catch (e: Exception) {
            Response.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun getPaymentSummaryForItems(itemsInput: List<CheckoutItem>): Response<PaymentSummaryResponse> {
        val userId = auth.currentUser?.uid ?: return Response.Error("Not signed in")

        return try {
            // checkout settings
            val checkoutSnap = checkoutDoc(userId).get().await()

            val selectedShipping = when (checkoutSnap.getString("shippingOption")) {
                ShippingOption.Express.name -> ShippingOption.Express
                else -> ShippingOption.Standard
            }

            val selectedPaymentMethod = when (checkoutSnap.getString("paymentMethod")) {
                PaymentMethod.CashOnDelivery.name -> PaymentMethod.CashOnDelivery
                else -> PaymentMethod.Card
            }

            // load products (Realtime DB) - same as you do now
            val itemsSnapshot = firebaseDatabase.getReference("Items").get().await()
            val allItems = itemsSnapshot.children.mapNotNull { it.getValue(ItemResponse::class.java) }

            // join using itemsInput
            val paymentItems = itemsInput.mapNotNull { ci ->
                val matching = allItems.find { it.id == ci.itemId}
                matching?.let {
                    PaymentItemResponse(
                        id = ci.itemId.toString(),
                        title = it.title,
                        subtitle = "Size: ${ci.size}",
                        imageUrl = it.picUrl.firstOrNull(),
                        qty = ci.qty,
                        price = it.price.toDouble()
                    )
                }
            }

            val itemsTotal = paymentItems.sumOf { it.price * it.qty }
            val shippingFee = if (selectedShipping == ShippingOption.Express) 12.0 else 0.0
            val grandTotal = itemsTotal + shippingFee

            Response.Success(
                PaymentSummaryResponse(
                    items = paymentItems,
                    selectedShipping = selectedShipping,
                    selectedPaymentMethod = selectedPaymentMethod,
                    itemsTotal = itemsTotal,
                    shippingFee = shippingFee,
                    grandTotal = grandTotal
                )
            )
        } catch (e: Exception) {
            Response.Error(e.message ?: "Unknown error")
        }
    }


    // note: update() fails if doc doesn't exist -> use set(merge=true)
    suspend fun setShippingOption(option: ShippingOption): Response<Unit> {

        return try {
            val userId = auth.currentUser?.uid.toString()
            checkoutDoc(userId)
                .set(mapOf("shippingOption" to option.name), com.google.firebase.firestore.SetOptions.merge())
                .await()
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun setPaymentMethod(method: PaymentMethod): Response<Unit> {
        return try {
            val userId = auth.currentUser?.uid.toString()
            checkoutDoc(userId)
                .set(mapOf("paymentMethod" to method.name), com.google.firebase.firestore.SetOptions.merge())
                .await()
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun saveOrder(request: OrderRequest): Response<String> {
        val userId = auth.currentUser?.uid ?: return Response.Error("Not signed in")

        return try {
            val orderRef = firestore.collection("User")
                .document(userId)
                .collection("Orders")
                .document()

            val batch = firestore.batch()

            // header
            batch.set(orderRef, mapOf(
                "status" to "success",
                "createdAt" to System.currentTimeMillis(),
                "shippingOption" to request.shippingOption,
                "paymentMethod" to request.paymentMethod,
                "itemsTotal" to request.itemsTotal,
                "shippingFee" to request.shippingFee,
                "grandTotal" to request.grandTotal
            ))

            // items snapshot
            request.items.forEach { it ->
                val itemRef = orderRef.collection("Items").document(it.itemId)
                batch.set(itemRef, mapOf(
                    "itemId" to it.itemId,
                    "title" to it.title,
                    "subtitle" to it.subtitle,
                    "imageUrl" to it.imageUrl,
                    "qty" to it.qty,
                    "price" to it.price
                ))
            }

            batch.commit().await()

            Response.Success(orderRef.id)
        } catch (e: Exception) {
            Response.Error(e.message ?: "Unknown error")
        }
    }



}
