/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package visualizer.editor.graph

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import app.graph.ImDrawableNode
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GraphNode(
    modifier: Modifier = Modifier,
    node: ImDrawableNode,
    nodeSize: Dp,
    onNodeDrag: (ImDrawableNode, DpOffset) -> Unit,
    sDragProvider: () -> Offset,
    sScaleProvider: () -> ScreenScale
) {
    TooltipArea(
        modifier = modifier.zIndex(5f) // nodes must be in the front of the screen, covering lines
            .layout { measurable: Measurable, _: Constraints ->
                // avoid recomposition of nodes by reading scale, drag and cords in layout stage

                val placeable = measurable.measure(
                    // set fixed size = node size * scale
                    Constraints.fixed(
                        (nodeSize * sScaleProvider().scale).roundToPx(),
                        (nodeSize * sScaleProvider().scale).roundToPx()
                    )
                )

                layout(placeable.width, placeable.height) {
                    val drag = sDragProvider()
                    val scale = sScaleProvider()

                    // place node considering screen drag and scale
                    placeable.placeRelative(
                        ((node.x.toPx() + drag.x) * scale.scale + scale.posRelXYScale.x).roundToInt(),
                        ((node.y.toPx() + drag.y) * scale.scale + scale.posRelXYScale.y).roundToInt(),
                    )
                }
            },
        tooltip = {
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 3.dp
            ) {
                Text(
                    text = "Key: ${node.key}\nValue: ${node.value}",
                    modifier = Modifier.padding(13.dp)
                )
            }
        },
        tooltipPlacement = TooltipPlacement.CursorPoint(
            offset = DpOffset(0.dp, 16.dp)
        ),
        delayMillis = 800,
    ) {
        Box(modifier = modifier
            .fillMaxSize()
            .background(
                color = node.color ?: MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
            .pointerInput(node) {
                detectDragGestures { change, dragAmount ->
                    change.consume()

                    val scale = sScaleProvider().scale
                    onNodeDrag(
                        node,
                        DpOffset(
                            dragAmount.x.toDp() / scale,
                            dragAmount.y.toDp() / scale
                        ),
                    )
                }
            }
        ) {

            NodeText(
                modifier = Modifier.align(Alignment.Center),
                text = node.key.toString(),
                scaleProvider = { sScaleProvider().scale }
            )
        }
    }
}

@Composable
fun NodeText(
    modifier: Modifier = Modifier,
    text: String,
    scaleProvider: () -> Float,
) {
    val scale = scaleProvider()
    Text(
        modifier = modifier,
        text = if (text.length > 4) text.substring(0, 4) + ".." else text,
        color = MaterialTheme.colorScheme.onPrimary,
    )
}
