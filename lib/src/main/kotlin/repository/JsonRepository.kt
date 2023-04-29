/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repository

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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
class JsonRepository<T : Comparable<T>,
        NodeType : AbstractNode<T, NodeType>,
        TreeType : AbstractTree<T, NodeType>>
    (
    //serialization strategy for working with trees and nodes.
    strategy: Serialization<T, NodeType, TreeType, *>,
    //path to the directory where tree files will be stored in JSON format.
    dirPath: String
) : Repository<T, NodeType, TreeType>(strategy) {

    private val dirPath = "$dirPath/${strategy.typeOfTree.name.lowercase()}"

    //function to convert JsonNode to SerializableNode.
    private fun JsonNode.toSerializableNode(): SerializableNode {
        return SerializableNode(
            data,
            metadata,
            left?.toSerializableNode(),
            right?.toSerializableNode()
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
            data,
            metadata,
            left?.toJsonNode(),
            right?.toJsonNode()
        )
    }

    //function to convert SerializableTree to JsonTree
    private fun SerializableTree.toJsonTree(): JsonTree {
        return JsonTree(
            name,
            typeOfTree,
            root?.toJsonNode()
        )
    }

    //method for getting a list of tree names.
    override fun getNames(): List<String> =
        File(dirPath).listFiles()?.map {
            Json.decodeFromString<JsonTree>(it.readText()).name
        } ?: listOf()

    //method to load a tree by name
    override fun loadByName(name: String): TreeType? {
        val json = try {
            File(dirPath, "${name}.json").readText()
        } catch (_: FileNotFoundException) {
            return null
        }

        val jsonTree = Json.decodeFromString<JsonTree>(json)
        return strategy.createTree().apply {
            root = jsonTree.root?.deserialize()
        }
    }

    //method to save tree by name
    override fun save(name: String, tree: TreeType) {
        val jsonTree = tree.toSerializableTree(name).toJsonTree()

        File(dirPath).mkdirs()
        File(dirPath, "${name}.json").run {
            createNewFile()
            writeText(Json.encodeToString(jsonTree))
        }
    }

    //a method for deleting a tree by name
    override fun deleteByName(name: String) {
        File(dirPath, "${name}.json").delete()
    }
}