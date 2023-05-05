/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repository

import NodesTable
import SQLNodeEntity
import SQLTreeEntity
import TreesTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import repository.serialization.Metadata
import repository.serialization.SerializableNode
import repository.serialization.SerializableTree
import repository.serialization.SerializableValue
import repository.serialization.strategies.Serialization
import trees.AbstractTree
import trees.nodes.AbstractNode

class SQLRepository<T : Comparable<T>,
        NodeType : AbstractNode<T, NodeType>,
        TreeType : AbstractTree<T, NodeType>>(
    strategy: Serialization<T, NodeType, TreeType, *>,
    private val db: Database
) : Repository<T, NodeType, TreeType>(strategy) {

    private val typeOfTree = strategy.typeOfTree.toString()

    //Initializes the database tables for storing trees and nodes.
    init {
        transaction(db) {
            SchemaUtils.create(TreesTable)
            SchemaUtils.create(NodesTable)
        }
    }

    //Converts an instance of SQLNodeEntity to a SerializableNode object using the serialization strategy.
    private fun SQLNodeEntity.toSerializableNode(): SerializableNode {
        return SerializableNode(
            SerializableValue(data),
            Metadata(metadata),
            left?.toSerializableNode(),
            right?.toSerializableNode(),
        )
    }

    //Deserializes an instance of SQLNodeEntity to a NodeType object using the serialization strategy.
    private fun SQLNodeEntity.deserialize(parent: NodeType? = null): NodeType? {
        val node = strategy.createNode(this.toSerializableNode())
        node?.parent = parent
        node?.left = left?.deserialize(node)
        node?.right = right?.deserialize(node)
        return node
    }

    //Converts a SerializableNode object to an instance of SQLNodeEntity using the serialization strategy.
    private fun SerializableNode.toEntity(tree: SQLTreeEntity): SQLNodeEntity = SQLNodeEntity.new {
        this@new.data = this@toEntity.data.value
        this@new.metadata = this@toEntity.metadata.value
        this@new.left = this@toEntity.left?.toEntity(tree)
        this@new.right = this@toEntity.right?.toEntity(tree)
        this.tree = tree
    }

    //Converts a SerializableTree object to an instance of SQLTreeEntity.
    private fun SerializableTree.toEntity(): SQLTreeEntity {
        return SQLTreeEntity.new {
            this.name = this@toEntity.name
            this.typeOfTree = this@SQLRepository.typeOfTree
        }
    }

    //Saves a TreeType object to the database with a given name by deleting any existing tree with the same name and creating a new one.
    override fun save(name: String, tree: TreeType): Unit = transaction(db) {
        deleteByName(name)
        val entityTree = tree.toSerializableTree(name).toEntity()
        entityTree.root = tree.root?.toSerializableNode()?.toEntity(entityTree)
    }

    //Loads a TreeType object from the database by name, if it exists.
    override fun loadByName(name: String): TreeType? = transaction(db) {
        SQLTreeEntity.find(
            TreesTable.typeOfTree eq typeOfTree and (TreesTable.name eq name)
        ).firstOrNull()?.let {
            strategy.createTree().apply { root = it.root?.deserialize() }
        }
    }

    //Deletes a tree from the database by name.
    override fun deleteByName(name: String): Unit = transaction(db) {
        val treeId = SQLTreeEntity.find(
            TreesTable.typeOfTree eq strategy.typeOfTree.toString() and (TreesTable.name eq name)
        ).firstOrNull()?.id?.value
        SQLTreeEntity.find(
            TreesTable.id eq treeId
        ).firstOrNull()?.delete()
        NodesTable.deleteWhere {
            tree eq treeId
        }
    }

    //Returns a list of names of all the trees in the database.
    override fun getNames(): List<String> = transaction(db) {
        SQLTreeEntity.find(TreesTable.typeOfTree eq typeOfTree).map(SQLTreeEntity::name)
    }
}