package com.jaydev.netmodulesample.adapter

import com.google.gson.*
import com.jaydev.netmodulesample.NetConst.NetConst
import java.lang.reflect.Type

class StringTypeAdapter : JsonSerializer<String>, JsonDeserializer<String> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): String {
        return if (json.isJsonNull || json.asString == "") {
            NetConst.string_default
        } else {
            json.asJsonPrimitive.asString
        }
    }

    override fun serialize(src: String?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return if (src == null) {
            JsonPrimitive(NetConst.string_default)
        } else {
            JsonPrimitive(src.toString())
        }
    }
}
