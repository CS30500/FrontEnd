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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*


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
                ProfileCardWithStyle(profile)
                Spacer(modifier = Modifier.height(16.dp))
                NotificationCardWithStyle(profile)
                Spacer(modifier = Modifier.height(16.dp))
                PersonalInfoCardWithStyle(profile)
            }


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
            .padding(16.dp)
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
            StatColumn("Total days", "${profile?.totalDays ?: "--"}", Modifier.weight(1f))
            StatColumn("Longest streak", "${profile?.longestStreak ?: "--"}", Modifier.weight(1f))
            StatColumn("Hydration", "${profile?.hydration ?: "--"}%", Modifier.weight(1f))
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
fun ProfileCardWithStyle(profile: Profile?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0x1A000000), RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        ProfileCard(profile)
    }
}

@Composable
fun NotificationCardWithStyle(profile: Profile?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0x1A000000), RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(horizontal = 32.dp, vertical = 20.dp) //
    ) {
        NotificationCard(profile)
    }
}




@Composable
fun PersonalInfoCardWithStyle(profile: Profile?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0x1A000000), RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(horizontal = 32.dp, vertical = 20.dp)
    ) {
        PersonalInfoCard(profile)
    }
}

@Composable
fun PersonalInfoCard(profile: Profile?) {
    var isEditing by remember { mutableStateOf(false) }

    Column {
        // Header row with title and edit icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Personal Info",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            IconButton(onClick = { isEditing = !isEditing }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Modify")
            }
        }


        if (isEditing) {
            // EDIT MODE: show TextFieldView inputs
            TextFieldView(
                value = profile?.age?.toString() ?: "",
                onValueChange = { /* TODO: update ViewModel */ },
                placeholder = "Enter age",
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .dashedBorder(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(bottom = 12.dp)
            )
            TextFieldView(
                value = profile?.sex ?: "",
                onValueChange = { /* TODO */ },
                placeholder = "Select your gender",
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .dashedBorder(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(bottom = 12.dp)
            )
            TextFieldView(
                value = profile?.weight?.toString() ?: "",
                onValueChange = { /* TODO */ },
                placeholder = "Enter weight",
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .dashedBorder(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(bottom = 12.dp)
            )
            TextFieldView(
                value = profile?.height?.toString() ?: "",
                onValueChange = { /* TODO */ },
                placeholder = "Enter height",
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .dashedBorder(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(bottom = 12.dp)
            )
        } else {
            // VIEW MODE: show plain text rows like NotificationSettingRow
            PersonalInfoRow("Age", "${profile?.age ?: "--"}")
            PersonalInfoRow("Gender", profile?.sex ?: "--")
            PersonalInfoRow("Weight", "${profile?.weight ?: "--"} kg")
            PersonalInfoRow("Height", "${profile?.height ?: "--"} cm")
        }
    }
}

@Composable
fun PersonalInfoRow(label: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(bottom = 12.dp)
            .fillMaxWidth()
    ) {
        Text(
            label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF595757),
            modifier = Modifier.weight(2f)
        )
        Text(
            value,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF115AD8),
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}

fun formatTime(military: Int): String {
    val hour = military / 100
    val minute = military % 100
    return "%d:%02d".format(hour, minute)
}
@Composable
fun NotificationCard(profile: Profile?) {
    var isEditing by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
    ) {
        // Title + edit button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Notification Settings",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            IconButton(onClick = { isEditing = !isEditing }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Modify Notification Settings"
                )
            }
        }

        if (isEditing) {
            NotificationSettingRow("High temperature alert", "Edit mode")
            NotificationSettingRow("Reminder after last hydration", "Edit mode")
            NotificationSettingRow("Do not disturb", "Edit mode")
        } else {
            NotificationSettingRow(
                "High temperature alert",
                profile?.alertTemperature?.let { "over ${it.toInt()}°C" } ?: "--"
            )

            NotificationSettingRow(
                "Reminder after last hydration",
                profile?.hydrationReminder?.let { "${it / 60}h ${it % 60}m" } ?: "--"
            )

            NotificationSettingRow(
                "Do not disturb",
                if (profile?.dndStart != null && profile.dndEnd != null)
                    "${formatTime(profile.dndStart)} ~ ${formatTime(profile.dndEnd)}"
                else "--"
            )
        }
    }
}



@Composable
fun NotificationSettingRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF595757),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF115AD8),
            textAlign = TextAlign.End,
            modifier = Modifier
                .wrapContentWidth()
        )
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
        hydration = 94,
        alertTemperature = 24.0,
        hydrationReminder = 150,
        dndStart = 1300,
        dndEnd = 2100
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

@Composable
fun ProfileTextField(placeholder: String, value: String) {
    TextFieldView(
        value = value,
        onValueChange = {}, // You can connect this to ViewModel if needed
        placeholder = placeholder,
        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp),
        modifier = Modifier
            .fillMaxWidth()
            .dashedBorder(color = Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(bottom = 12.dp)
    )
}