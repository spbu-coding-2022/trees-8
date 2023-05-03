/*
 MIT License

Copyright (c) 2023-present Yakshigulov Vadim, Dyachkov Vitaliy, Perevalov Efim

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
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
