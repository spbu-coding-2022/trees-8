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

    override fun delete(data: T) {
        if (!contain(data)) return

        val node = simpleContains(root, RBNode(data)) ?: return
        val next: RBNode<T>

        if ((node.left == null) && (node.right == null)) {
            if (node == root) {
                root = null
            } else {
                if (node.color == Color.RED) {
                    // delete red node without child
                    replaceChild(node, null)
                } else {
                    // delete black node without children
                    root = balanceDelete(node)
                }
            }
            return
        }

        if ((node.left == null) || (node.right == null)) {
            // delete for black node with one red child
            replaceChild(node, node.right ?: node.left)?.color = Color.BLACK
        } else {
            // delete node with two children
            next = getMinimal(node.right ?: error("Node must have right child"))
            node.data = next.data.also { next.data = node.data }
            if (next.color == Color.RED) {
                // delete red node without child
                replaceChild(next, null)
            } else {
                if (next.right == null) {
                    root = balanceDelete(next)
                } else {
                    // delete for black node with one child
                    replaceChild(next, next.right)?.color = Color.BLACK // next.right must be red and not null
                }
            }
        }
    }

    override fun contain(data: T): Boolean {
        return (simpleContains(root, RBNode(data)) != null)
    }

    fun balanceDelete(node: RBNode<T>?): RBNode<T>? {
        var newRoot = root
        var current = node

        while ((current != newRoot) && (current?.color == Color.BLACK)) {
            if (current == current.parent?.left) {
                var brother = current.parent?.right
                if (brother?.color == Color.RED) {
                    brother.color = Color.BLACK
                    current.parent?.color = Color.RED
                    newRoot = clearRotateLeft(current.parent, newRoot)
                    brother = current.parent?.right
                }
                if ((brother?.left?.color == Color.BLACK) && (brother.right?.color == Color.BLACK)) {
                    brother.color = Color.RED
                    current = current.parent
                } else {
                    if (brother?.right?.color == Color.BLACK) {
                        brother.left?.color = Color.BLACK
                        brother.color = Color.RED
                        newRoot = clearRotateRight(brother, newRoot)
                        brother = current.parent?.right
                    }
                    brother?.color = current.parent?.color ?: Color.BLACK
                    current.parent?.color = Color.BLACK
                    brother?.right?.color = Color.BLACK
                    newRoot = clearRotateLeft(current.parent, newRoot)
                    current = newRoot
                }
            } else {
                var brother = current.parent?.left
                if (brother?.color == Color.RED) {
                    brother.color = Color.BLACK
                    current.parent?.color = Color.RED
                    newRoot = clearRotateRight(current.parent, newRoot)
                    brother = current.parent?.left
                }
                if (brother?.right?.color == Color.BLACK && brother.left?.color == Color.BLACK) {
                    brother.color = Color.RED
                    current = current.parent
                } else {
                    if (brother?.left?.color == Color.BLACK) {
                        brother.right?.color = Color.BLACK
                        brother.color = Color.RED
                        newRoot = clearRotateLeft(brother, newRoot)
                        brother = current.parent?.left
                    }
                    brother?.color = current.parent?.color ?: Color.BLACK
                    current.parent?.color = Color.BLACK
                    brother?.left?.color = Color.BLACK
                    newRoot = clearRotateRight(current.parent, newRoot)
                    current = newRoot
                }
            }
        }
        current?.color = Color.BLACK
        return newRoot
    }

    fun balanceAdd(initNode: RBNode<T>?, subRoot: RBNode<T>?): RBNode<T>? {
        if (initNode?.parent == null) return subRoot
        var newRoot = subRoot
        var current = initNode
        while (current?.parent?.color == Color.RED) {
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

    fun get(data: T): T? {
        return simpleContains(root, RBNode(data))?.data
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