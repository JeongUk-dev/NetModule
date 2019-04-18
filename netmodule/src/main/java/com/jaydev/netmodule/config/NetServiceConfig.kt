package com.jaydev.netmodule.config

import com.google.gson.Gson
import com.jaydev.netmodule.netInterface.NetConfigDefaultParams
import com.jaydev.netmodule.netInterface.NetErrorProcess
import com.jaydev.netmodule.netInterface.NetFailProcess

class NetServiceConfig(
    val baseURL: String,
    val gson: Gson,
    val defaultParams: NetConfigDefaultParams?,
    val netFailProcess: NetFailProcess?,
    val netErrorProcess: NetErrorProcess?,
    val isNetDebugMode: Boolean,
    val connectionPoolSize: Int,
    val timeOutSec: Long
) {

    private constructor(builder: Builder) : this(
        builder.baseURL,
        builder.gSon,
        builder.defaultParams,
        builder.netFailProcess,
        builder.netErrorProcess,
        builder.isNetDebugMode,
        builder.connectionPoolSize,
        builder.timeOutSec
    )

    companion object {
        inline fun build(baseURL: String, gSon: Gson, block: Builder.() -> Unit) =
            Builder(baseURL, gSon).apply(block).build()
    }

    class Builder(val baseURL: String, val gSon: Gson) {
        var defaultParams: NetConfigDefaultParams? = null
        var netFailProcess: NetFailProcess? = null
        var netErrorProcess: NetErrorProcess? = null
        var isNetDebugMode = false
        var connectionPoolSize = 10
        var timeOutSec: Long = 50

        fun build() = NetServiceConfig(this)
    }
}