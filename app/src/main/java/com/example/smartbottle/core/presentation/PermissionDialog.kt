package com.example.smartbottle.core.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Divider()
                Text(
                    text = if (isPermanentlyDeclined) {
                        "Grant permission"
                    } else{
                        "OK"
                    },
                    modifier = Modifier.fillMaxWidth().clickable {
                        if (isPermanentlyDeclined) {
                            onGoToAppSettingsClick()
                        } else {
                            onOkClick()
                        }
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
        },
        title = {
            Text(text = "Permission required")
        },
        text = {
            Text(
                text = permissionTextProvider.getDescription(
                    isPermanentlyDeclined = isPermanentlyDeclined
                )
            )
        },
        modifier = modifier
    )

}

interface PermissionTextProvider{
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

class BluetoothPermissionTextProvider: PermissionTextProvider{
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return  if(isPermanentlyDeclined) {
            "go to the settings"
        }else{
            "this app needs permission"
        }
    }

}

class LocationPermissionTextProvider: PermissionTextProvider{
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return  if(isPermanentlyDeclined) {
            "go to the settings"
        }else{
            "this app needs permission"
        }
    }

}

class ForegroundServicePermissionTextProvider: PermissionTextProvider{
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return  if(isPermanentlyDeclined) {
            "go to the settings"
        }else{
            "this app needs permission"
        }
    }

}

