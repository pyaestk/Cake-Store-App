package com.example.shoppingapp.data.remote

import android.util.Log
import com.example.shoppingapp.data.model.response.ItemResponse
import com.example.shoppingapp.domain.util.Response
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ItemRemoteDataSource(
    private val firebaseDatabase: FirebaseDatabase
) {
    fun loadItems(): Flow<Response<List<ItemResponse>>> = callbackFlow {
        val ref = firebaseDatabase.getReference("Items")
        /*.orderByChild("showRecommended")
        .equalTo(true)*/
        trySend(Response.Loading()).isSuccess

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<ItemResponse>()
                for (itemSnapShot in snapshot.children) {
                    itemSnapShot.getValue(ItemResponse::class.java)
                        ?.let { list.add(it) }
                }
                trySend(Response.Success(list)).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Response.Error(message = error.message)).isSuccess
            }
        }
        ref.addValueEventListener(valueEventListener)
        awaitClose {
            ref.removeEventListener(valueEventListener)
        }
    }

    fun loadItemDetail(itemId: Int): Flow<Response<ItemResponse>> = callbackFlow {
        val ref = firebaseDatabase.getReference("Items")

        trySend(Response.Loading()).isSuccess

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var foundItem: ItemResponse? = null
                for (itemSnapshot in snapshot.children) {
                    val itemResponse = itemSnapshot.getValue(ItemResponse::class.java)
                    if (itemResponse?.id == itemId) {
                        foundItem = itemResponse
                        break
                    }
                }

                if (foundItem != null) {
                    trySend(Response.Success(foundItem)).isSuccess
                } else {
                    trySend(Response.Error("Item with ID $itemId not found")).isSuccess
                }
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Response.Error(message = error.message)).isSuccess
            }
        }

        ref.addListenerForSingleValueEvent(valueEventListener)

        awaitClose {
            ref.removeEventListener(valueEventListener)
        }
    }



    fun loadItemByCategory(categoryId: Int): Flow<Response<List<ItemResponse>>> = callbackFlow {
        val query = firebaseDatabase
            .getReference("Items")
            .orderByChild("categoryId")
            .equalTo(categoryId.toString())

        trySend(Response.Loading())

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<ItemResponse>()
                for (itemSnapShot in snapshot.children) {
                    itemSnapShot.getValue(ItemResponse::class.java)?.let {
                        list.add(it)
                    }
                }
                Log.i("loadItemByCategory", "Category ID: $categoryId, Items found: ${list.size}")
                trySend(Response.Success(list))
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("loadItemByCategory", "Error")
                trySend(Response.Error(message = error.message))
            }
        }

        query.addValueEventListener(valueEventListener)

        awaitClose {
            query.removeEventListener(valueEventListener)
        }
    }
}