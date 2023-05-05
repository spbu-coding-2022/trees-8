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
    //An extension function that converts an instance of a NodeType to a serializable
    //representation of a SerializableNode using the serialization strategy
    protected fun NodeType.toSerializableNode(): SerializableNode {
        return SerializableNode(
            strategy.serializeValue(this.data),
            strategy.serializeMetadata(this),
            left?.toSerializableNode(),
            right?.toSerializableNode(),
        )
    }

    //An extension function that converts a TreeType instance to a serializable
    // representation of a SerializableTree using the serialization  strategy.
    // The name of the tree is given by the name parameter
    protected fun TreeType.toSerializableTree(name: String): SerializableTree {
        return SerializableTree(
            name = name,
            typeOfTree = strategy.typeOfTree,
            root = this.root?.toSerializableNode(),
        )
    }

    //an abstract method that saves an instance of tree in the repository under the given name
    abstract fun save(name: String, tree: TreeType)

    //abstract method that loads a tree instance from the repository given name.
    // If no tree with the specified name is found, the method should return null.
    abstract fun loadByName(name: String): TreeType?

    //an abstract method that removes a tree instance from the repository at the given name
    abstract fun deleteByName(name: String)

    //abstract method that returns a list of the names of all trees stored in the repository.
    // If there are no trees in the repository, the method should return an empty list
    abstract fun getNames(): List<String>
}