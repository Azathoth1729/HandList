/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.azathoth.handlist.data

import android.content.Context
import com.azathoth.handlist.data.task.HandListDatabase
import com.azathoth.handlist.data.task.OfflineTasksRepository
import com.azathoth.handlist.data.task.TasksRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val tasksRepository: TasksRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val tasksRepository: TasksRepository by lazy {
        OfflineTasksRepository(HandListDatabase.getDatabase(context).itemDao())
    }
}