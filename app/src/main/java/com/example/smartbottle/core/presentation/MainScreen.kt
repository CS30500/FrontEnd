package com.example.smartbottle.core.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smartbottle.auth.presentation.signup.RegisterScreen
import com.example.smartbottle.history.presentation.HistoryScreen
import com.example.smartbottle.notification.presentation.NotificationScreen
import com.example.smartbottle.profile.presentation.ProfileScreen
import com.example.smartbottle.water.presentation.HomeScreen
import kotlin.reflect.KClass

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()){

    val items = listOf(
        BottomNavigationItem(
            title = "History",
            route = Screen.HistoryScreen::class,
            selectedIcon = Icons.Filled.List,
            unselectedIcon = Icons.Outlined.List,
            hasNews = false,
        ),

        BottomNavigationItem(
            title = "Home",
            route = Screen.HomeScreen::class,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false,
        ),

        BottomNavigationItem(
            title = "Profile",
            route = Screen.ProfileScreen::class,
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            hasNews = false,
        ),

    )

    Scaffold(
        bottomBar = {

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val bottomBarDestination = items.any { it.route.qualifiedName== currentDestination?.route }

            if(bottomBarDestination) {
                NavigationBar() {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any {
                                it.route == item.route.qualifiedName
                            } == true,
                            onClick = {
                                item.route.qualifiedName?.let {
                                    navController.navigate(it) {
                                        popUpTo(navController.graph.findStartDestination().id )
                                        launchSingleTop = true
                                    }
                                }
                            },
                            label = {
                                Text(
                                    text = item.title,
                                )
                            },
                            icon = {

                                Icon(
                                    imageVector = if(index == 0) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        MainNavHost(modifier = Modifier.padding(innerPadding), navController = navController)
    }
}

@Composable
fun MainNavHost(modifier: Modifier, navController: NavHostController){
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Screen.HomeScreen,
    ){
        composable<Screen.HomeScreen>{
            HomeScreen(
                onNavigation = {navController.navigate(Screen.NotificationScreen)}
            )
        }

        composable<Screen.ProfileScreen>{
            ProfileScreen()
        }

        composable<Screen.HistoryScreen>{
            HistoryScreen(
                onNavigation = {navController.navigate(Screen.NotificationScreen)}
            )
        }

        composable<Screen.NotificationScreen>{
            NotificationScreen(
                onNavigation = {navController.navigate(Screen.HomeScreen)}
            )
        }



    }
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val route: KClass<out Any>,
)
