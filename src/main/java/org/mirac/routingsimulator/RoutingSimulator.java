package org.mirac.routingsimulator;

import javafx.application.Application;
import javafx.stage.Stage;
import org.mirac.routingsimulator.utils.RouterUtils;
import org.mirac.routingsimulator.view.Mapview;

public class RoutingSimulator extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        RouterUtils.initializeRouters();
        Mapview.setupUI(primaryStage);
    }
}
