/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app.trees.nodes

class BSNode<T : Comparable<T>>(
    //stores the data value of the current node
    override var data: T,
    //stores the left subtree of the current node
    override var left: BSNode<T>? = null,
    //stores the right subtree of the current node
    override var right: BSNode<T>? = null,
    //stores the parent node of the current node
    override var parent: BSNode<T>? = null,
) : AbstractNode<T, BSNode<T>>()