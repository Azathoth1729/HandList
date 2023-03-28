package com.azathoth.handlist.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

interface NavDest {
    val route: String
}

interface IconNavDest : NavDest {
    val icon: ImageVector

}