/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

class SQLTreeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SQLTreeEntity>(TreesTable)

    var name by TreesTable.name
    var typeOfTree by TreesTable.typeOfTree
    var root by SQLNodeEntity optionalReferencedOn TreesTable.root
}

internal object TreesTable : IntIdTable("trees") {
    val name = text("name")
    val typeOfTree = text("type")
    val root = reference("root_node_id", NodesTable).nullable()

    init {
        uniqueIndex(name, typeOfTree)
    }
}