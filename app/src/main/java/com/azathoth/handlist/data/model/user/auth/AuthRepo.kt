package com.azathoth.handlist.data.model.user.auth


interface AuthRepo {
    suspend fun signUp(email: String, password: String, nickname: String): AuthResult<Unit>
    suspend fun signIn(email: String, password: String): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>
}