/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repository.serialization.strategies

import repository.serialization.Metadata
import repository.serialization.SerializableNode
import repository.serialization.SerializableValue
import repository.serialization.TypeOfTree
import trees.RBTree
import trees.nodes.Color
import trees.nodes.RBNode

class RBStrategy<T : Comparable<T>>(
    serializeData: (T) -> SerializableValue,
    deserializeData: (SerializableValue) -> T
) : Serialization<T, RBNode<T>, RBTree<T>, Color>(serializeData, deserializeData) {
    override val typeOfTree: TypeOfTree = TypeOfTree.RB_TREE

    //method that creates an AVL tree node from the passed SerializableNode, which was obtained as a result of serialization.
    override fun createNode(node: SerializableNode?): RBNode<T>? = node?.let {
        RBNode(
            data = deserializeValue(node.data),
            color = deserializeMetadata(node.metadata),
            left = createNode(node.left),
            right = createNode(node.right),
        )
    }

    //method that deserializes the node's metadata (in this case, the node's height). Returns the deserialized metadata.
    override fun deserializeMetadata(metadata: Metadata): Color {
        return when (metadata.value) {
            "RED" -> Color.RED
            "BLACK" -> Color.BLACK
            else -> throw IllegalArgumentException("Can't deserialize metadata $metadata")
        }
    }

    //method that serializes the node's metadata (in this case, the node's height). Returns the serialized metadata.
    override fun serializeMetadata(node: RBNode<T>): Metadata {
        return Metadata(
            when (node.color) {
                Color.RED -> "RED"
                else -> "BLACK"
            }
        )
    }

    //method that creates an AVL tree. Returns a new  tree of type RBTree<T>.
    override fun createTree() = RBTree<T>()
}
