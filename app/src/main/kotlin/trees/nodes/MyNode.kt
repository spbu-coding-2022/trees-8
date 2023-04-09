/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees.nodes/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

import trees.interfaces.Node

abstract class MyNode<T : Comparable<T>, Subtype : MyNode<T, Subtype>> : Node<T, Subtype> {
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
}