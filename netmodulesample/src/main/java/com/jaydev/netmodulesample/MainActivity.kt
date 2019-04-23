package com.jaydev.netmodulesample

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.jaydev.netmodule.mgr.NetMgr
import com.jaydev.netmodule.model.NetError
import com.jaydev.netmodule.netInterface.NetCallback
import com.jaydev.netmodule.service.NetResponse
import com.jaydev.netmodule.service.NetService
import com.jaydev.netmodulesample.data.PagingResponse
import com.jaydev.netmodulesample.data.Task
import com.kt.ar.supporter.supporttools.data.User
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
	val TAG = MainActivity::class.java.simpleName

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)


		button1.setOnClickListener {

			val testService = NetService.createConfiguredNetService(TestService::class.java)

			val call = testService.getMyInfo()
			NetMgr.request(call, object : NetCallback<NetResponse<User>> {
				override fun onSuccResponse(responseData: NetResponse<User>) {
					val myInfo = responseData.receiveData
				}

				override fun onFailResponse(error: NetError, responseData: NetResponse<User>) {
					Log.w(TAG, "onFailResponse getMyInfo")
				}

				override fun clone(): NetCallback<NetResponse<User>> {
					return this
				}
			})
		}



		button2.setOnClickListener {
			val testService = NetService.createConfiguredNetService(TestService::class.java)
			val requestQueryMap: HashMap<String, Any?> = hashMapOf(
				"gePlanedStartDT" to getTodayStartUTCZero(),
				"ltplanedStartDT" to getTodayEndUTCZero(),
				"sort" to "Id"
//                "gtId" to null,
//                "otherUserId" to null,
//                "sort" to "Id",
//                "page" to null,
//                "pageSize" to null
			)
			val call = testService.getMyTask(requestQueryMap)
			NetMgr.request(call, object : NetCallback<NetResponse<PagingResponse<ArrayList<Task>>>> {
				override fun onSuccResponse(responseData: NetResponse<PagingResponse<ArrayList<Task>>>) {
					val taskList = responseData.receiveData
				}

				override fun onFailResponse(error: NetError, responseData: NetResponse<PagingResponse<ArrayList<Task>>>) {
					Log.w(TAG, "onFailResponse getMyTask")
				}

				override fun clone(): NetCallback<NetResponse<PagingResponse<ArrayList<Task>>>> {
					return this
				}
			})
		}
	}

	fun getTodayStartUTCZero(): String {
		val locale = Locale.getDefault()
		val c = Calendar.getInstance().time
		c.hours = 0
		c.minutes = 0
		c.seconds = 0

		val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000Z", locale)
		df.timeZone = TimeZone.getTimeZone("UTC")
		return df.format(c)
	}

	fun getTodayEndUTCZero(): String {
		val locale = Locale.getDefault()
		val c = Calendar.getInstance().time
		c.hours = 23
		c.minutes = 59
		c.seconds = 59

		val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000Z", locale)
		df.timeZone = TimeZone.getTimeZone("UTC")
		return df.format(c)
	}
}
