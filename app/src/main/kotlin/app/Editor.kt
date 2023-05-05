/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import trees.nodes.AbstractNode

@Composable
fun <N : AbstractNode<NodeDataGUI, N>> Editor(
    editorController: EditorController<N>,
    onGoHome: () -> Unit,
) {

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            editorController.initTree()
        }
    }

    val coroutineScope = rememberCoroutineScope { Dispatchers.Default }


    Row {
        Menu(
            onAdd = { key, value -> coroutineScope.launch { editorController.add(key, value) } },
            onDelete = { coroutineScope.launch { editorController.delete(it) } },
            onContains = { coroutineScope.launch { editorController.contains(it) } },
            onSave = { coroutineScope.launch { editorController.saveTree() } },
            onGoHome = onGoHome,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(240, 240, 240))
                .padding(20.dp)
        ) {
            MaterialTheme {
                EditorScreen(editorController)
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
    onGoHome: () -> Unit
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
                if (keyString.isNotEmpty()) onAdd(keyString.toInt(), valueString)
                keyString = ""
                valueString = ""
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
            Button(onClick = {
                if (keyString.isNotEmpty()) onContains(keyString.toInt())
                keyString = ""
                valueString = ""
            }) {
                Icon(Icons.Default.Search, contentDescription = "Contains")
            }
            Button(onClick = {
                if (keyString.isNotEmpty()) onDelete(keyString.toInt())
                keyString = ""
                valueString = ""
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        )
        {
            Button(
                onClick = onSave,
                modifier = Modifier.weight(2f)
            ) {
                Text(
                    text = "Save"
                )
            }
            Button(
                onClick = onGoHome,
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Home, contentDescription = "Go back")
            }
        }
    }
}