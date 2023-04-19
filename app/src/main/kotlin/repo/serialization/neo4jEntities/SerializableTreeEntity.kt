/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repo.serialization.neo4jEntities

import org.neo4j.ogm.annotation.*
import repo.serialization.TypeOfTree

@NodeEntity("Tree")
class SerializableTreeEntity(
    @Property("name")
    var name: String,

    @Property("typeOfTree")
    var typeOfTree: TypeOfTree,

    @Relationship(type = "ROOT", direction = Relationship.Direction.OUTGOING)
    var root: SerializableNodeEntity?,
) {

    @Id
    @GeneratedValue
    var id: Long? = null

}