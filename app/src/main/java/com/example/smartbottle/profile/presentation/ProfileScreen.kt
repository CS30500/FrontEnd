package com.example.smartbottle.profile.presentation

import androidx.compose.runtime.Composable
import com.example.smartbottle.profile.domain.Profile
import com.example.smartbottle.profile.presentation.components.TextFieldView
import com.example.smartbottle.profile.presentation.components.dashedBorder
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.Text
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import org.koin.androidx.compose.koinViewModel
import com.example.smartbottle.R
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun ProfileScreen(
    viewmodel: ProfileViewModel = koinViewModel(),
    onNavigation: () -> Unit
) {
    ProfileScreenCore(
        state = viewmodel.state,
        onAction = viewmodel::onAction,
        onNavigation = onNavigation
    )
}

@Composable
private fun ProfileScreenCore(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    onNavigation: () -> Unit
) {
    val profile = state.profile

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFFF8FAFC))
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 3.dp, bottom = 75.dp, start = 2.dp, end = 2.dp)
                    .height(39.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "9:30",
                    color = Color(0xFF171D1B),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 0.dp, top = 9.dp, end = 151.dp, bottom = 9.dp)
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .size(24.dp)
                        .background(Color(0xFF2E2E2E))
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            Column(modifier = Modifier.padding(bottom = 130.dp, start = 26.dp, end = 26.dp)) {
                ProfileCard(profile)
                NotificationCard(profile)
                PersonalInfoCard(profile)
            }

            BottomNavigationBar()
        }
    }
}

@Composable
fun ProfileCard(profile: Profile?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 20.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(52.dp).padding(end = 18.dp)
            )
            Column {
                Text(profile?.user_id ?: "Unknown", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF303030))
                Text("Samsung Health connected", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF939393))
            }
        }
        Row(Modifier.fillMaxWidth()) {
            StatColumn("Total days", "${profile?.totalDays ?: "--"}")
            StatColumn("Longest streak", "${profile?.longestStreak ?: "--"}")
            StatColumn("Hydration", "${profile?.hydration ?: "--"}%")
        }
    }
}

@Composable
fun StatColumn(title: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF939393),
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF303030)
        )
    }
}



@Composable
fun NotificationCard(profile: Profile?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(vertical = 20.dp, horizontal = 24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 23.dp)) {
            Text("Notification Settings", fontSize = 19.sp, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.weight(1f))
        }
        NotificationSettingRow("High temperature alert", "over 24°C")
        NotificationSettingRow("Reminder after last hydration", "2h 30m")
        NotificationSettingRow("Do not disturb", "1:00 ~ 9:00")
    }
}

@Composable
fun NotificationSettingRow(label: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 12.dp).fillMaxWidth()
    ) {
        Text(label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF595757), modifier = Modifier.weight(1f))
        Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF115AD8), textAlign = TextAlign.End, modifier = Modifier.weight(1f))
    }
}

@Composable
fun PersonalInfoCard(profile: Profile?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(vertical = 20.dp, horizontal = 23.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 23.dp)) {
            Text("Personal Info", fontSize = 19.sp, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.weight(1f))
        }
        TextFieldView(
            value = profile?.age?.toString() ?: "",
            onValueChange = {}, // TODO: Connect with ViewModel
            placeholder = "Enter age",
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp),
            modifier = Modifier
                .fillMaxWidth()
                .dashedBorder(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                .padding(bottom = 12.dp)
        )

        TextFieldView(
            value = profile?.sex?.toString() ?: "",
            onValueChange = {}, // TODO: Connect with ViewModel
            placeholder = "Select your gender",
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp),
            modifier = Modifier
                .fillMaxWidth()
                .dashedBorder(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                .padding(bottom = 12.dp)
        )

        TextFieldView(
            value = profile?.weight?.toString() ?: "",
            onValueChange = {}, // TODO: Connect with ViewModel
            placeholder = "Enter weight",
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp),
            modifier = Modifier
                .fillMaxWidth()
                .dashedBorder(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                .padding(bottom = 12.dp)
        )
        TextFieldView(
            value = profile?.height?.toString() ?: "",
            onValueChange = {}, // TODO: Connect with ViewModel
            placeholder = "Enter height",
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp),
            modifier = Modifier
                .fillMaxWidth()
                .dashedBorder(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                .padding(bottom = 12.dp)
        )
    }
}

@Composable
fun BottomNavigationBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 18.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            BottomNavItem("History")
            BottomNavItem("Home")
            BottomNavItem("Profile", active = true)
        }
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(12.dp))
                .width(108.dp)
                .height(4.dp)
                .background(Color(0xFF171D1B))
        )
    }
}

@Composable
fun BottomNavItem(label: String, active: Boolean = false) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(width = 24.dp, height = 24.dp)
                .border(if (active) 2.dp else 0.dp, Color(0xFF939393), RoundedCornerShape(4.dp))
        ) {}
        Text(label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (active) Color(0xFF457EDF) else Color(0xFF939393))
    }
}




@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val mockProfile = Profile(
        user_id = "ireum1234",
        age = 30,
        sex = "Female",
        weight = 56.2,
        height = 165.7,
        totalDays = 264,
        longestStreak = 32,
        hydration = 94
    )

    ProfileScreenCore(
        state = ProfileState(
            isLoading = false,
            isError = false,
            profile = mockProfile
        ),
        onAction = {},
        onNavigation = {}
    )
}
