package com.maxst.netmodule.config

import com.maxst.netmodule.netInterface.NetCallParams
import com.maxst.netmodule.netInterface.NetConfigDefaultParams
import com.maxst.netmodule.netInterface.NetErrorProcess
import com.maxst.netmodule.netInterface.NetFailProcess

class NetServiceConfig(val baseURL: String,
					   val headerMap: HashMap<String, String>? = null,
					   val netCallParams: NetCallParams<*>?,
					   val defaultParams: NetConfigDefaultParams?,
					   val netFailProcess: NetFailProcess?,
					   val netErrorProcess: NetErrorProcess?,
					   val isNetDebugMode: Boolean,
					   val connectionPoolSize: Int,
					   val timeOutSec: Long) {

	private constructor(builder: Builder) : this(builder.baseURL, builder.headerMap, builder.netCallParams, builder.defaultParams, builder.netFailProcess, builder.netErrorProcess, builder.isNetDebugMode, builder.connectionPoolSize, builder.timeOutSec)

	companion object {
		inline fun build(bseURL: String, block: Builder.() -> Unit) = Builder(bseURL).apply(block).build()
	}

	class Builder(val baseURL: String) {
		var netCallParams: NetCallParams<*>? = null
		var headerMap: HashMap<String, String>? = null
		var defaultParams: NetConfigDefaultParams? = null
		var netFailProcess: NetFailProcess? = null
		var netErrorProcess: NetErrorProcess? = null
		var isNetDebugMode = false
		var connectionPoolSize = 10
		var timeOutSec: Long = 50

		fun build() = NetServiceConfig(this)

	}
}