package com.example.smartbottle.core.presentation

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object LoginScreen : Screen

    @Serializable
    data object RegisterScreen : Screen

    @Serializable
    data object HomeScreen : Screen

    @Serializable
    data object HistoryScreen : Screen

    @Serializable
    data object ProfileScreen : Screen

    @Serializable
    data object NotificationScreen : Screen


}

sealed interface SubGraph {
    @Serializable
    data object Auth : SubGraph

    @Serializable
    data object Main : SubGraph

}