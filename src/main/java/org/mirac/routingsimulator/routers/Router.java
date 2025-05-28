package org.mirac.routingsimulator.routers;

import org.mirac.routingsimulator.entity.RouteEntry;

import java.util.ArrayList;
import java.util.List;

public class Router {
    private final String name;
    private final List<RouteEntry> routingTable = new ArrayList<>();

    public Router(String name) {
        this.name = name;
    }

    public void addRouteEntry(RouteEntry entry) {
        routingTable.add(entry);
    }

    public String getName() {
        return name;
    }

    public List<RouteEntry> getRoutingTable() {
        return routingTable;
    }
}
