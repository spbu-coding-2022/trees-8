/*
 * Copyright 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

class BSTree<K : Comparable<K>, V> : ABSTree<KeyValue<K, V>, BSNode<K, V>>() {


    override fun add(data: KeyValue<K, V>) {
        simpleAdd(BSNode(data))
    }

    override fun contain(data: KeyValue<K, V>): Boolean {
        return (simpleContains(BSNode(data)) != null)
    }

    fun get(key: K): V? {
        return simpleContains(BSNode(KeyValue(key, null)))?.getValue()
    }

    override fun delete(data: KeyValue<K, V>) {
        val curNode = simpleContains(BSNode(data)) ?: return
        simpleDelete(curNode)
    }


}