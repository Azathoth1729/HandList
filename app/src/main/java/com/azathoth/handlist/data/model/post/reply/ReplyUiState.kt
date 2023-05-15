package com.azathoth.handlist.data.model.post.reply

import com.azathoth.handlist.common.util.format
import com.azathoth.handlist.data.model.user.User
import java.time.LocalDate

data class ReplyUiState(
    val id: Long = 0,
    val content: String = "",
    val createTime: String = LocalDate.now().format(),
    val lastModifiedTime: String = createTime,
    val user: User,
    val postId: Long = 0,
)

fun Reply.toUiState(): ReplyUiState = ReplyUiState(
    id = id,
    content = content,
    createTime = createTime,
    lastModifiedTime = lastModifiedTime,
    user = user,
    postId = postId,
)
