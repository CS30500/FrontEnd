package com.example.smartbottle

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.smartbottle.core.data.remote.BleManager


class RunningService :  Service() {

    private lateinit var bleManager: BleManager

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        bleManager = BleManager(this)

        bleManager.startScan()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(){
        val notification = NotificationCompat.Builder(this, "running_channel")
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Smart Bottle")
            .setContentText("Retrieving SmartBottle data")
            .build()

        startForeground(1, notification)
    }

    override fun onDestroy() {
        bleManager.disconnect()
        super.onDestroy()

    }


    enum class Actions{
        START, STOP
    }
}