/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.graph.TreeGraph
import trees.nodes.AbstractNode

@Composable
fun <NodeType : AbstractNode<NodeDataGUI, NodeType>> EditorScreen(
    editorController: EditorController<NodeType>
) {
    val viewModel = remember { editorController }
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Surface(
            modifier = Modifier.fillMaxSize().weight(1f),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            viewModel.drawableRoot?.let {
                TreeGraph(it, 60.dp, viewModel::dragNode)
            }
        }
    }
}