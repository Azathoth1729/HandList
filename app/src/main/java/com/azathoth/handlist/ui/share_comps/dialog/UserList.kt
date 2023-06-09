package com.azathoth.handlist.ui.share_comps.dialog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.azathoth.handlist.data.DemoDataProvider
import com.azathoth.handlist.data.model.user.User
import com.azathoth.handlist.ui.theme.HandListTheme

@Composable
fun UserList(
    allUsers: List<User>,
    assignUsers: MutableList<User>,
    modifier: Modifier = Modifier,
    toggleUser: ((User, Boolean) -> Unit)? = null,
) {
    LazyColumn(modifier = modifier) {
        items(
            items = allUsers,
            key = { user -> user.id }
        ) { user ->
            val isContain = assignUsers.contains(user)
            UserItem(
                user = user,
                checked = isContain,
                onCheckedChange = {
                    if (toggleUser != null) {
                        toggleUser(it, isContain)
                    }
                },
            )
        }
    }
}


@Composable
fun UserItem(
    user: User,
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (User) -> Unit = {},
) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = "${user.nickname} (${user.email})"
        )
        TriStateCheckbox(
            state = ToggleableState(checked),
            onClick = { onCheckedChange(user) },
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun UserListPreview() {
    HandListTheme {
        val allUsers = DemoDataProvider.demoAllUsers
        val assignUsers = DemoDataProvider.demoAssignUsers.toMutableStateList()
        UserList(
            allUsers = allUsers,
            assignUsers = assignUsers,
            toggleUser = { user, isContain ->
                if (isContain) {
                    assignUsers.remove(user)
                } else {
                    assignUsers.add(user)
                }
            }
        )
    }
}