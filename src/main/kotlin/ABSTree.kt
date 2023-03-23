import Node
interface Tree {
    fun add()
    fun contain()
    fun delete()
}

abstract class ABSTree<K : Comparable<K>, V : Any>: Tree {
    var root: BSNode<K, V>? = null
    fun simple_add(key: K, value: V) {
        val node = BSNode(KeyValue(key, value))
        while (true) {
        }
    }
    fun simple_delete() {}
}