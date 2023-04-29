/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

import trees.KeyValue

class DrawableNode(
    var data: KeyValue<Int, String>,
    var x: Long,
    var y: Long
) : Comparable<DrawableNode> {
    override fun compareTo(other: DrawableNode): Int {
        return data.compareTo(other.data)
    }
}