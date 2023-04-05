/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees.trees

import trees.ABSTree
import trees.nodes.AVLNode

class AVLTree<T : Comparable<T>> : ABSTree<T, AVLNode<T>>() {

    override fun add(data: T) {
        root = simpleAdd(root, AVLNode(data))
        root?.updateHeight()
        root?.parent = null
    }

    override fun contain(data: T): Boolean {
        return (simpleContains(root, AVLNode(data)) != null)
    }

    override fun delete(data: T) {
        root = simpleDelete(root, AVLNode(data))
        root?.updateHeight()
        root?.parent = null
    }

    override fun balance(initNode: AVLNode<T>?, type: Boolean): AVLNode<T>? {
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
        return simpleContains(root, AVLNode(data))?.data
    }

    private fun updateChildrenHeight(node: AVLNode<T>?) {
        node?.left?.updateHeight()
        node?.right?.updateHeight()
    }
}