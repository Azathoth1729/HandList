package com.azathoth.handlist.data.model.post.reply

import com.azathoth.handlist.data.model.user.User


class Reply(
    val id: Long,
    var content: String,
    var createTime: String,
    var lastModifiedTime: String,
    val user: User,
    val postId: Long
)