package com.azathoth.handlist.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.azathoth.handlist.ui.components.BottomBar
import com.azathoth.handlist.ui.navigation.AppMainScreens
import com.azathoth.handlist.ui.navigation.HandListNavHost
import com.azathoth.handlist.ui.theme.HandListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HandListApp(navController: NavHostController = rememberNavController()) {
    HandListTheme {
        val currentBackStack by navController.currentBackStackEntryAsState()

        val curDest = currentBackStack?.destination

        val curScreen = AppMainScreens.find { it.route == curDest?.route }

        Scaffold(bottomBar = {
            if (curScreen != null) {
                BottomBar(
                    mainScreens = AppMainScreens,
                    currentScreen = curScreen,
                    onTabSelected = { screen ->
                        navController.navigate(screen.route)
                    })
            }
        }) { innerPadding ->
            HandListNavHost(
                navController = navController, modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
