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

abstract class Repository<E : Comparable<E>,
        NodeType : MyNode<E, NodeType>,
        TreeType : ABSTree<E, NodeType>>(
    protected val strategy: Serialization<E, NodeType, TreeType, *>
) {
    protected fun NodeType.toSerializableNode(): SerializableNode {
        return SerializableNode(
            strategy.serializeValue(this.data),
            strategy.serializeMetadata(this),
            left?.toSerializableNode(),
            right?.toSerializableNode(),
            parent?.toSerializableNode(),
        )
    }


    protected fun TreeType.toSerializableTree(verboseName: String): SerializableTree {
        return SerializableTree(
            verboseName = verboseName,
            typeOfTree = strategy.typeOfTree,
            root = this.root?.toSerializableNode(),
        )
    }

    abstract fun save(verboseName: String, tree: TreeType)
    abstract fun loadByVerboseName(verboseName: String): TreeType?
    abstract fun deleteByVerboseName(verboseName: String)
}