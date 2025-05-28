package org.mirac.routingsimulator.entity;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class DataPacket {
    public InetAddress sourceIp;
    public InetAddress destIp;
    public byte[] data;
    public int checkSum;
    public int ttl;

    public DataPacket() {
        ttl = 0;
    }

    public DataPacket(String sourceIp, String destIp, String data, int ttl) {
        this();
        try {
            this.sourceIp = InetAddress.getByName(sourceIp);
            this.destIp = InetAddress.getByName(destIp);
            this.data = data.getBytes();
            this.ttl = ttl;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public String getSourceIp() {
        return sourceIp.toString().substring(1);
    }

    public void setSourceIp(String sourceIp) {
        try {
            this.sourceIp = InetAddress.getByName(sourceIp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDestIp() {
        return destIp.toString().substring(1);
    }

    public void setDestIp(String destIp) {
        try {
            this.destIp = InetAddress.getByName(destIp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getData() {
        return new String(data,StandardCharsets.UTF_8);
    }

    public void setData(String data) {
        this.data = data.getBytes(StandardCharsets.UTF_8);
    }

    public int getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(int checkSum) {
        this.checkSum = checkSum;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }
}
