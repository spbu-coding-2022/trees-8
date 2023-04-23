/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app.trees

import app.trees.nodes.BSNode

class BSTree<T : Comparable<T>> : AbstractTree<T, BSNode<T>>() {


    override fun add(data: T) {
        root = balancedAdd(root, BSNode(data))
    }

    override fun contains(data: T): Boolean {
        return (contains(root, BSNode(data)) != null)
    }

    override fun delete(data: T) {
        root = balancedDelete(root, BSNode(data))
        root?.parent = null
    }

    fun get(data: T): T? {
        return contains(root, BSNode(data))?.data
    }
}