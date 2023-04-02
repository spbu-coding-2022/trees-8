package trees

/*
 * Copyright 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

class BSTree<T : Comparable<T>> : ABSTree<T, BSNode<T>>() {


    override fun add(data:T) {
        root = simpleAdd(root, BSNode(data))
    }

    override fun contain(data: T): Boolean {
        return (simpleContains(root, BSNode(data)) != null)
    }

    fun get(data: T): T? {
        return simpleContains(root, BSNode(data))?.data
    }

    override fun delete(data: T) {
        root = simpleDelete(root, BSNode(data))
    }

    fun min(): T? {
        return root?.let { getMinimal(it).data }
    }
}