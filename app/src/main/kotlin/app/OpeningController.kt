/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app

import androidx.compose.ui.awt.ComposeWindow
import repository.JsonRepository
import repository.Repository
import repository.serialization.TypeOfTree
import repository.serialization.strategies.AVLStrategy
import repository.serialization.strategies.BSStrategy
import repository.serialization.strategies.RBStrategy
import repository.serialization.strategies.Serialization
import trees.AVLTree
import trees.AbstractTree
import trees.BSTree
import trees.RBTree
import trees.nodes.AbstractNode
import java.awt.FileDialog
import java.io.File

class OpeningController<
        NodeType : AbstractNode<NodeDataGUI, NodeType>,
        TreeType : AbstractTree<NodeDataGUI, NodeType>,
        SerializationType : Serialization<NodeDataGUI, NodeType, TreeType, *>> {

    var repository: Repository<NodeDataGUI, *, AbstractTree<NodeDataGUI, *>>? = null
    var tree: AbstractTree<NodeDataGUI, NodeType>? = null
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
        val strategy = getStrategy(typeOfTree)
        repository = when (typeOfDatabase) {
            TypeOfDatabase.Json -> JsonRepository(
                strategy,
                dirPath,
                filename
            ) as Repository<NodeDataGUI, *, AbstractTree<NodeDataGUI, *>>
//            TypeOfDatabase.Neo4j -> Neo4jRepo(strategy, dirPath)
//            TypeOfDatabase.SQL -> SQLRepository(strategy, dirPath)
            else -> JsonRepository(
                strategy,
                dirPath,
                filename
            ) as Repository<NodeDataGUI, *, AbstractTree<NodeDataGUI, *>>
        }
    }

    fun getNamesOfTrees(): List<String> {
        return repository?.getNames() ?: listOf()
    }

    fun loadTree(typeOfTree: TypeOfTree, name: String) {
        tree = repository?.loadByName(name) as AbstractTree<NodeDataGUI, NodeType>?
        if (tree == null) {
            tree = when (typeOfTree) {
                TypeOfTree.RB_TREE -> RBTree<NodeDataGUI>() as AbstractTree<NodeDataGUI, NodeType>
                TypeOfTree.AVL_TREE -> AVLTree<NodeDataGUI>() as AbstractTree<NodeDataGUI, NodeType>
                TypeOfTree.BS_TREE -> BSTree<NodeDataGUI>() as AbstractTree<NodeDataGUI, NodeType>
            }
        }
    }

    fun getStrategy(typeOfTree: TypeOfTree): SerializationType {
        val strategy = when (typeOfTree) {
            TypeOfTree.AVL_TREE -> AVLStrategy(NodeDataGUI::serialize, NodeDataGUI::deserialize)
            TypeOfTree.BS_TREE -> BSStrategy(NodeDataGUI::serialize, NodeDataGUI::deserialize)
            TypeOfTree.RB_TREE -> RBStrategy(NodeDataGUI::serialize, NodeDataGUI::deserialize)
        }
        return strategy as SerializationType
    }
}