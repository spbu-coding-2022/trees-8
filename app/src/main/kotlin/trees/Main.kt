/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

import trees.KeyValue
import trees.trees.BSTree

/*
 * Copyright 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

fun main(args: Array<String>) {
    val tree = BSTree<KeyValue<Int, Int>>()
    tree.add(KeyValue(10, 100))
    println(tree.get(KeyValue(10, null)))
}