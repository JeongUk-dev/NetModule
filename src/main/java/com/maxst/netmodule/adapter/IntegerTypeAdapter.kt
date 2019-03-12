package com.maxst.netmodule.adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.maxst.netmodule.consts.NetConst

import java.lang.reflect.Type

class IntegerTypeAdapter : JsonSerializer<Int>, JsonDeserializer<Int> {

	@Throws(JsonParseException::class)
	override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Int? {
		return if (json.isJsonNull) {
			NetConst.default_int_value

		} else {
			json.asJsonPrimitive.asInt

		}

	}

	override fun serialize(src: Int?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
		return if (src == null) {
			JsonPrimitive(NetConst.default_int_value)

		} else {
			JsonPrimitive(src)

		}
	}
}
