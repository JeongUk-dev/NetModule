package com.kt.ar.supporter.supporttools.data

object ApiUtil {
	const val MAXST_LOGIN = "maxstLogin"
	const val GET_USER_INFO = "getUserInfo"
	const val GET_WORKERS = "getWorkers"
	const val GET_TASKS = "getTasks"
	const val GET_NOTICES = "getNotices"
}

interface ServerResponseListener<T> {
	fun onSuccess(result: T)
	fun onFail(resultCode: String)
}

data class LoginDummyData(var userId: String, var userPassword: String)

data class RequestParam(val request: String, val param: String)
