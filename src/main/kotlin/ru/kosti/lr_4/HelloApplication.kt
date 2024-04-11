package ru.kosti.lr_4

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import ru.kosti.lr_4.util.Tree
import java.util.*

class HelloApplication : Application() {
    private fun generateSequence(): Tree {
        val n = Random().nextInt(3, 15)
        for (i in 1..n) {
            tree.add(Random().nextInt(1, 100))
        }
        return tree
    }

    override fun start(stage: Stage) {
        generateSequence()
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("hello-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 620.0, 500.0)
        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
    }
}
val tree = Tree()

fun main() {
    Application.launch(HelloApplication::class.java)
}