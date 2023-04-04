/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees.interfaces

interface Tree<T : Comparable<T>> {
    fun add(data: T)
    fun contain(data: T): Boolean
    fun delete(data: T)
}