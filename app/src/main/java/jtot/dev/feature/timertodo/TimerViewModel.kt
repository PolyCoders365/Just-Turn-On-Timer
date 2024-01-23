package jtot.dev.feature.timertodo

import androidx.lifecycle.viewModelScope
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.firebase.messaging.FirebaseMessaging
import jtot.dev.base.BaseViewModel
import jtot.dev.model.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException
import org.json.JSONObject
import java.io.InputStream

class TimerViewModel : BaseViewModel() {
    private lateinit var schedule: Schedule
    private lateinit var accessToken: String

    fun setSchedule(schedule: Schedule) {
        this.schedule = schedule
    }

    fun getSchedule() = schedule

    fun setAccessToken(token: String) {
        accessToken = token
    }

    fun getAccessToken() = accessToken

    fun getAccessToken(
        asset: InputStream,
        isSuccess: (String) -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val googleCredential =
                GoogleCredential.fromStream(asset)
                    .createScoped(listOf("https://www.googleapis.com/auth/firebase.messaging"))
            if (googleCredential.accessToken.isNullOrEmpty()) {
                googleCredential.refreshToken()
            }
            isSuccess(googleCredential.accessToken)
        }
    }

    fun createFCMToken(isSuccess: (String) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                //
            }
            isSuccess(task.result)
        }
    }

    fun sendNotifications(
        accessToken: String,
        receiveDeviceToken: String,
        title: String,
        content: String,
    ) {
        val api =
            "https://fcm.googleapis.com/v1/projects/just-turn-on-timer/messages:send"

        val url = api.toHttpUrlOrNull()!!.newBuilder().build()
        val client = OkHttpClient()

        val json =
            JSONObject().apply {
                put(
                    "message",
                    JSONObject().apply {
                        put("token", receiveDeviceToken)
                        put(
                            "notification",
                            JSONObject().apply {
                                put("title", title)
                                put("body", content)
                            },
                        )
                    },
                )
            }

        val body =
            json.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val request =
            Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", "Bearer $accessToken")
                .addHeader("Content-Type", "application/json")
                .build()

        client.newCall(request).enqueue(
            object : Callback {
                override fun onFailure(
                    call: Call,
                    e: IOException,
                ) {
                    e.printStackTrace()
                }

                override fun onResponse(
                    call: Call,
                    response: Response,
                ) {
                    // Handle success
                    val responseBody = response.body?.string()
                    println("FCM Response: $responseBody")
                }
            },
        )
    }
}
