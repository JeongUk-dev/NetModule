package com.kt.ar.supporter.supporttools.data

import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type

object DataRequestController {

	var sourceType = DataSourceType.SERVER
	private var assetManager: AssetManager? = null

	enum class DataSourceType {
		SERVER, FILE
	}

	fun init(context: Context) {
		assetManager = context.assets
	}

	fun <T> request(requestParam: RequestParam, serverResponseListener: ServerResponseListener<T>, type: Type) {
		when (sourceType) {
			DataSourceType.SERVER -> {
				// TODO : Request to server
			}

			DataSourceType.FILE -> {
				var fileName = ""

				when (requestParam.request) {
					ApiUtil.MAXST_LOGIN -> fileName = "${requestParam.request}${requestParam.param}.json"
					ApiUtil.GET_USER_INFO -> fileName = "${requestParam.request}${requestParam.param}.json"
					ApiUtil.GET_WORKERS -> fileName = "${requestParam.request}${requestParam.param}.json"
					ApiUtil.GET_NOTICES -> fileName = "${requestParam.request}${requestParam.param}.json"
					ApiUtil.GET_TASKS -> fileName = "${requestParam.request}${requestParam.param}.json"
				}

				val resultString = readAssetText(fileName)
				if (resultString.isNotEmpty()) {
					val result = Gson().fromJson(resultString, type) as T
					serverResponseListener.onSuccess(result)
				} else {
					serverResponseListener.onFail("404 not found")
				}
			}
		}
	}

	fun getUserList() = readAssetText("userInfo.json")

	private fun readAssetText(fileName: String): String {
		val stringBuilder = StringBuilder()
		var json: InputStream? = null
		try {
			json = assetManager!!.open(fileName)
			val inputStream = BufferedReader(InputStreamReader(json!!, "UTF-8"))
			inputStream.forEachLine { stringBuilder.append(it) }
			inputStream.close()
		} catch (e: IOException) {
			e.printStackTrace()
		}

		return stringBuilder.toString()
	}
}
