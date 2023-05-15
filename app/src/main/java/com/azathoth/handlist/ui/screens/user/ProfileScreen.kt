package com.azathoth.handlist.ui.screens.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.azathoth.handlist.R
import com.azathoth.handlist.data.model.user.User
import com.azathoth.handlist.ui.share_comps.EditTopBar

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = { },
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
                Profile(user = userState.data)
            }
        }

    }
}

@Composable
fun Profile(
    user: User,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = user.nickname,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = user.email,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline,
        )
        Text(
            text = user.role,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline,
        )
    }

}