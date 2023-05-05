/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import app.graph.DrawableNode
import repository.Repository
import trees.AbstractTree
import trees.KeyValue
import trees.nodes.AbstractNode
import trees.nodes.Color
import trees.nodes.RBNode


class EditorController<NodeType : AbstractNode<NodeDataGUI, NodeType>>(
    private val tree: AbstractTree<NodeDataGUI, NodeType>?,
    private val repository: Repository<NodeDataGUI, *, AbstractTree<NodeDataGUI, *>>?,
    private val name: String,
) {

    var drawableRoot: DrawableNode? by mutableStateOf(toDrawableNode(tree?.root))
        private set

    fun initTree() {
        drawableRoot = tree?.root?.let { toDrawableNode(it, savePosition = true) }
    }

    fun saveTree() {
        fun saveCoordinates(node: NodeType, drawableNode: DrawableNode?) {
            if (drawableNode == null) {
                return
            }
            node.left?.let { saveCoordinates(it, drawableNode.left) }
            node.data.x = drawableNode.x
            node.data.y = drawableNode.y
            node.right?.let { saveCoordinates(it, drawableNode.right) }
        }

        tree?.root?.let { saveCoordinates(it, drawableRoot) }
        if (tree != null) {
            repository?.save(name, tree)
        }
    }

    fun add(key: Int, value: String) {
        tree?.add(NodeDataGUI(KeyValue(key, value)))
        drawableRoot = tree?.root?.let { toDrawableNode(it) }
    }

    fun delete(key: Int) {
        tree?.delete(NodeDataGUI(KeyValue(key, "")))
        drawableRoot = tree?.root?.let { toDrawableNode(it) }
    }

    fun contains(key: Int) {
        val res = tree?.contains(NodeDataGUI(KeyValue(key, "")))
    }

    private fun toDrawableNode(root: NodeType?, savePosition: Boolean = false): DrawableNode? {
        if (root == null) {
            return null
        }

        val drawRoot = DrawableNode(root.data.data.key, root.data.data.value)

        fun calculateCoordinates(
            node: NodeType,
            drawableNode: DrawableNode,
            offsetX: Int,
            curH: Int,
        ): Int {
            var resX = offsetX
            node.left?.let { left ->
                drawableNode.left = DrawableNode(left.data.data.key, left.data.data.value).also { drawLeft ->
                    resX = calculateCoordinates(left, drawLeft, offsetX, curH + 1) + 1
                }
            }

            drawableNode.x = if (savePosition) node.data.x else ((60.dp * 2 / 3) * resX)
            drawableNode.y = if (savePosition) node.data.y else ((60.dp * 5 / 4) * curH)
            if (node is RBNode<*>) {
                drawableNode.color = when (node.color) {
                    Color.RED -> androidx.compose.ui.graphics.Color.Red
                    Color.BLACK -> androidx.compose.ui.graphics.Color.Black
                }
            }

            node.right?.let { right ->
                drawableNode.right = DrawableNode(right.data.data.key, right.data.data.value).also { drawRight ->
                    resX = calculateCoordinates(right, drawRight, resX + 1, curH + 1)
                }
            }

            return resX
        }
        calculateCoordinates(root, drawRoot, 0, 0)
        return drawRoot
    }

    fun dragNode(node: DrawableNode, dragAmount: DpOffset) {
        (node).let {
            node.x += dragAmount.x
            node.y += dragAmount.y
        }
    }
}
