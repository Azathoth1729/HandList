package com.azathoth.handlist.data.model.user

import com.azathoth.handlist.data.remote.HandListApi

class UserRepoImpl(private val userApi: HandListApi) : UserRepo {
    override suspend fun getAllUsers(): List<User> =
        userApi.getAllUsers()
}