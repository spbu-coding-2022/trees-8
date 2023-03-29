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
    fun simpleAdd(data: NodeType) {
        if (root == null) {
            root = data
            return
        }
        var curNode = root!!
        while (true) {

            if (data < curNode) {
                if (curNode.left == null) {
                    data.parent = curNode
                    curNode.left = data
                    break
                } else {
                    curNode = curNode.left!!
                }

            } else if (data > curNode) {
                if (curNode.right == null) {
                    data.parent = curNode
                    curNode.right = data
                    break
                } else {
                    curNode = curNode.right!!
                }
            }
        }
    }

    fun simpleContains(node: NodeType): NodeType? {
        if (root == null) {
            return null
        }
        var curNode = root!!

        while (true) {

            if (node < curNode) {
                if (curNode.left == null) {
                    break
                } else {
                    curNode = curNode.left!!
                }

            } else if (node > curNode) {
                if (curNode.right == null) {
                    break
                } else {
                    curNode = curNode.right!!
                }
            } else if (node == curNode) {
                return curNode
            }
        }

        return null
    }

    fun simpleDelete(node: NodeType) {

        if (node == root) {
            if ((node.left == null) and (node.right != null)) {
                root = node.right
            } else if ((node.right == null) and (node.left != null)) {
                root = node.left
            } else if ((node.right == null) and (node.left == null)) {
                root = null
            } else {
                val curNode = root?.right ?: error("Not reachable")
                val newNode = getMinimal(curNode)
                if (newNode.parent?.left == newNode) {
                    newNode.parent?.left = null
                } else {
                    newNode.parent?.right = null
                }
                newNode.left = root?.left
                newNode.right = root?.right
                root = newNode
            }
            root?.parent = null
            return
        }
        val parent = node.parent ?: error("Not reachable")
        val isRight = (parent.right == node)

        if ((node.left == null) and (node.right != null)) {
            if (isRight) {
                parent.right = node.right
                node.right?.parent = parent
            } else {
                parent.left = node.right
                node.right?.parent = parent
            }

        } else if ((node.right == null) and (node.left != null)) {
            if (isRight) {
                parent.right = node.left
                node.left?.parent = parent
            } else {
                parent.left = node.left
                node.left?.parent = parent
            }

        } else if ((node.right == null) and (node.left == null)) {
            if (isRight) {
                parent.right = null
            } else {
                parent.left = null
            }
        } else {
            val newNode = node.right?.let { getMinimal(it) }
            if (newNode?.parent?.left == newNode) {
                newNode?.parent?.left = null
            } else {
                newNode?.parent?.right = null
            }
            if (isRight) {
                parent.right = newNode
            } else {
                parent.left = newNode
            }
            newNode?.parent = parent
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
}