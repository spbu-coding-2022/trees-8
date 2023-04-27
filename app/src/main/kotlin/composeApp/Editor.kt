/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

@Composable
fun Editor(

) {
    Row {
        Menu()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(245, 245, 245))
                .padding(20.dp)
        ) {
            MaterialTheme(
                colorScheme = MaterialTheme.colorScheme.copy(
                    surface = Color.White,
                )
            ) {
//            EditorScreen()
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(

) {
    val addState = remember { mutableStateOf("") }
    val deleteState = remember { mutableStateOf("") }
    // Размещаем поля ввода в вертикальном столбце
    Column(
        Modifier.padding(16.dp).width(260.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextField(
            value = addState.value,
            onValueChange = { addState.value = it },
            label = { Text("Key") },
            modifier = Modifier.fillMaxWidth(),
        )

        TextField(
            value = deleteState.value,
            onValueChange = { deleteState.value = it },
            label = { Text("Value") },
            modifier = Modifier.fillMaxWidth(),
        )
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {}) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
            Button(onClick = {}) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
            Button(onClick = {}) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}