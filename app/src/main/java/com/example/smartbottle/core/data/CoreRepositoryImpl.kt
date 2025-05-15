package com.example.smartbottle.core.data

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.example.smartbottle.core.domain.CoreRepository
import com.example.smartbottle.core.domain.CoreResult
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import java.util.UUID


class CoreRepositoryImpl(
    private val context: Context,
    private val httpClient: HttpClient,
    private val prefs: SharedPreferences
) : CoreRepository {

    private val tag = "CoreRepository: "
    private val baseUrl = NetworkConstants.BASE_URL

    // BLE 관련 멤버들
    private val bluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
    private var bluetoothGatt: BluetoothGatt? = null
    private var notifyCallback: ((String) -> Unit)? = null


    override suspend fun postBleData (
        temperature: Float,
        pressure: Int,
        waterIntake: Float
    ) : CoreResult<Unit> {
        return try {
                // 예: /bottle/data (서버에 실제로 해당 엔드포인트가 존재해야 함)
                val token = prefs.getString("jwt", null) ?: return CoreResult.Error("토큰이 없습니다.")

                val response: HttpResponse = httpClient.post("$baseUrl/bottle/data") {
                    header("Authorization", "Bearer $token")
                    contentType(ContentType.Application.Json)
                    // 만드는 JSON 구조는 서버 요구사항에 따라 맞춤 구성 필요
                    setBody(
                        mapOf(
                            "temperature" to temperature,
                            "pressure" to pressure,
                            "waterIntake" to waterIntake
                        )
                    )
                }

                if (response.status.isSuccess()) {
                    CoreResult.Success(Unit)
                } else {
                    CoreResult.Error("error")
                }

            }  catch (e: ClientRequestException) {
            // 4xx 요청 오류
            when (e.response.status) {
                HttpStatusCode.Unauthorized -> CoreResult.Error("error")
                else -> CoreResult.Error("error")
            }
        } catch (e: ServerResponseException) {
            // 5xx 서버 오류
            CoreResult.Error("error")
        } catch (e: RedirectResponseException) {
            // 3xx 리다이렉트 오류
            CoreResult.Error("error")
        } catch (e: Exception) {
            // 기타 예외
            CoreResult.Error("error")
        }

    }

    private val scanCallback = object : ScanCallback() {
        @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN])
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            result?.device?.let { device ->
                Log.d(tag, "Found device: ${device.name}, address: ${device.address}")
                if (device.name?.contains("HMSoft") == true) {
                    stopScan()
                    connectToDevice(device)
                }
            }
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    private fun connectToDevice(device: BluetoothDevice) {
        bluetoothGatt = device.connectGatt(context, false, gattCallback)
        Log.d(tag, "Connecting to ${device.address}")
    }


    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    override fun bleConnect() {
        val scanner = bluetoothAdapter?.bluetoothLeScanner
        val scanFilter = ScanFilter.Builder().build()
        val settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .build()

        scanner?.startScan(listOf(scanFilter), settings, scanCallback)
        Log.d(tag, "Started BLE scan")

        // 자동 종료 타이머 (10초 후 종료)
        Handler(Looper.getMainLooper()).postDelayed({
            stopScan()
        }, BleConstants.SCAN_TIMEOUT)
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    private fun stopScan() {
        bluetoothAdapter?.bluetoothLeScanner?.stopScan(scanCallback)
        Log.d(tag, "Stopped BLE scan")
    }

    private val gattCallback = object : BluetoothGattCallback() {
        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.d(tag, "Connected to GATT server")
                gatt?.discoverServices()
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.d(tag, "Disconnected from GATT server")
            }
        }

        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            val c = gatt
                .getService(BleConstants.SERVICE_UUID)
                ?.getCharacteristic(BleConstants.CHARACTERISTIC_UUID)
                ?: run {
                    Log.e(tag, "Characteristic not found!")
                    return
                }


            gatt.setCharacteristicNotification(c, true)

            val desc = c.getDescriptor(
                UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")
            ) ?: return

            val enable = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // API 33+
                gatt.writeDescriptor(desc, enable)
            } else {
                @Suppress("DEPRECATION")
                desc.value = enable
                @Suppress("DEPRECATION")
                gatt.writeDescriptor(desc)
            }
            Log.d(tag, "Notification set up")
        }

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray
        ) {
            val data = value.toString(Charsets.UTF_8)
            notifyCallback?.invoke(data)
            Log.d(tag, "Received BLE data (API33+): $data")
        }

        // ② 레거시(≤32)용 기존 콜백 - DEPRECATION 억제
        @Deprecated(
            message = "Deprecated BLE callback (API ≤ 32)",
            level   = DeprecationLevel.HIDDEN   // 외부 호출을 완전히 숨김
        )
        @Suppress("DEPRECATION")
        override fun onCharacteristicChanged(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?
        ) {
            val data = characteristic?.value?.toString(Charsets.UTF_8)
            data?.let { notifyCallback?.invoke(it) }
            Log.d(tag, "Received BLE data (legacy): $data")
        }
    }

    fun setNotifyCallback(callback: (String) -> Unit) {
        this.notifyCallback = callback
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    override fun bleDisconnect() {
        bluetoothGatt?.disconnect()
        bluetoothGatt?.close()
        bluetoothGatt = null
    }




}