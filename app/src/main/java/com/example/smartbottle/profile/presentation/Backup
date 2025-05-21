package com.example.smartbottle.profile.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbottle.R
import com.example.smartbottle.core.presentation.ui.theme.SmartBottleTheme
import com.example.smartbottle.profile.domain.Profile
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    viewmodel : ProfileViewModel = koinViewModel(),
    onNavigation : () -> Unit
){
    ProfileScreenCore(
        state = viewmodel.state,
        onAction = viewmodel::onAction,
        onNavigation
    )
}

@Composable
private fun ProfileScreenCore(
    state : ProfileState,
    onAction : (ProfileAction) -> Unit,
    onNavigation : () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Box(
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                painter = painterResource(id = R.drawable.group_7184),
                contentDescription = "profile"
            )

            Column(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "profile",
                    modifier = Modifier.clip(CircleShape)
                )

                Text(
                    text = if (state.isLoading) "Loading..." else state.profile?.user_id ?: "No data",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.padding(16.dp)
                )


            }



        }



    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryScreenPreview(){
    SmartBottleTheme {
        ProfileScreenCore(
            state = ProfileState(isLoading = false, isError = false, profile = Profile(user_id = "test")),
            onAction = {},
            onNavigation = {}
        )
    }
}