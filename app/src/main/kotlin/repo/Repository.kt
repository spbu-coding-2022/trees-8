/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repo

import app.trees.ABSTree
import app.trees.nodes.MyNode
import repo.serialization.SerializableNode
import repo.serialization.SerializableTree
import repo.serialization.strategies.Serialization

abstract class Repository<T : Comparable<T>,
        NodeType : MyNode<T, NodeType>,
        TreeType : ABSTree<T, NodeType>>(
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
}