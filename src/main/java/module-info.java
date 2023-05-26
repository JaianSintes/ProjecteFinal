module jordi.sintes.projectefinal {
    requires javafx.controls;
    requires javafx.fxml;


    opens jordi.sintes.projectefinal to javafx.fxml;
    exports jordi.sintes.projectefinal;
}