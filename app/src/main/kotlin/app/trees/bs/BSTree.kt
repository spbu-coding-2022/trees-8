/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app.trees.bs

import app.trees.abs.ABSTree

class BSTree<T : Comparable<T>> : ABSTree<T, BSNode<T>>() {


    override fun add(data: T) {
        root = defaultAdd(root, BSNode(data))
    }

    override fun contains(data: T): Boolean {
        return (defaultContains(root, BSNode(data)) != null)
    }

    override fun delete(data: T) {
        root = defaultDelete(root, BSNode(data))
        root?.parent = null
    }

    fun get(data: T): T? {
        return defaultContains(root, BSNode(data))?.data
    }
}