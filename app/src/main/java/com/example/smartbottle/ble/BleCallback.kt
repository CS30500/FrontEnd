package com.example.smartbottle.ble

interface BleCallback {

    // 아두이노에서 수신한 센서 데이터 처리
    fun onBleDataReceived(data: String)

    // 연결 상태 변화 처리
    fun onBleConnected()
    fun onBleDisconnected()
}
