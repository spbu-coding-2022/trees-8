class BSTree<K : Comparable<K>, V> : ABSTree<KeyValue<K, V>, BSNode<K, V>>() {


    override fun add(data: KeyValue<K, V>) {
        simpleAdd(BSNode(data))
    }

    override fun contain(data: KeyValue<K, V>): Boolean {
        return simpleContains(BSNode(data))
    }

    fun get(key: K): V? {
        if (root == null) {
            return null
        }
        var curNode = root!!

        while (true) {

            if (key < curNode.data.getKey()) {
                if (curNode.left == null) {
                    break
                } else {
                    curNode = curNode.left!!
                }

            } else if (key > curNode.data.getKey()) {
                if (curNode.right == null) {
                    break
                } else {
                    curNode = curNode.right!!
                }
            } else if (key == curNode.data.getKey()) {
                return curNode.data.getValue()
            }
        }
        return null
    }

    override fun delete(data: KeyValue<K, V>) {
        TODO("Not yet implemented")
    }


}