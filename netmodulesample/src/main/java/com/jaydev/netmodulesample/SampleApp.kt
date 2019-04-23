package com.jaydev.netmodulesample

import android.app.Application
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.jaydev.netmodule.config.HeaderInterceptor
import com.jaydev.netmodule.config.NetServiceConfig
import com.jaydev.netmodule.mgr.NetServiceManager
import com.jaydev.netmodule.model.NetError
import com.jaydev.netmodule.netInterface.NetCallback
import com.jaydev.netmodule.netInterface.NetErrorProcess
import com.jaydev.netmodule.netInterface.NetFailProcess
import com.jaydev.netmodule.service.NetCallProcess
import com.jaydev.netmodule.service.NetService
import okhttp3.Authenticator
import okhttp3.Headers
import retrofit2.Call

class SampleApp : Application() {

	var token: String? = null

	override fun onCreate() {
		super.onCreate()

		val gson = GsonBuilder()
			.setPrettyPrinting()
			.create()

		NetServiceManager.getInstance().netServiceConfig = NetServiceConfig.build("http://198.168.0.1/api/", gson) {
			isNetDebugMode = true

			defaultHeader = object : HeaderInterceptor {
				override val headers: Headers
					get() = Headers.Builder().add("Content-Type", "application/json").add("Authorization", "Bearer $token").build()
			}

			defaultAuthenticator = Authenticator { route, response ->
				if (!response.isSuccessful) { // API 통신 실패
					val tokenApi = NetService.createNetService(TestService::class.java)

					val param = mapOf(
						"userId" to "worker2",
						"password" to "asdfqwer1!",
						"appRole" to "W"
					)
					val responseToken = tokenApi.login(param).execute()
					if (responseToken.isSuccessful) { // 토큰 인증 성공
						token = responseToken.body()?.token

						val headers = defaultHeader?.headers

						return@Authenticator response.request().newBuilder()
							.headers(headers)
							.build()
					} else { // 토근 인증 실패

						return@Authenticator null
					}
				} else { // 통신 성공
					return@Authenticator null
				}
			}

			netErrorProcess = object : NetErrorProcess {
				override fun <T> onCommonErrorParse(error: NetError, callback: NetCallback<T>): Boolean {
					return when (error.code) {
						400 -> {
							Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
							true
						}
						else -> {
							true
						}
					}
				}

				override fun onErrorBehavior(call: Call<*>, retryNetCallProcess: NetCallProcess<*>) {
					Toast.makeText(applicationContext, "통신 오류 처리, 재시도 할 것인가..?", Toast.LENGTH_SHORT).show()
				}
			}

			netFailProcess = object : NetFailProcess {
				override fun onNetErrorBehavior(call: Call<*>, t: Throwable) {
					Toast.makeText(applicationContext, "네트워크 오류", Toast.LENGTH_SHORT).show()
				}
			}
		}
	}
}