/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repo

import NodesTable
import SQLNodeEntity
import SQLTreeEntity
import TreesTable
import app.trees.AbstractTree
import app.trees.nodes.AbstractNode
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import repo.serialization.Metadata
import repo.serialization.SerializableNode
import repo.serialization.SerializableTree
import repo.serialization.SerializableValue
import repo.serialization.strategies.Serialization

class SQLRepository<T : Comparable<T>,
        NodeType : AbstractNode<T, NodeType>,
        TreeType : AbstractTree<T, NodeType>>(
    strategy: Serialization<T, NodeType, TreeType, *>,
    private val db: Database
) : Repository<T, NodeType, TreeType>(strategy) {

    private val typeOfTree = strategy.typeOfTree.toString()

    init {
        transaction(db) {
            SchemaUtils.create(TreesTable)
            SchemaUtils.create(NodesTable)
        }
    }

    private fun SQLNodeEntity.toSerializableNode(): SerializableNode {
        return SerializableNode(
            SerializableValue(data),
            Metadata(metadata),
            left?.toSerializableNode(),
            right?.toSerializableNode(),
        )
    }

    private fun SQLNodeEntity.deserialize(parent: NodeType? = null): NodeType? {
        val node = strategy.createNode(this.toSerializableNode())
        node?.parent = parent
        node?.left = left?.deserialize(node)
        node?.right = right?.deserialize(node)
        return node
    }

    private fun SerializableNode.toEntity(tree: SQLTreeEntity): SQLNodeEntity = SQLNodeEntity.new {
        this@new.data = this@toEntity.data.value
        this@new.metadata = this@toEntity.metadata.value
        this@new.left = this@toEntity.left?.toEntity(tree)
        this@new.right = this@toEntity.right?.toEntity(tree)
        this.tree = tree
    }

    private fun SerializableTree.toEntity(): SQLTreeEntity {
        return SQLTreeEntity.new {
            this.name = this@toEntity.name
            this.typeOfTree = this@SQLRepository.typeOfTree
        }
    }

    override fun save(name: String, tree: TreeType): Unit = transaction(db) {
        deleteByName(name)
        val entityTree = tree.toSerializableTree(name).toEntity()
        entityTree.root = tree.root?.toSerializableNode()?.toEntity(entityTree)
    }


    override fun loadByName(name: String): TreeType? = transaction(db) {
        SQLTreeEntity.find(
            TreesTable.typeOfTree eq typeOfTree and (TreesTable.name eq name)
        ).firstOrNull()?.let {
            strategy.createTree().apply { root = it.root?.deserialize() }
        }
    }

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

    override fun getNames(): List<String> = transaction(db) {
        SQLTreeEntity.find(TreesTable.typeOfTree eq typeOfTree).map(SQLTreeEntity::name)
    }
}