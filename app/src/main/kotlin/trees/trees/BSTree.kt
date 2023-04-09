/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees.trees

import trees.ABSTree
import trees.nodes.BSNode

class BSTree<T : Comparable<T>> : ABSTree<T, BSNode<T>>() {


    override fun add(data: T) {
        root = simpleAdd(root, BSNode(data))
    }

    override fun contain(data: T): Boolean {
        return (simpleContains(root, BSNode(data)) != null)
    }

    override fun delete(data: T) {
        root = simpleDelete(root, BSNode(data))
    }

    fun get(data: T): T? {
        return simpleContains(root, BSNode(data))?.data
    }

    fun min(): T? {
        return root?.let { getMinimal(it).data }
    }
}