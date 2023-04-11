package trees

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import trees.nodes.BSNode
import trees.trees.BSTree

class BSTreeTest {

    @Test
    fun testInvariant() {
        val bst = BSTree<KeyValue<Int, Int>>()
        bst.add(KeyValue(10, 10))
        bst.add(KeyValue(10, 1))
        bst.add(KeyValue(10, 9))
        bst.add(KeyValue(10, 2))
        bst.add(KeyValue(10, 3))
        bst.add(KeyValue(10, 4))
        bst.add(KeyValue(10, 5))

        assertTrue(checkInvariant(bst.root))

    }

    private fun checkInvariant(node: BSNode<KeyValue<Int, Int>>?): Boolean {
        if (node == null) return true
        if (node.left != null && node.left!!.data > node.data) {
            return false
        }
        if (node.right != null && node.right!!.data < node.data) {
            return false
        }
        return checkInvariant(node.left) && checkInvariant(node.right)
    }
}