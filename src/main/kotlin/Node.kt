interface Node<T : Comparable<T>, Subtype : Node<T, Subtype>> {
    var data: T
    var left: Subtype?
    var right: Subtype?
    fun getKey() : Any
    fun getValue() : Any
}

class BSNode<K : Comparable<K>, V : Any>(override var data: KeyValue<K, V>) :  Node<KeyValue<K, V>, BSNode<K, V>> {
    override var left: BSNode<K, V>? = null
    override var right: BSNode<K, V>? = null

    override fun getKey(): K {
        return data.getKey()
    }

    override fun getValue(): V {
        return data.getValue()
    }
}

class KeyValue<K : Comparable<K>, V>(private val key: K, private var value: V) : Comparable<KeyValue<K, V>> {
    fun getKey(): K {
        return key
    }
    fun getValue(): V {
        return value
    }
    override fun compareTo(other: KeyValue<K, V>): Int {
        return key.compareTo(other.key)
    }

}