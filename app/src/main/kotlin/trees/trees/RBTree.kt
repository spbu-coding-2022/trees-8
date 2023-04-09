/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees.trees

import trees.ABSTree
import trees.nodes.Color
import trees.nodes.RBNode
import kotlin.coroutines.CoroutineContext

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
        if (!type) { //for balancing add
            var current = initNode
            if (current == root) {
                current?.color = Color.BLACK
                return balance(initNode)
            }
            while (current != root && current?.parent?.color == Color.RED) {
                if (current.parent == current.parent?.parent?.left) {
                    if (current.parent?.parent?.right?.color == Color.RED) {
                        current.parent?.color = Color.BLACK
                        current.parent?.parent?.right?.color = Color.BLACK
                        current.parent?.parent?.color = Color.RED
                        current = current.parent?.parent
                    } else {
                        if (current == current.parent?.right) {
                            current = current.parent!!
                            rotateLeft(current)
                            current.parent?.color = Color.BLACK
                            current.parent?.parent?.color = Color.RED
                            rotateRight(current.parent?.parent!!)
                        }
                    }
                } else {
                    if (current.parent?.parent?.left?.color == Color.RED) {
                        current.parent?.color = Color.BLACK
                        current.parent?.parent?.left?.color = Color.BLACK
                        current.parent?.parent?.color = Color.RED
                        current = current.parent?.parent
                    } else {
                        if (current == current.parent?.left) {
                            current = current.parent!!
                            rotateRight(current)
                            current.parent?.color = Color.BLACK
                            current.parent?.parent?.color = Color.RED
                            rotateLeft(current.parent!!.parent!!)
                        }
                    }
                }
            }
            root?.color = Color.BLACK
        }
        if (type) { //for balancing delete
            var current = initNode
            while ((current != root) && (current?.color == Color.BLACK)) {
                if (current == current.parent?.left) {
                    if (current.parent?.right?.color == Color.RED) {
                        current.parent?.right?.color == Color.BLACK
                        current.parent?.color = Color.RED
                        rotateLeft(current.parent!!)
                    }
                    if ((current.parent?.right?.right?.color == Color.BLACK) && (current.parent?.right?.left?.color == Color.BLACK)) {
                        current.parent?.right?.color = Color.RED
                    }
                    else {
                        if (current.parent?.right?.right?.color == Color.BLACK) {
                            current.parent?.right?.left?.color = Color.BLACK
                            current.parent?.right?.color = Color.RED
                            rotateRight(current.parent?.right!!)
                        }
                        current.parent?.right?.color = current.parent!!.color
                        current.parent?.color = Color.BLACK
                        current.parent?.right?.right?.color = Color.BLACK
                        rotateLeft(current.parent!!)
                        current = root
                    }
                }
                else {
                    if (current.parent?.left?.color == Color.RED) {
                        current.parent?.left?.color == Color.BLACK
                        current.parent?.color = Color.RED
                        rotateRight(current.parent!!)
                    }
                    if ((current.parent?.left?.right?.color == Color.BLACK) && (current.parent?.left?.left?.color == Color.BLACK)) {
                        current.parent?.left?.color = Color.RED
                    }
                    else {
                        if (current.parent?.left?.left?.color == Color.BLACK) {
                            current.parent?.left?.right?.color = Color.BLACK
                            current.parent?.left?.color = Color.RED
                            rotateLeft(current.parent?.left!!)
                        }
                        current.parent?.left=current.parent
                        current.parent?.color=Color.BLACK
                        current.parent?.left?.left?.color=Color.BLACK
                        rotateRight(current.parent!!)
                        current = root
                    }
                }
            }
            current?.color=Color.BLACK
            root?.color=Color.BLACK
        }
        return balance(initNode)
    }
}