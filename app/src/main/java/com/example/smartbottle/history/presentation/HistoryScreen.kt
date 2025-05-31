// File: HistoryScreen.kt
package com.example.smartbottle.history.presentation

import androidx.compose.runtime.Composable
import com.example.smartbottle.history.presentation.components.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreen(
    viewmodel: HistoryViewModel = koinViewModel(),
    onNavigation: () -> Unit
) {
    HistoryScreenCore(
        state = viewmodel.state,
        onAction = viewmodel::onAction,
        onNavigation = onNavigation
    )
}
