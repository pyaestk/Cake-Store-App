package com.example.shoppingapp.domain.repository

import com.example.shoppingapp.domain.model.UserModel
import com.example.shoppingapp.domain.util.Response

interface AuthRepository {
    suspend fun createAccountWithEmail(email: String, password: String): Response<UserModel>
    suspend fun loginWithEmail(email: String, password: String): Response<UserModel>
    suspend fun signOut()
    suspend fun signInWithGoogle(): Response<UserModel>

    suspend fun isLogin(): Response<Boolean>
}