/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

import kotlinx.serialization.Serializable
import repo.serialization.Metadata
import repo.serialization.SerializableValue

//A class that represents a tree node in JSON format. Contains
@Serializable
data class JsonNode(
    //Node value in serializable format;
    val data: SerializableValue,
    //Node metadata, including node height, balancing factor,
    //and other information needed to work with the tree;
    val metadata: Metadata,
    //Link to the left child node.
    val left: JsonNode?,
    //Link to the right child node.
    val right: JsonNode?
)