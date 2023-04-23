/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repo

import JsonNode
import JsonTree
import app.trees.AbstractTree
import app.trees.nodes.AbstractNode
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import repo.serialization.SerializableNode
import repo.serialization.SerializableTree
import repo.serialization.strategies.Serialization
import java.io.File
import java.io.FileNotFoundException


class JsonRepository<T : Comparable<T>,
        NodeType : AbstractNode<T, NodeType>,
        TreeType : AbstractTree<T, NodeType>>
    (
    strategy: Serialization<T, NodeType, TreeType, *>,
    dirPath: String
) : Repository<T, NodeType, TreeType>(strategy) {
    private val dirPath = "$dirPath/${strategy.typeOfTree.name.lowercase()}"

    private fun JsonNode.toSerializableNode(): SerializableNode {
        return SerializableNode(
            data,
            metadata,
            left?.toSerializableNode(),
            right?.toSerializableNode()
        )
    }

    private fun JsonNode.deserialize(parent: NodeType? = null): NodeType? {
        val node = strategy.createNode(this.toSerializableNode())
        node?.parent = parent
        node?.left = left?.deserialize(node)
        node?.right = right?.deserialize(node)
        return node
    }

    private fun SerializableNode.toJsonNode(): JsonNode {
        return JsonNode(
            data,
            metadata,
            left?.toJsonNode(),
            right?.toJsonNode()
        )
    }

    private fun SerializableTree.toJsonTree(): JsonTree {
        return JsonTree(
            name,
            root?.toJsonNode()
        )
    }

    override fun getNames(): List<String> =
        File(dirPath).listFiles()?.map {
            Json.decodeFromString<JsonTree>(it.readText()).name
        } ?: listOf()

    override fun loadByName(name: String): TreeType? {
        val json = try {
            File(dirPath, "${name.hashCode()}.json").readText()
        } catch (_: FileNotFoundException) {
            return null
        }

        val jsonTree = Json.decodeFromString<JsonTree>(json)
        return strategy.createTree().apply {
            root = jsonTree.root?.deserialize()
        }
    }

    override fun save(name: String, tree: TreeType) {
        val jsonTree = tree.toSerializableTree(name).toJsonTree()

        File(dirPath).mkdirs()
        File(dirPath, "${name.hashCode()}.json").run {
            createNewFile()
            writeText(Json.encodeToString(jsonTree))
        }
    }

    override fun deleteByName(name: String) {
        File(dirPath, "${name.hashCode()}.json").delete()
    }
}