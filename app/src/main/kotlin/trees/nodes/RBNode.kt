/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees.nodes

enum class Color {
    RED,
    BLACK
}

class RBNode<T : Comparable<T>>(override var data: T) : MyNode<T, RBNode<T>>() {
    var color: Color = Color.RED
    override var left: RBNode<T>? = null
    override var right: RBNode<T>? = null
    override var parent: RBNode<T>? = null

    override fun equals(other: Any?): Boolean {
        if (other is RBNode<*>) {
            return data == other.data
        }
        return false
    }

    override fun toString(): String {
        return "$color - $data"
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }
}