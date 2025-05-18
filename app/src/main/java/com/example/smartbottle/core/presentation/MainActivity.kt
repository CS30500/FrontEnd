package com.example.smartbottle.core.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.smartbottle.auth.presentation.login.LoginScreen
import com.example.smartbottle.auth.presentation.signup.RegisterScreen
import com.example.smartbottle.core.presentation.ui.theme.SmartBottleTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartBottleTheme(
                dynamicColor = false,
                darkTheme = false,
            ) {

                val viewModel = viewModel<MainViewModel>()
                val dialogQueue = viewModel.visiblePermissionDialogQueue

                val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { perms ->
                        perms.keys.forEach { permission ->
                            viewModel.onPermissionResult(
                                permission = permission,
                                isGranted = perms[permission] == true
                            )
                        }
                    }
                )

                LaunchedEffect(key1 = true) {
                    multiplePermissionResultLauncher.launch(
                        arrayOf(
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.FOREGROUND_SERVICE,
                        Manifest.permission.FOREGROUND_SERVICE_LOCATION,
                        Manifest.permission.FOREGROUND_SERVICE_CONNECTED_DEVICE,
                        Manifest.permission.POST_NOTIFICATIONS,
                        )
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White,
                ) {

                    Navigation(
                        modifier = Modifier
                    )
                }

                dialogQueue
                    .reversed()
                    .forEach { permission ->
                    PermissionDialog(
                        permissionTextProvider = LocationPermissionTextProvider(),
                        isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                            permission,
                        ),
                        onDismiss = viewModel::dismissDialog,
                        onOkClick = {
                            viewModel.dismissDialog()
                            multiplePermissionResultLauncher.launch(
                                arrayOf(permission)
                            )
                        },
                        onGoToAppSettingsClick = ::openAppSettings
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
                        navController.navigate(SubGraph.Main) {
                            popUpTo(Screen.LoginScreen) {
                                inclusive = true
                            }
                        }
                    },
                    onRegister = {
                        navController.navigate(Screen.RegisterScreen)
                    }
                )
            }
            composable<Screen.RegisterScreen>{
                RegisterScreen(
                    onNavigation = {
                        navController.navigate(Screen.LoginScreen)
                    }
                )
            }
        }

        composable<SubGraph.Main>{
            MainScreen()
        }


    }
}

fun Activity.openAppSettings(){
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}


