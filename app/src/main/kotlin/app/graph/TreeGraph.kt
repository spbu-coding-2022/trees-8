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

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TreeGraph(
    root: DrawableNode,
    nodeSize: Dp,
    onNodeDrag: (DrawableNode, DpOffset) -> Unit,
    graphState: GraphState = rememberGraphState()
) {
    val currentDensity = LocalDensity.current
    fun centerGraph(viewWidth: Int) {
        currentDensity.run {
            graphState.resetGraphView(
                dragX = viewWidth / 2 - (nodeSize / 2 + root.x).toPx(),
                dragY = nodeSize.toPx()
            )
        }
    }

    var graphViewWidth = 0
    Box(modifier = Modifier
        .fillMaxSize()
        .onPointerEvent(PointerEventType.Scroll) {
            graphState.handleScroll(
                it.changes.first().scrollDelta,
                it.changes.first().position
            )
        }
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                graphState.handleScreenDrag(dragAmount)
            }
        }
        .onSizeChanged {
            graphViewWidth = it.width
        }
    ) {
        LaunchedEffect(Unit) {
            centerGraph(graphViewWidth)
        }

        drawTree(
            node = root,
            nodeSize = nodeSize,
            onNodeDrag = onNodeDrag,
            sDragProvider = { graphState.screenDrag },
            sScaleProvider = { graphState.screenZoom }
        )

        TextButton(
            modifier = Modifier.align(Alignment.TopEnd).padding(10.dp),
            onClick = { centerGraph(graphViewWidth) }
        ) {
            Text("Reset view")
        }
    }
}

@Composable
fun drawTree(
    node: DrawableNode?,
    parent: DrawableNode? = null,
    nodeSize: Dp,
    onNodeDrag: (DrawableNode, DpOffset) -> Unit,
    sDragProvider: () -> Offset,
    sScaleProvider: () -> ScreenZoom
) {
    node?.let {
        parent?.let { parent ->
            GraphLine(
                start = parent,
                end = node,
                nodeSize = nodeSize,
                sDragProvider = sDragProvider,
                sScaleProvider = sScaleProvider
            )
        }

        drawTree(node.left, node, nodeSize, onNodeDrag, sDragProvider, sScaleProvider)
        drawTree(node.right, node, nodeSize, onNodeDrag, sDragProvider, sScaleProvider)

        GraphNode(
            node = node,
            nodeSize = nodeSize,
            onNodeDrag = onNodeDrag,
            sDragProvider = sDragProvider,
            sScaleProvider = sScaleProvider
        )
    }
}
