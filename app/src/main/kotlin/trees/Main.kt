/*
 * Copyright 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

fun main(args: Array<String>) {
    val tree = BSTree<Int, Int>()
    tree.add(KeyValue(10, 100))
    println(tree.get(10))
}