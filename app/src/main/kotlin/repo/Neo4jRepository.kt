/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repo

import app.trees.abs.ABSTree
import app.trees.abs.MyNode
import org.neo4j.ogm.config.Configuration
import org.neo4j.ogm.cypher.ComparisonOperator
import org.neo4j.ogm.cypher.Filter
import org.neo4j.ogm.cypher.Filters
import org.neo4j.ogm.session.SessionFactory
import repo.serialization.SerializableNode
import repo.serialization.SerializableTree
import repo.serialization.neo4jEntities.SerializableNodeEntity
import repo.serialization.neo4jEntities.SerializableTreeEntity
import repo.serialization.strategies.Serialization

class Neo4jRepo<
        T : Comparable<T>,
        NodeType : MyNode<T, NodeType>,
        TreeType : ABSTree<T, NodeType>,
        >(
    strategy: Serialization<T, NodeType, TreeType, *>,
    configuration: Configuration
) : Repository<T, NodeType, TreeType>(strategy) {
    private val sessionFactory = SessionFactory(configuration, "repo")
    private val session = sessionFactory.openSession()

    private fun SerializableNodeEntity.toSerializableNode(): SerializableNode {
        return SerializableNode(
            data,
            metadata,
            left?.toSerializableNode(),
            right?.toSerializableNode(),
        )
    }

    private fun SerializableTreeEntity.toTree(): SerializableTree {
        return SerializableTree(
            name,
            typeOfTree,
            root?.toSerializableNode(),
        )
    }

    private fun SerializableNode.toEntity(): SerializableNodeEntity {
        return SerializableNodeEntity(
            data,
            metadata,
            left?.toEntity(),
            right?.toEntity(),
        )
    }

    private fun SerializableTree.toEntity(): SerializableTreeEntity {
        return SerializableTreeEntity(
            name,
            typeOfTree,
            root?.toEntity(),
        )
    }

    override fun save(name: String, tree: TreeType) {
        deleteByName(name)
        val entityTree = tree.toSerializableTree(name).toEntity()
        session.save(entityTree)
    }

    private fun findByVerboseName(name: String) = session.loadAll(
        SerializableTreeEntity::class.java,
        Filters().and(
            Filter("name", ComparisonOperator.EQUALS, name)
        ).and(
            Filter("typeOfTree", ComparisonOperator.EQUALS, strategy.typeOfTree)
        ),
        -1
    )

    override fun loadByName(name: String): TreeType {
        val tree = findByVerboseName(name).singleOrNull()
        val result = strategy.createTree().apply {
            root = tree?.root?.deserialize()
        }
        return result
    }

    override fun deleteByName(name: String) {
        session.query(
            "MATCH toDelete=(" +
                    "t:SerializableTreeEntity {typeOfTree: \$typeOfTree, name : \$name}" +
                    ")-[*0..]->() DETACH DELETE toDelete",
            mapOf("typeOfTree" to strategy.typeOfTree, "name" to name)
        )
    }

    private fun SerializableNodeEntity.deserialize(parent: NodeType? = null): NodeType? {
        val node = strategy.createNode(this.toSerializableNode())
        node?.parent = parent
        node?.left = left?.deserialize(node)
        node?.right = right?.deserialize(node)
        return node
    }
}