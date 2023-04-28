/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repository.serialization.strategies

import repository.serialization.Metadata
import repository.serialization.SerializableNode
import repository.serialization.SerializableValue
import repository.serialization.TypeOfTree
import trees.BSTree
import trees.nodes.BSNode

class BSStrategy<T : Comparable<T>>(
    serializeData: (T) -> SerializableValue,
    deserializeData: (SerializableValue) -> T
) : Serialization<T, BSNode<T>, BSTree<T>, Int>(serializeData, deserializeData) {
    override val typeOfTree: TypeOfTree = TypeOfTree.BS_TREE

    override fun createNode(node: SerializableNode?): BSNode<T>? = node?.let {
        BSNode(
            data = deserializeValue(node.data),
            left = createNode(node.left),
            right = createNode(node.right),
        )
    }

    override fun deserializeMetadata(metadata: Metadata) = metadata.value.toInt()

    override fun serializeMetadata(node: BSNode<T>) = Metadata("")

    override fun createTree() = BSTree<T>()
}
