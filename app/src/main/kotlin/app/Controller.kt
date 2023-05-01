/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

import androidx.compose.ui.awt.ComposeWindow
import app.NodeDataGUI
import app.TypeOfDatabase
import repository.JsonRepository
import repository.Repository
import repository.serialization.TypeOfTree
import repository.serialization.strategies.AVLStrategy
import repository.serialization.strategies.BSStrategy
import repository.serialization.strategies.RBStrategy
import trees.AVLTree
import trees.AbstractTree
import trees.BSTree
import trees.RBTree
import java.awt.FileDialog
import java.io.File

class Controller {

    var repository: Repository<NodeDataGUI, *, AbstractTree<NodeDataGUI, *>>? = null
    var tree: AbstractTree<NodeDataGUI, *>? = null
    fun openFileDialog(
        window: ComposeWindow, title: String, allowedExtensions: List<String>, allowMultiSelection: Boolean = true
    ): File? {
        val fileDialog = FileDialog(window, title, FileDialog.LOAD)
        fileDialog.isMultipleMode = allowMultiSelection
        fileDialog.isVisible = true
        fileDialog.setFilenameFilter { _, name -> name.endsWith(".jpg") }
        return fileDialog.files.firstOrNull()
    }

    fun loadDatabase(
        typeOfDatabase: TypeOfDatabase?,
        typeOfTree: TypeOfTree?,
        dirPath: String?,
        filename: String?,
    ) {
        if (dirPath == null || filename == null || typeOfDatabase == null || typeOfTree == null)
            return
        val strategy = when (typeOfTree) {
            TypeOfTree.AVL_TREE -> AVLStrategy(NodeDataGUI::serialize, NodeDataGUI::deserialize)
            TypeOfTree.BS_TREE -> BSStrategy(NodeDataGUI::serialize, NodeDataGUI::deserialize)
            TypeOfTree.RB_TREE -> RBStrategy(NodeDataGUI::serialize, NodeDataGUI::deserialize)
        }
        repository = when (typeOfDatabase) {
            TypeOfDatabase.Json -> JsonRepository(
                strategy,
                dirPath,
                filename
            ) as JsonRepository<NodeDataGUI, *, AbstractTree<NodeDataGUI, *>>
//            TypeOfDatabase.Neo4j -> Neo4jRepo(strategy, dirPath)
//            TypeOfDatabase.SQL -> SQLRepository(strategy, dirPath)
            else -> JsonRepository(
                strategy,
                dirPath,
                filename
            ) as JsonRepository<NodeDataGUI, *, AbstractTree<NodeDataGUI, *>>
        }
    }

    fun getNamesOfTrees(): List<String> {
        return repository?.getNames() ?: listOf()
    }

    fun loadTree(typeOfTree: TypeOfTree, name: String) {
        tree = repository?.loadByName(name)
        if (tree == null) {
            tree = when (typeOfTree) {
                TypeOfTree.RB_TREE -> RBTree()
                TypeOfTree.AVL_TREE -> AVLTree()
                TypeOfTree.BS_TREE -> BSTree()
            }
        }
    }
}