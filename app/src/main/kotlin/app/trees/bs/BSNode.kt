/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app.trees.bs

import app.trees.abs.MyNode

data class BSNode<T : Comparable<T>>(
    override var data: T,
    override var left: BSNode<T>? = null,
    override var right: BSNode<T>? = null,
    override var parent: BSNode<T>? = null,
) : MyNode<T, BSNode<T>>() {


    override fun equals(other: Any?): Boolean {
        if (other is BSNode<*>) {
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