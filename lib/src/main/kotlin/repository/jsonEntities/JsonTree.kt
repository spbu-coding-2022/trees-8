/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repository.jsonEntities


import kotlinx.serialization.Serializable
import repository.serialization.TypeOfTree

@Serializable
data class JsonTree(
    val name: String,
    val typeOfTree: TypeOfTree,
    val root: JsonNode?
)