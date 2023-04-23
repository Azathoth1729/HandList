package com.azathoth.handlist.di

import com.azathoth.handlist.common.AppConfig.BASE_URL
import com.azathoth.handlist.data.remote.HandListApi
import com.azathoth.handlist.data.model.spacenode.SpaceNodeRepo
import com.azathoth.handlist.data.model.spacenode.SpaceNodeRepoImpl
import com.azathoth.handlist.data.model.task.TaskRepo
import com.azathoth.handlist.data.model.task.TasksRepoImpl
import com.azathoth.handlist.data.model.user.UserRepo
import com.azathoth.handlist.data.model.user.UserRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideHandListApi(): HandListApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HandListApi::class.java)

    @Provides
    @Singleton
    fun provideTaskRepo(handlistApi: HandListApi): TaskRepo =
        TasksRepoImpl(handlistApi)

    @Provides
    @Singleton
    fun provideNodeRepo(handlistApi: HandListApi): SpaceNodeRepo =
        SpaceNodeRepoImpl(handlistApi)

    @Provides
    @Singleton
    fun provideUserRepo(handlistApi: HandListApi): UserRepo =
        UserRepoImpl(handlistApi)
}