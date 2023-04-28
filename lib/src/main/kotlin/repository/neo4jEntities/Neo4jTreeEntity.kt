/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repository.neo4jEntities

import org.neo4j.ogm.annotation.*
import repository.serialization.TypeOfTree

@NodeEntity("Tree")
class Neo4jTreeEntity(
    @Property("name")
    var name: String,

    @Property("typeOfTree")
    var typeOfTree: TypeOfTree,

    @Relationship(type = "ROOT", direction = Relationship.Direction.OUTGOING)
    var root: Neo4jNodeEntity?,
) {

    @Id
    @GeneratedValue
    var id: Long? = null

}