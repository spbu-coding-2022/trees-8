/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app.trees.nodes

enum class Color {
    RED, BLACK
}

class RBNode<T : Comparable<T>>(
    override var data: T,
    var color: Color = Color.RED,
    override var left: RBNode<T>? = null,
    override var right: RBNode<T>? = null,
    override var parent: RBNode<T>? = null,
) : AbstractNode<T, RBNode<T>>() {
    override fun toString(): String {
        return "$color - $data"
    }
}