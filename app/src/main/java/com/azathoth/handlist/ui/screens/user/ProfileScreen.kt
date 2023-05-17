package com.azathoth.handlist.ui.screens.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.azathoth.handlist.R
import com.azathoth.handlist.data.model.user.User
import com.azathoth.handlist.ui.share_comps.EditTopBar

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = { },
    navigateToAuth: () -> Unit = { },
    viewModel: ProfileVM = hiltViewModel()
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EditTopBar(
            navigateBack = navigateBack,
        )
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            val userState = viewModel.userState

            if (userState.error.isNotBlank()) {
                Text(
                    text = userState.error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (userState.isLoading) {
                Text(
                    text = stringResource(R.string.loading),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (userState.data == null) {
                Text(
                    text = stringResource(R.string.no_user_item),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Profile(user = userState.data, modifier = modifier, onLogout = navigateToAuth)
            }
        }

    }
}

@Composable
fun Profile(
    user: User,
    modifier: Modifier = Modifier,
    onLogout: () -> Unit = { },
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileImage()
        Text(
            text = user.nickname,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = user.email,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.outline,
        )
        Spacer(modifier = modifier.height(20.dp))

        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.width(100.dp)
        ) {
            Text(stringResource(R.string.logout_action))
        }
    }

}

@Composable
fun ProfileImage() {
    val defaultAvatar = painterResource(R.drawable.ic_user)

    Card(
        modifier = Modifier
            .size(100.dp)
            .padding(8.dp),
        shape = CircleShape,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = defaultAvatar,
                contentDescription = null,
                modifier = Modifier.size(70.dp),
                contentScale = ContentScale.Crop
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    val user = User(
        id = 0,
        email = "miku@qq.com",
        nickname = "miku",
        role = "USER"
    )
    Profile(user)
}