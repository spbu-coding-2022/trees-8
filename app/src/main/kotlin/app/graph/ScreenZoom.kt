/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app.graph

import androidx.compose.ui.geometry.Offset

data class ScreenZoom(
    val scale: Float,
    val offset: Offset
)