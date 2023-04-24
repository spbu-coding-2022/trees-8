/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

import app.trees.AVLTree
import org.neo4j.ogm.config.Configuration
import repo.Neo4jRepo
import repo.serialization.SerializableValue
import repo.serialization.strategies.AVLStrategy
import kotlin.random.Random

fun main() {
    val tree = AVLTree<Int>()
    val randomizer = Random(40)
    val lst = List(10) { randomizer.nextInt(100) }
    lst.forEach { tree.add(it) }
    val conf = Configuration.Builder()
        .uri("neo4j://localhost")
        .credentials("neo4j", "359F35QD88glhf.")
        .build()

    fun serializeInt(data: Int): SerializableValue {
        return SerializableValue(data.toString())
    }

    fun deserializeInt(data: SerializableValue): Int {
        return data.value.toInt()
    }

    val avlRepo = Neo4jRepo(AVLStrategy(::serializeInt, ::deserializeInt), conf)
    avlRepo.save("main", tree)
    val testTree = avlRepo.loadByName("main")
    println(testTree.preOrder())
}