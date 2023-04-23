/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app.trees.avl

import app.trees.abs.ABSTree

class AVLTree<T : Comparable<T>> : ABSTree<T, AVLNode<T>>() {

    override fun add(data: T) {
        root = defaultAdd(root, AVLNode(data))
        root?.updateHeight()
        root?.parent = null
    }

    override fun contains(data: T): Boolean {
        return (defaultContains(root, AVLNode(data)) != null)
    }

    override fun delete(data: T) {
        root = defaultDelete(root, AVLNode(data))
        root?.updateHeight()
        root?.parent = null
    }

    override fun balance(initNode: AVLNode<T>?): AVLNode<T>? {
        if (initNode == null) {
            return null
        }
        initNode.updateHeight()
        // Calculates the balance factor of the initial node.
        val balanceFactor = initNode.balanceFactor()

        // If the balance factor is greater than 1, the tree is left-heavy and requires a right rotation.
        if (balanceFactor > 1) {
            // Checks if the right child of the initial node is left-heavy.
            if ((initNode.right?.balanceFactor() ?: 0) < 0) {
                // Performs a right rotation on the right child of the initial node.
                initNode.right = initNode.right?.let { rotateRight(it) }
                // Updates the height of the right child of the initial node.
                initNode.right?.updateHeight()
            }
            val node = rotateLeft(initNode)
            updateChildrenHeight(node)
            node?.updateHeight()
            return node
        // If the balance factor is less than -1, the tree is right-heavy and requires a left rotation.
        } else if (balanceFactor < -1) {
            // Checks if the left child of the initial node is right-heavy.
            if ((initNode.left?.balanceFactor() ?: 0) > 0) {
                // Performs a left rotation on the left child of the initial node.
                initNode.left = initNode.left?.let { rotateLeft(it) }
                // Updates the height of the left child of the initial node.
                initNode.left?.updateHeight()
            }
            val node = rotateRight(initNode)
            updateChildrenHeight(node)
            node?.updateHeight()
            return node
        }
        return initNode
    }

    fun get(data: T): T? {
        return defaultContains(root, AVLNode(data))?.data
    }

    private fun updateChildrenHeight(node: AVLNode<T>?) {
        node?.left?.updateHeight()
        node?.right?.updateHeight()
    }
}