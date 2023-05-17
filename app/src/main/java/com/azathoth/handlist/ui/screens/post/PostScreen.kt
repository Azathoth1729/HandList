package com.azathoth.handlist.ui.screens.post

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.azathoth.handlist.R
import com.azathoth.handlist.data.model.post.PostUiState
import com.azathoth.handlist.ui.share_comps.MainTopBar

@Composable
fun PostScreen(
    modifier: Modifier = Modifier,
    navigateToEditPost: (Long) -> Unit = {},
    navigateToProfile: () -> Unit = { },
    viewModel: PostVM = hiltViewModel()
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainTopBar(navigateToProfile = navigateToProfile)
        PostList(
            postListState = viewModel.posts,
            onPostClick = navigateToEditPost,
            modifier = modifier
        )
    }
}

@Composable
fun PostList(
    postListState: PostListState,
    onPostClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (postListState.error.isNotBlank()) {
            Text(
                text = postListState.error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (postListState.isLoading) {
            Text(
                text = stringResource(R.string.loading),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (postListState.posts.isEmpty()) {
            Text(
                text = stringResource(R.string.no_post_item),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(postListState.posts) { post ->
                    PostItem(
                        post = post,
                        onPostClick = { onPostClick(it.id) },
                        modifier = Modifier
                            .padding(4.dp)
                            .padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PostItem(
    post: PostUiState,
    onPostClick: (PostUiState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .clickable { onPostClick(post) }) {
        Text(
            text = post.title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        post.user?.let {
            Text(
                text = it.email,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outline,
            )
        }
        Text(
            text = post.lastModifiedTime,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline,
        )
        Text(
            text = post.content,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            overflow = TextOverflow.Ellipsis
        )
    }
}