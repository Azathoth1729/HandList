package com.azathoth.handlist.data.model.user

data class User(
    val id: Long,
    val email: String,
    val nickname: String,
    val role: String
)