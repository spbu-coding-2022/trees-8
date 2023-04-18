/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app.trees.nodes

data class AVLNode<T : Comparable<T>>(
    override var data: T,
    internal var height: Int = 1,
    override var left: AVLNode<T>? = null,
    override var right: AVLNode<T>? = null,
    override var parent: AVLNode<T>? = null,
) : MyNode<T, AVLNode<T>>() {


    internal fun updateHeight() {
        val leftNode = left
        val rightNode = right
        val leftHeight = leftNode?.height ?: 0
        val rightHeight = rightNode?.height ?: 0
        height = 1 + maxOf(leftHeight, rightHeight)
    }

    internal fun balanceFactor(): Int {
        val leftNode = left
        val rightNode = right
        return (rightNode?.height ?: 0) - (leftNode?.height ?: 0)
    }

    override fun equals(other: Any?): Boolean {
        if (other is AVLNode<*>) {
            return data == other.data
        }
        return false
    }

    override fun toString(): String {
        return "$data"
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }
}