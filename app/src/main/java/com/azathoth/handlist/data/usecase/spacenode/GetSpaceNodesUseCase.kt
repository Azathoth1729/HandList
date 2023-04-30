package com.azathoth.handlist.data.usecase.spacenode

import com.azathoth.handlist.common.Resource
import com.azathoth.handlist.data.model.spacenode.SpaceNode
import com.azathoth.handlist.data.model.spacenode.SpaceNodeRepo
import com.azathoth.handlist.data.model.spacenode.SpaceNodeUiState
import com.azathoth.handlist.data.model.spacenode.toUiState

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSpaceNodesUseCase @Inject constructor(
    private val repo: SpaceNodeRepo
) {
    operator fun invoke(): Flow<Resource<List<SpaceNodeUiState>>> = flow {
        try {
            emit(Resource.Loading())
            val nodes = repo.getAllNodes().map(SpaceNode::toUiState)
            emit(Resource.Success(nodes))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}