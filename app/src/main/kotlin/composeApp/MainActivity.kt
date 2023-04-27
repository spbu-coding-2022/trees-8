/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.awt.ComposeWindow
import composeApp.OpenTree
import java.io.File

/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

enum class States {
    OPENING_TREE, DRAW_TREE
}


enum class TypeOfDatabase {
    Neo4j,
    Json,
    SQL
}

@Composable
fun run(window: ComposeWindow) {
    val databases = remember {
        mapOf(
            "SQL" to TypeOfDatabase.SQL,
            "Neo4j" to TypeOfDatabase.Neo4j,
            ".json file" to TypeOfDatabase.Json
        )
    }
    val listOfDatabase = databases.keys.toList()
    val stringTypeOfDatabaseState = remember { mutableStateOf(".json file") }
    val pathState = remember { mutableStateOf("") }
    val typeOfDatabaseState =
        remember { mutableStateOf(databases.getOrDefault(stringTypeOfDatabaseState.value, TypeOfDatabase.Json)) }


    val state = remember { mutableStateOf(States.OPENING_TREE) }
    val controller = remember { mutableStateOf(Controller()) }
    val file = remember { mutableStateOf<File?>(null) }

    val listNames = remember { mutableStateOf(listOf("test", "main")) }
    val nameState = remember { mutableStateOf("") }
    when (state.value) {
        States.OPENING_TREE -> OpenTree(
            listOfDatabase = listOfDatabase,
            onTypeOfDatabaseChanged = { newType ->
                stringTypeOfDatabaseState.value = newType
                typeOfDatabaseState.value = databases.getOrDefault(stringTypeOfDatabaseState.value, TypeOfDatabase.Json)
            },
            typeOfDatabaseState = typeOfDatabaseState,
            stringTypeOfDatabaseState = stringTypeOfDatabaseState,

            pathState = pathState,
            file = file,
            onPathChanged = { newPath -> pathState.value = newPath },
            onFilePicked = {
                file.value =
                    controller.value.openFileDialog(
                        window,
                        "Load a file",
                        listOf(".json"),
                        allowMultiSelection = false
                    )
            },

            listOfNames = listNames.value,
            nameState = nameState,
            onNameChanged = { newName -> nameState.value = newName },

            onLoadTree = {
//                controller.value.loadTree(nameState.value)
                state.value = States.DRAW_TREE
            }


        )

        else -> {
            window.setSize(1080, 800)
            Editor()
        }
    }
}
