package com.example.smartbottle.profile.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Black,
                    fontSize = 20.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isEditing) {

                    IconButton(onClick = onSave) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "Save",
                            tint = Blue4
                        )
                    }

                    /*TextButton(onClick = onSave) {
                        Text(
                            text = "Save",
                            color = Blue4,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(4.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }*/
                }
                else {
                    IconButton(onClick = onEditToggle) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = if (isEditing) "Cancel" else "Edit",
                            tint = Blue4
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isEditing) {
            editableContent()
        } else {
            readOnlyContent()
        }
    }
}
