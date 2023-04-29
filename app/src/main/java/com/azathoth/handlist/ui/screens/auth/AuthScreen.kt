package com.azathoth.handlist.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.azathoth.handlist.R
import com.azathoth.handlist.data.model.user.auth.AuthResult
import com.azathoth.handlist.data.model.user.auth.AuthUiEvent
import com.azathoth.handlist.ui.theme.HandListTheme

@Composable
fun AuthScreen(
    navigateToHome: () -> Unit = {},
    viewModel: AuthVM = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.authResult.collect {
            when (it) {
                is AuthResult.Authorized -> {
                    navigateToHome()
                }
                is AuthResult.Unauthorized -> {
                    Toast.makeText(
                        context,
                        "You're not authorized",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is AuthResult.UnknownError -> {
                    Toast.makeText(
                        context,
                        "An unknown error occurred",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignInForm(viewModel)
        SignUpForm(viewModel)
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnScope.SignInForm(viewModel: AuthVM) {
    val state = viewModel.state
    TextField(
        value = state.signUpEmail,
        onValueChange = {
            viewModel.onEvent(AuthUiEvent.SignUpEmailChanged(it))
        },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = stringResource(R.string.email))
        }
    )
    Spacer(modifier = Modifier.height(16.dp))
    TextField(
        value = state.signUpPassword,
        onValueChange = {
            viewModel.onEvent(AuthUiEvent.SignUpPasswordChanged(it))
        },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = stringResource(R.string.password))
        }
    )
    Spacer(modifier = Modifier.height(16.dp))
    TextField(
        value = state.signUpNickname,
        onValueChange = {
            viewModel.onEvent(AuthUiEvent.SignUpNicknameChanged(it))
        },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = stringResource(R.string.nickname))
        }
    )
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = {
            viewModel.onEvent(AuthUiEvent.SignUp)
        },
        modifier = Modifier.align(Alignment.CenterHorizontally)
    ) {
        Text(text = stringResource(R.string.sign_up))
    }

    Spacer(modifier = Modifier.height(64.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnScope.SignUpForm(viewModel: AuthVM) {
    val state = viewModel.state
    TextField(
        value = state.signInEmail,
        onValueChange = {
            viewModel.onEvent(AuthUiEvent.SignInEmailChanged(it))
        },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = stringResource(R.string.email))
        }
    )
    Spacer(modifier = Modifier.height(16.dp))
    TextField(
        value = state.signInPassword,
        onValueChange = {
            viewModel.onEvent(AuthUiEvent.SignInPasswordChanged(it))
        },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = stringResource(R.string.password))
        }
    )
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = {
            viewModel.onEvent(AuthUiEvent.SignIn)
        },
        modifier = Modifier.align(Alignment.CenterHorizontally)
    ) {
        Text(text = stringResource(R.string.sign_in))
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenBodyPreview() {
    HandListTheme {
        AuthScreen()
    }
}
