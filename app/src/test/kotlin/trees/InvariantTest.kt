/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package trees

import trees.interfaces.Node
import trees.nodes.AVLNode
import trees.nodes.Color
import trees.nodes.RBNode
import trees.trees.RBTree
import kotlin.math.abs

object InvariantTest {
    fun <E : Comparable<E>, NodeType : Node<E, NodeType>> checkLinksToParent(node: Node<E, NodeType>?): Boolean {
        if (node == null) return true
        var result = true
        result = result && ((node.left?.parent == node) || (node.left == null))
        result = result && ((node.right?.parent == node) || (node.right == null))
        if (result) {
            return checkLinksToParent(node.left) && checkLinksToParent(node.right)
        }
        return false
    }

    fun <E : Comparable<E>, NodeType : Node<E, NodeType>> checkDataInNodes(node: Node<E, NodeType>?): Boolean {
        if (node == null) return true
        var result = true
        val leftChild = node.left
        val rightChild = node.right
        result = result && ((leftChild?.data == null) || (leftChild.data < node.data))
        result = result && ((rightChild?.data == null) || (rightChild.data > node.data))
        if (result) {
            return checkDataInNodes(node.left) && checkDataInNodes(node.right)
        }
        return false
    }

    fun <E : Comparable<E>> checkHeightAVL(node: AVLNode<E>?): Boolean {
        if (node == null) return true
        var result = true
        val rightHeight = node.right?.height ?: 0
        val leftHeight = node.left?.height ?: 0
        result = result && (maxOf(rightHeight, leftHeight) + 1 == node.height)
        result = result && (abs(node.balanceFactor()) <= 1)
        if (result) {
            return checkHeightAVL(node.right) && checkHeightAVL(node.left)
        }
        return false
    }

    fun <E : Comparable<E>> checkBlackHeight(node: RBNode<E>?): Boolean {
        fun <E : Comparable<E>> getBlackHeightRB(node: RBNode<E>?): Pair<Boolean, Int> {
            if (node == null) return Pair(true, 1)
            val leftHeight = getBlackHeightRB(node.left)
            val rightHeight = getBlackHeightRB(node.right)
            if (leftHeight.second != rightHeight.second || !leftHeight.first || !rightHeight.first) {
                return Pair(false, rightHeight.second + (if (node.color == Color.BLACK) 1 else 0))
            }
            return if (node.color == Color.BLACK)
                Pair(true, rightHeight.second + 1)
            else Pair(true, rightHeight.second)
        }
        return getBlackHeightRB(node).first
    }

    fun <E : Comparable<E>> checkRedParent(node: RBNode<E>?): Boolean {
        if (node == null) return true
        if (RBTree.isRed(node)) {
            if ((RBTree.isRed(node.left)) or (RBTree.isRed(node.right)))
                return false
        }
        return checkRedParent(node.left) && checkRedParent(node.right)
    }
}
