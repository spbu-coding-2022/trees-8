/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app.trees.interfaces

interface Tree<T : Comparable<T>> {
    fun add(data: T)
    fun contains(data: T): Boolean
    fun delete(data: T)
}