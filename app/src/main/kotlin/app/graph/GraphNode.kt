/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app.graph

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GraphNode(
    modifier: Modifier = Modifier,
    node: DrawableNode,
    nodeSize: Dp,
    onNodeDrag: (DrawableNode, DpOffset) -> Unit,
    sDragProvider: () -> Offset,
    sScaleProvider: () -> ScreenZoom
) {
    TooltipArea(
        modifier = modifier.zIndex(5f)
            .layout { measurable: Measurable, _: Constraints ->
                val placeable = measurable.measure(
                    Constraints.fixed(
                        (nodeSize * sScaleProvider().scale).roundToPx(),
                        (nodeSize * sScaleProvider().scale).roundToPx()
                    )
                )
                layout(placeable.width, placeable.height) {
                    val drag = sDragProvider()
                    val scale = sScaleProvider()
                    placeable.placeRelative(
                        ((node.x.toPx() + drag.x) * scale.scale + scale.offset.x).roundToInt(),
                        ((node.y.toPx() + drag.y) * scale.scale + scale.offset.y).roundToInt(),
                    )
                }
            },
        tooltip = {
            Surface(
                shape = MaterialTheme.shapes.medium,
            ) {
                Text(
                    text = "Key: ${node.key}\nValue: ${node.value}",
                    modifier = Modifier.padding(15.dp)
                )
            }
        },
        tooltipPlacement = TooltipPlacement.CursorPoint(
            offset = DpOffset(0.dp, (-100).dp)
        ),
        delayMillis = 600,
    ) {
        Box(modifier = modifier
            .fillMaxSize()
            .background(
                color = node.color ?: MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
            .pointerInput(node) {
                detectDragGestures { change, offset ->
                    change.consume()
                    val scale = sScaleProvider().scale
                    onNodeDrag(
                        node,
                        DpOffset(
                            offset.x.toDp() / scale,
                            offset.y.toDp() / scale
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
        text = if (text.length > 4) text.substring(0, 5) + ".." else text,
        color = MaterialTheme.colorScheme.onPrimary,
        style = TextStyle(
            fontSize = MaterialTheme.typography.bodyMedium.fontSize * scale,
            lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * scale
        )
    )
}
