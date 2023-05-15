package com.azathoth.handlist.data.model.post

import androidx.compose.runtime.mutableStateListOf
import com.azathoth.handlist.common.util.format
import com.azathoth.handlist.data.model.post.reply.Reply
import com.azathoth.handlist.data.model.post.tag.PostTag
import com.azathoth.handlist.data.model.user.User
import java.time.LocalDate

data class PostUiState(
    val id: Long = 0,
    val title: String = "",
    val content: String = "",
    var createTime: String = LocalDate.now().format(),
    var lastModifiedTime: String = createTime,
    var user: User? = null,
    var replies: MutableList<Reply> = mutableStateListOf(),
    var tags: MutableList<PostTag> = mutableStateListOf()
)

fun Post.toUiState(): PostUiState = PostUiState(
    id = id,
    title = title,
    content = content,
    createTime = createTime,
    lastModifiedTime = lastModifiedTime,
    user = user,
    replies = replies,
    tags = tags
)