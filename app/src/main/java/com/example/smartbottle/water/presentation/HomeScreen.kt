package com.example.smartbottle.water.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
// juyoung modify
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.smartbottle.ble.BleManager

@Composable
fun HomeScreen(
    onNavigation : ()->Unit
){
    // juyoung modify
    val context = LocalContext.current
    val bleManager = remember { BleManager(context) }
    var bleData by remember { mutableStateOf("") }

    // BLE 데이터 수신 처리
    bleManager.setNotifyCallback { data ->
        bleData = data
        Log.d("BLE", "📡 수신된 BLE 데이터: $data")
    }

    // BLE 스캔 시작
    LaunchedEffect(Unit) {
        bleManager.startScan()
    }

    Column {
        Text("BLE 데이터: $bleData") // ✅ 화면에 보일 부분
    }
    // juyoung modify


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Home Screen")
        Text(text = "BLE 데이터: $bleData") // 수신 값 출력 (Juyoung Modify)
        Button(
            onClick = onNavigation
        ) {
            Text("Go to Notification")
        }
    }
}