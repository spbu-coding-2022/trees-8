/*
 * Copyright (c) 2023 Yakshigulov Vadim, Dyachkov Vitaliy, Perevalov Efim
 * SPDX-License-Identifier: MIT
 */

package repository.serialization

import kotlinx.serialization.Serializable

@Serializable
enum class TypeOfTree {
    BS_TREE,
    RB_TREE,
    AVL_TREE
}

@Serializable
class SerializableNode(
    val data: SerializableValue,
    val metadata: Metadata,
    val left: SerializableNode? = null,
    val right: SerializableNode? = null,
)

@Serializable
class SerializableTree(
    val name: String,
    val typeOfTree: TypeOfTree,
    val root: SerializableNode?,
)

@Serializable
@JvmInline
value class Metadata(val value: String)

@Serializable
@JvmInline
value class SerializableValue(val value: String)