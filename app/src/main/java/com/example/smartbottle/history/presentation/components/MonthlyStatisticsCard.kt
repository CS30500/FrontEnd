package com.example.smartbottle.history.presentation.components

import android.bluetooth.BluetoothProfile
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import com.example.smartbottle.core.presentation.ui.theme.*


@Composable
fun MonthlyStatisticsCard(percentList: List<Float>) {
    val levels = listOf(1.0f to "100%", 0.8f to "80%", 0.6f to "60%", 0.4f to "Less")
    val colors = listOf(
        Blue4, // 100%
        Blue3, // 80%
        Blue2, // 60%
        Blue1  // LESS
    )

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {

        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                colors.forEachIndexed { index, color ->
                    val validWeight = if (percentList[index] <= 0f) 0.0001f else percentList[index]
                    Box(
                        modifier = Modifier
                            .weight(validWeight)
                            .fillMaxHeight()
                            .background(color)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterHorizontally)
        ) {
            levels.forEachIndexed { index, (_, label) ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(colors[index], shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                    )
                }
            }
        }
    }
}
