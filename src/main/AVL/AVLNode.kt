class AVLNodee<K : Comparable<K>, V>(override var data: KeyValue<K, V>) : Node<KeyValue<K, V>, AVLNodee<K, V>> {
    var height: Int?
    override var left: AVLNode<K, V>? = null
    override var right: AVLNode<K, V>? = null
    override var parent: AVLNode<K, V>? = null

    fun updateHeight()
    fun rotateLeft(): AVLNode<K, V>
    fun rotateRight(): AVLNode<K, V>
    fun balanceFactor(): Int
    fun rebalance()
    fun add(data: KeyValue<K, V>)
    fun delete(data: KeyValue<K,V>)

    fun getKey(): K {
        return data.getKey()
    }
    fun getValue(): V {
        return data.getValue()
    }


    override fun compareTo(other: Node<KeyValue<K,V>, AVLNode<K,V>>): Int {
        return data.compareTo(other.data.getKey())
    }

    override fun equals(other: Any?): Boolean {
        if (other is BSNode<*, *>) {
            return data.equals(other.data)
        }
        return false
    }
}