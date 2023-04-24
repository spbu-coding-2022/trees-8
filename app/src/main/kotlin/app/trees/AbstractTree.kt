/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app.trees

import app.trees.interfaces.Tree
import app.trees.nodes.AbstractNode

abstract class AbstractTree<T : Comparable<T>, NodeType : AbstractNode<T, NodeType>> : Tree<T> {
    var root: NodeType? = null
        internal set

    protected open fun balance(initNode: NodeType?): NodeType? {
        return initNode
    }

    protected fun balancedAdd(initNode: NodeType?, node: NodeType): NodeType? {

        if (initNode == null) {
            return node
        }

        if (initNode < node) {
            initNode.right = balancedAdd(initNode.right, node)
            initNode.right?.parent = initNode
        } else if (initNode > node) {
            initNode.left = balancedAdd(initNode.left, node)
            initNode.left?.parent = initNode
        }
        return balance(initNode)
    }

    protected fun balancedDelete(initNode: NodeType?, node: NodeType): NodeType? {
        if (initNode == null) {
            return null
        }
        if (initNode < node) {
            initNode.right = balancedDelete(initNode.right, node)
            initNode.right?.parent = initNode
        } else if (initNode > node) {
            initNode.left = balancedDelete(initNode.left, node)
            initNode.left?.parent = initNode
        } else {
            if ((initNode.left == null) || (initNode.right == null)) {
                return initNode.left ?: initNode.right
            } else {
                initNode.right?.let {
                    val tmp = getMinimal(it)
                    initNode.data = tmp.data
                    initNode.right = balancedDelete(initNode.right, tmp)
                    initNode.right?.parent = initNode
                }
            }
        }
        return balance(initNode)
    }

    protected fun contains(initNode: NodeType?, node: NodeType): NodeType? {
        if (initNode == null) {
            return null
        }

        return if (initNode < node) {
            contains(initNode.right, node)
        } else if (initNode > node) {
            contains(initNode.left, node)
        } else {
            initNode
        }
    }

    protected fun getMinimal(node: NodeType): NodeType {
        var minNode = node
        while (true) {
            minNode = minNode.left ?: break
        }
        return minNode
    }

    protected fun getMaximal(node: NodeType): NodeType {
        var maxNode = node
        while (true) {
            maxNode = maxNode.left ?: break
        }
        return maxNode
    }

    protected fun rotateLeft(node: NodeType): NodeType? {
        val rightChild = node.right
        val secondSubtree = rightChild?.left
        rightChild?.left = node
        rightChild?.left?.parent = rightChild
        node.right = secondSubtree
        node.right?.parent = node
        return rightChild
    }

    protected fun rotateRight(node: NodeType): NodeType? {
        val leftChild = node.left
        val secondSubtree = leftChild?.right
        leftChild?.right = node
        leftChild?.right?.parent = leftChild
        node.left = secondSubtree
        node.left?.parent = node
        return leftChild
    }

    protected fun replaceChild(child: NodeType, newChild: NodeType?): NodeType? {
        if (child == root) {
            root = newChild
            newChild?.parent = null
            return newChild
        }
        if (child.parent?.left == child) {
            child.parent?.left = newChild
        } else if (child.parent?.right == child) {
            child.parent?.right = newChild
        }
        newChild?.parent = child.parent
        return newChild
    }

    fun preOrder(): List<NodeType> {
        val result = mutableListOf<NodeType>()
        fun walk(node: NodeType, lst: MutableList<NodeType>) {
            lst.add(node)
            node.left?.let { walk(it, lst) }
            node.right?.let { walk(it, lst) }
        }
        if (root == null) return result
        root?.let { walk(it, result) }
        return result
    }
}