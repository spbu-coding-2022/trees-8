package trees

import Node

/*
 * Copyright 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */


abstract class ABSTree<T : Comparable<T>, NodeType : Node<T, NodeType>> : Tree<T> {
    protected var root: NodeType? = null

    fun rebalance(initNode: NodeType): NodeType {
        return initNode
    }

    fun simpleAdd(initNode: NodeType?, node: NodeType): NodeType {

        if (initNode == null) {
            return node
        }

        if (initNode < node) {
            initNode.right = simpleAdd(initNode.right, node)
            initNode.right?.parent = initNode
        } else {
            initNode.left = simpleAdd(initNode.left, node)
            initNode.left?.parent = initNode
        }
        return rebalance(initNode)
    }

    fun simpleDelete(initNode: NodeType?, node: NodeType): NodeType? {
        if (initNode == null) {
            return null
        }
        if (initNode < node) {
            initNode.right = simpleDelete(initNode.right, node)
            initNode.right?.parent = initNode
        } else if (initNode > node) {
            initNode.left = simpleDelete(initNode.left, node)
            initNode.left?.parent = initNode
        } else {
            if ((initNode.left == null) or (initNode.right == null)) {
                return initNode.left ?: initNode.right
            } else {
                initNode.right?.let {
                    val tmp = getMinimal(it)
                    initNode.data = tmp.data
                    initNode.right = simpleDelete(initNode.right, tmp)
                    initNode.right?.parent = initNode
                }
            }
        }
        return rebalance(initNode)
    }

    fun simpleContains(initNode: NodeType?, node: NodeType): NodeType? {
        if (initNode == null) {
            return null
        }

        return if (initNode < node) {
            simpleContains(initNode.right, node)
        } else if (initNode > node) {
            simpleContains(initNode.left, node)
        } else {
            initNode
        }
    }

    fun getMinimal(node: NodeType): NodeType {
        var minNode = node
        while (true) {
            minNode = minNode.left ?: break
        }
        return minNode
    }

    fun getMaximal(node: NodeType): NodeType {
        var maxNode = node
        while (true) {
            maxNode = maxNode.left ?: break
        }
        return maxNode
    }

    fun rotateLeft(node: NodeType): NodeType? {
        val rightChild = node.right
        val secondSubtree = rightChild?.left
        rightChild?.left = node
        rightChild?.left?.parent = rightChild
        node.right = secondSubtree
        node.right?.parent = node
        return rightChild
    }

    fun rotateRight(node: NodeType): NodeType? {
        val leftChild = node.left
        val secondSubtree = leftChild?.right
        leftChild?.right = node
        leftChild?.right?.parent = leftChild
        node.left = secondSubtree
        node.left?.parent = node
        return leftChild
    }
}