/*
 * Copyright (c) 2023 teem-4
 * SPDX-License-Identifier: MIT
 */

package app.graph

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class DrawableNode(
    val key: Int,
    var value: String,
    var left: DrawableNode? = null,
    var right: DrawableNode? = null,
    var color: Color? = null,
    y: Dp = 0.dp,
    x: Dp = 0.dp,
) {
    var x by mutableStateOf(x)
    var y by mutableStateOf(y)
}
