interface Node<T : Comparable<T>, Subtype : Node<T, Subtype>> : Comparable<Node<T, Subtype>> {
    var data: T
    var left: Subtype?
    var right: Subtype?
    var parent: Subtype?
}

class BSNode<K : Comparable<K>, V>(override var data: KeyValue<K, V>) : Node<KeyValue<K, V>, BSNode<K, V>> {
    override var left: BSNode<K, V>? = null
    override var right: BSNode<K, V>? = null
    override var parent: BSNode<K, V>? = null

    override fun compareTo(other: Node<KeyValue<K, V>, BSNode<K, V>>): Int {
        return data.getKey().compareTo(other.data.getKey())
    }

    override fun equals(other: Any?): Boolean {
        if (other is BSNode<*, *>) {
            return data.equals(other.data)
        }
        return false
    }

    fun getKey(): K {
        return data.getKey()
    }

    fun getValue(): V {
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

    override fun equals(other: Any?): Boolean {
        if (other is KeyValue<*, *>) {
            return key.equals(other.getKey())
        }
        return false
    }

}