/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repository.neo4jEntities

import org.neo4j.ogm.annotation.*
import repository.serialization.TypeOfTree

@NodeEntity("Tree")
class Neo4jTreeEntity(
    //a class property that stores the name of the tree.
    @Property("name")
    var name: String,
    //a class property that stores the tree type.
    @Property("typeOfTree")
    var typeOfTree: TypeOfTree,
    //a class property that stores a reference to the root node of the tree.
    @Relationship(type = "ROOT", direction = Relationship.Direction.OUTGOING)
    var root: Neo4jNodeEntity?,
) {
    //a class property that stores the tree ID in the Neo4j database.
    @Id
    @GeneratedValue
    var id: Long? = null

}