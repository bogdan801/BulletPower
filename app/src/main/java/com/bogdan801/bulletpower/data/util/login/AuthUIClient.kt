package com.bogdan801.bulletpower.data.util.login

import android.content.Context
import android.widget.Toast
import com.bogdan801.bulletpower.domain.model.User
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class AuthUIClient(
    private val context: Context
) {
    private val auth = Firebase.auth

    suspend fun signInWithEmailAndPassword(email: String, password: String): SignInResult {
        try {
            val result = auth.signInWithEmailAndPassword(email.trimEnd(), password).await()
            return SignInResult(
                userData = User(
                    userID = result.user?.uid.toString(),
                    username = result.user?.displayName,
                    profilePictureUrl = result.user?.photoUrl.toString()
                )
            )
        }
        catch (e: Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e

            return SignInResult(
                userData = null,
                errorMessage = e.message,
                errorType = when (e) {
                    is FirebaseAuthInvalidCredentialsException -> ErrorType.WrongEmailOrPassWord
                    is FirebaseAuthInvalidUserException -> ErrorType.WrongEmailOrPassWord
                    is FirebaseNetworkException -> ErrorType.NoInternetConnection
                    else -> ErrorType.Other
                }
            )
        }
    }

    fun signOut(){
        try {
            auth.signOut()
        }
        catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            if(e is CancellationException) throw e
        }
    }

    fun getSignedInUser(): User? = auth.currentUser?.run {
        User(
            userID = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }
}