package com.azathoth.handlist.data.model.user.auth

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val nickname: String,
    val email: String,
    val password: String
)

data class AuthState(
    val isLoading: Boolean = false,

    val signUpEmail: String = "",
    val signUpPassword: String = "",
    val signUpNickname: String = "",

    val signInEmail: String = "",
    val signInPassword: String = "",
)

data class TokenResponse(
    @SerializedName("access_token")
    val token: String
)