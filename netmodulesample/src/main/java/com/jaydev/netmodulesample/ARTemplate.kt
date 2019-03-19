package com.jaydev.netmodulesample

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * AR Item Position GSON Model
 */
data class Position(val x: Int, val y: Int, val z: Int)

/**
 * AR Item GSON Model
 */
data class ARItem(val type: String, val dataCol: String, val name: String, val position: Position, val align: String, val bgColor: String)

/**
 * Template GSON Model
 */
data class ARTemplate(val isExposed: Boolean, val markerHeight: Int, val markerWidth: Int, val name: String, val itemsJsonText: String, val createDT: String, val updateDT: String, val id: Int) {
    fun getARItemList(): List<ARItem> {
        val listType = object : TypeToken<List<ARItem>>() {}.type
        return Gson().fromJson(itemsJsonText, listType)
    }
}