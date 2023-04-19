/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repo.serialization.neo4jEntities

import org.neo4j.ogm.annotation.*
import repo.serialization.Metadata
import repo.serialization.SerializableValue

@NodeEntity
class SerializableNodeEntity(
    @Property
    var value: SerializableValue,

    @Property
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