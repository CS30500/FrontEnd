// PersonalInfoCard.kt
package com.example.smartbottle.profile.presentation.components

import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.smartbottle.profile.domain.Profile
import androidx.compose.foundation.layout.*

@Composable
fun PersonalInfoCard(profile: Profile?) {
    var isEditing by remember { mutableStateOf(false) }

    EditableCardLayout(
        title = "Personal Info",
        isEditing = isEditing,
        onEditToggle = { isEditing = !isEditing },
        onSave = { isEditing = false },
        readOnlyContent = {
            PersonalInfoSettingRow("Age", "${profile?.age ?: "--"}")
            PersonalInfoSettingRow("Gender", profile?.sex ?: "--")
            PersonalInfoSettingRow("Weight", "${profile?.weight ?: "--"} kg")
            PersonalInfoSettingRow("Height", "${profile?.height ?: "--"} cm")
        },
        editableContent = {
            TextFieldView(
                value = profile?.age?.toString() ?: "",
                onValueChange = { },
                placeholder = "Enter age",
                textStyle = TextStyle(fontSize = 12.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .dashedBorder(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(bottom = 12.dp)
            )
            TextFieldView(
                value = profile?.sex ?: "",
                onValueChange = { },
                placeholder = "Select your gender",
                textStyle = TextStyle(fontSize = 12.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .dashedBorder(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(bottom = 12.dp)
            )
            TextFieldView(
                value = profile?.weight?.toString() ?: "",
                onValueChange = { },
                placeholder = "Enter weight",
                textStyle = TextStyle(fontSize = 12.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .dashedBorder(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(bottom = 12.dp)
            )
            TextFieldView(
                value = profile?.height?.toString() ?: "",
                onValueChange = { },
                placeholder = "Enter height",
                textStyle = TextStyle(fontSize = 12.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .dashedBorder(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(bottom = 12.dp)
            )
        }
    )
}
