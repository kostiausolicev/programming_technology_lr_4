package ru.kosti.lr_4.util

import kotlin.math.abs

class Tree(
    var root: Node? = null,
) {

    fun checkBalance(): Int {
        val left = getHeight(root?.left ?: return 0)
        val right = getHeight(root?.right ?: return 0)

        val diff = right - left

        return if (abs(diff) <= 1) 0
        else if (diff > 1) -1
        else 1
    }

    fun balance() {
        do {
            val balance = checkBalance()
            if (balance < 0) rotateLeft(root!!)
            else if (balance > 0) rotateRight(root!!)
        } while (balance != 0)
    }

    fun getHeight(node: Node?): Int {
        if (node == null) return 0
        return 1 + maxOf(getHeight(node.left), getHeight(node.right))
    }

    fun rotateLeft(pivot: Node) {
        if (pivot.right == null) throw Exception()
        val right = pivot.right!!
        val pivotParent = pivot.parent
            ?.let { Node(pivot.parent!!.key, pivot.parent?.parent, pivot.parent?.left, pivot.parent?.right) }

        pivot.parent = right
        pivot.right = right.left

        right.parent = pivotParent
        right.left = pivot

        if (pivot.key == root?.key) root = right
    }

    fun rotateRight(pivot: Node) {
        if (pivot.left == null) throw Exception()
        val left = pivot.left!!
        val pivotParent = pivot.parent
            ?.let { Node(pivot.parent!!.key, pivot.parent?.parent, pivot.parent?.left, pivot.parent?.right) }

        pivot.parent = left
        pivot.left = left.right

        left.parent = pivotParent
        left.right = pivot

        if (pivot.key == root?.key) root = left
    }

    fun add(key: Int) {
        if (root == null) {
            root = Node(key = key)
            balance()
            return
        }
        var current = root!!
        while (true) {
            if (key == current.key) {
                return
            } else if (key < current.key) {
                if (current.left == null) {
                    val node = Node(key = key, parent = current)
                    current.left = node
                    balance()
                    return
                }
                current = current.left!!
                continue
            } else {
                if (current.right == null) {
                    val node = Node(key = key, parent = current)
                    current.right = node
                    balance()
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
            if (node.parent == null) this.root = next
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
        }
        balance()
    }
}