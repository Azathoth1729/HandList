package com.azathoth.handlist.data.model.post.tag

data class PostTagUiState(
    val id: Long = 0,
    val name: String = "",
)

fun PostTag.toDTO(): PostTagUiState = PostTagUiState(
    id = id,
    name = name
)