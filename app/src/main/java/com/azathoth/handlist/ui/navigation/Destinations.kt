package com.azathoth.handlist.ui.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Menu

object AuthDest: NavDest {
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

object NewDest : IconNavDest {
    override val icon = Icons.Default.AddCircle
    override val route = "New"
}

object TaskEntryDest : NavDest {
    override val route = "task_entry"
    const val taskIdArg = "taskId"
    val routeWithArgs = "$route/{$taskIdArg}"
}

val AppMainScreens = listOf(HomeDest, SpaceDest, NewDest)