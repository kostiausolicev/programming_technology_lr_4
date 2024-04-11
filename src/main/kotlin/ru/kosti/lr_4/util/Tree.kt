package ru.kosti.lr_4.util

class Tree(
    private var root: Node? = null
) {
    fun add(node: Node) {
        if (root == null) {
            root = node
            return
        }
        var current = root!!
        while (true) {
            if (node.key == current.key)
                return
            else if (node.key < current.key) {
                if (current.left == null) {
                    current.left = node
                    node.parent = current
                    return
                }
                current = current.left!!
                continue
            }
            else {
                if (current.right == null) {
                    current.right = node
                    node.parent = current
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

    fun delete(node: Node) {

    }
}