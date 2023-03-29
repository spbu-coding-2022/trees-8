/*
 * Copyright 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

interface Tree<T : Comparable<T>> {
    fun add(data: T)
    fun contain(data: T): Boolean
    fun delete(data: T)
}

abstract class ABSTree<T : Comparable<T>, NodeType : Node<T, NodeType>> : Tree<T> {
    protected var root: NodeType? = null

    fun simpleAdd(initNode: NodeType?, node: NodeType): NodeType {

        if (initNode == null) {
            return node
        }

        if (initNode < node) {
            initNode.right = simpleAdd(initNode.right, node)
        } else {
            initNode.left = simpleAdd(initNode.left, node)
        }
        return initNode
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

    fun simpleDelete(initNode: NodeType?, node: NodeType): NodeType? {
        if (initNode == null) {
            return null
        }
        if (initNode < node) {
            initNode.right = simpleDelete(initNode.right, node)
        } else if (initNode > node) {
            initNode.left = simpleDelete(initNode.left, node)
        } else {
            if ((initNode.left == null) or (initNode.right == null)) {
                return initNode.left ?: initNode.right
            } else {
                val tmp = getMinimal(initNode.right ?: error("Not reachable"))
                initNode.data = tmp.data
                initNode.right = simpleDelete(initNode.right, tmp)
            }
        }
        return initNode
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
        node.right = secondSubtree
        return rightChild
    }

    fun rotateRight(node: NodeType): NodeType? {
        val leftChild = node.left
        val secondSubtree = leftChild?.right
        leftChild?.right = node
        node.left = secondSubtree
        return leftChild
    }
}