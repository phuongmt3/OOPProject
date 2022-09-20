module com.example.dbproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;

    opens com.example.dbproject to javafx.fxml;
    exports com.example.dbproject;
}