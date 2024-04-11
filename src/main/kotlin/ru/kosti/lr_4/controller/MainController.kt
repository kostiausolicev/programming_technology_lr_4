package ru.kosti.lr_4.controller

import javafx.scene.control.Alert
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
        initLabels(tree.root ?: run {
            throwAlert("Нет корня у дерева", Alert.AlertType.ERROR)
            return
        })
    }

    fun search() {
        val key = keyInputField.text.toIntOrNull() ?: run {
            return throwAlert("Вы ввели некорректное число", Alert.AlertType.ERROR)
        }
        val node = tree.search(key) ?: run {
            return throwAlert("Узел не найден", Alert.AlertType.INFORMATION)
        }
        initLabels(node)
    }

    fun delete() {
        val key = keyInputField.text.toIntOrNull() ?: run {
            throwAlert("Вы ввели некорректное число", Alert.AlertType.ERROR)
            return
        }
        try {
            tree.delete(key)
        } catch (e: Exception) {
            throwAlert(e.message?: "Ошибка удаления", Alert.AlertType.ERROR)
            return
        }
        throwAlert("Удаление завершено, необходимо обновить", Alert.AlertType.INFORMATION)
    }

    fun add() {
        val key = keyInputField.text.toIntOrNull() ?: run {
            return throwAlert("Вы ввели некорректное число", Alert.AlertType.ERROR)
        }
        tree.add(key)
        val node = tree.search(key) ?: run {
            throwAlert("Узел не найден", Alert.AlertType.INFORMATION)
            return
        }
        initLabels(node)
    }

    fun toLeft() {
        try {
            val current = tree.search(currentNodeKey.text.toInt()) ?: return
            initLabels(current.left ?: run {
                throwAlert("Нет выборанного узла")
                return
            })
        } catch (e: Exception) {
            throwAlert("Сначала обновите узлы (кнопка обновить)")
            return
        }
    }

    fun toRight() {
        try {
            val current = tree.search(currentNodeKey.text.toInt()) ?: return
            initLabels(current.right ?: run {
                throwAlert("Нет выборанного узла")
                return
            })
        } catch (e: Exception) {
            throwAlert("Сначала обновите узлы (кнопка обновить)")
            return
        }
    }

    fun toParent() {
        try {
            val current = tree.search(currentNodeKey.text.toInt()) ?: return
            initLabels(current.parent ?: run {
                throwAlert("Нет выборанного узла")
                return
            })
        } catch (e: Exception) {
            throwAlert("Сначала обновите узлы (кнопка обновить)")
            return
        }
    }

    private fun throwAlert(text: String, type: Alert.AlertType = Alert.AlertType.WARNING) {
        val alert = Alert(type, text)
        alert.showAndWait()
    }

    private fun initLabels(node: Node) {
        currentNodeKey.text = node.key.toString()
        leftNodeKey.text = node.left?.key?.toString() ?: ""
        rightNodeKey.text = node.right?.key?.toString() ?: ""
        parentNodeKey.text = node.parent?.key?.toString() ?: ""
    }
}