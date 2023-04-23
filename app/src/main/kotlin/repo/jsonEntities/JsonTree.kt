/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */


import kotlinx.serialization.Serializable

@Serializable
data class JsonTree(
    val name: String,
    val root: JsonNode?
)