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
        root = simpleAdd(root, RBNode(data))

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

    override fun balance(initNode: RBNode<T>?, type: Boolean): RBNode<T>? {
        return if (type) balanceAdd(initNode) else balanceDelete(initNode)
    }

    fun balanceAdd(initNode: RBNode<T>?): RBNode<T>? {
        var current = initNode
        if (current == root) {
            current?.color = Color.BLACK
            return initNode
        }
        while (current != root && current?.parent?.color == Color.RED) {
            val parent = current.parent
            val brother = parent?.right
            val sister = parent?.left
            val grandpa = parent?.parent
            if (parent == grandpa?.left) {
                if (grandpa?.right?.color == Color.RED) {
                    parent.color = Color.BLACK
                    grandpa.right?.color = Color.BLACK
                    grandpa.color = Color.RED
                    current = grandpa
                } else {
                    if (current == brother) {
                        current = parent
                        rotateLeft(current)
                        parent.color = Color.BLACK
                        grandpa?.color = Color.RED
                        grandpa?.let { rotateRight(it) }
                    }
                }
            } else {
                if (grandpa?.left?.color == Color.RED) {
                    parent.color = Color.BLACK
                    grandpa.left?.color = Color.BLACK
                    grandpa.color = Color.RED
                    current = grandpa
                } else {
                    if (current == sister) {
                        current = parent
                        rotateRight(current)
                        parent.color = Color.BLACK
                        grandpa?.color = Color.RED
                        grandpa?.let { rotateLeft(it) }
                    }
                }
            }
        }
        root?.color = Color.BLACK
        return initNode
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

}