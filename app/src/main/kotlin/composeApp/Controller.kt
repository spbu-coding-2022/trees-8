/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

import androidx.compose.ui.awt.ComposeWindow
import com.google.gson.Gson
import repo.JsonRepository
import repo.serialization.SerializableValue
import repo.serialization.strategies.AVLStrategy
import java.awt.FileDialog
import java.io.File

/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

class Controller {
    fun openFileDialog(
        window: ComposeWindow, title: String, allowedExtensions: List<String>, allowMultiSelection: Boolean = true
    ): File? {
        val fileDialog = FileDialog(window, title, FileDialog.LOAD)
        fileDialog.isMultipleMode = allowMultiSelection
        fileDialog.isVisible = true
        fileDialog.setFilenameFilter { _, name -> name.endsWith(".jpg") }
        return fileDialog.files.firstOrNull()
    }

    fun getNamesOfTrees(dirPath: String?, filename: String?): List<String> {
        if (dirPath == null || filename == null)
            return listOf()
        val gson = Gson()

        fun drawableNodeToString(node: DrawableNode): SerializableValue {
            return SerializableValue(gson.toJson(node))
        }

        fun jsonStringToDrawableNode(string: SerializableValue): DrawableNode {
            return gson.fromJson(string.value, DrawableNode::class.java)!!
        }

        val repo = JsonRepository(AVLStrategy(::drawableNodeToString, ::jsonStringToDrawableNode), dirPath, filename)
        return repo.getNames()
    }

    fun loadTree(name: String) {
        TODO("Not yet implemented")
    }
}