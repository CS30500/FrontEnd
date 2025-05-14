package com.example.smartbottle.ble
import java.util.UUID

object BleConstants {
    // 로그 태그
    const val TAG = "BLE"

    // HM-10 UUID (Service & Characteristic)
    val SERVICE_UUID: UUID = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb")
    val CHARACTERISTIC_UUID: UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")

    // Descriptor for enabling notifications
    val CLIENT_CHARACTERISTIC_CONFIG_UUID: UUID =
        UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")

    // 스캔 타임아웃 (ms)
    const val SCAN_TIMEOUT: Long = 10000

    // 기기 이름 필터 (기본: HM-10 모듈은 "HMSoft")
    const val DEVICE_NAME_PREFIX = "HMSoft"
}
