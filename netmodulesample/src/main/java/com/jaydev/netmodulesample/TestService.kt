package com.jaydev.netmodulesample

import com.jaydev.netmodulesample.data.PagingResponse
import com.jaydev.netmodulesample.data.Task
import com.kt.ar.supporter.supporttools.data.Login
import com.kt.ar.supporter.supporttools.data.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap


interface TestService {
	@POST("Token")
	fun login(@Body body: Map<String, String?>): Call<Login>


	@GET("Users/My")
	fun getMyInfo(): Call<User>

	@GET("Works/My")
	fun getMyTask(@QueryMap requestQueryMap: HashMap<String, Any?>): Call<PagingResponse<ArrayList<Task>>>
}