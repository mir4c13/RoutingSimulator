package org.mirac.routingsimulator.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.mirac.routingsimulator.entity.RouteEntry;
import org.mirac.routingsimulator.routers.Router;
import org.mirac.routingsimulator.utils.DataUtils;
import org.mirac.routingsimulator.utils.RouterUtils;
import org.mirac.routingsimulator.utils.SimulationUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Mapview {
    public static TextArea logArea;
    public static TableView<RouteEntry> routeTable;
    public static final List<Router> routers = new ArrayList<>();
    public static final ObservableList<RouteEntry> routeData = FXCollections.observableArrayList();

    public static void setupUI(Stage stage) {
        // 主布局
        BorderPane root = new BorderPane();
        SplitPane splitPane = new SplitPane();
        splitPane.setStyle("-fx-border-width: 0; -fx-padding: 0;");

        // 左侧日志区域
        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setWrapText(true);
        StackPane logContainer = new StackPane(logArea);
        logContainer.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        logArea.prefWidthProperty().bind(logContainer.widthProperty());
        logArea.prefHeightProperty().bind(logContainer.heightProperty());
        splitPane.getItems().add(logContainer);
        logArea.setPadding(new Insets(5));

        // 右侧控制面板
        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setStyle("-fx-border-width: 0; -fx-background-color: #f0f0f0;");
        controlPanel.setMinWidth(330);
        controlPanel.setPrefWidth(330);
        controlPanel.setMaxWidth(330);

        logArea.setStyle("-fx-font-size: 18px;");

        Button startBtn = new Button("开始模拟");
        Button exitBtn = new Button("退出");
        Button customSimulationBtn = new Button("自定义数据包模拟");
        Button addBtn = new Button("添加路由");


        startBtn.setOnAction(e -> {
            startBtn.setDisable(true);
            Task<Void> task = SimulationUtils.startSimulationThread(startBtn);
            new Thread(task).start();
        });
        customSimulationBtn.setOnAction(e -> {
            customSimulationBtn.setDisable(true);
            DataUtils.startCustomSimulationDialog(customSimulationBtn);
        });
        exitBtn.setOnAction(e -> Platform.exit());
        addBtn.setOnAction(e -> RouterUtils.showAddRouteDialog());

        startBtn.setPrefWidth(140);
        startBtn.setPrefHeight(50);
        exitBtn.setPrefWidth(140);
        exitBtn.setPrefHeight(50);
        customSimulationBtn.setPrefWidth(140);
        customSimulationBtn.setPrefHeight(50);
        addBtn.setPrefHeight(40);
        addBtn.setPrefWidth(100);

        HBox btnBox = new HBox(10);
        btnBox.getChildren().addAll(startBtn,exitBtn);
        btnBox.setSpacing(20);
        VBox tableBox = new VBox(10);

        HBox routeBox = new HBox(10);
        Label tableLabel = new Label("\n路由表信息:");
        routeBox.getChildren().addAll(tableLabel, addBtn);
        routeBox.setSpacing(120);

        tableLabel.setStyle("-fx-font-size: 15px;");
        tableBox.getChildren().addAll(routeBox ,RouterUtils.createRouteTable());

        controlPanel.getChildren().addAll(
                btnBox,
                customSimulationBtn,
                tableBox
        );
        controlPanel.setSpacing(40);

        splitPane.getItems().add(controlPanel);

        SplitPane.setResizableWithParent(controlPanel, false);
        root.setCenter(splitPane);

        Scene scene = new Scene(root, 1200, 800);
        stage.setTitle("CIDR 路由模拟");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void log(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        Platform.runLater(() ->
                logArea.appendText("[" + timestamp + "]   " + message + "\n")
        );
    }
}
