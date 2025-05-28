package org.mirac.routingsimulator.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RouteEntry {
    private final String network;
    private final int prefixLength;
    private final String nextHop;
    private final String interface_;

    public RouteEntry(String network, int prefixLength, String nextHop, String interface_) {
        this.network = network;
        this.prefixLength = prefixLength;
        this.nextHop = nextHop;
        this.interface_ = interface_;
    }

    public String getNetwork() { return network; }
    public int getPrefixLength() { return prefixLength; }
    public String getNextHop() { return nextHop; }
    public String getInterface() { return interface_; }

    public StringProperty networkProperty() {
        return new SimpleStringProperty(network + "/" + prefixLength);
    }
    public IntegerProperty prefixLengthProperty() {
        return new SimpleIntegerProperty(prefixLength);
    }
    public StringProperty nextHopProperty() {
        return new SimpleStringProperty(nextHop);
    }
    public StringProperty interfaceProperty() {
        return new SimpleStringProperty(interface_);
    }

    @Override
    public String toString() {
        return String.format("%-12s →  下一跳: %-15s 接口: %s",
                network + "/" + prefixLength, nextHop, interface_);
    }
}
