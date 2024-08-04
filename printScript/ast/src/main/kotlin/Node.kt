package org.example

interface Node {
    fun addLeft(node: Node): Node

    fun addRight(node: Node): Node

    fun addValue(value: String): Node
}