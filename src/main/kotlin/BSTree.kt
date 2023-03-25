class BSTree<K : Comparable<K>, V> : ABSTree<KeyValue<K, V>, BSNode<K, V>>() {


    override fun add(data: KeyValue<K, V>) {
        simpleAdd(BSNode(data))
    }

    override fun contain(data: KeyValue<K, V>): Boolean {
        return simpleContains(BSNode(data))
    }

    override fun delete(data: KeyValue<K, V>) {
        TODO("Not yet implemented")
    }


}