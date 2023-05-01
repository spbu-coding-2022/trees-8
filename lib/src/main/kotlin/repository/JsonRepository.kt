/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import repository.jsonEntities.JsonNode
import repository.jsonEntities.JsonTree
import repository.serialization.SerializableNode
import repository.serialization.SerializableTree
import repository.serialization.strategies.Serialization
import trees.AbstractTree
import trees.nodes.AbstractNode
import java.io.File
import java.io.FileNotFoundException

//creates a new JsonRepository object with the given strategy and dirPath. strategy
class JsonRepository<T : Comparable<T>, NodeType : AbstractNode<T, NodeType>, TreeType : AbstractTree<T, NodeType>>
    (
    //serialization strategy for working with trees and nodes.
    strategy: Serialization<T, NodeType, TreeType, *>,
    //path to the directory where tree files will be stored in JSON format.
    private val dirPath: String, private val filename: String
) : Repository<T, NodeType, TreeType>(strategy) {

    private val typeToken = object : TypeToken<MutableList<JsonTree>>() {}.type


    //function to convert JsonNode to SerializableNode.
    private fun JsonNode.toSerializableNode(): SerializableNode {
        return SerializableNode(
            data, metadata, left?.toSerializableNode(), right?.toSerializableNode()
        )
    }

    //a function to deserialize a JsonNode into a tree node
    private fun JsonNode.deserialize(parent: NodeType? = null): NodeType? {
        val node = strategy.createNode(this.toSerializableNode())
        node?.parent = parent
        node?.left = left?.deserialize(node)
        node?.right = right?.deserialize(node)
        return node
    }

    //function to convert SerializableNode to JsonNode.
    private fun SerializableNode.toJsonNode(): JsonNode {
        return JsonNode(
            data, metadata, left?.toJsonNode(), right?.toJsonNode()
        )
    }

    //function to convert SerializableTree to JsonTree
    private fun SerializableTree.toJsonTree(): JsonTree {
        return JsonTree(
            name, typeOfTree, root?.toJsonNode()
        )
    }

    //method for getting a list of tree names.
    override fun getNames(): List<String> {
        try {
            File(dirPath, filename).run {
                val names = Gson().fromJson<MutableList<JsonTree>>(readText(), typeToken)
                    ?.filter { it.typeOfTree == strategy.typeOfTree }?.map { it.name }
                return names ?: listOf()
            }
        } catch (_: FileNotFoundException) {
            return listOf()
        }
    }

    //method to load a tree by name
    override fun loadByName(name: String): TreeType? {
        val json = try {
            File(dirPath, filename).readText()
        } catch (_: FileNotFoundException) {
            return null
        }

        val jsonTree = Gson().fromJson<MutableList<JsonTree>>(json, typeToken)
            ?.firstOrNull { it.name == name && it.typeOfTree == strategy.typeOfTree } ?: return null
        return strategy.createTree().apply {
            root = jsonTree.root?.deserialize()
        }
    }

    //method to save tree by name
    override fun save(name: String, tree: TreeType) {
        val jsonTree = tree.toSerializableTree(name).toJsonTree()

        deleteByName(name)

        File(dirPath).mkdirs()
        File(dirPath, filename).run {
            createNewFile()
            var trees = Gson().fromJson<MutableList<JsonTree>>(readText(), typeToken)
            if (trees == null) {
                trees = mutableListOf()
            }
            trees.add(jsonTree)
            writeText(Gson().toJson(trees))
        }
    }

    //a method for deleting a tree by name
    override fun deleteByName(name: String) {
        try {
            File(dirPath, filename).run {
                val trees = Gson().fromJson<MutableList<JsonTree>>(readText(), typeToken) ?: mutableListOf()
                trees.removeIf { it.name == name && it.typeOfTree == strategy.typeOfTree }
                writeText(Gson().toJson(trees))
            }
        } catch (_: FileNotFoundException) {
        }
    }
}