package com.yourapp.ble

import android.util.Log
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaTypeOrNull

class BleRepository(private val accessToken: String) {

    private val client = OkHttpClient()
    private val baseUrl = "https://your-api-url.com" // Todo : 서버 주소로 교체

    fun uploadTemperature(tempValue: Float) {
        val url = "$baseUrl/bottle/"
        val json = JSONObject().apply {
            put("temperature", tempValue)
        }

        val body = RequestBody.create("application/json".toMediaTypeOrNull(), json.toString())
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $accessToken")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("BleRepo", "🔥 온도 전송 실패: $e")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d("BleRepo", "✅ 온도 전송 성공")
                } else {
                    Log.w("BleRepo", "⚠️ 응답 오류: ${response.code}")
                }
            }
        })
    }

    fun uploadPressure(pressureValue: Int) {
        val url = "$baseUrl/bottle/"
        val json = JSONObject().apply {
            put("temperature", 0) // 온도 센서 없이 압력만 보낸다고 가정할 경우 기본값
            put("pressure", pressureValue) // 백엔드에서 해당 필드를 받아줘야 함
        }

        val body = RequestBody.create("application/json".toMediaTypeOrNull(), json.toString())
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $accessToken")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("BleRepo", "🔥 압력 전송 실패: $e")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d("BleRepo", "✅ 압력 전송 성공")
                } else {
                    Log.w("BleRepo", "⚠️ 응답 오류: ${response.code}")
                }
            }
        })
    }

    fun uploadWaterIntake(amountMl: Float) {
        val url = "$baseUrl/hydration/log?amount=$amountMl"

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $accessToken")
            .post(RequestBody.create(null, ByteArray(0))) // body 없이도 POST 가능
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("BleRepo", "🔥 음수량 전송 실패: $e")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d("BleRepo", "✅ 음수량 전송 성공")
                } else {
                    Log.w("BleRepo", "⚠️ 응답 오류: ${response.code}")
                }
            }
        })
    }


}
