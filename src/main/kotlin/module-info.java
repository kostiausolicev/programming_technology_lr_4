module ru.kosti.lr_4 {
    requires javafx.controls;
    requires javafx.fxml;
                requires kotlin.stdlib;
    
                            
    opens ru.kosti.lr_4 to javafx.fxml;
    exports ru.kosti.lr_4;
    exports ru.kosti.lr_4.controller;
}