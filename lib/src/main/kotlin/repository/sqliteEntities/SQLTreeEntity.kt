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

    //tree name
    var name by TreesTable.name

    //tree type
    var typeOfTree by TreesTable.typeOfTree

    //root node of the tree, refers to a node in the node table
    var root by SQLNodeEntity optionalReferencedOn TreesTable.root
}

//an object containing metadata for the trees table in the database.
internal object TreesTable : IntIdTable("trees") {
    //tree name
    val name = text("name")

    //tree type
    val typeOfTree = text("type")

    //root node of the tree, refers to a node in the node table
    val root = reference("root_node_id", NodesTable).nullable()

    //a table initializer that sets a unique index by tree name and type.
    init {
        uniqueIndex(name, typeOfTree)
    }
}