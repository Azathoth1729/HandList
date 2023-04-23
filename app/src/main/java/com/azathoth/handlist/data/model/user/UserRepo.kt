package com.azathoth.handlist.data.model.user

interface UserRepo {
    suspend fun getAllUsers(): List<User>
}