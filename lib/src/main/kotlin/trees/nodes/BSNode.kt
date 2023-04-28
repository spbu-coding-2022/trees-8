/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees.nodes

class BSNode<T : Comparable<T>>(
    override var data: T,
    override var left: BSNode<T>? = null,
    override var right: BSNode<T>? = null,
    override var parent: BSNode<T>? = null,
) : AbstractNode<T, BSNode<T>>()