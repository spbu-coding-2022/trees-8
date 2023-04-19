/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repo

import app.trees.ABSTree
import app.trees.nodes.MyNode
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
        E : Comparable<E>,
        NodeType : MyNode<E, NodeType>,
        TreeType : ABSTree<E, NodeType>,
        >(
    strategy: Serialization<E, NodeType, TreeType, *>,
    configuration: Configuration
) : Repository<E, NodeType, TreeType>(strategy) {
    private val sessionFactory = SessionFactory(configuration, "repo")
    private val session = sessionFactory.openSession()

    private fun SerializableNodeEntity.toSerializableNode(): SerializableNode {
        return SerializableNode(
            value,
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
            value,
            metadata,
            left?.toEntity(),
            right?.toEntity(),
        )
    }

    private fun SerializableTree.toEntity(): SerializableTreeEntity {
        return SerializableTreeEntity(
            verboseName,
            typeOfTree,
            root?.toEntity(),
        )
    }

    override fun save(verboseName: String, tree: TreeType) {
        deleteByVerboseName(verboseName)
        val entityTree = tree.toSerializableTree(verboseName).toEntity()
        session.save(entityTree)
    }

    private fun findByVerboseName(verboseName: String) = session.loadAll(
        SerializableTreeEntity::class.java,
        Filters().and(
            Filter("name", ComparisonOperator.EQUALS, verboseName)
        ).and(
            Filter("typeOfTree", ComparisonOperator.EQUALS, strategy.typeOfTree)
        ),
        -1
    )

    override fun loadByVerboseName(verboseName: String): TreeType {
        val tree = findByVerboseName(verboseName).singleOrNull()
        val result = strategy.createTree().apply {
            root = tree?.root?.deserialize()
        }
        return result
    }

    override fun deleteByVerboseName(verboseName: String) {
        session.query(
            "MATCH toDelete=(" +
                    "t:SerializableTreeEntity {typeOfTree: \$typeOfTree, verboseName : \$verboseName}" +
                    ")-[*0..]->() DETACH DELETE toDelete",
            mapOf("typeOfTree" to strategy.typeOfTree, "verboseName" to verboseName)
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