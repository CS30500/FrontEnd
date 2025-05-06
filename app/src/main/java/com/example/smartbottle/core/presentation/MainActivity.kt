package com.example.smartbottle.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.smartbottle.auth.presentation.login.LoginScreen
import com.example.smartbottle.auth.presentation.signup.RegisterScreen
import com.example.smartbottle.core.presentation.ui.theme.SmartBottleTheme
import com.example.smartbottle.history.presentation.HistoryScreen
import com.example.smartbottle.profile.presentation.ProfileScreen
import com.example.smartbottle.water.presentation.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartBottleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Navigation(
                        modifier = Modifier
                    )
                }

            }
        }
    }
}


@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = SubGraph.Auth
    ){
        navigation<SubGraph.Auth>(
            startDestination = Screen.LoginScreen,
        ){
            composable<Screen.LoginScreen>{
                LoginScreen(
                    onNavigation = {
                        navController.navigate(SubGraph.Main)
                    }
                )
            }
            composable<Screen.RegisterScreen>{
                RegisterScreen()
            }
        }

        composable<SubGraph.Main>{
            MainScreen()
        }


    }
}



