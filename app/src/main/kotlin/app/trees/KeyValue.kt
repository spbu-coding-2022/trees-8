/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package app.trees

class KeyValue<K : Comparable<K>, V>(
    val key: K,
    var value: V?
) : Comparable<KeyValue<K, V>> {

    override fun compareTo(other: KeyValue<K, V>): Int {
        return key.compareTo(other.key)
    }

    override fun equals(other: Any?): Boolean {
        if (other is KeyValue<*, *>) {
            return key == other.key
        }
        return false
    }

    override fun toString(): String {
        return "$key: $value"
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }
}