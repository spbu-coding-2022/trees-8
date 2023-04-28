/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app.trees

class KeyValue<K : Comparable<K>, V>(
    val key: K,
    var value: V?
) : Comparable<KeyValue<K, V>> {

    //Compares KeyValue objects based on their keys.
    override fun compareTo(other: KeyValue<K, V>): Int {
        return key.compareTo(other.key)
    }

    //Checks if a KeyValue object is equal to another object.
    //Two objects are considered equal if their keys are equal.
    override fun equals(other: Any?): Boolean {
        if (other is KeyValue<*, *>) {
            return key == other.key
        }
        return false
    }

    //Returns the string representation of the KeyValue object in "key: value" format.
    override fun toString(): String {
        return "$key: $value"
    }

    //Returns the hash code of the KeyValue object based on the hash code of the key.
    override fun hashCode(): Int {
        return key.hashCode()
    }
}