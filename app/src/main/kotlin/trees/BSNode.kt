package trees

import Node

class BSNode<T: Comparable<T>>(override var data: T) : Node<T, BSNode<T>> {
    override var left: BSNode<T>? = null
    override var right: BSNode<T>? = null
    override var parent: BSNode<T>? = null

    override fun compareTo(other: Node<T, BSNode<T>>): Int {
        return data.compareTo(other.data)
    }

    override fun equals(other: Any?): Boolean {
        if (other is BSNode<*>) {
            return data.equals(other.data)
        }
        return false
    }
}