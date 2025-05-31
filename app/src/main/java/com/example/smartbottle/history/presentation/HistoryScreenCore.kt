package com.example.smartbottle.history.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.smartbottle.core.presentation.ui.theme.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.smartbottle.history.presentation.components.CalendarWithProgress
import com.example.smartbottle.history.presentation.components.MonthlyStatisticsCard
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.draw.shadow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults


@Composable
fun HistoryScreenCore(
    state: HistoryState,
    onAction: (HistoryAction) -> Unit,
    onNavigation: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Background)
                .verticalScroll(rememberScrollState())
        ) {
            Column(modifier = Modifier.padding(horizontal = 28.dp, vertical = 32.dp)) {
                // Streak Text
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = MaterialTheme.typography.titleLarge.toSpanStyle().copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 28.sp,
                                color = Gray3
                            )) {
                            append("You are on a")
                        }
                        withStyle(
                            style = MaterialTheme.typography.displayLarge.toSpanStyle().copy(
                                fontWeight = FontWeight.Bold,
                                color = Blue4,
                                fontSize = 36.sp
                            )
                        ) {
                            append(" ${state.streakCount} day\n")
                        }
                        withStyle(
                            style = MaterialTheme.typography.titleLarge.toSpanStyle().copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 28.sp,
                                color = Gray3
                            )) {
                            append("hydration Streak!")
                        }
                    },
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                Text(
                    text = "Monthly records",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        color = Gray3
                    ),
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )



                // Calendar Box
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .shadow(
                            elevation = 6.dp,
                            shape = RoundedCornerShape(16.dp),
                            spotColor = Gray2
                        )
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    if (state.isLoading) {
                        Text("Loading")
                    } else if (state.isError) {
                        Text("Error")
                    } else {
                        CalendarWithProgress(onAction = onAction, state = state)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))



                Text(
                    text = "Monthly statistics",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        color = Gray3
                    ),
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )
                // Statistics Box
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .shadow(
                            elevation = 6.dp,
                            shape = RoundedCornerShape(16.dp),
                            spotColor = Gray2
                        )
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .padding(horizontal = 4.dp, vertical = 12.dp)
                ) {
                    if (state.isLoading) {
                        Text("Loading")
                    } else if (state.isError) {
                        Text("Error")
                    } else {
                        MonthlyStatisticsCard(percentList = state.monthStatistics)
                    }
                }
            }
        }
    }
}
