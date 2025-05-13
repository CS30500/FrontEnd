package com.example.smartbottle.history.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HistoryScreen(
    viewmodel : HistoryViewModel = HistoryViewModel(),
    onNavigation : () -> Unit
){
    HistoryScreenCore(
        state = viewmodel.state,
        onAction = viewmodel::onAction,
        onNavigation
    )
}

@Composable
private fun HistoryScreenCore(
    state : HistoryState,
    onAction : (HistoryAction) -> Unit,
    onNavigation : () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "History Screen")
    }
}