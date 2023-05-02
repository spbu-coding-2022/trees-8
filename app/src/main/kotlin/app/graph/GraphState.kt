/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app.graph

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import kotlin.math.max
import kotlin.math.min

class GraphState {
    var screenZoom by mutableStateOf(ScreenZoom(1f, Offset(0f, 0f)))
        private set

    var screenDrag by mutableStateOf(Offset(0f, 0f))
        private set

    fun handleScroll(scrollDelta: Offset, scrollPosition: Offset) {
        screenDrag += Offset(-scrollDelta.x / screenZoom.scale * 25, 0f)

        val prevScale = screenZoom.scale
        val newScale = min(
            max(
                screenZoom.scale - scrollDelta.y / 20,
                0.1f
            ), 2f
        )
        val relScale = newScale / prevScale

        screenZoom = ScreenZoom(
            scale = newScale,
            offset = Offset(
                screenZoom.offset.x * relScale + scrollPosition.x * (1 - relScale),
                screenZoom.offset.y * relScale + scrollPosition.y * (1 - relScale)
            )
        )
    }

    fun handleScreenDrag(dragAmount: Offset) {
        screenDrag += Offset(
            dragAmount.x / screenZoom.scale,
            dragAmount.y / screenZoom.scale
        )
    }


    fun resetGraphView(dragX: Float = 0f, dragY: Float = 0f) {
        screenDrag = Offset(dragX, dragY)
        screenZoom = ScreenZoom(1f, Offset(0f, 0f))
    }

}

@Composable
fun rememberGraphState() = remember { GraphState() }
