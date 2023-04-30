package com.azathoth.handlist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.azathoth.handlist.ui.screens.auth.AuthScreen
import com.azathoth.handlist.ui.screens.home.HomeScreen
import com.azathoth.handlist.ui.screens.spacenode.SpacesScreen
import com.azathoth.handlist.ui.screens.spacenode.TasksOfNodeScreen
import com.azathoth.handlist.ui.screens.task.EditTaskScreen
import com.azathoth.handlist.ui.screens.task.NewTaskScreen

@Composable
fun HandListNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = AuthDest.route,
        modifier = modifier
    ) {
        composable(route = AuthDest.route) {
            AuthScreen(navigateToHome = {
                navController.navigate(HomeDest.route) {
                    popUpTo(HomeDest.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(route = HomeDest.route) {
            HomeScreen(navigateToEditTask = {
                navController.navigate("${TaskEntryDest.route}/${it}")
            })
        }

        composable(route = SpaceDest.route) {
            SpacesScreen(navigateToTasksOfNode = {
                navController.navigate("${TasksOfNodeDest.route}/${it}")
            })
        }

        composable(
            route = TasksOfNodeDest.routeWithArgs,
            arguments = listOf(
                navArgument(TasksOfNodeDest.nodeIdArg) { type = NavType.LongType }
            )
        ) {
            TasksOfNodeScreen(
                navigateBack = { navController.popBackStack() },
                navigateToTasksOfNode = { navController.navigate("${TaskEntryDest.route}/${it}") })
        }

        composable(route = NewTaskDest.route) {
            NewTaskScreen(navigateBack = { navController.popBackStack() })
        }

        composable(
            route = TaskEntryDest.routeWithArgs,
            arguments = listOf(
                navArgument(TaskEntryDest.taskIdArg) { type = NavType.IntType }
            )
        ) {
            EditTaskScreen(navigateBack = { navController.popBackStack() })
        }


    }
}