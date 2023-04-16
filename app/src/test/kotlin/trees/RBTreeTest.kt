/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees


import trees.nodes.Color
import trees.nodes.RBNode
import trees.trees.RBTree
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
            assertTrue(InvariantTest.checkBlackHeight(tree.root), "Failed invariant, incorrect black height")
            assertTrue(InvariantTest.checkRedParent(tree, tree.root), "Failed invariant, incorrect color for node")
            assertTrue(InvariantTest.checkDataInNodes(tree.root), "Failed invariant, incorrect data")
            assertTrue(InvariantTest.checkLinksToParent(tree.root), "Failed invariant, incorrect parent's link")
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
            assertTrue(InvariantTest.checkBlackHeight(tree.root), "Failed invariant, incorrect black height")
            assertTrue(InvariantTest.checkRedParent(tree, tree.root), "Failed invariant, incorrect color for node")
            assertTrue(InvariantTest.checkDataInNodes(tree.root), "Failed invariant, incorrect data")
            assertTrue(InvariantTest.checkLinksToParent(tree.root), "Failed invariant, incorrect parent's link")
        }
    }

    @Test
    fun `special incorrect test`() {
        tree.add(10)
        tree.root?.left = RBNode(15)
        tree.root?.right = RBNode(5)
        tree.root?.right?.color = Color.BLACK
        tree.root?.color = Color.RED
        assertFalse(InvariantTest.checkBlackHeight(tree.root), "Failed invariant, incorrect black height")
        assertFalse(InvariantTest.checkRedParent(tree, tree.root), "Failed invariant, incorrect color for node")
        assertFalse(InvariantTest.checkDataInNodes(tree.root), "Failed invariant, incorrect data")
        assertFalse(InvariantTest.checkLinksToParent(tree.root), "Failed invariant, incorrect parent's link")
    }
}