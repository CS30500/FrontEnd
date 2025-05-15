package com.example.smartbottle.core.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeAt(visiblePermissionDialogQueue.lastIndex)
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ){
        if (!isGranted) {
            visiblePermissionDialogQueue.add(0, permission)
        }
    }
}