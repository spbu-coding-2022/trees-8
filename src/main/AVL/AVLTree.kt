class AVLTree<K : Comparable<K>, V> : ABSTree<KeyValue<K, V>, AVLNode<K, V>>() {

    var root: AVLNode<K, V>? = null



    override fun contain(data: KeyValue<K, V>): Boolean {
        return (simpleContains(BSNode(data)) != null)
    }

    override fun add(data: KeyValue<K, V>) {
        simpleAdd(BSNode(data))
        root?.rebalance()
    }

    override fun delete(data: KeyValue<K, V>) {
        val curNode = simpleContains(BSNode(data)) ?: return
        simpleDelete(curNode)
        root?.rebalance()
    }

    fun get(key: K): V? {
        return simpleContains(BSNode(KeyValue(key, null)))?.getValue()
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun balanceFactor(): Int {
        return (right?.height ?: 0) - (left?.height ?: 0)
    }

    override fun updateHeight() {
        val leftHeight = left?.height ?: 0
        val rightHeight = right?.height ?: 0
        height = 1 + maxOf(leftHeight, rightHeight)
    }

    override fun rotateLeft(): AVLNode<K, V> {
        val newRoot = right
        right = newRoot?.left
        newRoot?.left = this
        return newRoot ?: this
    }

    override fun rotateRight(): AVLNode<K, V> {
        val newRoot = left
        left = newRoot?.right
        newRoot?.right = this
        return newRoot ?: this
    }

    override fun rebalance() {
        val balanceFactor = balanceFactor()

        if (balanceFactor > 1) {
            if (right?.balanceFactor() ?: 0 < 0) {
                right = right?.rotateRight()
            }
            root = rotateLeft()
        } else if (balanceFactor < -1) {
            if (left?.balanceFactor() ?: 0 > 0) {
                left = left?.rotateLeft()
            }
            root = rotateRight()
        }
    }

}
