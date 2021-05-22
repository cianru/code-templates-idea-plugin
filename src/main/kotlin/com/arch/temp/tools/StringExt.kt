package com.arch.temp.tools

fun String.replaceTemplate(map: Map<String, String>): String {
    var stringTemp = this
    map.keys.forEach { key ->
        stringTemp = stringTemp.replace("{$key}", map[key]!!)
    }
    return stringTemp
}

fun String.toTmFile() = "$this.tm"