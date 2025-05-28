package org.mirac.routingsimulator.utils;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
import org.mirac.routingsimulator.entity.DataPacket;
import org.mirac.routingsimulator.entity.RouteEntry;
import org.mirac.routingsimulator.routers.Router;
import org.mirac.routingsimulator.view.Mapview;

import java.util.List;

public class SimulationUtils {
    public static Task<Void> startSimulationThread(Button startButton) {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {

                List<DataPacket> dataPackets = DataUtils.initDataPackets();
                if (dataPackets != null) {
                    log("[*] 模拟数据包初始化完成");
                } else {
                    log("[*] 模拟数据包初始化失败");
                    // 抛出异常触发 failed()
                    throw new Exception("模拟数据包初始化失败");
                }
                Router router = Mapview.routers.getFirst();
                List<RouteEntry> routeEntries = router.getRoutingTable();

                sleep(600);
                log("开始接收数据包");

                for (DataPacket dataPacket : dataPackets) {
                    // 检查任务是否被取消
                    if (isCancelled()) {
                        log("任务被取消");
                        break;
                    }
                    sleep(600);

                    if (dataPacket != null) {
                        log("接收到数据包 解析中...");
                        sleep(600);
                        log("解析完成，计算校验和...");
                        sleep(600);
                        int checkSum = DataUtils.calculateChecksum(dataPacket);
                        log("计算完成，计算所得校验和为 " + checkSum + " 数据包中校验和为 " + dataPacket.checkSum);
                        sleep(600);
                        if (checkSum == dataPacket.checkSum) {
                            log("校验和校验正确，TTL-1...");
                            sleep(600);
                            dataPacket.ttl = dataPacket.ttl - 1;
                            if (dataPacket.ttl < 0) {
                                log("TTL 小于 0，丢弃数据包并发送ICMP超时报文");
                                continue;
                            } else {
                                log("TTL-1成功，重新计算校验和...");
                                sleep(600);
                                dataPacket.checkSum = DataUtils.calculateChecksum(dataPacket);
                                log("新的校验和为 " + dataPacket.checkSum);
                                log("更新数据包...");
                                sleep(600);
                                log("数据包目标IP为 " + dataPacket.destIp + "  开始路由匹配");

                                boolean isFound = false;
                                RouteEntry bestMatch = null;
                                int maxPrefix = -1;
                                int ip = IPUtils.ipToInt(dataPacket.getDestIp());

                                for (RouteEntry entry : routeEntries) {
                                    int network = IPUtils.ipToInt(IPUtils.getNetworkAddress(entry.getNetwork(), entry.getPrefixLength()));
                                    int mask = IPUtils.prefixToMask(entry.getPrefixLength());

                                    if ((ip & mask) == network) {
                                        isFound = true;
                                        sleep(600);
                                        Mapview.log(entry.getNetwork() + "/" + entry.getPrefixLength() + " 前缀匹配!");
                                        if (entry.getPrefixLength() > maxPrefix) {
                                            maxPrefix = entry.getPrefixLength();
                                            bestMatch = entry;
                                        }
                                    } else {
                                        sleep(600);
                                        Mapview.log(entry.getNetwork() + "/" + entry.getPrefixLength() + " 前缀不匹配!");
                                    }
                                }

                                sleep(600);
                                if (!isFound || bestMatch == null) {
                                    Mapview.log("未找到路由，丢弃数据包");
                                } else {
                                    Mapview.log("根据最长前缀匹配原则，匹配到一条最优条目");
                                    sleep(400);
                                    Mapview.log(bestMatch.getNetwork() + "/" + bestMatch.getPrefixLength() + " 是最优条目  下一跳是 " + bestMatch.getNextHop() + "   接口是 " + bestMatch.getInterface());
                                    sleep(400);
                                    Mapview.log("将数据包转发至 " + bestMatch.getNextHop() + "\n");
                                }
                            }
                        } else {
                            log("校验和校验错误，丢弃数据包");
                            continue;
                        }
                    } else {
                        continue;
                    }
                    sleep(800);
                }

                return null;
            }

            @Override
            protected void succeeded() {
                startButton.setDisable(false);
                log("模拟完成！");
            }

            @Override
            protected void failed() {
                startButton.setDisable(false);
                log("模拟失败！");
            }

            @Override
            protected void running() {
                // 清空日志
                Mapview.logArea.clear();
            }
        };
    }

    public static Task<Void> startCustomSimulationThread(DataPacket dataPacket, Button startButton) {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {

                if (dataPacket != null) {
                    log("[*] 模拟数据包初始化完成");
                } else {
                    log("[*] 模拟数据包初始化失败");
                    // 抛出异常触发 failed()
                    throw new Exception("模拟数据包初始化失败");
                }
                Router router = Mapview.routers.getFirst();
                List<RouteEntry> routeEntries = router.getRoutingTable();

                sleep(600);
                log("开始接收数据包");

                // 检查任务是否被取消
                if (isCancelled()) {
                    updateMessage("任务被取消");
                    throw new Exception("任务被取消");
                }
                sleep(600);

                log("接收到数据包 解析中...");
                sleep(600);
                log("解析完成，计算校验和...");
                sleep(600);
                int checkSum = DataUtils.calculateChecksum(dataPacket);
                log("计算完成，计算所得校验和为 " + checkSum + " 数据包中校验和为 " + dataPacket.checkSum);
                sleep(600);
                if (checkSum == dataPacket.checkSum) {
                    log("校验和校验正确，TTL-1...");
                    sleep(600);
                    dataPacket.ttl = dataPacket.ttl - 1;
                    if (dataPacket.ttl < 0) {
                        log("TTL 小于 0，丢弃数据包并发送ICMP超时报文");
                    } else {
                        log("TTL-1成功，重新计算校验和...");
                        sleep(600);
                        dataPacket.checkSum = DataUtils.calculateChecksum(dataPacket);
                        log("新的校验和为 " + dataPacket.checkSum);
                        log("更新数据包...");
                        sleep(600);
                        log("数据包目标IP为 " + dataPacket.destIp + "  开始路由匹配");

                        boolean isFound = false;
                        RouteEntry bestMatch = null;
                        int maxPrefix = -1;
                        int ip = IPUtils.ipToInt(dataPacket.getDestIp());

                        for (RouteEntry entry : routeEntries) {
                            int network = IPUtils.ipToInt(IPUtils.getNetworkAddress(entry.getNetwork(), entry.getPrefixLength()));
                            int mask = IPUtils.prefixToMask(entry.getPrefixLength());

                            if ((ip & mask) == network) {
                                isFound = true;
                                sleep(600);
                                Mapview.log(entry.getNetwork() + "/" + entry.getPrefixLength() + " 前缀匹配!");
                                if (entry.getPrefixLength() > maxPrefix) {
                                    maxPrefix = entry.getPrefixLength();
                                    bestMatch = entry;
                                }
                            } else {
                                sleep(600);
                                Mapview.log(entry.getNetwork() + "/" + entry.getPrefixLength() + " 前缀不匹配!");
                            }
                        }

                        sleep(600);
                        if (!isFound || bestMatch == null) {
                            Mapview.log("未找到路由，丢弃数据包");
                        } else {
                            Mapview.log("根据最长前缀匹配原则，匹配到一条最优条目");
                            sleep(300);
                            Mapview.log(bestMatch.getNetwork() + "/" + bestMatch.getPrefixLength() + " 是最优条目  下一跳是 " + bestMatch.getNextHop() + "   接口是 " + bestMatch.getInterface());
                            sleep(400);
                            Mapview.log("将数据包转发至 " + bestMatch.getNextHop() + "\n");
                        }
                    }
                } else {
                    log("校验和校验错误，丢弃数据包");
                }
                sleep(800);

                return null;
            }

            @Override
            protected void succeeded() {
                startButton.setDisable(false);
                log("模拟完成！");
            }

            @Override
            protected void failed() {
                startButton.setDisable(false);
                log("模拟失败！");
            }

            @Override
            protected void running() {
                // 清空日志
                Mapview.logArea.clear();
            }
        };
    }

    public static void log(String message) {
        Mapview.log(message);
    }

    public static void sleep(long ms) throws Exception {
        Thread.sleep(ms);
    }
}
