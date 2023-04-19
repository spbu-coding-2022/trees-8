/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package repo.serialization.neo4jEntities

import org.neo4j.ogm.annotation.*
import repo.serialization.TypeOfTree

@NodeEntity
class SerializableTreeEntity(
    @Property
    var verboseName: String,

    @Property
    var typeOfTree: TypeOfTree,

    @Relationship(type = "ROOT")
    var root: SerializableNodeEntity? = null,
) {

    @Id
    @GeneratedValue
    var id: Long? = null

}