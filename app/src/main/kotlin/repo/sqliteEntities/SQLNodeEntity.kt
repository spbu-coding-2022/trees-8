/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

class SQLNodeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SQLNodeEntity>(NodesTable)

    var data by NodesTable.data
    var metadata by NodesTable.metadata
    var left by SQLNodeEntity optionalReferencedOn NodesTable.left
    var right by SQLNodeEntity optionalReferencedOn NodesTable.right
    var tree by SQLTreeEntity referencedOn NodesTable.tree
}

internal object NodesTable : IntIdTable("nodes") {
    val data = text("data")
    val metadata = text("metadata")
    val left = reference("left_id", NodesTable).nullable()
    val right = reference("right_id", NodesTable).nullable()
    val tree = reference("tree_id", TreesTable, onDelete = ReferenceOption.CASCADE)
}