package com.example.shoppingapp.data.remote

import com.example.shoppingapp.data.model.response.BannerResponse
import com.example.shoppingapp.domain.util.Response
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class BannerRemoteDataSource (
    private val firebaseDatabase: FirebaseDatabase
) {

    fun loadBanners(): Flow<Response<List<BannerResponse>>> = callbackFlow {
        val ref = firebaseDatabase.getReference("Banner")
        trySend(Response.Loading()).isSuccess
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<BannerResponse>()
                for (itemSnapShot in snapshot.children) {
                    itemSnapShot.getValue(BannerResponse::class.java)
                        ?.let { list.add(it) }
                }
                // Try to send the success result, logging any errors if the channel is closed
                trySend(Response.Success(list)).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                // Try to send the error result, logging any errors if the channel is closed
                trySend(Response.Error(message = error.message)).isSuccess
            }
        }
        ref.addValueEventListener(valueEventListener)

        // This block is called when the Flow is cancelled or completed.
        // It's important to remove the listener to avoid memory leaks.
        awaitClose {
            ref.removeEventListener(valueEventListener)
        }
    }
}