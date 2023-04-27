/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package composeApp

import TypeOfDatabase
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
    onTypeOfDatabaseChanged: (String) -> Unit,
    typeOfDatabaseState: State<TypeOfDatabase>,
    stringTypeOfDatabaseState: State<String>,

    onFilePicked: () -> Unit,
    file: State<File?>,
    pathState: State<String>,
    onPathChanged: (String) -> Unit,

    listOfNames: List<String>,
    nameState: State<String>,
    onNameChanged: (String) -> Unit,

    onLoadTree: () -> Unit,
) {
    MaterialTheme {
        Scaffold(topBar = {
            TopAppBar(title = { Text("Выберите тип дерева") })
        }) {
            Column(Modifier.padding(10.dp)) {
                ChoosingStorageType(listOfDatabase, stringTypeOfDatabaseState, onTypeOfDatabaseChanged)
                when (typeOfDatabaseState.value) {
                    TypeOfDatabase.Json -> FilePicker(onFilePicked, file)
                    TypeOfDatabase.Neo4j -> PathToStorage(pathState, onPathChanged) {}
                    TypeOfDatabase.SQL -> PathToStorage(pathState, onPathChanged) {}
                    else -> {}
                }
                ListOfTrees(listOfNames, nameState, onNameChanged, onLoadTree)
            }
        }
    }
}


@Composable
fun ListOfTrees(
    listOfNames: List<String>,
    nameState: State<String>,
    onNameChanged: (String) -> Unit,
    onLoadTree: () -> Unit,
) {
    var mExpanded by remember { mutableStateOf(false) }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
    val mSelectedText = nameState.value

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(horizontal = 10.dp)) {
        OutlinedTextField(
            value = mSelectedText,
            singleLine = true,
            readOnly = true,
            onValueChange = onNameChanged,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    mTextFieldSize = coordinates.size.toSize()
                },
            label = {
                Text(
                    text = "Name of tree",
                )
            },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            }
        )
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
        ) {
            listOfNames.forEach { label ->
                DropdownMenuItem(onClick = {
                    onNameChanged(label)
                    mExpanded = false
                }) {
                    Text(text = label)
                }
            }
        }
        Button(
            onClick = onLoadTree
        ) {
            Text("Let's go!")
        }
    }
}

@Composable
fun FilePicker(
    onFilePicked: () -> Unit,
    file: State<File?>,
) {
    Row(
        modifier = Modifier.padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onFilePicked
        ) {
            Text(
                text = "Выберите файл",
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        file.value?.let {
            Text(file.value?.name ?: "")
        }
    }
}

@Composable
fun ChoosingStorageType(
    listOfDatabase: List<String>,
    typeOfDatabaseState: State<String>,
    onTypeOfDatabaseChanged: (String) -> Unit
) {
    var mExpanded by remember { mutableStateOf(false) }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
    val mTrees = listOfDatabase
    val mSelectedText = typeOfDatabaseState.value

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(horizontal = 10.dp)) {
        OutlinedTextField(
            value = mSelectedText,
            singleLine = true,
            readOnly = true,
            onValueChange = onTypeOfDatabaseChanged,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    mTextFieldSize = coordinates.size.toSize()
                },
            label = {
                Text(
                    text = "Type of storage",
                )
            },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            }
        )
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
        ) {
            mTrees.forEach { label ->
                DropdownMenuItem(onClick = {
                    onTypeOfDatabaseChanged(label)
                    mExpanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
}

@Composable
fun PathToStorage(
    pathState: State<String>,
    onPathChanged: (String) -> Unit,
    setPath: (String) -> Unit
) {
    val path = pathState.value
    Column(Modifier.padding(horizontal = 10.dp)) {
        OutlinedTextField(
            value = path,
            singleLine = true,
            onValueChange = onPathChanged,
            label = { Text(text = "Введите путь к базе") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
//            modifier = Modifier.padding(vertical = 10.dp),
            onClick = {}
        ) {
            Text("Загрузить")
        }
    }
}
