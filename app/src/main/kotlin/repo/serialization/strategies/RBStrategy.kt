/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repo.serialization.strategies
import app.trees.rb.Color
import app.trees.rb.RBNode
import app.trees.rb.RBTree
import repo.serialization.Metadata
import repo.serialization.SerializableNode
import repo.serialization.SerializableValue
import repo.serialization.TypeOfTree
class RBStrategy<T : Comparable<T>>(
    serializeData: (T) -> SerializableValue,
    deserializeData: (SerializableValue) -> T
) : Serialization<T, RBNode<T>, RBTree<T>, Color>(serializeData, deserializeData) {
    override val typeOfTree: TypeOfTree = TypeOfTree.RED_BLACK_TREE

    override fun createNode(node: SerializableNode?): RBNode<T>? = node?.let {
        RBNode(
            data = deserializeValue(node.data),
            left = createNode(node.left),
            right = createNode(node.right),
            color = deserializeMetadata(node.metadata),
        )
    }

    override fun deserializeMetadata(metadata: Metadata): Color {
        return when (metadata.value) {
            "RED" -> Color.RED
            "BLACK" -> Color.BLACK
            else -> throw IllegalArgumentException("Can't deserialize metadata, only 'RED' or 'BLACK' is allowed")
        }
    }
    override fun serializeMetadata(node: RBNode<T>): Metadata {
        return Metadata(
            when (node.color) {
                Color.RED -> "RED"
                else -> "BLACK"
            }
        )
    }


    override fun createTree() = RBTree<T>()
}