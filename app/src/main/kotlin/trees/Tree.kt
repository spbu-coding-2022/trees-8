package trees

interface Tree<T : Comparable<T>> {
    fun add(data: T)
    fun contain(data: T): Boolean
    fun delete(data: T)
}