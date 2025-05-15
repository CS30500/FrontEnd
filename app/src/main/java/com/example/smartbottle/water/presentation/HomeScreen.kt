package com.example.smartbottle.water.presentation

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.smartbottle.RunningService

@Composable
fun HomeScreen(
    onNavigation : ()->Unit
){
    // juyoung modify
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Home Screen")
        Button(
            onClick = onNavigation
        ) {
            Text("Go to Notification")
        }

        Button(
            onClick = {
                Intent(context, RunningService::class.java).also {
                    it.action = RunningService.Actions.START.toString()
                    context.startService(it)
                }
            }
        ){
            Text("Start")
        }

        Button(
            onClick = {
                Intent(context, RunningService::class.java).also {
                    it.action = RunningService.Actions.STOP.toString()
                    context.startService(it)
                }
            }
        ){
            Text("Stop")
        }
    }
}