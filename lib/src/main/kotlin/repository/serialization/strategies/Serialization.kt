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
    abstract fun createNode(node: SerializableNode?): NodeType?
    abstract fun deserializeMetadata(metadata: Metadata): M
    abstract fun serializeMetadata(node: NodeType): Metadata
    abstract fun createTree(): TreeType
}