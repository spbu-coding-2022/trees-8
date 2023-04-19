/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repo.serialization.strategies

import app.trees.ABSTree
import app.trees.nodes.MyNode
import repo.serialization.Metadata
import repo.serialization.SerializableNode
import repo.serialization.SerializableValue
import repo.serialization.TypeOfTree


abstract class Serialization<
        E : Comparable<E>,
        NodeType : MyNode<E, NodeType>,
        TreeType : ABSTree<E, NodeType>,
        M,
        >(
    val serializeValue: (E) -> SerializableValue,
    val deserializeValue: (SerializableValue) -> E,
) {
    abstract val typeOfTree: TypeOfTree
    abstract fun createNode(node: SerializableNode?): NodeType?
    abstract fun deserializeMetadata(metadata: Metadata): M
    abstract fun serializeMetadata(node: NodeType): Metadata
    abstract fun createTree(): TreeType
}