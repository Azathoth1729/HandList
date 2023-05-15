package com.azathoth.handlist.ui.screens.post

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azathoth.handlist.common.Resource
import com.azathoth.handlist.data.model.post.PostRepo
import com.azathoth.handlist.data.model.post.PostUiState
import com.azathoth.handlist.data.model.post.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostListState(
    val isLoading: Boolean = false,
    val posts: List<PostUiState> = emptyList(),
    val error: String = ""
)

@HiltViewModel
class PostVM @Inject constructor(private val repo: PostRepo) : ViewModel() {
    var posts by mutableStateOf(PostListState())
        private set

    init {
        allPostsFlow().onEach {
            posts = when (it) {
                is Resource.Success -> {
                    PostListState(posts = it.data ?: emptyList())
                }

                is Resource.Error -> {
                    PostListState(error = it.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    PostListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun allPostsFlow(): Flow<Resource<List<PostUiState>>> = flow {
        try {
            emit(Resource.Loading())
            val posts = repo.findAll().map { it.toUiState() }
            emit(Resource.Success(posts))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server.\nCheck your internet connection."))
        }
    }
}