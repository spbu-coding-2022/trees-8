interface Tree<T : Comparable<T>> {
    fun add(data: T)
    fun contain(data: T)
    fun delete(data: T)
}

abstract class ABSTree<T : Comparable<T>, NodeType : Node<T, NodeType>> : Tree<T> {
    var root: NodeType? = null
    fun simple_add(data: NodeType) {
        if (root == null) {
            root = data
            return
        }
        var curNode = root!!
        while (true) {

            if (data < curNode) {
                if (curNode.left == null) {
                    curNode.left = data
                    break
                } else {
                    curNode = curNode.left!!
                }

            } else if (data > curNode) {
                if (curNode.right == null) {
                    curNode.right = data
                    break
                } else {
                    curNode = curNode.right!!
                }
            }

        }
    }
}