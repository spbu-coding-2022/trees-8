/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repository.jsonEntities

import kotlinx.serialization.Serializable
import repository.serialization.TypeOfTree

//Describes the "repository.jsonEntities.JsonTree" object - this is the tree
// that will be serialized and stored in JSON format
@Serializable
data class JsonTree(
    //a string that contains the name of the tree.
    val name: String,
    //contains a tree type, which can be one of the "TypeOfTree" enums.
    val typeOfTree: TypeOfTree,
    //This is a reference to the root node of the tree, which can either be a "repository.jsonEntities.JsonNode" object
    val root: JsonNode?
)