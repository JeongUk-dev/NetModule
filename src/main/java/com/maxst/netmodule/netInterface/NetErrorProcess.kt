package com.maxst.netmodule.netInterface

import com.maxst.netmodule.model.NetError
import com.maxst.netmodule.service.NetCallProcess
import retrofit2.Call

interface NetErrorProcess {
    fun <T> onCommonErrorParse(error: NetError, callback: NetCallback<T>): Boolean
    fun onErrorBehavior(call: Call<*>, retryNetCallProcess: NetCallProcess<*>)

}
