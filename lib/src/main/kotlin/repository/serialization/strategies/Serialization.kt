/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repository.serialization.strategies

import repository.serialization.Metadata
import repository.serialization.SerializableNode
import repository.serialization.SerializableValue
import repository.serialization.TypeOfTree
import trees.AbstractTree
import trees.nodes.AbstractNode

abstract class Serialization<T : Comparable<T>,
        NodeType : AbstractNode<T, NodeType>,
        TreeType : AbstractTree<T, NodeType>,
        M>(
    val serializeValue: (T) -> SerializableValue,
    val deserializeValue: (SerializableValue) -> T
) {
    abstract val typeOfTree: TypeOfTree

    //abstract method that creates an AVL tree node from the passed SerializableNode, which was obtained as a result of serialization.
    abstract fun createNode(node: SerializableNode?): NodeType?

    //abstract method that deserializes the node's metadata (in this case, the node's height). Returns the deserialized metadata.
    abstract fun deserializeMetadata(metadata: Metadata): M

    //abstract method that serializes the node's metadata (in this case, the node's height). Returns the serialized metadata.
    abstract fun serializeMetadata(node: NodeType): Metadata

    //abstract method that creates an AVL tree. Returns a new tree of type Tree<T>.
    abstract fun createTree(): TreeType
}