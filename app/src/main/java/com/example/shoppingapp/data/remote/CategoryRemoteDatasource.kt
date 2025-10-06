package com.example.shoppingapp.data.remote

import com.example.shoppingapp.data.model.response.CategoryResponse
import com.example.shoppingapp.domain.util.Response
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RemoteDatasource(
    private val firebaseDatabase: FirebaseDatabase
) {


    fun loadCategories(): Flow<Response<List<CategoryResponse>>> = callbackFlow {
        val ref = firebaseDatabase.getReference("Category")
        trySend(Response.Loading()).isSuccess
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<CategoryResponse>()
                for (itemSnapShot in snapshot.children) {
                    itemSnapShot.getValue(CategoryResponse::class.java)
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



}
