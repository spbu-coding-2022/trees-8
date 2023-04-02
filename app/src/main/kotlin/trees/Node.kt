/*
 * Copyright 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

interface Node<T : Comparable<T>, Subtype : Node<T, Subtype>> : Comparable<Node<T, Subtype>> {
    var data: T
    var left: Subtype?
    var right: Subtype?
    var parent: Subtype?
}
