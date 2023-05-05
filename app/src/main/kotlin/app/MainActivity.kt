/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app

import androidx.compose.runtime.*
import androidx.compose.ui.awt.ComposeWindow
import com.google.gson.JsonSyntaxException
import repository.serialization.TypeOfTree
import java.io.File


enum class States {
    OPENING_TREE, DRAW_TREE
}


enum class TypeOfDatabase {
    Neo4j, Json, SQL
}

@Composable
fun app(window: ComposeWindow) {
    val typesOfDatabase = remember {
        mapOf(
            "SQL" to TypeOfDatabase.SQL, "Neo4j" to TypeOfDatabase.Neo4j, ".json file" to TypeOfDatabase.Json
        )
    }

    val typesOfTrees = remember {
        mapOf(
            "AVL" to TypeOfTree.AVL_TREE,
            "Red-black" to TypeOfTree.RB_TREE,
            "Binary" to TypeOfTree.BS_TREE,
        )
    }

    val listOfTrees = typesOfTrees.keys.toList()
    val listOfDatabase = typesOfDatabase.keys.toList()
    var listNames by remember { mutableStateOf(listOf("")) }

    val typeOfTree = remember { mutableStateOf<TypeOfTree?>(TypeOfTree.BS_TREE) }
    val stringTypeOfDatabaseState = remember { mutableStateOf("") }
    val pathState = remember { mutableStateOf("") }
    val typeOfDatabaseState = remember { mutableStateOf(typesOfDatabase[stringTypeOfDatabaseState.value]) }

    val state = remember { mutableStateOf(States.OPENING_TREE) }
    val openingController by remember { mutableStateOf(OpeningController()) }
    val fileState = remember { mutableStateOf<File?>(null) }

    val nameState = remember { mutableStateOf("") }

    fun loadTrees() {
        if (fileState.value?.name?.endsWith(".json") == true) {
            try {
                openingController.loadDatabase(
                    typeOfDatabaseState.value, typeOfTree.value, fileState.value?.parent, fileState.value?.name
                )
                listNames = openingController.getNamesOfTrees()
            } catch (_: JsonSyntaxException) {
                fileState.value = null
            }
        } else {
            fileState.value = null
        }
    }

    fun resetStates() {
        fileState.value = null
        nameState.value = ""
        typeOfDatabaseState.value = null
        stringTypeOfDatabaseState.value = ""
        typeOfTree.value = null
        pathState.value = ""
    }

    when (state.value) {
        States.OPENING_TREE -> {
            window.setSize(700, 700)
            OpenTree(
                listOfDatabase = listOfDatabase,
                listOfNames = listNames,
                listOfTypes = listOfTrees,

                typeOfDatabaseState = typeOfDatabaseState,
                file = fileState,

                onTypeOfDatabaseChanged = { newType ->
                    resetStates()
                    stringTypeOfDatabaseState.value = newType
                    typeOfDatabaseState.value = typesOfDatabase[stringTypeOfDatabaseState.value]
                },
                onPathChanged = { newPath -> pathState.value = newPath },
                onFilePicked = {
                    fileState.value = openingController.openFileDialog(
                        window, "Load a file", listOf(".json"), allowMultiSelection = false
                    )
                    loadTrees()
                },
                onNameChanged = { newName -> nameState.value = newName },
                onTypeChanged = { newType ->
                    typeOfTree.value = typesOfTrees[newType]
                    loadTrees()
                },
                onLoadTree = {
                    state.value = States.DRAW_TREE
                    typeOfTree.value?.let { openingController.loadTree(it, nameState.value) }
                },
                onLoadDatabase = {
                    openingController.loadDatabase(
                        typeOfDatabaseState.value, typeOfTree.value, pathState.value, fileState.value?.name
                    )
                },
                isEnabled = (fileState.value != null && nameState.value != "")
            )
        }

        else -> {
            window.setSize(1080, 800)
            Editor(
                editorController = EditorController(
                    openingController.tree,
                    openingController.repository,
                    nameState.value
                ),
                onGoHome = {
                    resetStates()
                    state.value = States.OPENING_TREE
                }
            )
        }
    }
}
