/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees.interfaces

interface Node<T : Comparable<T>, Subtype : Node<T, Subtype>> : Comparable<Node<T, Subtype>> {
    val data: T
    val left: Subtype?
    val right: Subtype?
    val parent: Subtype?
}
