/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app.trees.trees

import app.trees.ABSTree
import app.trees.nodes.BSNode

class BSTree<T : Comparable<T>> : ABSTree<T, BSNode<T>>() {


    override fun add(data: T) {
        root = simpleAdd(root, BSNode(data))
    }

    override fun contain(data: T): Boolean {
        return (simpleContains(root, BSNode(data)) != null)
    }

    override fun delete(data: T) {
        root = simpleDelete(root, BSNode(data))
        root?.parent = null
    }

    fun get(data: T): T? {
        return simpleContains(root, BSNode(data))?.data
    }
}