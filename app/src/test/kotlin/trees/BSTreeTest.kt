/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees

import app.trees.nodes.BSNode
import app.trees.trees.BSTree
import kotlin.random.Random
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class BSTreeTest {
    companion object {
        const val seed = 42
    }

    private lateinit var tree: BSTree<Int>
    private lateinit var values: Array<Int>
    private val randomizer = Random(seed)

    @BeforeTest
    fun init() {
        values = Array(1000) { randomizer.nextInt(10000) }
        tree = BSTree()
    }

    @Test
    fun `check invariant while adding`() {
        for (value in values) {
            tree.add(value)
            assertTrue(InvariantTest.checkDataInNodes(tree.root), "Failed invariant, incorrect data, value: $value")
            assertTrue(
                InvariantTest.checkLinksToParent(tree.root),
                "Failed invariant, incorrect parent's link, value: $value"
            )
        }
    }

    @Test
    fun `check invariant while deleting`() {
        for (value in values) {
            tree.add(value)
        }
        values.shuffle(randomizer)
        for (value in values) {
            tree.delete(value)
            assertTrue(InvariantTest.checkDataInNodes(tree.root), "Failed invariant, incorrect data, value: $value")
            assertTrue(
                InvariantTest.checkLinksToParent(tree.root),
                "Failed invariant, incorrect parent's link, value: $value"
            )
        }
    }

    @Test
    fun `special incorrect test`() {
        tree.add(10)
        tree.root?.left = BSNode(15)
        tree.root?.right = BSNode(5)
        assertFalse(InvariantTest.checkDataInNodes(tree.root), "Failed invariant, incorrect data")
        assertFalse(InvariantTest.checkLinksToParent(tree.root), "Failed invariant, incorrect parent's link")
    }

    @Test
    fun `check for the presence of elements`() {
        for (value in values) {
            tree.add(value)
            assertTrue(tree.contain(value))
        }
    }
}