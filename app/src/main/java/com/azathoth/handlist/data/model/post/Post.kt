package com.azathoth.handlist.data.model.post

import com.azathoth.handlist.data.model.post.reply.Reply
import com.azathoth.handlist.data.model.post.tag.PostTag
import com.azathoth.handlist.data.model.user.User


class Post(
    val id: Long,
    var title: String,
    var content: String,
    var createTime: String,
    var lastModifiedTime: String,
    var user: User,
    var replies: MutableList<Reply>,
    var tags: MutableList<PostTag>
) {

    fun addReply(reply: Reply) = this.replies.add(reply)

    fun rmReply(reply: Reply) = this.replies.remove(reply)

    fun addTag(tag: PostTag) = this.tags.add(tag)

    fun rmTag(tag: PostTag) = this.tags.remove(tag)
}

