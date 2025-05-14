package com.example.smartbottle.ble

import android.bluetooth.*
import android.bluetooth.le.*
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.util.*

class BleManager(private val context: Context) {

    companion object {
        private const val TAG = "BleManager"

        // HM-10 일반 UUID (실제 장치에 따라 다를 수 있음)
        val SERVICE_UUID: UUID = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb")
        val CHARACTERISTIC_UUID: UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")
    }

    private val bluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

    private var bluetoothGatt: BluetoothGatt? = null
    private var notifyCallback: ((String) -> Unit)? = null

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            result?.device?.let { device ->
                Log.d(TAG, "Found device: ${device.name}, address: ${device.address}")
                if (device.name?.contains("HMSoft") == true) {
                    stopScan()
                    connectToDevice(device)
                }
            }
        }
    }

    fun startScan() {
        val scanner = bluetoothAdapter?.bluetoothLeScanner
        val scanFilter = ScanFilter.Builder().build()
        val settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .build()

        scanner?.startScan(listOf(scanFilter), settings, scanCallback)
        Log.d(TAG, "Started BLE scan")

        // 자동 종료 타이머 (10초 후 종료)
        Handler(Looper.getMainLooper()).postDelayed({
            stopScan()
        }, 10000)
    }

    fun sendCommand(command: String) {
        val service = bluetoothGatt?.getService(SERVICE_UUID)
        val characteristic = service?.getCharacteristic(CHARACTERISTIC_UUID)
        if (characteristic != null) {
            characteristic.writeType = BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
            characteristic.value = "$command\n".toByteArray(Charsets.UTF_8)
            val success = bluetoothGatt?.writeCharacteristic(characteristic) ?: false
            Log.d(TAG, "Command '$command' sent: $success")
        } else {
            Log.e(TAG, "Cannot send command: characteristic is null")
        }
    }


    private fun stopScan() {
        bluetoothAdapter?.bluetoothLeScanner?.stopScan(scanCallback)
        Log.d(TAG, "Stopped BLE scan")
    }

    private fun connectToDevice(device: BluetoothDevice) {
        bluetoothGatt = device.connectGatt(context, false, gattCallback)
        Log.d(TAG, "Connecting to ${device.address}")
    }

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.d(TAG, "Connected to GATT server")
                gatt?.discoverServices()
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.d(TAG, "Disconnected from GATT server")
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            val characteristic = gatt
                ?.getService(SERVICE_UUID)
                ?.getCharacteristic(CHARACTERISTIC_UUID)

            characteristic?.let {
                gatt.setCharacteristicNotification(it, true)
                val descriptor = it.getDescriptor(
                    UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")
                )
                descriptor?.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                gatt.writeDescriptor(descriptor)
                Log.d(TAG, "Notification set up")
            } ?: run {
                Log.e(TAG, "Characteristic not found!")
            }
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?
        ) {
            super.onCharacteristicChanged(gatt, characteristic)
            val data = characteristic?.value?.toString(Charsets.UTF_8)
            Log.d(TAG, "Received BLE data: $data")
            data?.let { notifyCallback?.invoke(it) }
        }
    }

    fun setNotifyCallback(callback: (String) -> Unit) {
        this.notifyCallback = callback
    }

    fun disconnect() {
        bluetoothGatt?.disconnect()
        bluetoothGatt?.close()
        bluetoothGatt = null
    }
}
