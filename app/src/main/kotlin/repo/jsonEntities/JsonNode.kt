/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

import kotlinx.serialization.Serializable
import repo.serialization.Metadata
import repo.serialization.SerializableValue


@Serializable
data class JsonNode(
    val data: SerializableValue,
    val metadata: Metadata,
    val left: JsonNode?,
    val right: JsonNode?
)