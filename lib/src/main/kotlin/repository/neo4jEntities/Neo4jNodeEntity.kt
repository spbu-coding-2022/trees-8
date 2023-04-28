/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repository.neo4jEntities

import org.neo4j.ogm.annotation.*
import repository.serialization.Metadata
import repository.serialization.SerializableValue

@NodeEntity("Node")
class Neo4jNodeEntity(
    @Property("data")
    var data: SerializableValue,

    @Property("metadata")
    var metadata: Metadata,

    @Relationship(type = "LEFT", direction = Relationship.Direction.OUTGOING)
    var left: Neo4jNodeEntity? = null,

    @Relationship(type = "RIGHT", direction = Relationship.Direction.OUTGOING)
    var right: Neo4jNodeEntity? = null,
) {
    @Id
    @GeneratedValue
    var id: Long? = null
}