package com.maxst.netmodule.adapter

import com.google.gson.*
import com.maxst.netmodule.consts.NetConst
import java.lang.reflect.Type

class StringTypeAdpater : JsonSerializer<String>, JsonDeserializer<String> {

	override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): String {
		return if (json.isJsonNull || json.asString == "") {
			NetConst.default_string_value
		} else {
			json.asJsonPrimitive.asString
		}
	}

	override fun serialize(src: String?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
		return if (src == null) {
			JsonPrimitive(NetConst.default_string_value)
		} else {
			JsonPrimitive(src.toString())
		}
	}
}
