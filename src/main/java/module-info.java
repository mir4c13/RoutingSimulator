module org.mirac.routingsimulator {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens org.mirac.routingsimulator to javafx.fxml;
    exports org.mirac.routingsimulator;
    exports org.mirac.routingsimulator.utils;
    exports org.mirac.routingsimulator.entity;
    exports org.mirac.routingsimulator.routers;
    exports org.mirac.routingsimulator.view;
    exports org.mirac.routingsimulator.Test;
}