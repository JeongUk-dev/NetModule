package com.maxst.netmodule.service

import com.maxst.netmodule.netInterface.NetCallParams

class NetResponse<T> {
    var netCallParams: NetCallParams<*>? = null
    var receiveData: T? = null
}
