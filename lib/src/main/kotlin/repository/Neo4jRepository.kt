/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repository

import org.neo4j.ogm.config.Configuration
import org.neo4j.ogm.cypher.ComparisonOperator
import org.neo4j.ogm.cypher.Filter
import org.neo4j.ogm.cypher.Filters
import org.neo4j.ogm.session.SessionFactory
import repository.neo4jEntities.Neo4jNodeEntity
import repository.neo4jEntities.Neo4jTreeEntity
import repository.serialization.SerializableNode
import repository.serialization.SerializableTree
import repository.serialization.strategies.Serialization
import trees.AbstractTree
import trees.nodes.AbstractNode

class Neo4jRepo<T : Comparable<T>,
        NodeType : AbstractNode<T, NodeType>,
        TreeType : AbstractTree<T, NodeType>>(
    strategy: Serialization<T, NodeType, TreeType, *>,
    configuration: Configuration
) : Repository<T, NodeType, TreeType>(strategy) {
    private val sessionFactory = SessionFactory(configuration, "repository")
    private val session = sessionFactory.openSession()

    //converts Neo4jNodeEntity to SerializableNode.
    private fun Neo4jNodeEntity.toSerializableNode(): SerializableNode {
        return SerializableNode(
            data,
            metadata,
            left?.toSerializableNode(),
            right?.toSerializableNode(),
        )
    }

    //converts Neo4jNodeEntity to a node
    private fun Neo4jNodeEntity.deserialize(parent: NodeType? = null): NodeType? {
        val node = strategy.createNode(this.toSerializableNode())
        node?.parent = parent
        node?.left = left?.deserialize(node)
        node?.right = right?.deserialize(node)
        return node
    }

    //converts SerializableTree to Neo4jTreeEntity.
    private fun SerializableNode.toEntity(): Neo4jNodeEntity {
        return Neo4jNodeEntity(
            data,
            metadata,
            left?.toEntity(),
            right?.toEntity(),
        )
    }

    //converts Neo4jTreeEntity to SerializableTree.
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

    //saves the tree with the specified name to the database.
    override fun save(name: String, tree: TreeType) {
        deleteByName(name)
        val entityTree = tree.toSerializableTree(name).toEntity()
        session.save(entityTree)
    }

    //is used to get a list of trees from the database that match the specified filtering options.
    private fun findByVerboseName(name: String) = session.loadAll(
        Neo4jTreeEntity::class.java,
        Filters().and(
            Filter("name", ComparisonOperator.EQUALS, name)
        ).and(
            Filter("typeOfTree", ComparisonOperator.EQUALS, strategy.typeOfTree)
        ),
        -1
    )

    //loads the tree with the specified name from the database.
    override fun loadByName(name: String): TreeType {
        val tree = findByVerboseName(name).singleOrNull()
        val result = strategy.createTree().apply {
            root = tree?.root?.deserialize()
        }
        return result
    }

    //removes the tree with the specified name from the database.
    override fun deleteByName(name: String) {
        session.query(
            "MATCH toDelete=(" +
                    "t:Tree {typeOfTree: \$typeOfTree, name : \$name}" +
                    ")-[*0..]->() DETACH DELETE toDelete",
            mapOf("typeOfTree" to strategy.typeOfTree, "name" to name)
        )
    }

    //returns a list of the names of all saved trees in the database.
    override fun getNames(): List<String> = session.loadAll(
        Neo4jTreeEntity::class.java,
        Filter("typeOfTree", ComparisonOperator.EQUALS, strategy.typeOfTree),
        0
    ).map(Neo4jTreeEntity::name)
}