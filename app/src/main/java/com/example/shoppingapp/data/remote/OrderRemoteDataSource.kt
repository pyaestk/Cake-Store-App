import com.example.shoppingapp.data.model.response.OrderItemResponse
import com.example.shoppingapp.data.model.response.OrderResponse
import com.example.shoppingapp.domain.util.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OrderRemoteDataSource(
    private val auth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) {

    fun loadOrders(): Flow<Response<List<OrderResponse>>> = callbackFlow {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            trySend(Response.Error("User not logged in"))
            close()
            return@callbackFlow
        }

        trySend(Response.Loading())

        val ordersRef = firebaseFirestore
            .collection("User")
            .document(userId)
            .collection("Orders")

        val reg = ordersRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                trySend(Response.Error(error.message ?: "Unknown error"))
                return@addSnapshotListener
            }

            if (snapshot == null || snapshot.isEmpty) {
                trySend(Response.Success(emptyList()))
                return@addSnapshotListener
            }

            // async fetches in a coroutine
            launch {
                try {
                    val orders = snapshot.documents.map { doc ->
                        val base = doc.toObject(OrderResponse::class.java) ?: OrderResponse()

                        val itemsSnap = ordersRef
                            .document(doc.id)
                            .collection("Items")
                            .get()
                            .await()

                        val items = itemsSnap.documents.mapNotNull { it.toObject(OrderItemResponse::class.java) }

                        base.copy(items = items)
                    }

                    trySend(Response.Success(orders))
                } catch (e: Exception) {
                    trySend(Response.Error(e.message ?: "Failed to load order items"))
                }
            }
        }

        awaitClose { reg.remove() }
    }
}
