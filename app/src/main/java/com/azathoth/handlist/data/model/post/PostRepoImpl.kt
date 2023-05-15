package com.azathoth.handlist.data.model.post

import com.azathoth.handlist.data.model.post.reply.Reply
import com.azathoth.handlist.data.remote.HandListApi

class PostRepoImpl(private val postApi: HandListApi) : PostRepo {
    override suspend fun findAll(): List<Post> = postApi.findAllPosts()

    override suspend fun insert(email: String, post: Post) = postApi.insertPost(email, post)

    override suspend fun update(postId: Long, post: Post) = postApi.updatePost(postId, post)

    override suspend fun deleteById(postId: Long) = postApi.deletePostById(postId)

    override suspend fun assignUser(postId: Long, reply: Reply) = postApi.assignUserForPost(postId, reply)

    override suspend fun freeUser(postId: Long, reply: Reply) = postApi.freeUserForPost(postId, reply)
}