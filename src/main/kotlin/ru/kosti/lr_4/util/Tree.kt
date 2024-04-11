package ru.kosti.lr_4.util

class Tree(
    private var root: Node? = null
) {
    fun check(): Boolean {
        if (root == null) return true
        var current = root!!
        while (current.left!= null) {
            if (current.left!!.key >= current.key) return false
            current = current.left!!
        }
        current = root!!
        while (current.right!= null) {
            if (current.right!!.key <= current.key) return false
            current = current.right!!
        }
        return true
    }

    fun add(key: Int) {
        if (root == null) {
            root = Node(key = key)
            return
        }
        var current = root!!
        while (true) {
            if (key == current.key) {
                if (!check())
                    throw Exception("Дерево не правильное")
                return
            }
            else if (key < current.key) {
                if (current.left == null) {
                    val node = Node(key = key, parent = current)
                    current.left = node
                    if (!check())
                        throw Exception("Дерево не правильное")
                    return
                }
                current = current.left!!
                continue
            }
            else {
                if (current.right == null) {
                    val node = Node(key = key, parent = current)
                    current.right = node
                    if (!check())
                        throw Exception("Дерево не правильное")
                    return
                }
                current = current.right!!
                continue
            }
        }
    }

    fun search(key: Int, rootSubTree: Node? = this.root): Node? {
        if (rootSubTree == null) return null
        var current: Node = rootSubTree
        while (key != current.key) {
            if (key < current.key) {
                if (current.left == null) return null
                current = current.left!!
                continue
            }
            else {
                if (current.right == null) return null
                current = current.right!!
                continue
            }
        }
        return current
    }

    fun searchNext(rootSubTree: Node): Node {
        var current: Node = rootSubTree
        while (current.left != null) {
            current = current.left!!
        }
        return current
    }

    fun delete(key: Int) {
        val node = search(key) ?: return
        // Удаление листа
        if (node.left == null && node.right == null) {
            if (node.key < node.parent!!.key) {
                node.parent!!.left = null
            }
            else {
                node.parent!!.right = null
            }
            if (!check())
                throw Exception("Дерево не правильное")
        }
        // Удаление с правым потомком
        else if (node.left == null) {
            val rightNode = node.right!!
            if (rightNode.key < node.parent!!.key) {
                node.parent!!.left = rightNode
            }
            else if (rightNode.key > node.parent!!.key) {
                node.parent!!.right = rightNode
            }
        }
        // Удаление с левым потомком
        else if (node.right == null) {
            val leftNode = node.left!!
            if (leftNode.key < node.parent!!.key) {
                node.parent!!.left = leftNode
            }
            else if (leftNode.key > node.parent!!.key) {
                node.parent!!.right = leftNode
            }
        }
        // Удаление с левым потомком и правым потомком
        else {
            val next = searchNext(node)
            if (next.parent == null) return
            next.parent!!.left = null
            next.parent = node.parent
            next.left = node.left
            next.right = node.right
            if (!check())
                throw Exception("Дерево не правильное")
        }
    }
}