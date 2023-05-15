package com.azathoth.handlist.ui.screens.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.azathoth.handlist.ui.share_comps.MainTopBar
import com.azathoth.handlist.ui.share_comps.TaskList

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToEditPost: (Long) -> Unit = { },
    navigateToNewPost: () -> Unit = { },
    viewModel: HomeVM = hiltViewModel()
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainTopBar()
        TaskList(
            taskListState = viewModel.tasks,
            onTaskClick = navigateToEditPost,
            modifier = modifier
        )
        FloatingActionButton(
            onClick = navigateToNewPost,
        ) {
            Icon(Icons.Filled.Add, "Localized description")
        }
    }
}