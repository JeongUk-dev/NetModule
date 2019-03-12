package com.maxst.netmodule.mgr

import com.maxst.netmodule.netInterface.NetCallParams
import com.maxst.netmodule.netInterface.NetCallback
import com.maxst.netmodule.service.NetCallProcess
import com.maxst.netmodule.service.NetResponse
import retrofit2.Call

object NetMgr {
	fun <T> request(call: Call<T>, callback: NetCallback<NetResponse<T>>) {
		call.enqueue(NetCallProcess(callback))
	}

	fun <T> request(call: Call<T>, callback: NetCallback<NetResponse<T>>, netCallParams: NetCallParams<*>) {
		call.enqueue(NetCallProcess(callback, netCallParams))
	}
}
