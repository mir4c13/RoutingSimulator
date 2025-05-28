module org.mirac.routingsimulator {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.mirac.routingsimulator to javafx.fxml;
    exports org.mirac.routingsimulator;
}