/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repo

import app.trees.AbstractTree
import app.trees.nodes.AbstractNode
import org.neo4j.ogm.config.Configuration
import org.neo4j.ogm.cypher.ComparisonOperator
import org.neo4j.ogm.cypher.Filter
import org.neo4j.ogm.cypher.Filters
import org.neo4j.ogm.session.SessionFactory
import repo.neo4jEntities.Neo4jNodeEntity
import repo.neo4jEntities.Neo4jTreeEntity
import repo.serialization.SerializableNode
import repo.serialization.SerializableTree
import repo.serialization.strategies.Serialization

class Neo4jRepo<T : Comparable<T>,
        NodeType : AbstractNode<T, NodeType>,
        TreeType : AbstractTree<T, NodeType>>(
    strategy: Serialization<T, NodeType, TreeType, *>,
    configuration: Configuration
) : Repository<T, NodeType, TreeType>(strategy) {
    private val sessionFactory = SessionFactory(configuration, "repo")
    private val session = sessionFactory.openSession()

    private fun Neo4jNodeEntity.toSerializableNode(): SerializableNode {
        return SerializableNode(
            data,
            metadata,
            left?.toSerializableNode(),
            right?.toSerializableNode(),
        )
    }

    private fun Neo4jNodeEntity.deserialize(parent: NodeType? = null): NodeType? {
        val node = strategy.createNode(this.toSerializableNode())
        node?.parent = parent
        node?.left = left?.deserialize(node)
        node?.right = right?.deserialize(node)
        return node
    }

    private fun SerializableNode.toEntity(): Neo4jNodeEntity {
        return Neo4jNodeEntity(
            data,
            metadata,
            left?.toEntity(),
            right?.toEntity(),
        )
    }

    private fun Neo4jTreeEntity.toTree(): SerializableTree {
        return SerializableTree(
            name,
            typeOfTree,
            root?.toSerializableNode(),
        )
    }

    private fun SerializableTree.toEntity(): Neo4jTreeEntity {
        return Neo4jTreeEntity(
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
        Neo4jTreeEntity::class.java,
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
                    "t:Tree {typeOfTree: \$typeOfTree, name : \$name}" +
                    ")-[*0..]->() DETACH DELETE toDelete",
            mapOf("typeOfTree" to strategy.typeOfTree, "name" to name)
        )
    }

    override fun getNames(): List<String> = session.loadAll(
        Neo4jTreeEntity::class.java,
        Filter("typeOfTree", ComparisonOperator.EQUALS, strategy.typeOfTree),
        0
    ).map(Neo4jTreeEntity::name)
}