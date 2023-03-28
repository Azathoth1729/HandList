package com.azathoth.handlist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.azathoth.handlist.ui.screens.EditTaskScreen
import com.azathoth.handlist.ui.screens.HomeScreen
import com.azathoth.handlist.ui.screens.NewTaskScreen
import com.azathoth.handlist.ui.screens.SpacesScreen

@Composable
fun HandListNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDest.route,
        modifier = modifier
    ) {
        composable(route = HomeDest.route) {
            HomeScreen(navigateToEditTask = {
                navController.navigate("${TaskEntryDest.route}/${it}")
            })
        }
        composable(route = SpaceDest.route) {
            SpacesScreen()
        }
        composable(route = NewDest.route) {
            NewTaskScreen(navigateBack = { navController.popBackStack() })
        }
        composable(
            route = TaskEntryDest.routeWithArgs,
            arguments = listOf(
                navArgument(TaskEntryDest.taskIdArg) { type = NavType.IntType }
            )
        ) {
            EditTaskScreen(
                navigateBack = { navController.popBackStack() },

                )
        }
    }
}