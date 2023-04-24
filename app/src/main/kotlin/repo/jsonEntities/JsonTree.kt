/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */


import kotlinx.serialization.Serializable
import repo.serialization.TypeOfTree

@Serializable
data class JsonTree(
    val name: String,
    val typeOfTree: TypeOfTree,
    val root: JsonNode?
)