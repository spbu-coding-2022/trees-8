/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import app.app
import java.awt.Dimension

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose for Desktop",
        state = rememberWindowState(
            size = DpSize(700.dp, 700.dp),
            position = WindowPosition(alignment = Alignment.Center)
        )

    ) {
        window.minimumSize = Dimension(700, 700)
        app(window)
    }
//    val username = "neo4j"
//    val password = "isabel-except-toronto-monaco-never-5754" // insert password to database here
//    val conf = Configuration.Builder()
//        .uri("bolt://localhost")
//        .credentials(username, password)
//        .build()
//
//    fun serializeInt(data: Int) = SerializableValue(data.toString())
//
//    fun deserializeInt(data: SerializableValue) = data.value.toInt()
//
//    val avlRepo = Neo4jRepo(AVLStrategy(::serializeInt, ::deserializeInt), conf)
//// !!! storing AVLTree<Int> and AVLTree<String> in the same db is unsupported
//
//    val tree = AVLTree<Int>()
//    val randomizer = Random(42)
//    val lst = List(15) { randomizer.nextInt(1000) }
//    lst.forEach { tree.add(it) }
//    avlRepo.save("test", tree)
//    val testTree = avlRepo.loadByName("test")
//    println(testTree.preOrder()) // output pre-order traversal of tree
//    avlRepo.save("wow", tree)
//    println(avlRepo.getNames())
}