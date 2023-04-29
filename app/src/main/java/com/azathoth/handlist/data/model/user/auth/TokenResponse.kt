package com.azathoth.handlist.data.model.user.auth

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("access_token")
    val token: String
)