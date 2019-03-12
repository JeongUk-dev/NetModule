package com.maxst.netmodule.service

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.maxst.netmodule.mgr.NetServiceManager
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object NetService {

	private fun createGson(): Gson {

		return GsonBuilder()
				.serializeNulls()
				.setPrettyPrinting()
//				.registerTypeAdapter(String::class.java, StringTypeAdpater())
//				.registerTypeAdapter(Int::class.java, IntegerTypeAdapter())
				.create()
	}


	fun <T> createNetService(netTarget: Class<T>): T {
		val httpClient = OkHttpClient().newBuilder()
		val builder = Retrofit.Builder()
				.baseUrl(NetServiceManager.getInstance().netServiceConfig.baseURL)
				.addConverterFactory(GsonConverterFactory.create(createGson()))

		if (NetServiceManager.getInstance().netServiceConfig.isNetDebugMode) {
			val loggingInterceptor = HttpLoggingInterceptor()
			loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

			httpClient.addInterceptor(loggingInterceptor)

		}

		httpClient.connectionPool(ConnectionPool(NetServiceManager.getInstance().netServiceConfig.connectionPoolSize, NetServiceManager.getInstance().netServiceConfig.timeOutSec, TimeUnit.SECONDS))
		httpClient.readTimeout(NetServiceManager.getInstance().netServiceConfig.timeOutSec, TimeUnit.SECONDS)
		httpClient.connectTimeout(NetServiceManager.getInstance().netServiceConfig.timeOutSec, TimeUnit.SECONDS)

		builder.client(httpClient.build())

		return builder.build().create(netTarget)

	}

	fun <T> createNetService(netTarget: Class<T>, interceptor: Interceptor?): T {
		val httpClient = OkHttpClient().newBuilder()
		val builder = Retrofit.Builder()
				.baseUrl(NetServiceManager.getInstance().netServiceConfig.baseURL)
				.addConverterFactory(GsonConverterFactory.create(createGson()))

		if (NetServiceManager.getInstance().netServiceConfig.isNetDebugMode) {
			val loggingInterceptor = HttpLoggingInterceptor()
			loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

			httpClient.addInterceptor(loggingInterceptor)

		}

		httpClient.addInterceptor(interceptor!!)

		httpClient.connectionPool(ConnectionPool(NetServiceManager.getInstance().netServiceConfig.connectionPoolSize, NetServiceManager.getInstance().netServiceConfig.timeOutSec, TimeUnit.SECONDS))
		httpClient.readTimeout(NetServiceManager.getInstance().netServiceConfig.timeOutSec, TimeUnit.SECONDS)
		httpClient.connectTimeout(NetServiceManager.getInstance().netServiceConfig.timeOutSec, TimeUnit.SECONDS)

		builder.client(httpClient.build())

		return builder.build().create(netTarget)

	}

	/**
	 * php 통신 json 파라미터 생성
	 */
	fun createJsonParams(params: Any): JsonObject {
		val paramMap = NetServiceManager.getInstance().netServiceConfig.defaultParams?.configDefaultParams()

		val gson = Gson()

		val jsonStr = Gson().toJson(params)
		val typeOfHashMap = object : TypeToken<Map<String, Any>>() {}.type
		val newMap = Gson().fromJson<HashMap<String, Any>>(jsonStr, typeOfHashMap)
		paramMap?.let { newMap.putAll(it) }

		return JsonParser().parse(gson.toJson(newMap)).asJsonObject

	}

	/**
	 * php 통신 json 파라미터 생성
	 */
	fun createDefaultJsonParams(): JsonObject {
		val paramMap = NetServiceManager.getInstance().netServiceConfig.defaultParams?.configDefaultParams()

		val gson = Gson()
		return JsonParser().parse(gson.toJson(paramMap)).asJsonObject

	}

	fun isNetworkConnected(context: Context): Boolean {
		val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

		val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

		val activeNetwork = cm.activeNetworkInfo

		return activeNetwork != null && activeNetwork.isConnectedOrConnecting

	}

}
