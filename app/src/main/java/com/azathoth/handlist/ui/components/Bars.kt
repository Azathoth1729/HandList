package com.azathoth.handlist.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.azathoth.handlist.ui.navigation.AppMainScreens
import com.azathoth.handlist.ui.navigation.NavDest
import com.azathoth.handlist.ui.navigation.HomeDest
import com.azathoth.handlist.ui.navigation.IconNavDest
import com.azathoth.handlist.ui.theme.HandListTheme

/**
Bar at the bottom of the app's MainScreen to serve as a navigation bar
 */
@Composable
fun BottomBar(
    mainScreens: List<IconNavDest>,
    currentScreen: IconNavDest,
    onTabSelected: (IconNavDest) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        mainScreens.forEach { screen ->
            NavigationBarItem(icon = { Icon(screen.icon, contentDescription = screen.route) },
                label = null,
                selected = currentScreen == screen,
                onClick = { onTabSelected(screen) })
        }
    }
}

/**
Bar at the top of the app's MainScreen to display the user management and general navigation screen navigator
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
    onNavClick: () -> Unit = { },
    onActions: () -> Unit = { },
) {
    CenterAlignedTopAppBar(
        title = {
            Text("HeadList")
        },
        navigationIcon = {
            IconButton(onClick = onNavClick) {
                Icon(
                    imageVector = Icons.Filled.Menu, contentDescription = "Top Left Navigation"
                )
            }
        },
        actions = {
            IconButton(onClick = onActions) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "User management Navigation"
                )
            }
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTopBar(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = { },
    onMore: () -> Unit = { },
) {
    CenterAlignedTopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    imageVector = Icons.Filled.ChevronLeft,
                    contentDescription = "Back to previous"
                )
            }
        },
        actions = {
            IconButton(onClick = onMore) {
                Icon(
                    imageVector = Icons.Filled.MoreHoriz,
                    contentDescription = "More operation to this task"
                )
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    HandListTheme {
        MainTopBar()
    }
}

@Preview(showBackground = true)
@Composable
fun EditTopBarPreview() {
    HandListTheme {
        EditTopBar()
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    var currentNavDest: IconNavDest by remember { mutableStateOf(HomeDest) }

    HandListTheme {
        BottomBar(mainScreens = AppMainScreens,
            currentScreen = currentNavDest,
            onTabSelected = { screen ->
                currentNavDest = screen
            })
    }
}