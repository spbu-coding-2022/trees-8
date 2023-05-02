/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import java.io.File


@Composable
fun OpenTree(
    listOfDatabase: List<String>,
    listOfTypes: List<String>,
    listOfNames: List<String>,

    typeOfDatabaseState: State<TypeOfDatabase?>,
    file: State<File?>,

    onTypeChanged: (String) -> Unit,
    onTypeOfDatabaseChanged: (String) -> Unit,
    onFilePicked: () -> Unit,
    onPathChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
    onLoadTree: () -> Unit,
    onLoadDatabase: () -> Unit,

    isEnabled: Boolean = false,
) {
    MaterialTheme {
        Scaffold(topBar = {
            TopAppBar(title = { Text("Tree viewer by teemEight") })
        }) {
            Column(Modifier.padding(10.dp)) {
                DropDownTextFiled("Type of database", listOfDatabase, onTypeOfDatabaseChanged)
                DropDownTextFiled("Type of tree", listOfTypes, onTypeChanged)
                when (typeOfDatabaseState.value) {
                    TypeOfDatabase.Json -> FilePicker(onFilePicked, file)
                    TypeOfDatabase.Neo4j -> PathToStorage(onPathChanged, onLoadDatabase)
                    TypeOfDatabase.SQL -> PathToStorage(onPathChanged, onLoadDatabase)
                    else -> {}
                }
                if (typeOfDatabaseState.value != null) {

                    DropDownTextFiled("Name of tree", listOfNames, onNameChanged, false)
                    Button(
                        onClick = onLoadTree,
                        modifier = Modifier.padding(horizontal = 10.dp),
                        enabled = isEnabled,
                    ) {
                        Text("Let's go!")
                    }
                }
            }
        }
    }
}


@Composable
fun FilePicker(
    onFilePicked: () -> Unit,
    file: State<File?>,
) {
    Row(
        modifier = Modifier.padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onFilePicked
        ) {
            Text(
                text = "Choose File",
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        file.value?.let {
            Text(file.value?.name ?: "")
        }
    }
}

@Composable
fun PathToStorage(
    onPathChanged: (String) -> Unit,
    onLoadDatabase: () -> Unit,
) {
    var path by remember { mutableStateOf("") }
    Column(Modifier.padding(horizontal = 10.dp)) {
        OutlinedTextField(
            value = path,
            singleLine = true,
            onValueChange = {
                onPathChanged(it)
                path = it
            },
            label = { Text(text = "Path to database") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onLoadDatabase
        ) {
            Text("Load")
        }
    }
}

@Composable
fun DropDownTextFiled(
    title: String,
    listOfValues: List<String>,
    onValueChanged: (String) -> Unit,
    readOnly: Boolean = true,
) {
    var mExpanded by remember { mutableStateOf(false) }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
    var mSelectedText by remember { mutableStateOf("") }

    val icon = if (mExpanded) Icons.Filled.KeyboardArrowUp
    else Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(horizontal = 10.dp)) {
        OutlinedTextField(value = mSelectedText,
            singleLine = true,
            readOnly = readOnly,
            onValueChange = { newName ->
                mSelectedText = newName
                onValueChanged(newName)
            },
            modifier = Modifier.fillMaxWidth().onGloballyPositioned { coordinates ->
                mTextFieldSize = coordinates.size.toSize()
            },
            label = {
                Text(
                    text = title,
                )
            },
            trailingIcon = {
                Icon(icon, "contentDescription", Modifier.clickable { mExpanded = !mExpanded })
            })
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
        ) {
            listOfValues.forEach { label ->
                DropdownMenuItem(onClick = {
                    mSelectedText = label
                    onValueChanged(label)
                    mExpanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
}
