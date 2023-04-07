/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees.trees

import trees.ABSTree
import trees.nodes.Color
import trees.nodes.RBNode

class RBTree<T : Comparable<T>> : ABSTree<T, RBNode<T>>() {
    override fun add(data: T) {
        val node = RBNode(data)
        root = simpleAdd(root, node)
        root = balanceAdd(node, root)
        root?.parent = null
        root?.color = Color.BLACK
    }

    override fun contain(data: T): Boolean {
        return (simpleContains(root, RBNode(data)) != null)
    }

    fun get(data: T): T? {
        return simpleContains(root, RBNode(data))?.data
    }

    override fun delete(data: T) {
        root = simpleDelete(root, RBNode(data))
    }

    fun balanceAdd(initNode: RBNode<T>?, subRoot: RBNode<T>?): RBNode<T>? {
        if (initNode?.parent == null) return subRoot
        var newRoot = subRoot
        var current = initNode
        while (current?.parent?.color == Color.RED) {
            val grandpa = current.parent?.parent
            if (current.parent == current.parent?.parent?.left) {
                if (current.parent?.parent?.right?.color == Color.RED) {
                    current.parent?.color = Color.BLACK
                    current.parent?.parent?.right?.color = Color.BLACK
                    current.parent?.parent?.color = Color.RED
                    current = current.parent?.parent
                } else {
                    if (current == current.parent?.right) {
                        current = current.parent
                        newRoot = current?.let { clearRotateLeft(it, newRoot) }
                    }
                    current?.parent?.color = Color.BLACK
                    current?.parent?.parent?.color = Color.RED
                    newRoot = current?.parent?.parent?.let { clearRotateRight(it, newRoot) }

                }
            } else {
                if (current.parent?.parent?.left?.color == Color.RED) {
                    current.parent?.color = Color.BLACK
                    current.parent?.parent?.left?.color = Color.BLACK
                    current.parent?.parent?.color = Color.RED
                    current = current.parent?.parent
                } else {
                    if (current == current.parent?.left) {
                        current = current.parent
                        newRoot = current?.let { clearRotateRight(it, newRoot) }
                    }
                    current?.parent?.color = Color.BLACK
                    current?.parent?.parent?.color = Color.RED
                    newRoot = current?.parent?.parent?.let { clearRotateLeft(it, newRoot) }

                }
            }
        }
        newRoot?.color = Color.BLACK
        return newRoot ?: root
    }

    fun balanceDelete(initNode: RBNode<T>?): RBNode<T>? {
        var current = initNode
        while ((current != root) && (current?.color == Color.BLACK)) {
            val parent = current.parent
            val uncle = parent?.right
            var ant = parent?.left
            if (current == ant) {
                if (uncle?.color == Color.RED) {
                    uncle.color = Color.BLACK
                    parent.color = Color.RED
                    current.parent?.let { rotateLeft(it) }
                }
                if ((uncle?.right?.color == Color.BLACK) && (uncle.left?.color == Color.BLACK)) {
                    uncle.color = Color.RED
                } else {
                    if (uncle?.right?.color == Color.BLACK) {
                        uncle.left?.color = Color.BLACK
                        uncle.color = Color.RED
                        rotateRight(uncle)
                    }
                    uncle?.color = parent?.color!!
                    current.parent?.color = Color.BLACK
                    uncle?.right?.color = Color.BLACK
                    current.parent?.let { rotateLeft(it) }
                    current = root
                }
            } else {
                if (ant?.color == Color.RED) {
                    ant.color = Color.BLACK
                    current.parent?.color = Color.RED
                    current.parent?.let { rotateRight(it) }
                }
                if ((ant?.right?.color == Color.BLACK) && (ant.left?.color == Color.BLACK)) {
                    ant.color = Color.RED
                } else {
                    if (ant?.left?.color == Color.BLACK) {
                        ant.right?.color = Color.BLACK
                        ant.color = Color.RED
                        rotateLeft(ant)
                    }
                    ant = current.parent
                    current.parent?.color = Color.BLACK
                    ant?.left?.color = Color.BLACK
                    current.parent?.let { rotateRight(it) }
                    current = root
                }
            }
        }
        current?.color = Color.BLACK
        root?.color = Color.BLACK
        return balance(initNode)
    }

    fun clearRotateLeft(node: RBNode<T>?, root: RBNode<T>?): RBNode<T>? {
        if (node?.right == null) return null

        var newRoot = root
        val parent = node.parent
        val subTree = rotateLeft(node)

        if (parent == null) {
            newRoot = subTree
        } else if (parent.left == node) {
            parent.left = subTree
        } else {
            parent.right = subTree
        }

        return newRoot
    }

    fun clearRotateRight(node: RBNode<T>?, root: RBNode<T>?): RBNode<T>? {
        if (node?.left == null) return null

        var newRoot = root
        val parent = node.parent
        val subTree = rotateRight(node)

        if (parent == null) {
            newRoot = subTree
        } else if (parent.left == node) {
            parent.left = subTree
        } else {
            parent.right = subTree
        }

        return newRoot
    }
}