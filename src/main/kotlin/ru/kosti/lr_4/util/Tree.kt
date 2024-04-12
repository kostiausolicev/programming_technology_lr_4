package ru.kosti.lr_4.util

class Tree(
    var root: Node? = null,
) {
    fun check(node: Node? = this.root): Boolean {
        return true
//        if (node == null) return true
//        if (node.left!!.key >= node.key || node.key >= node.right!!.key) return false
//        return check(node.left) && check(node.right)
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
            } else if (key < current.key) {
                if (current.left == null) {
                    val node = Node(key = key, parent = current)
                    current.left = node
                    if (!check())
                        throw Exception("Дерево не правильное")
                    return
                }
                current = current.left!!
                continue
            } else {
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
            } else {
                if (current.right == null) return null
                current = current.right!!
                continue
            }
        }
        return current
    }

    fun maximum(rootSubTree: Node? = this.root): Node? =
        if (rootSubTree == null) null
        else if (rootSubTree.right != null) {
            var current = rootSubTree.right!!
            while (current.right != null) {
                current = current.right!!
            }
            current
        } else rootSubTree

    fun minimum(rootSubTree: Node? = this.root): Node? =
        if (rootSubTree == null) null
        else if (rootSubTree.left != null) {
            var current = rootSubTree.left!!
            while (current.left != null) {
                current = current.left!!
            }
            current
        } else rootSubTree

    fun searchNext(rootSubTree: Node): Node? {
        if (rootSubTree.right != null)
            return minimum(rootSubTree.right)
        var current = rootSubTree
        var parent = rootSubTree.parent
        while (parent != null && current.key == parent.right!!.key) {
            current = parent
            parent = parent.parent
        }
        return parent
    }

    fun delete(key: Int) {
        val node = search(key) ?: return
        // Удаление листа
        if (node.left == null && node.right == null) {
            if (node.key < node.parent!!.key) {
                node.parent!!.left = null
            } else {
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
            } else if (rightNode.key > node.parent!!.key) {
                node.parent!!.right = rightNode
            }
        }
        // Удаление с левым потомком
        else if (node.right == null) {
            val leftNode = node.left!!
            if (leftNode.key < node.parent!!.key) {
                node.parent!!.left = leftNode
            } else if (leftNode.key > node.parent!!.key) {
                node.parent!!.right = leftNode
            }
        }
        // Удаление с левым потомком и правым потомком
        else {
            val next = searchNext(node)
                ?: throw Exception()
            if (node.parent == null) root = next
            if (next.parent!!.key < next.key) {
                next.parent!!.right = null
            } else {
                next.parent!!.left = null
            }
            // Сохранение правого поддерева следующего элемента
            if (next.right != null) {
                next.right!!.parent = next.parent
                next.parent!!.left = next.right
            }
            next.parent = node.parent
            next.left = node.left
            next.right = node.right
            // Перенаправление ссылок удаляемого узла на новый узел
            node.left!!.parent = next
            node.right!!.parent = next
            if (node.parent!!.key < node.key) {
                node.parent!!.right = next
            } else {
                node.parent!!.left = next
            }
            // Удаление ссылок на удаляемый узел
            node.left = null
            node.right = null
            node.parent = null
            if (!check())
                throw Exception("Дерево не правильное")
        }
    }
}