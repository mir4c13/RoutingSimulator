package org.mirac.routingsimulator.utils;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.mirac.routingsimulator.entity.RouteEntry;
import org.mirac.routingsimulator.routers.Router;
import org.mirac.routingsimulator.view.Mapview;

import static org.mirac.routingsimulator.view.Mapview.*;

public class RouterUtils {

    public static void initializeRouters() {
        Router router = new Router("Router");
        router.addRouteEntry(new RouteEntry("0.0.0.0", 0, "222.45.26.112", "eth0"));
        router.addRouteEntry(new RouteEntry("192.168.1.0", 24, "192.168.1.3", "eth1"));
        router.addRouteEntry(new RouteEntry("192.168.1.1", 32, "0.0.0.0", "lo"));
        router.addRouteEntry(new RouteEntry("172.16.0.0", 16, "172.16.0.5", "eth0"));
        router.addRouteEntry(new RouteEntry("172.16.0.0", 8, "172.16.0.5", "eth0"));
        router.addRouteEntry(new RouteEntry("10.0.0.0", 8, "direct", "eth1"));

        routers.add(router);

        // 填充表格数据
        routers.forEach(r -> routeData.addAll(r.getRoutingTable()));
    }

    public static void showAddRouteDialog() {
        Dialog<RouteEntry> dialog = new Dialog<>();
        dialog.setTitle("添加路由条目");
        dialog.setHeaderText("请输入路由信息");

        // 设置按钮类型
        ButtonType addButtonType = new ButtonType("添加", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // 创建输入字段
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField networkField = new TextField("111.1.1.1");
        TextField prefixField = new TextField("8");
        TextField nextHopField = new TextField("112.156.1.3");
        TextField interfaceField = new TextField("eth1");

        grid.add(new Label("网络地址:"), 0, 0);
        grid.add(networkField, 1, 0);
        grid.add(new Label("前缀长度:"), 0, 1);
        grid.add(prefixField, 1, 1);
        grid.add(new Label("下一跳:"), 0, 2);
        grid.add(nextHopField, 1, 2);
        grid.add(new Label("接口:"), 0, 3);
        grid.add(interfaceField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // 结果转换器
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                // 验证输入
                if (IPUtils.isInvalidIP(networkField.getText())) {
                    showErrorAlert("无效的网络地址");
                    return null;
                }

                try {
                    int prefix = Integer.parseInt(prefixField.getText());
                    if (prefix < 0 || prefix > 32) {
                        showErrorAlert("前缀长度必须为 0-32");
                        return null;
                    }
                } catch (NumberFormatException e) {
                    showErrorAlert("前缀长度必须为数字");
                    return null;
                }

                if (!nextHopField.getText().equalsIgnoreCase("direct") &&
                        IPUtils.isInvalidIP(nextHopField.getText())) {
                    showErrorAlert("无效的下一跳地址（必须是IP或DIRECT）");
                    return null;
                }

                if (interfaceField.getText().trim().isEmpty()) {
                    showErrorAlert("接口不能为空");
                    return null;
                }

                return new RouteEntry(
                        networkField.getText(),
                        Integer.parseInt(prefixField.getText()),
                        nextHopField.getText(),
                        interfaceField.getText()
                );
            }
            return null;
        });

        // 显示对话框并处理结果
        dialog.showAndWait().ifPresent(route -> {
            if (!routers.isEmpty()) {
                routers.getFirst().addRouteEntry(route);
                routeData.add(route);
                Mapview.log("已添加路由: " + route + "\n");
            } else {
                showErrorAlert("没有可用的路由器");
            }
        });
    }

    public static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("输入错误");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static TableView<RouteEntry> createRouteTable() {
        routeTable = new TableView<>(routeData);

        TableColumn<RouteEntry, String> networkCol = new TableColumn<>("Network");
        networkCol.setCellValueFactory(cdf -> cdf.getValue().networkProperty());

        TableColumn<RouteEntry, Integer> prefixCol = new TableColumn<>("Prefix");
        prefixCol.setCellValueFactory(cdf -> cdf.getValue().prefixLengthProperty().asObject());

        TableColumn<RouteEntry, String> nextHopCol = new TableColumn<>("Next Hop");
        nextHopCol.setCellValueFactory(cdf -> cdf.getValue().nextHopProperty());

        TableColumn<RouteEntry, String> interfaceCol = new TableColumn<>("Interface");
        interfaceCol.setCellValueFactory(cdf -> cdf.getValue().interfaceProperty());

        routeTable.getColumns().addAll(networkCol, prefixCol, nextHopCol, interfaceCol);
        routeTable.setPrefHeight(520);
        return routeTable;
    }
}
