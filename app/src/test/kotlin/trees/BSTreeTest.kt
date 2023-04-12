package trees

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import trees.nodes.BSNode
import trees.trees.BSTree
import java.util.*

class BSTreeTest {

    @Test
    fun testInvariant() {
        val bst = BSTree<Int>()
        val lst = List(100) { Random(42).nextInt() }
        for (num in lst) {
            bst.add(num)
        }

        assertTrue(checkInvariant(bst.root))

    }

    private fun checkInvariant(node: BSNode<Int>?): Boolean {
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