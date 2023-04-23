/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees


import app.trees.rb.Color
import app.trees.rb.RBNode
import app.trees.rb.RBTree
import kotlin.random.Random
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RBTreeTest {
    companion object {
        const val seed = 42
    }

    private lateinit var tree: RBTree<Int>
    private lateinit var values: Array<Int>
    private val randomizer = Random(seed)

    @BeforeTest
    fun init() {
        values = Array(1000) { randomizer.nextInt(10000) }
        tree = RBTree()
    }

    @Test
    fun `check invariant while adding`() {
        for (value in values) {
            tree.add(value)
            assertTrue(
                InvariantTester.checkBlackHeight(tree.root),
                "Failed invariant, incorrect black height, value: $value"
            )
            assertTrue(
                InvariantTester.checkRedParent(tree.root),
                "Failed invariant, incorrect color for node, value: $value"
            )
            assertTrue(InvariantTester.checkDataInNodes(tree.root), "Failed invariant, incorrect data, value: $value")
            assertTrue(
                InvariantTester.checkLinksToParent(tree.root),
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
            assertTrue(
                InvariantTester.checkBlackHeight(tree.root),
                "Failed invariant, incorrect black height, value: $value"
            )
            assertTrue(
                InvariantTester.checkRedParent(tree.root),
                "Failed invariant, incorrect color for node, value: $value"
            )
            assertTrue(InvariantTester.checkDataInNodes(tree.root), "Failed invariant, incorrect data, value: $value")
            assertTrue(
                InvariantTester.checkLinksToParent(tree.root),
                "Failed invariant, incorrect parent's link, value: $value"
            )
        }
    }

    @Test
    fun `special incorrect test`() {
        tree.add(10)
        tree.root?.left = RBNode(15)
        tree.root?.right = RBNode(5)
        tree.root?.right?.color = Color.BLACK
        tree.root?.color = Color.RED
        assertFalse(InvariantTester.checkBlackHeight(tree.root), "Failed invariant, incorrect black height")
        assertFalse(InvariantTester.checkRedParent(tree.root), "Failed invariant, incorrect color for node")
        assertFalse(InvariantTester.checkDataInNodes(tree.root), "Failed invariant, incorrect data")
        assertFalse(InvariantTester.checkLinksToParent(tree.root), "Failed invariant, incorrect parent's link")
    }

    @Test
    fun `check for the presence of elements`() {
        for (value in values) {
            tree.add(value)
            assertTrue(tree.contains(value))
        }
    }
}