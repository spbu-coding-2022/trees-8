/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees

import trees.nodes.AVLNode

class AVLTree<T : Comparable<T>> : AbstractTree<T, AVLNode<T>>() {
    //adds a new node to the AVL tree.
    override fun add(data: T) {
        root = balancedAdd(root, AVLNode(data))
        root?.updateHeight()
        root?.parent = null
    }

    //checks if the given value is contained in the AVL tree.
    override fun contains(data: T): Boolean {
        return (contains(root, AVLNode(data)) != null)
    }

    //removes the node with the given value from the AVL tree.
    override fun delete(data: T) {
        root = balancedDelete(root, AVLNode(data))
        root?.updateHeight()
        root?.parent = null
    }

    //method that balances the tree starting from the given node.
    override fun balance(initNode: AVLNode<T>?): AVLNode<T>? {
        if (initNode == null) {
            return null
        }
        initNode.updateHeight()
        val balanceFactor = initNode.balanceFactor()

        if (balanceFactor > 1) {
            if ((initNode.right?.balanceFactor() ?: 0) < 0) {
                initNode.right = initNode.right?.let { rotateRight(it) }
                initNode.right?.updateHeight()
            }
            val node = rotateLeft(initNode)
            updateChildrenHeight(node)
            node?.updateHeight()
            return node
        } else if (balanceFactor < -1) {
            if ((initNode.left?.balanceFactor() ?: 0) > 0) {
                initNode.left = initNode.left?.let { rotateLeft(it) }
                initNode.left?.updateHeight()
            }
            val node = rotateRight(initNode)
            updateChildrenHeight(node)
            node?.updateHeight()
            return node
        }
        return initNode
    }

    //method that returns the value of the node with the given value.
    fun get(data: T): T? {
        return contains(root, AVLNode(data))?.data
    }

    //helper method of the AVLTree class that updates the height of the passed node's child nodes.
    private fun updateChildrenHeight(node: AVLNode<T>?) {
        node?.left?.updateHeight()
        node?.right?.updateHeight()
    }
}