interface Node<T : Comparable<T>, Subtype : Node<T, Subtype>> : Comparable<Node<T, Subtype>> {
    override operator fun compareTo(other: Node<T, Subtype>): Int {
        return data.compareTo(other.data)
    }

    var data: T
    var left: Subtype?
    var right: Subtype?
}

class BSNode<K : Comparable<K>, V : Any>(override var data: KeyValue<K, V>) : Node<KeyValue<K, V>, BSNode<K, V>> {
    override var left: BSNode<K, V>? = null
    override var right: BSNode<K, V>? = null

    fun getKey(): K {
        return data.getKey()
    }

    fun getValue(): V {
        return data.getValue()
    }

    operator fun compareTo(other: BSNode<K, V>?): Int {
        if (other != null) {
            return data.getKey().compareTo(other.getKey())
        }
        return 0
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