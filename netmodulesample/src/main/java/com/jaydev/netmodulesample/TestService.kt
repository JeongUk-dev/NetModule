package com.jaydev.netmodulesample

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface TestService {
    @GET("api/Templates")
    fun getTemplateList(): Call<ArrayList<ARTemplate>>

    @GET("api/Templates/{id}")
    fun getTemplate(@Path("id") id: String): Call<ARTemplate>
}