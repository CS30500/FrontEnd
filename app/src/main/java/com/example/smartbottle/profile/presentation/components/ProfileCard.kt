// ProfileCard.kt
package com.example.smartbottle.profile.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbottle.R
import com.example.smartbottle.core.presentation.ui.theme.*
import com.example.smartbottle.profile.domain.Profile

@Composable
fun ProfileCard(profile: Profile?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 20.dp)
        ){
            Image(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(100.dp).padding(end = 20.dp)
            )
            Column {
                Text(profile?.user_id ?: "Unknown", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Black)
                Text("Samsung Health connected", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Gray2)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatColumn("Total days", "${profile?.totalDays ?: "--"}");
            StatColumn("Longest streak", "${profile?.longestStreak ?: "--"} Days");
            StatColumn("Hydration", "${profile?.hydration ?: "--"}%");
        }
    }
}

@Composable
fun StatColumn(title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Gray2,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Black
        )
    }
}

