package com.jaydev.netmodulesample

import android.app.Application
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.jaydev.netmodulesample.adapter.IntegerTypeAdapter
import com.jaydev.netmodulesample.adapter.StringTypeAdapter
import com.jaydev.netmodule.config.NetServiceConfig
import com.jaydev.netmodule.mgr.NetServiceManager
import com.jaydev.netmodule.model.NetError
import com.jaydev.netmodule.netInterface.NetCallback
import com.jaydev.netmodule.netInterface.NetErrorProcess
import com.jaydev.netmodule.netInterface.NetFailProcess
import com.jaydev.netmodule.service.NetCallProcess
import retrofit2.Call

class SampleApp: Application() {


    override fun onCreate() {
        super.onCreate()

        val gson = GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .registerTypeAdapter(String::class.java, StringTypeAdapter())
            .registerTypeAdapter(Int::class.java, IntegerTypeAdapter())
            .create()

        NetServiceManager.getInstance().netServiceConfig = NetServiceConfig.build("http://192.168.0.16:8000/", gson) {
            isNetDebugMode = true
            netErrorProcess = object: NetErrorProcess {
                override fun <T> onCommonErrorParse(error: NetError, callback: NetCallback<T>): Boolean {
                    return when(error.code) {
                        400 -> {
                            Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
                            false
                        }
                        else -> {
                            true
                        }
                    }
                }

                override fun onErrorBehavior(call: Call<*>, retryNetCallProcess: NetCallProcess<*>) {
                    Toast.makeText(applicationContext, "통신 오류 처리, 재시도 할 것인가..?", Toast.LENGTH_SHORT).show()
                }
            }

            netFailProcess = object : NetFailProcess {
                override fun onNetErrorBehavior() {
                    Toast.makeText(applicationContext, "네트워크 오류", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}