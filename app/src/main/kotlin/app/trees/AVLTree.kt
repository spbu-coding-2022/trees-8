/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app.trees

import app.trees.nodes.AVLNode

class AVLTree<T : Comparable<T>> : AbstractTree<T, AVLNode<T>>() {

    override fun add(data: T) {
        root = balancedAdd(root, AVLNode(data))
        root?.updateHeight()
        root?.parent = null
    }

    override fun contains(data: T): Boolean {
        return (contains(root, AVLNode(data)) != null)
    }

    override fun delete(data: T) {
        root = balancedDelete(root, AVLNode(data))
        root?.updateHeight()
        root?.parent = null
    }

    override fun balance(initNode: AVLNode<T>?): AVLNode<T>? {
        if (initNode == null) {
            return null
        }
        initNode.updateHeight()
        val balanceFactor = initNode.balanceFactor()

        if (balanceFactor > 1) {
            if ((initNode.right?.balanceFactor() ?: 0) < 0) {
                initNode.right = initNode.right?.let { rotateRight(it) }  //  right = right?.rotateRight()
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

    fun get(data: T): T? {
        return contains(root, AVLNode(data))?.data
    }

    private fun updateChildrenHeight(node: AVLNode<T>?) {
        node?.left?.updateHeight()
        node?.right?.updateHeight()
    }
}