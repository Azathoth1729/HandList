package com.azathoth.handlist.ui.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azathoth.handlist.data.model.user.auth.AuthRepo
import com.azathoth.handlist.data.model.user.auth.AuthResult
import com.azathoth.handlist.data.model.user.auth.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthVM @Inject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {
    var state by mutableStateOf(AuthState())

    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResult = resultChannel.receiveAsFlow()

    fun onEvent(event: AuthUiEvent) {
        when (event) {
            is AuthUiEvent.SignUpEmailChanged -> {
                state = state.copy(signUpEmail = event.value)
            }
            is AuthUiEvent.SignUpPasswordChanged -> {
                state = state.copy(signUpPassword = event.value)
            }
            is AuthUiEvent.SignUpNicknameChanged -> {
                state = state.copy(signUpNickname = event.value)
            }
            is AuthUiEvent.SignUp -> {
                signUp()
            }

            is AuthUiEvent.SignInEmailChanged -> {
                state = state.copy(signInEmail = event.value)
            }
            is AuthUiEvent.SignInPasswordChanged -> {
                state = state.copy(signInPassword = event.value)
            }
            is AuthUiEvent.SignIn -> {
                signIn()
            }
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            val result = authRepo.signIn(
                state.signInEmail,
                state.signInPassword,
            )
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            val result = authRepo.signUp(
                state.signUpEmail,
                state.signUpPassword,
                state.signUpNickname,
            )
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }
}