/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees

import trees.nodes.AVLNode
import kotlin.random.Random
import kotlin.test.*


class AVLTreeTest {
    companion object {
        const val seed = 42
    }

    private lateinit var tree: AVLTree<Int>
    private lateinit var values: Array<Int>
    private val randomizer = Random(seed)

    @BeforeTest
    fun init() {
        values = Array(1000) { randomizer.nextInt(10000) }
        tree = AVLTree()
    }

    @Test
    fun `check invariant while adding`() {
        for (value in values) {
            tree.add(value)
            assertTrue(InvariantChecker.checkHeightAVL(tree.root), "Failed invariant, incorrect height, value: $value")
            assertTrue(InvariantChecker.checkDataInNodes(tree.root), "Failed invariant, incorrect data, value: $value")
            assertTrue(
                InvariantChecker.checkLinksToParent(tree.root),
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
            assertTrue(InvariantChecker.checkHeightAVL(tree.root), "Failed invariant, incorrect height, value: $value")
            assertTrue(InvariantChecker.checkDataInNodes(tree.root), "Failed invariant, incorrect data, value: $value")
            assertTrue(
                InvariantChecker.checkLinksToParent(tree.root),
                "Failed invariant, incorrect parent's link, value: $value"
            )
        }
    }

    @Test
    fun `special incorrect test`() {
        tree.add(10)
        tree.add(15)
        tree.add(5)
        tree.root?.left?.left = AVLNode(100)
        tree.root?.left?.left?.left = AVLNode(150)
        assertFalse(InvariantChecker.checkHeightAVL(tree.root))
        assertFalse(InvariantChecker.checkDataInNodes(tree.root), "Failed invariant, incorrect data")
        assertFalse(InvariantChecker.checkLinksToParent(tree.root), "Failed invariant, incorrect parent's link")
    }

    @Test
    fun `check deleting from empty tree without exceptions`() {
        values.forEach { tree.delete(it) }
        assertNull(tree.root, "Root should be null")
    }


    @Test
    fun `check for the presence of elements`() {
        for (value in values) {
            tree.add(value)
            assertTrue(tree.contains(value))
        }
    }

    @Test
    fun `check using KeyValue in data`() {
        val newTree = AVLTree<KeyValue<String, Int>>()
        val keyValue = values.map { KeyValue(it.toString(), it) }
        for (stringIntKeyValue in keyValue) {
            newTree.add(stringIntKeyValue)
            assertEquals(
                stringIntKeyValue.value,
                newTree.get(KeyValue(stringIntKeyValue.key, 0))?.value,
                "Values should be equals"
            )
        }
    }
}