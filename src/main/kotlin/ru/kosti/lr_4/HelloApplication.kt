package ru.kosti.lr_4

import javafx.application.Application
import javafx.stage.Stage
import ru.kosti.lr_4.util.Tree
import java.util.*

class HelloApplication : Application() {
    private fun generateSequence(): Tree {
        val tree = Tree()
        val n = Random().nextInt(3, 15)
        for (i in 1..n) {
            tree.add(Random().nextInt(1, 100))
        }
        return tree
    }

    override fun start(stage: Stage) {
        generateSequence()
    }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}