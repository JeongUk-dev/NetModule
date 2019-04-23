package com.jaydev.netmodulesample.adapter

import com.google.gson.*
import com.jaydev.netmodulesample.NetConst.NetConst
import java.lang.reflect.Type

class IntegerTypeAdapter : JsonSerializer<Int>, JsonDeserializer<Int> {

	@Throws(JsonParseException::class)
	override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Int {
		return if (json.isJsonNull) {
			NetConst.integer_default
		} else {
			json.asJsonPrimitive.asInt
		}
	}

	override fun serialize(src: Int?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
		return if (src == null) {
			JsonPrimitive(NetConst.integer_default)

		} else {
			JsonPrimitive(src)

		}
	}
}
