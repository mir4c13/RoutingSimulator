package org.mirac.routingsimulator.Test;

import org.junit.Test;
import org.mirac.routingsimulator.entity.RouteEntry;
import org.mirac.routingsimulator.routers.Router;
import org.mirac.routingsimulator.utils.IPUtils;

import java.util.List;

public class SimulationUtilsTest {

    @Test
    public void accurateMatchTest() {
        String destIp = "192.168.1.5";
        Router router = new Router("测试");
        router.addRouteEntry(new RouteEntry("192.168.1.0",24,"192.168.1.1","eth1"));
        List<RouteEntry> routeEntries = router.getRoutingTable();
        boolean isFound = false;
        RouteEntry bestMatch = null;
        int maxPrefix = -1;
        int ip = IPUtils.ipToInt(destIp);

        for (RouteEntry entry : routeEntries) {
            int network = IPUtils.ipToInt(IPUtils.getNetworkAddress(entry.getNetwork(), entry.getPrefixLength()));
            int mask = IPUtils.prefixToMask(entry.getPrefixLength());

            if ((ip & mask) == network) {
                isFound = true;
                System.out.println(entry.getNetwork() + "/" + entry.getPrefixLength() + " 前缀匹配!");
                if (entry.getPrefixLength() > maxPrefix) {
                    maxPrefix = entry.getPrefixLength();
                    bestMatch = entry;
                }
            } else {
                System.out.println(entry.getNetwork() + "/" + entry.getPrefixLength() + " 前缀不匹配!");
            }
        }
        if (!isFound || bestMatch == null) {
            System.out.println("未找到路由，丢弃数据包");
        } else {
            System.out.println("根据最长前缀匹配原则，匹配到一条最优条目");
            System.out.println(bestMatch.getNetwork() + "/" + bestMatch.getPrefixLength() + " 是最优条目  下一跳是 " + bestMatch.getNextHop() + "   接口是 " + bestMatch.getInterface());
            System.out.println("将数据包转发至 " + bestMatch.getNextHop() + "\n");
        }
    }

    @Test
    public void longestPrefixMatchTest() {
        String destIp = "10.1.2.3";
        Router router = new Router("测试");
        router.addRouteEntry(new RouteEntry("10.0.0.0",8,"10.0.0.1","eth1"));
        router.addRouteEntry(new RouteEntry("10.1.0.0",16,"10.1.1.1","eth1"));
        List<RouteEntry> routeEntries = router.getRoutingTable();
        boolean isFound = false;
        RouteEntry bestMatch = null;
        int maxPrefix = -1;
        int ip = IPUtils.ipToInt(destIp);

        for (RouteEntry entry : routeEntries) {
            int network = IPUtils.ipToInt(IPUtils.getNetworkAddress(entry.getNetwork(), entry.getPrefixLength()));
            int mask = IPUtils.prefixToMask(entry.getPrefixLength());

            if ((ip & mask) == network) {
                isFound = true;
                System.out.println(entry.getNetwork() + "/" + entry.getPrefixLength() + " 前缀匹配!");
                if (entry.getPrefixLength() > maxPrefix) {
                    maxPrefix = entry.getPrefixLength();
                    bestMatch = entry;
                }
            } else {
                System.out.println(entry.getNetwork() + "/" + entry.getPrefixLength() + " 前缀不匹配!");
            }
        }
        if (!isFound || bestMatch == null) {
            System.out.println("未找到路由，丢弃数据包");
        } else {
            System.out.println("根据最长前缀匹配原则，匹配到一条最优条目");
            System.out.println(bestMatch.getNetwork() + "/" + bestMatch.getPrefixLength() + " 是最优条目  下一跳是 " + bestMatch.getNextHop() + "   接口是 " + bestMatch.getInterface());
            System.out.println("将数据包转发至 " + bestMatch.getNextHop() + "\n");
        }
    }

    @Test
    public void defaultRouteMatchTest() {
        String destIp = "172.16.0.1";
        Router router = new Router("测试");
        router.addRouteEntry(new RouteEntry("0.0.0.0",0,"direct","eth0"));
        List<RouteEntry> routeEntries = router.getRoutingTable();
        boolean isFound = false;
        RouteEntry bestMatch = null;
        int maxPrefix = -1;
        int ip = IPUtils.ipToInt(destIp);

        for (RouteEntry entry : routeEntries) {
            int network = IPUtils.ipToInt(IPUtils.getNetworkAddress(entry.getNetwork(), entry.getPrefixLength()));
            int mask = IPUtils.prefixToMask(entry.getPrefixLength());

            if ((ip & mask) == network) {
                isFound = true;
                System.out.println(entry.getNetwork() + "/" + entry.getPrefixLength() + " 前缀匹配!");
                if (entry.getPrefixLength() > maxPrefix) {
                    maxPrefix = entry.getPrefixLength();
                    bestMatch = entry;
                }
            } else {
                System.out.println(entry.getNetwork() + "/" + entry.getPrefixLength() + " 前缀不匹配!");
            }
        }
        if (!isFound || bestMatch == null) {
            System.out.println("未找到路由，丢弃数据包");
        } else {
            System.out.println("根据最长前缀匹配原则，匹配到一条最优条目");
            System.out.println(bestMatch.getNetwork() + "/" + bestMatch.getPrefixLength() + " 是最优条目  下一跳是 " + bestMatch.getNextHop() + "   接口是 " + bestMatch.getInterface());
            System.out.println("将数据包转发至 " + bestMatch.getNextHop() + "\n");
        }
    }

    @Test
    public void noMatchItemTest() {
        String destIp = "203.0.113.5";
        Router router = new Router("测试");
        router.addRouteEntry(new RouteEntry("192.168.1.0",24,"192.168.1.1","eth1"));
        List<RouteEntry> routeEntries = router.getRoutingTable();
        boolean isFound = false;
        RouteEntry bestMatch = null;
        int maxPrefix = -1;
        int ip = IPUtils.ipToInt(destIp);

        for (RouteEntry entry : routeEntries) {
            int network = IPUtils.ipToInt(IPUtils.getNetworkAddress(entry.getNetwork(), entry.getPrefixLength()));
            int mask = IPUtils.prefixToMask(entry.getPrefixLength());

            if ((ip & mask) == network) {
                isFound = true;
                System.out.println(entry.getNetwork() + "/" + entry.getPrefixLength() + " 前缀匹配!");
                if (entry.getPrefixLength() > maxPrefix) {
                    maxPrefix = entry.getPrefixLength();
                    bestMatch = entry;
                }
            } else {
                System.out.println(entry.getNetwork() + "/" + entry.getPrefixLength() + " 前缀不匹配!");
            }
        }
        if (!isFound || bestMatch == null) {
            System.out.println("未找到路由，丢弃数据包");
        } else {
            System.out.println("根据最长前缀匹配原则，匹配到一条最优条目");
            System.out.println(bestMatch.getNetwork() + "/" + bestMatch.getPrefixLength() + " 是最优条目  下一跳是 " + bestMatch.getNextHop() + "   接口是 " + bestMatch.getInterface());
            System.out.println("将数据包转发至 " + bestMatch.getNextHop() + "\n");
        }
    }
}
