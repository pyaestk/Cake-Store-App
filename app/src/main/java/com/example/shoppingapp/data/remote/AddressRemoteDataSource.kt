package com.example.shoppingapp.data.remote

import com.example.shoppingapp.data.model.request.AddAddressRequest
import com.example.shoppingapp.data.model.response.AddressResponse
import com.example.shoppingapp.domain.util.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AddressRemoteDataSource(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private fun addressDoc(userId: String) =
        firestore.collection("User")
            .document(userId)
            .collection("Address")
            .document("shipping")

    suspend fun saveShippingAddress(
        address: AddAddressRequest
    ): Response<Unit> {
        val userId = auth.currentUser?.uid.toString()
        return try {
            addressDoc(userId)
                .set(address)
                .await()

            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun getShippingAddress(): Response<AddressResponse> {
        val userId = auth.currentUser?.uid.toString()
        return try {
            val snapshot = addressDoc(userId).get().await()
            if (!snapshot.exists()) return Response.Success(null)

            val dto = snapshot.toObject(AddAddressRequest::class.java)
            Response.Success(
                dto?.let {
                    AddressResponse(
                        name = it.name,
                        addressNum = it.addressNum,
                        city = it.city,
                        country = it.country,
                        zipCode = it.zipCode,
                        phoneNumber = it.phoneNumber
                    )
                }
            )
        } catch (e: Exception) {
            Response.Error(e.message ?: "Unknown error")
        }
    }


    suspend fun deleteShippingAddress(): Response<Unit> {
        return try {
            val userId = auth.currentUser?.uid.toString()
            addressDoc(userId).delete().await()
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Error(e.message ?: "Unknown error")
        }
    }
}
