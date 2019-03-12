package com.maxst.netmodule.mgr

import com.maxst.netmodule.config.NetServiceConfig


class NetServiceManager private constructor() {

	lateinit var netServiceConfig: NetServiceConfig

	companion object {
		private var instance: NetServiceManager? = null

		@JvmStatic
		fun getInstance() = instance ?: synchronized(this) {
			instance ?: NetServiceManager().also { instance = it }
		}
	}
}
