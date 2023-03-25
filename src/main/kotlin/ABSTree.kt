interface Tree<T : Comparable<T>> {
    fun add(data: T)
    fun contain(data: T) : Boolean
    fun delete(data: T)
}

abstract class ABSTree<T : Comparable<T>, NodeType : Node<T, NodeType>> : Tree<T> {
    protected var root: NodeType? = null
    fun simpleAdd(data: NodeType) {
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
    fun simpleContains(node: NodeType): Boolean {
        if (root == null) {
            return false
        }
        var curNode = root!!

        while (true) {

            if (node < curNode) {
                if (curNode.left == null) {
                    break
                } else {
                    curNode = curNode.left!!
                }

            } else if (node > curNode) {
                if (curNode.right == null) {
                    break
                } else {
                    curNode = curNode.right!!
                }
            } else if (node == curNode) {
                return true
            }
        }

        return false
    }
}