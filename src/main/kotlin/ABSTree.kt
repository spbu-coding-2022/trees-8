interface Tree<T : Comparable<T>> {
    fun add(data: T)
    fun contain(data: T)
    fun delete(data: T)
}

abstract class ABSTree<K : Comparable<K>, V : Any> : Tree<KeyValue<K, V>> {
    var root: BSNode<K, V>? = null
    fun simple_add(data: KeyValue<K, V>) {
        val node = BSNode(data)
        if (root == null) {
            root = node
            return
        }
        var curNode = root!!
        while (true) {

            if (node < curNode) {
                if (curNode.left == null) {
                    curNode.left = node
                    break
                } else {
                    curNode = curNode.left!!
                }

            } else if (node > curNode) {
                if (curNode.right == null) {
                    curNode.right = node
                    break
                } else {
                    curNode = curNode.right!!
                }
            }

        }
    }
}