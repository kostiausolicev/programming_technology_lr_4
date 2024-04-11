package ru.kosti.lr_4.util

data class Node(
    val key: Int,
    var parent: Node? = null,
    var left: Node? = null,
    var right: Node? = null
)