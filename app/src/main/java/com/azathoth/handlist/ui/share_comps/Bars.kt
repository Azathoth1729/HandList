package com.azathoth.handlist.ui.share_comps

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.azathoth.handlist.R
import com.azathoth.handlist.ui.navigation.AppMainScreens
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
    navigateToProfile: () -> Unit = { },
) {
    CenterAlignedTopAppBar(
        title = {
            Text(stringResource(R.string.app_name))
        },
        navigationIcon = {
            IconButton(onClick = onNavClick) {
                Icon(
                    imageVector = Icons.Filled.Menu, contentDescription = "Top Left Navigation"
                )
            }
        },
        actions = {
            IconButton(onClick = navigateToProfile) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "User Profile Navigation"
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
    onEdit: (() -> Unit)? = null,
    onDel: () -> Unit = { }
) {

    var expanded by remember { mutableStateOf(false) }

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
            if (onEdit != null) {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Filled.MoreHoriz,
                        contentDescription = "More operation to this task"
                    )
                    DropdownMenu(expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Edit") },
                            onClick = onEdit,
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Edit,
                                    contentDescription = "edit node"
                                )
                            }
                        )
                    }
                    DropdownMenu(expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Delete") },
                            onClick = onDel,
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Edit,
                                    contentDescription = "delete node"
                                )
                            })
                    }
                }
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

@Preview(showBackground = true)
@Composable
fun EditTopBarPreview() {
    HandListTheme {
        EditTopBar()
    }
}