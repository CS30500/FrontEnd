// File: HomeScreen.kt
package com.example.smartbottle.water.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoDelete
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartbottle.core.presentation.ui.theme.SmartBottleTheme
import com.example.smartbottle.water.domain.DailyHydration as DomainDailyHydration
import com.example.smartbottle.water.domain.Environment
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewmodel: HomeViewModel = koinViewModel(),
    onNavigation: () -> Unit
) {
    HomeScreenCore(
        state = viewmodel.state,
        onAction = viewmodel::onAction,
        onNavigation = onNavigation
    )
}

@Composable
private fun HomeScreenCore(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    onNavigation: () -> Unit
) {
    val ctx = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFFF8FAFC))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(16.dp),
                        clip = false
                    )
                    .fillMaxWidth()
                    .fillMaxHeight()
//                    .border(1.dp, Color(0x1A000000), RoundedCornerShape(16.dp))
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(horizontal = 24.dp, vertical = 24.dp)

            ) {
                DailyProgress(state)

                Button(
                    onClick = { onAction(HomeAction.ChangeWater)},
                    modifier = Modifier
                        .width(48.dp)
                        .height(24.dp)
                        .align(Alignment.BottomEnd),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF457EDF),
                        contentColor = Color.White
                    ),
                    shape = CircleShape,
                    contentPadding = PaddingValues(
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp
                    )
                ){
                    Icon(
                        Icons.Default.AutoDelete,
                        contentDescription = "Change Water",
                        modifier = Modifier.padding(start = 5.dp, end = 0.dp, top = 4.dp, bottom = 4.dp)
                    )
                }
            }


            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(16.dp),
                        clip = false
                    )
                    .fillMaxWidth()
//                    .border(1.dp, Color(0x1A000000), RoundedCornerShape(16.dp))
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(horizontal = 0.dp, vertical = 24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val env = state.dailyHydration?.environment
                    MetricCircle("OUTSIDE", "${env?.outsideTemp ?: "--"}°", Color(0xFFFF6A00))
                    MetricCircle("WATER", "${env?.waterTemp ?: "--"}°", Color(0xFF457EDF))
                    MetricCircle("HUMIDITY", "${env?.humidity ?: "--"}%", Color(0xFF8D66FF))
                }
            }

            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(16.dp),
                        clip = false
                    )
                    .fillMaxWidth()
//                    .border(1.dp, Color(0x1A000000), RoundedCornerShape(16.dp))
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(horizontal = 32.dp, vertical = 32.dp)
            ) {
                ReminderList(state.reminders)
            }
            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(16.dp),
                        clip = false
                    )
                    .fillMaxWidth()
//                    .border(1.dp, Color(0x1A000000), RoundedCornerShape(16.dp))
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(24.dp)
            ) {
                StartStopButtons(ctx)
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    SmartBottleTheme {
        HomeScreenCore(
            state = HomeState(
                dailyHydration = DomainDailyHydration(
                    date = "2022-12-25",
                    total_intake_ml = 1.87,
                    target_ml = 2.4,
                    environment = Environment(
                        outsideTemp = 21.2,
                        waterTemp = 12.2,
                        humidity = 67.5
                    )
                ),
                reminders = listOf(
                    Reminder("Regular reminder", "0.72L left · 4:46 PM", Color(0xFF457EDF)),
                    Reminder("Unsafe water alert", "2h 48m · 2:14 PM", Color(0xFFFF6A00)),
                    Reminder("Weather is hot today", "120mL/hr · 12:00 PM", Color(0xFFFF6A00))
                )
            ),
            onAction = {},
            onNavigation = {}
        )
    }
}
