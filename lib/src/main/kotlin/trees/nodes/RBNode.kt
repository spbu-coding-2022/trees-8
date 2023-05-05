/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees.nodes

//enum that defines two colors: RED and BLACK
enum class Color {
    RED, BLACK
}

class RBNode<T : Comparable<T>>(
    //stores the data value of the current node
    override var data: T,
    //node color, default is set to RED
    var color: Color = Color.RED,
    //stores the left subtree of the current node
    override var left: RBNode<T>? = null,
    //stores the right subtree of the current node
    override var right: RBNode<T>? = null,
    //stores the parent node of the current node
    override var parent: RBNode<T>? = null,
) : AbstractNode<T, RBNode<T>>() {

    //an overridden toString function that returns a string
    //representation of a node containing its color and data.
    override fun toString(): String {
        return "$color - $data"
    }
}