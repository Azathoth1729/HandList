package com.azathoth.handlist.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.azathoth.handlist.common.AppConfig.BASE_URL
import com.azathoth.handlist.data.model.post.PostRepo
import com.azathoth.handlist.data.model.post.PostRepoImpl
import com.azathoth.handlist.data.model.spacenode.SpaceNodeRepo
import com.azathoth.handlist.data.model.spacenode.SpaceNodeRepoImpl
import com.azathoth.handlist.data.model.task.TaskRepo
import com.azathoth.handlist.data.model.task.TasksRepoImpl
import com.azathoth.handlist.data.model.user.UserRepo
import com.azathoth.handlist.data.model.user.UserRepoImpl
import com.azathoth.handlist.data.model.user.auth.AuthRepo
import com.azathoth.handlist.data.model.user.auth.AuthRepoImpl
import com.azathoth.handlist.data.remote.HandListApi
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

    @Provides
    @Singleton
    fun providePostRepo(handlistApi: HandListApi): PostRepo =
        PostRepoImpl(handlistApi)

    @Provides
    @Singleton
    fun provideAuthRepo(handlistApi: HandListApi, prefs: SharedPreferences): AuthRepo =
        AuthRepoImpl(handlistApi, prefs)


    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences =
        app.getSharedPreferences("prefs", Context.MODE_PRIVATE)
}