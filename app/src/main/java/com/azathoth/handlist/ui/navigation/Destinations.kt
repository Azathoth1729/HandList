package com.azathoth.handlist.ui.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

interface NavDest {
    val route: String
}

interface IconNavDest : NavDest {
    val icon: ImageVector
}

object AuthDest : NavDest {
    override val route: String = "Auth"
}

object HomeDest : IconNavDest {
    override val icon = Icons.Default.Menu
    override val route = "Home"
}

object SpaceDest : IconNavDest {
    override val icon = Icons.Default.Apps
    override val route = "Spaces"
}

object NewTaskDest : IconNavDest {
    override val icon = Icons.Default.AddCircle
    override val route = "New"
}

object PostDest : IconNavDest {
    override val icon = Icons.Default.Chat
    override val route = "Post"
}


object TasksOfNodeDest : NavDest {
    override val route: String = "TasksOfNode"
    const val nodeIdArg = "nodeId"
    val routeWithArgs = "$route/{$nodeIdArg}"
}

object TaskEntryDest : NavDest {
    override val route = "task_entry"
    const val taskIdArg = "taskId"
    val routeWithArgs = "$route/{$taskIdArg}"
}

val AppMainScreens = listOf(HomeDest, SpaceDest, PostDest, NewTaskDest)