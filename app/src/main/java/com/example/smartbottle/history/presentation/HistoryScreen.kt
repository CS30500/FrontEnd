package com.example.smartbottle.history.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartbottle.core.presentation.ui.theme.SmartBottleTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreen(
    viewmodel : HistoryViewModel = koinViewModel(),
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
        val firstItem = state.historyList.firstOrNull()
        if (firstItem == null) {
            Text("데이터가 없습니다.")
        } else {
            // 정상적으로 가져온 firstItem에 대한 UI 처리
            Text("시간: ${firstItem.date}")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryScreenPreview(){
    SmartBottleTheme {
        HistoryScreenCore(
            state = HistoryState(),
            onAction = {},
            onNavigation = {}
        )
    }
}