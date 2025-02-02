package com.spacekay.thatsong.extentions

object StringArrayExtension {
    fun refineStringArray(data: List<String>): List<String> {
        val newArray = ArrayList<String>()
        for (string in data) {
            newArray.add(string.filterNot { it == '"' })
        }
        return newArray
    }

    fun arrayToString(data: List<String>): String {
        if (data.isEmpty())
            return ""
        return data.joinToString(", ")
    }
}