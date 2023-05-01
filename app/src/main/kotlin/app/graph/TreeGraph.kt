/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
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
import visualizer.editor.graph.GraphNode
import visualizer.editor.graph.GraphState
import visualizer.editor.graph.ScreenScale
import visualizer.editor.graph.rememberGraphState


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TreeGraph(
    root: ImDrawableNode,
    nodeSize: Dp,
    onNodeDrag: (ImDrawableNode, DpOffset) -> Unit,
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
            sScaleProvider = { graphState.screenScale }
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
    node: ImDrawableNode?,
    parent: ImDrawableNode? = null,
    nodeSize: Dp,
    onNodeDrag: (ImDrawableNode, DpOffset) -> Unit,
    sDragProvider: () -> Offset,
    sScaleProvider: () -> ScreenScale
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