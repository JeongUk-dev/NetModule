package com.maxst.netmodule.service

import com.maxst.netmodule.mgr.NetServiceManager
import com.maxst.netmodule.model.NetError
import com.maxst.netmodule.netInterface.NetCallParams
import com.maxst.netmodule.netInterface.NetCallback

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetCallProcess<T> : Callback<T>, Cloneable {
    private var netCallback: NetCallback<NetResponse<T>>
    private var netCallParams: NetCallParams<*>? = null

    constructor(netCallback: NetCallback<NetResponse<T>>) {
        this.netCallback = netCallback
    }

    constructor(netCallback: NetCallback<NetResponse<T>>, netCallParams: NetCallParams<*>?) {
        this.netCallback = netCallback
        this.netCallParams = netCallParams
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            val netModel = response.body()
            val netResponse = NetResponse<T>()
            netResponse.netCallParams = netCallParams

            netResponse.receiveData = netModel
            netCallback.onSuccResponse(netResponse)

        } else {
            val netResponse = NetResponse<T>()
            val netError = NetError(response.code(), response.message())
            netResponse.receiveData = response.body()

            var isNeedExtraErrorProcess = true // 공통 에러 처리 후 각각의 화면에서 에러 처리가 필요할때.
            NetServiceManager.getInstance().netServiceConfig.netErrorProcess?.let {
                isNeedExtraErrorProcess = it.onCommonErrorParse(netError, netCallback)
            }

            if (isNeedExtraErrorProcess) { // 각 화면단 에러 처리가 필요할때
                netCallback.onFailResponse(netError, netResponse) // 통신 실패를 각 화면으로 넘긴다.
            }

            NetServiceManager.getInstance().netServiceConfig.netErrorProcess?.let {
                it.onErrorBehavior(call.clone(), NetCallProcess(this.netCallback.clone(), this.netCallParams?.clone()))
            } // 통신 에러일때 재시도 할 것인지.
        }
        call.cancel()
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        NetServiceManager.getInstance().netServiceConfig.netFailProcess?.let { it.onNetFailBehavior() } // 통신에 완전 실패함. (네트워크 연결 오류 등)
    }

    @Throws(CloneNotSupportedException::class)
    override fun clone(): Any {
        return super.clone()
    }
}
