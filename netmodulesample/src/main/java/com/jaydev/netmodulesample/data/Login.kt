package com.kt.ar.supporter.supporttools.data

import android.util.Base64
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Login(var token: String) {

	fun getTokenCreatedDate(token: String): Long {
		val splitted = token.split(".")
		val payloadString = String(Base64.decode(splitted[1], 0))
		val tokenType = object : TypeToken<Payload>() {}.type
		val payload = Gson().fromJson<Payload>(payloadString, tokenType)
		return payload.Iat
	}
}

data class Payload(var Id: Int, var UserId: String, var UserName: String, var Role: String, var Iat: Long)