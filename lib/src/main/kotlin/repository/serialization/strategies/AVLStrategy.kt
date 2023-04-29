/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repository.serialization.strategies

import repository.serialization.Metadata
import repository.serialization.SerializableNode
import repository.serialization.SerializableValue
import repository.serialization.TypeOfTree
import trees.AVLTree
import trees.nodes.AVLNode

class AVLStrategy<T : Comparable<T>>(
    serializeData: (T) -> SerializableValue,
    deserializeData: (SerializableValue) -> T
) : Serialization<T, AVLNode<T>, AVLTree<T>, Int>(serializeData, deserializeData) {
    override val typeOfTree: TypeOfTree = TypeOfTree.AVL_TREE

    //method that creates an AVL tree node from the passed SerializableNode, which was obtained as a result of serialization.
    override fun createNode(node: SerializableNode?): AVLNode<T>? = node?.let {
        AVLNode(
            data = deserializeValue(node.data),
            left = createNode(node.left),
            right = createNode(node.right),
            height = deserializeMetadata(node.metadata),
        )
    }

    //method that deserializes the node's metadata (in this case, the node's height). Returns the deserialized metadata.
    override fun deserializeMetadata(metadata: Metadata) = metadata.value.toInt()

    //method that serializes the node's metadata (in this case, the node's height). Returns the serialized metadata.
    override fun serializeMetadata(node: AVLNode<T>) = Metadata(node.height.toString())

    //method that creates an AVL tree. Returns a new AVL tree of type AVLTree<T>.
    override fun createTree() = AVLTree<T>()
}
