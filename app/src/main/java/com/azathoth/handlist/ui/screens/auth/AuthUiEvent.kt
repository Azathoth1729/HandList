package com.azathoth.handlist.ui.screens.auth

sealed class AuthUiEvent {
    data class SignUpEmailChanged(val value: String) : AuthUiEvent()
    data class SignUpPasswordChanged(val value: String) : AuthUiEvent()
    data class SignUpNicknameChanged(val value: String) : AuthUiEvent()
    object SignUp : AuthUiEvent()

    data class SignInEmailChanged(val value: String) : AuthUiEvent()
    data class SignInPasswordChanged(val value: String) : AuthUiEvent()
    object SignIn : AuthUiEvent()
}