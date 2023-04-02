/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

package trees

class KeyValue<K : Comparable<K>, V>(private val key: K, private var value: V?) : Comparable<KeyValue<K, V>> {
    fun getKey(): K {
        return key
    }

    fun getValue(): V? {
        return value
    }

    override fun compareTo(other: KeyValue<K, V>): Int {
        return key.compareTo(other.key)
    }

    override fun equals(other: Any?): Boolean {
        if (other is KeyValue<*, *>) {
            return key.equals(other.getKey())
        }
        return false
    }

    override fun toString(): String {
        return "$key: $value"
    }
}