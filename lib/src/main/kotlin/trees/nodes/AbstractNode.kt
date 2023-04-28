/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees.nodes

import trees.interfaces.Node

abstract class AbstractNode<T : Comparable<T>, Subtype : AbstractNode<T, Subtype>> : Node<T, Subtype> {
    abstract override var data: T
        internal set
    abstract override var left: Subtype?
        internal set
    abstract override var right: Subtype?
        internal set
    abstract override var parent: Subtype?
        internal set

    override fun compareTo(other: Node<T, Subtype>): Int {
        return data.compareTo(other.data)
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other is AbstractNode<*, *>) {
            return data == other.data
        }
        return false
    }

    override fun toString(): String {
        return "$data"
    }
}