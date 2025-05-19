package com.example.smartbottle.water.presentation

import android.content.Intent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartbottle.RunningService
import com.example.smartbottle.core.presentation.ui.theme.SmartBottleTheme
import com.example.smartbottle.history.presentation.HistoryAction
import com.example.smartbottle.water.domain.DailyHydration
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewmodel : HomeViewModel = koinViewModel(),
    onNavigation : ()->Unit
){
   HomeScreenCore(
       state = viewmodel.state,
       onAction = viewmodel::onAction,
       onNavigation
   )
}


@Composable
private fun HomeScreenCore(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    onNavigation: () -> Unit
){
    // juyoung modify
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        DailyProgress(state)

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

@Composable
fun DailyProgress(state:HomeState) {
    Box(
        modifier = Modifier
            .fillMaxWidth().padding(64.dp),
        contentAlignment = Alignment.Center,
    ) {

        val progress = state.dailyHydration?.total_intake_ml?.div(state.dailyHydration.target_ml)
            ?.toFloat()
            ?: 0f

        Canvas(modifier = Modifier.size(250.dp)) {
            val stroke = Stroke(width = 32.dp.toPx(), cap = StrokeCap.Round)

            drawCircle(
                color = Color.LightGray
                ,
                style = Stroke(width = 32.dp.toPx(), cap = StrokeCap.Round),
            )
            drawCircle(
                color = Color.White,
                style = Stroke(width = 28.dp.toPx(), cap = StrokeCap.Round),
            )
            drawArc(
                color = Color(0xFF3B82F6), // blue
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = stroke
            )
        }

        if(state.isError){
            Text("Error")
        } else if(state.isLoading){
            Text("Loading")
        } else {
            Text(
                text = "${state.dailyHydration?.total_intake_ml} L",
                style = MaterialTheme.typography.displayLarge.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Normal
                )
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    SmartBottleTheme {
        HomeScreenCore(
            state = HomeState(
                dailyHydration = DailyHydration(
                    date = "2022-12-25" ,
                    total_intake_ml = 1.87,
                    target_ml = 2.4,
                )
            ),
            onAction = {},
            onNavigation = {}
        )
    }
}