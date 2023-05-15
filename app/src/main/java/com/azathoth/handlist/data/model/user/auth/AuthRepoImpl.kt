package com.azathoth.handlist.data.model.user.auth

import android.content.SharedPreferences
import com.azathoth.handlist.data.remote.HandListApi
import retrofit2.HttpException

class AuthRepoImpl(
    private val authApi: HandListApi,
    private val prefs: SharedPreferences
) : AuthRepo {
    override suspend fun signUp(
        email: String,
        password: String,
        nickname: String
    ): AuthResult<Unit> = try {
        authApi.signUp(RegisterRequest(nickname = nickname, email = email, password = password))

        signIn(email, password)
    } catch (e: HttpException) {
        if (e.code() == 401) {
            AuthResult.Unauthorized()
        } else {
            AuthResult.UnknownError()
        }
    } catch (e: Exception) {
        AuthResult.UnknownError()
    }

    override suspend fun signIn(email: String, password: String): AuthResult<Unit> = try {
        val response = authApi.signIn(AuthRequest(email = email, password = password))

        prefs.edit().putString("jwt", response.token).putString("userEmail", email).apply()

        AuthResult.Authorized()
    } catch (e: HttpException) {
        if (e.code() == 403) {
            AuthResult.Unauthorized()
        } else {
            AuthResult.UnknownError()
        }
    } catch (e: Exception) {
        AuthResult.UnknownError()
    }

    override suspend fun authenticate(): AuthResult<Unit> = try {
        val token = prefs.getString("jwt", null)

        if (token == null) {
            AuthResult.Unauthorized()
        } else {
            AuthResult.Authorized()
        }
    } catch (e: HttpException) {
        if (e.code() == 403) {
            AuthResult.Unauthorized()
        } else {
            AuthResult.UnknownError()
        }
    } catch (e: Exception) {
        AuthResult.UnknownError()
    }
}