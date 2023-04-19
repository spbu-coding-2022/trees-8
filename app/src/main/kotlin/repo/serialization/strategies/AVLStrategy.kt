/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repo.serialization.strategies

import app.trees.nodes.AVLNode
import app.trees.trees.AVLTree
import repo.serialization.Metadata
import repo.serialization.SerializableNode
import repo.serialization.SerializableValue
import repo.serialization.TypeOfTree

class AVLStrategy<T : Comparable<T>>(
    serializeValue: (T) -> SerializableValue,
    deserializeValue: (SerializableValue) -> T
) : Serialization<T, AVLNode<T>, AVLTree<T>, Int>(serializeValue, deserializeValue) {
    override val typeOfTree: TypeOfTree = TypeOfTree.AVL_TREE

    override fun createNode(node: SerializableNode?): AVLNode<T>? = node?.let {
        AVLNode(
            data = deserializeValue(node.value),
            left = createNode(node.left),
            right = createNode(node.right),
            height = deserializeMetadata(node.metadata),
        )
    }

    override fun deserializeMetadata(metadata: Metadata) = metadata.value.toInt()

    override fun serializeMetadata(node: AVLNode<T>) = Metadata(node.height.toString())

    override fun createTree() = AVLTree<T>()
}
