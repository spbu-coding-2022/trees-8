/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

import java.io.File
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import trees.KeyValue
import trees.trees.BSTree


fun writeToJSon(array: Array<Int>, fileName: String) {
    val map = mutableMapOf<String, Int>()
    for (i in array.indices step 2) {
        map["key${i/2}"] = array[i]
        map["value${i/2}"] = array[i+1]
    }
    val gson = Gson()
    val jsonString = gson.toJson(map)
    val file = File(fileName)
    file.writeText(jsonString)
}

fun buildBSTreeFromJson(jsonFileName: String): BSTree<KeyValue<Int, Int>> {
    val gson = Gson()
    val file = File(jsonFileName)
    val jsonString = file.readText()
    val type = object : TypeToken<Map<String, Int>>() {}.type
    val myMapRead: Map<String, Int> = gson.fromJson(jsonString, type)

    val BStree = BSTree<KeyValue<Int, Int>>()
    for (i in myMapRead.keys) {
        if (i.startsWith("key")) {
            val keyIndex = i.substring(3).toInt()
            val valueKey = myMapRead["value$keyIndex"]
            val keyValue = KeyValue(myMapRead[i]!!, valueKey!!)
            BStree.add(keyValue)
        }
    }
    return BStree
}


fun main() {
    val myArray = arrayOf(10, 100, 15, 150, 20, 200, 14, 140)
    writeToJSon(myArray, "data.json")

    val tree = buildBSTreeFromJson("data.json")

}