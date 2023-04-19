/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

import app.trees.trees.AVLTree
import kotlin.random.Random

fun main(args: Array<String>) {
    val tree = AVLTree<Int>()
    val lst = List(100) { Random.nextInt(1000) }
    lst.forEach { tree.add(it) }
    tree.root = null
    println(tree.preOrder())
}