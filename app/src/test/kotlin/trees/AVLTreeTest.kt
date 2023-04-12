package trees

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import trees.trees.AVLTree
import trees.nodes.AVLNode
import java.lang.Math.abs


class AVLTree{
    @Test
    fun InvariantTest(){
        val AVL = AVLTree<KeyValue<Int, Int>>()
        assertTrue(AVL.root == null)
        AVL.add(KeyValue(10, 1040))
        AVL.add(KeyValue(59, 1041))
        AVL.add(KeyValue(60, 1030))
        AVL.add(KeyValue(7, 1080))
        AVL.add(KeyValue(12, 1060))
        AVL.add(KeyValue(6, 1010))
        AVL.add(KeyValue(15, 1000))
        assertTrue(AVL.root != null)


        assertTrue(checkerInvariant(AVL.root))
    }
    private fun checkerInvariant(node: AVLNode<KeyValue<Int, Int>>?): Boolean{
        if (node == null) return true

        if (node.left != null && node.left!!.data > node.data) return false

        if (node.right != null && node.right!!.data < node.data) return false

        if ((node.right != null) && (node.left != null) && (abs(node.balanceFactor()) > 1)) return false

        if (node.height != max(node.left?.height ?: 0, node.right?.height ?: 0) + 1) return false

        return checkerInvariant(node.left) && checkerInvariant(node.right)
    }

    private fun max(i: Int, j: Int): Int {
        if (i > j) return i
        if (j > i) return j
        return 0
    }
}