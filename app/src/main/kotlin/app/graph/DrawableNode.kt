/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app.graph

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

interface ImDrawableNode {
    val key: Int
    val value: String
    val left: ImDrawableNode?
    val right: ImDrawableNode?
    val color: Color?
    val x: Dp
    val y: Dp
}

class DrawableNode(
    override val key: Int,
    override var value: String,
    override var left: DrawableNode? = null,
    override var right: DrawableNode? = null,
    override var color: Color? = null,
    y: Dp = 0.dp,
    x: Dp = 0.dp,
) : ImDrawableNode {
    override var x by mutableStateOf(x)
    override var y by mutableStateOf(y)

}
