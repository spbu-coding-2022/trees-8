/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repository

import repository.serialization.SerializableNode
import repository.serialization.SerializableTree
import repository.serialization.strategies.Serialization
import trees.AbstractTree
import trees.nodes.AbstractNode

abstract class Repository<T : Comparable<T>,
        NodeType : AbstractNode<T, NodeType>,
        TreeType : AbstractTree<T, NodeType>>(
    protected val strategy: Serialization<T, NodeType, TreeType, *>
) {
    protected fun NodeType.toSerializableNode(): SerializableNode {
        return SerializableNode(
            strategy.serializeValue(this.data),
            strategy.serializeMetadata(this),
            left?.toSerializableNode(),
            right?.toSerializableNode(),
        )
    }


    protected fun TreeType.toSerializableTree(name: String): SerializableTree {
        return SerializableTree(
            name = name,
            typeOfTree = strategy.typeOfTree,
            root = this.root?.toSerializableNode(),
        )
    }

    abstract fun save(name: String, tree: TreeType)
    abstract fun loadByName(name: String): TreeType?
    abstract fun deleteByName(name: String)
    abstract fun getNames(): List<String>
}