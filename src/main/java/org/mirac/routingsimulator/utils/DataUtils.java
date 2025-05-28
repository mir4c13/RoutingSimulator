package org.mirac.routingsimulator.utils;

import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.mirac.routingsimulator.entity.DataPacket;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DataUtils {
    public static List<DataPacket> dataPackets;

    public static byte[] constructPacket(DataPacket dataPacket) {
        // 获取源IP和目的IP的字节数组
        byte[] sourceIpBytes = dataPacket.sourceIp.getAddress();
        byte[] destIpBytes = dataPacket.destIp.getAddress();

        // 拼接数据包
        ByteBuffer buffer = ByteBuffer.allocate(sourceIpBytes.length + destIpBytes.length + 1 + dataPacket.data.length);
        buffer.put(sourceIpBytes);
        buffer.put(destIpBytes);
        buffer.put((byte) dataPacket.ttl);
        buffer.put(dataPacket.data);

        return buffer.array();
    }

    public static int calculateChecksum(DataPacket dataPacket) {
        byte[] packet = constructPacket(dataPacket);
        int sum = 0;

        // 如果数据包长度是奇数，补0
        if (packet.length % 2 != 0) {
            byte[] temp = new byte[packet.length + 1];
            System.arraycopy(packet, 0, temp, 0, packet.length);
            packet = temp;
        }

        // 分块求和
        for (int i = 0; i < packet.length ; i += 2) {
            int word = ((packet[i] & 0xFF) << 8) + (packet[i+1] & 0xFF);
            sum += word;
            // 处理进位
            sum = (sum & 0xFFFF) + (sum >> 16);
        }

        // 取反码得到校验和
        return (~sum) & 0xFFFF;
    }

    public static List<DataPacket> initDataPackets() {
        dataPackets = new ArrayList<>();
        dataPackets.add(new DataPacket("192.168.1.1", "192.168.1.16", "hello", 64));
//        dataPackets.add(new DataPacket("172.168.1.1", "172.168.35.16", "java", 2));
        dataPackets.add(new DataPacket("10.68.1.1", "10.222.22.16", "python", 0));
        //dataPackets.add(new DataPacket("222.125.24.6", "112.165.24.5", "c#", 25));
        //dataPackets.add(new DataPacket("192.157.2.154", "192.148.34.5", "php", 16));
        for (DataPacket dataPacket : dataPackets) {
            dataPacket.setCheckSum(DataUtils.calculateChecksum(dataPacket));
        }
        return dataPackets;
    }

    public static void startCustomSimulationDialog(Button customSimulationBtn) {
        Dialog<DataPacket> dialog = new Dialog<>();
        dialog.setTitle("自定义数据包模拟");
        dialog.setHeaderText("请输入数据包信息");

        ButtonType startButtonType = new ButtonType("开始模拟", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(startButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField sourceIpField = new TextField("192.168.1.1");
        TextField destIpField = new TextField("192.168.31.1");
        TextField dataField = new TextField("hello");
        TextField ttlField = new TextField("64");

        grid.add(new Label("源IP地址:"), 0, 0);
        grid.add(sourceIpField, 1, 0);
        grid.add(new Label("目的IP地址:"), 0, 1);
        grid.add(destIpField, 1, 1);
        grid.add(new Label("数据:"), 0, 2);
        grid.add(dataField, 1, 2);
        grid.add(new Label("TTL:"), 0, 3);
        grid.add(ttlField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // 结果转换器
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == startButtonType) {
                // 验证输入
                if (IPUtils.isInvalidIP(sourceIpField.getText())) {
                    showErrorAlert("无效的网络地址");
                    return null;
                }

                if (IPUtils.isInvalidIP(destIpField.getText())) {
                    showErrorAlert("无效的网络地址");
                    return null;
                }

                try {
                    int ttl = Integer.parseInt(ttlField.getText());
                    if (ttl < 0) {
                        showErrorAlert("TTL无效");
                        return null;
                    }
                } catch (NumberFormatException e) {
                    showErrorAlert("TTL必须为数字");
                    return null;
                }

                if (dataField.getText() == null){
                    showErrorAlert("数据不能为空");
                    return null;
                }

                return new DataPacket(
                        sourceIpField.getText(),
                        destIpField.getText(),
                        dataField.getText(),
                        Integer.parseInt(ttlField.getText())
                        );
            }
            return null;
        });

        // 显示对话框并处理结果
        dialog.showAndWait().ifPresent(dataPacket -> {
            dataPacket.setCheckSum(DataUtils.calculateChecksum(dataPacket));
            Task<Void> task = SimulationUtils.startCustomSimulationThread(dataPacket,customSimulationBtn);
            new Thread(task).start();
        });
    }

    public static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("输入错误");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
