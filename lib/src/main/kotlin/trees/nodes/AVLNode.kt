/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees.nodes

class AVLNode<T : Comparable<T>>(
    //stores the data value of the current node
    override var data: T,
    //Stores the height of the tree
    internal var height: Int = 1,
    //stores the left subtree of the current node
    override var left: AVLNode<T>? = null,
    //stores the right subtree of the current node
    override var right: AVLNode<T>? = null,
    //stores the parent node of the current node
    override var parent: AVLNode<T>? = null,
) : AbstractNode<T, AVLNode<T>>() {

    //Updates the node's height value based on the height of its left and right subtrees.
// Gets the height of the left subtree and right subtree,
// then sets the height of the node to 1 + the maximum height of the left and right subtrees.
// This function is used to maintain balance in the tree.
    internal fun updateHeight() {
        val leftNode = left
        val rightNode = right
        val leftHeight = leftNode?.height ?: 0
        val rightHeight = rightNode?.height ?: 0
        height = 1 + maxOf(leftHeight, rightHeight)
    }

    //Calculates the balance factor of a node,
//which is the difference between the height of the right subtree and the height of the left subtree.
// The function is used to determine if the tree needs to be rebalanced.
//If the balance factor is greater than 1 or less than -1, then the tree needs to be rebalanced.
    internal fun balanceFactor(): Int {
        val leftNode = left
        val rightNode = right
        return (rightNode?.height ?: 0) - (leftNode?.height ?: 0)
    }

}