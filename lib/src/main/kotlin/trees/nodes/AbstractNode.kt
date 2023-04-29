/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees.nodes

import trees.interfaces.Node

abstract class AbstractNode<T : Comparable<T>, Subtype : AbstractNode<T, Subtype>> : Node<T, Subtype> {
    //stores the data value of the current node
    abstract override var data: T
        internal set

    //stores the left subtree of the current node
    abstract override var left: Subtype?
        internal set

    //stores the right subtree of the current node
    abstract override var right: Subtype?
        internal set

    //stores the parent node of the current node
    abstract override var parent: Subtype?
        internal set

    //method to compare nodes based on their data values
    override fun compareTo(other: Node<T, Subtype>): Int {
        return data.compareTo(other.data)
    }

    //method to get the hash code of the current node
    override fun hashCode(): Int {
        return data.hashCode()
    }

    //method for comparing the current node with another object,
    // checking for equality of the value of the data property
    override fun equals(other: Any?): Boolean {
        if (other is AbstractNode<*, *>) {
            return data == other.data
        }
        return false
    }

    //returns a string representation of the node in data format.
    override fun toString(): String {
        return "$data"
    }
}