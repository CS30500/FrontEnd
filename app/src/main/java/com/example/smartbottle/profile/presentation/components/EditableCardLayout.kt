package com.example.smartbottle.profile.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.smartbottle.core.presentation.ui.theme.*


@Composable
fun EditableCardLayout(
    title: String,
    isEditing: Boolean,
    onEditToggle: () -> Unit,
    onSave: () -> Unit,
    readOnlyContent: @Composable () -> Unit,
    editableContent: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Black,
                    fontSize = 20.sp
                )
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isEditing) {
                    TextButton(onClick = onSave) {
                        Text("Save")
                    }
                }
                IconButton(onClick = onEditToggle) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = if (isEditing) "Cancel" else "Edit"
                    )
                }
            }
        }

        if (isEditing) {
            editableContent()
        } else {
            readOnlyContent()
        }
    }
}
