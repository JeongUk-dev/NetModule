package com.jaydev.netmodulesample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.jaydev.netmodule.mgr.NetMgr
import com.jaydev.netmodule.model.NetError
import com.jaydev.netmodule.netInterface.NetCallback
import com.jaydev.netmodule.service.NetResponse
import com.jaydev.netmodule.service.NetService
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Authenticator
import okhttp3.Request
import java.io.IOException

class MainActivity : AppCompatActivity(), NetCallback<NetResponse<ARTemplate>> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button1.setOnClickListener {
            val testService = NetService.createNetService(TestService::class.java)
            val call = testService.getTemplateList()
            NetMgr.request(call, object : NetCallback<NetResponse<ArrayList<ARTemplate>>> {
                override fun onSuccResponse(responseData: NetResponse<ArrayList<ARTemplate>>) {
                    val templateModel = responseData.receiveData
                    textView.text = templateModel.toString()

                    val dialog =
                        AlertDialog.Builder(this@MainActivity).setMessage(templateModel!![0].getARItemList().toString())
                            .setPositiveButton("OK", null).create()
                    dialog.show()
                }

                override fun onFailResponse(error: NetError, responseData: NetResponse<ArrayList<ARTemplate>>) {
                    textView.text = responseData.receiveData.toString()
                }

                override fun clone(): NetCallback<NetResponse<ArrayList<ARTemplate>>> {
                    return this
                }
            })
        }



        button2.setOnClickListener {
            val authenticator = Authenticator { route, response ->
                response.request().newBuilder().build()
            }


            val testService = NetService.createNetService(TestService::class.java, authenticator)
            val testParam = TestParam("1")
            val call = testService.getTemplate(testParam.id)
            NetMgr.request(call, this@MainActivity, testParam)
        }
    }

    override fun onSuccResponse(responseData: NetResponse<ARTemplate>) {
        val templateModel = responseData.receiveData as ARTemplate
        textView.text = templateModel.toString()

        val dialog = AlertDialog.Builder(this@MainActivity).setMessage(templateModel.getARItemList().toString())
            .setPositiveButton("OK", null).create()
        dialog.show()
    }

    override fun onFailResponse(error: NetError, responseData: NetResponse<ARTemplate>) {
        textView.text = responseData.receiveData.toString()
    }

    override fun clone(): NetCallback<NetResponse<ARTemplate>> {
        return this
    }
}
