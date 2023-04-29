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
    //companion object of a class that inherits from IntEntityClass<SQLNodeEntity>
    //and defines the base class for the entity.
    companion object : IntEntityClass<SQLNodeEntity>(NodesTable)

    //a variable responsible for storing node data in the database.
    var data by NodesTable.data

    //a variable responsible for storing the node's metadata in the database.
    var metadata by NodesTable.metadata

    //a variable responsible for storing a link to the left node of the tree in the database.
    var left by SQLNodeEntity optionalReferencedOn NodesTable.left

    //a variable responsible for storing a link to the right node of the tree in the database.
    var right by SQLNodeEntity optionalReferencedOn NodesTable.right

    //variable responsible for saving the link to the tree in the database.
    var tree by SQLTreeEntity referencedOn NodesTable.tree
}

internal object NodesTable : IntIdTable("nodes") {
    //table column containing node data.
    val data = text("data")

    //a table column containing node metadata.
    val metadata = text("metadata")

    //table column containing a link to the left node of the tree.
    // It may be undefined if there is no left node.
    val left = reference("left_id", NodesTable).nullable()

    //table column containing a link to the right node of the tree.
    //It may be undefined if the right node is missing.
    val right = reference("right_id", NodesTable).nullable()

    //a table column containing a link to the tree.
    // When a tree is removed from the database, all nodes associated with it are also removed.
    val tree = reference("tree_id", TreesTable, onDelete = ReferenceOption.CASCADE)
}