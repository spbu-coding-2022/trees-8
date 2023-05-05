/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees

import trees.nodes.BSNode

class BSTree<T : Comparable<T>> : AbstractTree<T, BSNode<T>>() {

    //method that adds a new node
    override fun add(data: T) {
        root = balancedAdd(root, BSNode(data))
    }

    //method that checks if the given value is contained
    override fun contains(data: T): Boolean {
        return (contains(root, BSNode(data)) != null)
    }

    //method that removes a node with a given value
    override fun delete(data: T) {
        root = balancedDelete(root, BSNode(data))
        root?.parent = null
    }

    //method that returns the value of the node with the given value.
    fun get(data: T): T? {
        return contains(root, BSNode(data))?.data
    }
}