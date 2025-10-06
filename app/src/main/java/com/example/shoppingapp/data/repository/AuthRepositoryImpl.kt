package com.example.shoppingapp.data.repository

import com.example.shoppingapp.data.remote.AuthRemoteDataSource
import com.example.shoppingapp.data.util.toModel
import com.example.shoppingapp.domain.model.UserModel
import com.example.shoppingapp.domain.repository.AuthRepository
import com.example.shoppingapp.domain.util.Response
import com.example.shoppingapp.domain.util.map

class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource
): AuthRepository {
    override suspend fun createAccountWithEmail(
        email: String,
        password: String
    ): Response<UserModel> {
        return authRemoteDataSource.createAccountWithEmail(
            email,
            password
        ).map {
            it.toModel()
        }
    }

    override suspend fun loginWithEmail(
        email: String,
        password: String
    ): Response<UserModel> {
        return authRemoteDataSource.loginWithEmail(
            email,
            password
        ).map {
            it.toModel()
        }
    }

    override suspend fun signOut() {
        authRemoteDataSource.signOut()
    }

    override suspend fun signInWithGoogle(): Response<UserModel> {
        return authRemoteDataSource.signInWithGoogle().map {
            it.toModel()
        }
    }

    override suspend fun isLogin(): Response<Boolean> {
        return authRemoteDataSource.isLogin()
    }
}