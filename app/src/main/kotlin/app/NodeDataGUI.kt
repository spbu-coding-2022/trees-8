/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import repository.serialization.SerializableValue
import trees.KeyValue

class NodeDataGUI(
    val data: KeyValue<Int, String>,
    var x: Dp = 0.dp,
    var y: Dp = 0.dp,
) : Comparable<NodeDataGUI> {
    override fun compareTo(other: NodeDataGUI) = data.compareTo(other.data)

    override fun toString() = "key=${data.key} value=${data.value}\nx=$x y=$y"

    companion object {
        @JvmStatic
        fun serialize(data: NodeDataGUI) = SerializableValue(Gson().toJson(data))

        @JvmStatic
        fun deserialize(data: SerializableValue): NodeDataGUI = Gson().fromJson(data.value, NodeDataGUI::class.java)

    }
}

