package me.huteri.seekmax.features.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import me.huteri.seekmax.features.main.jobs.JobsScreen
import me.huteri.seekmax.features.main.tabs.ApplicationsScreen
import me.huteri.seekmax.features.main.myprofile.ProfileScreen
import me.huteri.seekmax.features.utils.NavigationUtils


@Composable
fun MainScreen(
    state: MainViewModel.MainState
) {

    Box(modifier = Modifier.fillMaxSize()) {

        if (state.navigateToLogin) {
            NavigationUtils.navigateToLogin(LocalContext.current)
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            MainContent()
        }

    }

}

@Composable
fun MainContent() {

    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = {
                Text("Seek Max")
            })
        },
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                tabList.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = MainTab.Jobs.route,
            Modifier.padding(innerPadding)
        ) {
            composable(MainTab.Jobs.route) { JobsScreen() }
            composable(MainTab.Applications.route) { ApplicationsScreen() }
            composable(MainTab.Profile.route) { ProfileScreen() }
        }
    }

}


val tabList = listOf(
    MainTab.Jobs,
    MainTab.Applications,
    MainTab.Profile
)

sealed class MainTab(val route: String, val title: String, val icon: ImageVector) {
    object Jobs : MainTab("jobs", "Jobs", Icons.Filled.List)
    object Applications : MainTab("applications", "My Applications", Icons.Filled.Person)
    object Profile : MainTab("profile", "My Profile", Icons.Filled.AccountCircle)
}

