import trees.ABSTree

/*
 * Copyright 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

class BSTree<K : Comparable<K>, V> : ABSTree<KeyValue<K, V>, BSNode<K, V>>() {


    override fun add(data: KeyValue<K, V>) {
        root = simpleAdd(root, BSNode(data))
    }

    override fun contain(data: KeyValue<K, V>): Boolean {
        return (simpleContains(root, BSNode(data)) != null)
    }

    fun get(key: K): V? {
        return simpleContains(root, BSNode(KeyValue(key, null)))?.getValue()
    }

    override fun delete(data: KeyValue<K, V>) {
        root = simpleDelete(root, BSNode(data))
    }

    fun min(): KeyValue<K, V>? {
        return root?.let { getMinimal(it).data }
    }
}