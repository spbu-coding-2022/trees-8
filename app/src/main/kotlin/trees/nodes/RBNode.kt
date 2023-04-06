/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees.nodes

import trees.interfaces.Node

enum class Color {
    RED,
    BLACK
}

class RBNode<T : Comparable<T>>(override var data: T) : Node<T, RBNode<T>> {
    var color: Color = Color.RED
    override var left: RBNode<T>? = null
    override var right: RBNode<T>? = null
    override var parent: RBNode<T>? = null

    override fun compareTo(other: Node<T, RBNode<T>>): Int {
        return data.compareTo(other.data)
    }

    override fun equals(other: Any?): Boolean {
        if (other is RBNode<*>) {
            return data.equals(other.data)
        }
        return false
    }

    override fun toString(): String {
        return "$color - $data"
    }
}