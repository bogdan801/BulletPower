package com.bogdan801.bulletpower.data.util.login

import com.bogdan801.bulletpower.domain.model.User

data class SignInResult(
    val userData: User?,
    val errorMessage: String? = null,
    val errorType: ErrorType? = null
)

enum class ErrorType {
    AccountAlreadyExists,
    WrongEmailFormat,
    WeakPassword,
    WrongEmailOrPassWord,
    NoInternetConnection,
    Other
}

