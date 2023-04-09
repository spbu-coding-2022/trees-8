/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees.nodes

import trees.interfaces.Node
class AVLNode<T : Comparable<T>>(override var data: T) : Node<T, AVLNode<T>> {
    var height: Int? = null
    override var left: AVLNode<T>? = null
    override var right: AVLNode<T>? = null
    override var parent: AVLNode<T>? = null


    fun updateHeight() {
        val leftNode = left
        val rightNode = right
        val leftHeight = if (leftNode != null) leftNode.height ?: 0 else 0
        val rightHeight = if (rightNode != null) rightNode.height ?: 0 else 0
        height = 1 + maxOf(leftHeight, rightHeight)
    }

    fun balanceFactor(): Int {
        val leftNode = left
        val rightNode = right
        return (rightNode?.height ?: 0) - (leftNode?.height ?: 0)
    }

    override fun compareTo(other: Node<T, AVLNode<T>>): Int {
        return data.compareTo(other.data)
    }

    override fun equals(other: Any?): Boolean {
        if (other is AVLNode<*>) {
            return data.equals(other.data)
        }
        return false
    }
}