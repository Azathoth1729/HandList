package com.azathoth.handlist.data.model.post

import com.azathoth.handlist.data.model.post.reply.Reply
import com.azathoth.handlist.data.remote.HandListApi

class PostRepoImpl(private val postApi: HandListApi) : PostRepo {
    override suspend fun findAll(): List<Post> = postApi.findAll()

    override suspend fun insert(email: String, post: Post) = postApi.insert(email, post)

    override suspend fun update(postId: Long, post: Post) = postApi.update(postId, post)

    override suspend fun deleteById(postId: Long) = postApi.deleteById(postId)

    override suspend fun assignUser(postId: Long, reply: Reply) = postApi.assignUser(postId, reply)

    override suspend fun freeUser(postId: Long, reply: Reply) = postApi.freeUser(postId, reply)
}