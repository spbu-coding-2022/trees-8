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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.EditorController
import app.EditorScreen
import app.NodeDataGUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import trees.nodes.AbstractNode

@Composable
fun <N : AbstractNode<NodeDataGUI, N>> Editor(
    viewModel: EditorController<N>,
) {

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            viewModel.initTree()
        }
    }

    val cScope = rememberCoroutineScope { Dispatchers.Default }


    Row {
        Menu(
            onAdd = { key, value -> cScope.launch { viewModel.add(key, value) } },
            onDelete = { cScope.launch { viewModel.delete(it) } },
            onContains = { cScope.launch { viewModel.contains(it) } },
            onSave = { cScope.launch { viewModel.saveTree() } }
        )
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
                EditorScreen(viewModel)
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(
    onAdd: (Int, String) -> Unit,
    onDelete: (Int) -> Unit,
    onContains: (Int) -> Unit,
    onSave: () -> Unit,

    ) {
    var keyString by remember { mutableStateOf("") }
    var valueString by remember { mutableStateOf("") }
    // Размещаем поля ввода в вертикальном столбце
    Column(
        Modifier.padding(16.dp).width(260.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextField(
            value = keyString,
            onValueChange = {
                if (it.isEmpty() || it == "-" || it.toIntOrNull() != null) {
                    keyString = it
                }
            },
            label = { Text("Key") },
            modifier = Modifier.fillMaxWidth(),
        )

        TextField(
            value = valueString,
            onValueChange = { valueString = it },
            label = { Text("Value") },
            modifier = Modifier.fillMaxWidth(),
        )
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                onAdd(keyString.toInt(), valueString)
                keyString = ""
                valueString = ""
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
            Button(onClick = {
                onContains(keyString.toInt())
                keyString = ""
                valueString = ""
            }) {
                Icon(Icons.Default.Search, contentDescription = "Contains")
            }
            Button(onClick = {
                onDelete(keyString.toInt())
                keyString = ""
                valueString = ""
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Save"
            )
        }
    }
}