/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repository.jsonEntities

import kotlinx.serialization.Serializable
import repository.serialization.Metadata
import repository.serialization.SerializableValue


@Serializable
data class JsonNode(
    val data: SerializableValue,
    val metadata: Metadata,
    val left: JsonNode?,
    val right: JsonNode?
)