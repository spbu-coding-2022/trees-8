import java.io.File
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import trees.KeyValue
import trees.trees.BSTree

fun writeToJSon(array: Array<Any>, fileName: String) {
    val map = mutableMapOf<String, Any>()
    for (i in array.indices step 2) {
        map["key${i/2}"] = array[i]
        map["value${i/2}"] = array[i+1]
    }
    val gson = Gson()
    val jsonString = gson.toJson(map)
    val file = File(fileName)
    file.writeText(jsonString)
}

fun buildBSTreeFromJson(jsonFileName: String): BSTree<KeyValue<Int, Any>> {
    val gson = Gson()
    val file = File(jsonFileName)
    val jsonString = file.readText()
    val type = object : TypeToken<Map<String, Any>>() {}.type
    val myMapRead: Map<String, Any> = gson.fromJson(jsonString, type)

    val BStree = BSTree<KeyValue<Int, Any>>()
    for (i in myMapRead.keys) {
        if (i.startsWith("key")) {
            val keyIndex = i.substring(3).toInt()
            val valueKey = myMapRead["value$keyIndex"]
            val keyValue = KeyValue(myMapRead[i] as Int, valueKey)
            BStree.add(keyValue)
        }
    }
    return BStree
}

fun main() {
    val myArray = arrayOf<Any>(10, "asd", 15, "Privet", 20, "Ti Pidor", 14, "suchiy")
    writeToJSon(myArray, "data.json")

    val tree = buildBSTreeFromJson("data.json")
}
