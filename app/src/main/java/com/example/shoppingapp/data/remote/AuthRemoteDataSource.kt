package com.example.shoppingapp.data.remote


import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.shoppingapp.R
import com.example.shoppingapp.data.model.response.UserResponse
import com.example.shoppingapp.data.services.createNonce
import com.example.shoppingapp.domain.util.Response
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRemoteDataSource(
    private val context: Context? = null,
    private val auth: FirebaseAuth
) {
    suspend fun createAccountWithEmail(
        email: String,
        password: String
    ): Response<UserResponse> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Response.Success(UserResponse(id = result.user?.uid ?: ""))
        } catch (e: Exception) {
            Response.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun isLogin(): Response<Boolean> {
        if (auth.currentUser != null) {
            return Response.Success(true)
        }
        return Response.Success(false)
    }

    suspend fun loginWithEmail(
        email: String,
        password: String
    ): Response<UserResponse> {
        return try {
            Log.i("LoginWithEmail", "Success Remote")
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Response.Success(UserResponse(id = result.user?.uid ?: ""))
        } catch (e: Exception) {
            Response.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun signOut() {
        auth.signOut()
    }

    suspend fun signInWithGoogle(): Response<UserResponse> {
        return try {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context!!.getString(R.string.web_client_id))
                .setAutoSelectEnabled(false)
                .setNonce(createNonce())
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val credentialManager = CredentialManager.create(context!!)
            val result = credentialManager.getCredential(context, request)

            val credential = result.credential
            if (credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                try {
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)

                    val firebaseCredential = GoogleAuthProvider.getCredential(
                        googleIdTokenCredential.idToken, null
                    )

                    val authResult = auth.signInWithCredential(firebaseCredential).await()
                    Response.Success(UserResponse(id = authResult.user?.uid ?: ""))
                } catch (e: GoogleIdTokenParsingException) {
                    Response.Error(e.message ?: "Unknown error")
                }
            } else {
                Response.Error("Invalid credential type")
            }
        } catch (e: Exception) {
            Response.Error(e.message ?: "Unknown error")
        }
    }
}
