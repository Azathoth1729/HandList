package com.azathoth.handlist.data.model.post

import com.azathoth.handlist.data.model.post.reply.Reply


interface PostRepo {
    suspend fun findAll(): List<Post>

    suspend fun insert(email: String, post: Post)

    suspend fun update(postId: Long, post: Post)

    suspend fun deleteById(postId: Long)

    // assign a reply to a post
    suspend fun assignUser(postId: Long, reply: Reply)

    // remove a reply from a post
    suspend fun freeUser(postId: Long, reply: Reply)

}