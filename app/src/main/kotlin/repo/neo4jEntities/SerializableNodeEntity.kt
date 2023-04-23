/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repo.neo4jEntities

import org.neo4j.ogm.annotation.*
import repo.serialization.Metadata
import repo.serialization.SerializableValue

@NodeEntity("Node")
class SerializableNodeEntity(
    @Property("data")
    var data: SerializableValue,

    @Property("metadata")
    var metadata: Metadata,

    @Relationship(type = "LEFT", direction = Relationship.Direction.OUTGOING)
    var left: SerializableNodeEntity? = null,

    @Relationship(type = "RIGHT", direction = Relationship.Direction.OUTGOING)
    var right: SerializableNodeEntity? = null,
) {
    @Id
    @GeneratedValue
    var id: Long? = null
}