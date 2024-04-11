package ru.kosti.lr_4.controller

import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import ru.kosti.lr_4.tree
import ru.kosti.lr_4.util.Node

class MainController {
    lateinit var reload: Button
    lateinit var addButton: Button
    lateinit var deleteButton: Button
    lateinit var searchButton: Button
    lateinit var keyInputField: TextField
    lateinit var toRight: Button
    lateinit var toLeft: Button
    lateinit var toParent: Button
    lateinit var parentNodeKey: Label
    lateinit var rightNodeKey: Label
    lateinit var leftNodeKey: Label
    lateinit var currentNodeKey: Label

    fun reload() {
        initLabels(tree.root ?: return ) //TODO добавить алерт
    }

    fun search() {
        val key = keyInputField.text.toIntOrNull() ?: run {
            return //TODO добавить алерт
        }
        val node = tree.search(key) ?: run {
            return //TODO добавить алерт
        }
        initLabels(node)
    }

    fun delete() {

    }

    fun add() {

    }

    fun toLeft() {
        try {
            val current = tree.search(currentNodeKey.text.toInt()) ?: return
            initLabels(current.left ?: run {
                return //TODO добавить алерт
            })
        } catch (e: Exception) {
            return //TODO добавить алерт
        }
    }

    fun toRight() {
        try {
            val current = tree.search(currentNodeKey.text.toInt()) ?: return
            initLabels(current.right ?: run {
                return //TODO добавить алерт
            })
        } catch (e: Exception) {
            return //TODO добавить алерт
        }
    }

    fun toParent() {
        try {
            val current = tree.search(currentNodeKey.text.toInt()) ?: return
            initLabels(current.parent ?: run {
                return //TODO добавить алерт
            })
        } catch (e: Exception) {
            return //TODO добавить алерт
        }
    }

    private fun initLabels(node: Node) {
        currentNodeKey.text = node.key.toString()
        leftNodeKey.text = node.left?.key?.toString() ?: ""
        rightNodeKey.text = node.right?.key?.toString() ?: ""
        parentNodeKey.text = node.parent?.key?.toString() ?: ""
    }
}