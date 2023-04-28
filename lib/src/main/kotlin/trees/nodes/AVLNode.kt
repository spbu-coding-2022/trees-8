/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees.nodes

class AVLNode<T : Comparable<T>>(
    override var data: T,
    internal var height: Int = 1,
    override var left: AVLNode<T>? = null,
    override var right: AVLNode<T>? = null,
    override var parent: AVLNode<T>? = null,
) : AbstractNode<T, AVLNode<T>>() {


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

}