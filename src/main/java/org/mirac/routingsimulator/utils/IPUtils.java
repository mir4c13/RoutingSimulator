package org.mirac.routingsimulator.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPUtils {
    public static boolean isInvalidIP(String ip) {
        return !ip.matches("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    }

    public static int ipToInt(String ip) {
        // 确保处理的是纯IP地址（不含CIDR前缀）
        String pureIp = ip.split("/")[0];
        String[] octets = pureIp.split("\\.");
        return (Integer.parseInt(octets[0]) << 24) |
                (Integer.parseInt(octets[1]) << 16) |
                (Integer.parseInt(octets[2]) << 8) |
                Integer.parseInt(octets[3]);
    }

    public static int prefixToMask(int prefix) {
        return prefix == 0 ? 0 : (0xFFFFFFFF << (32 - prefix));
    }

    public static String getNetworkAddress(String ip, int prefix) {
        long networkAddressAsLong = -1;
        try {
            InetAddress inetAddress = InetAddress.getByName(ip);
            byte[] netAddress = inetAddress.getAddress();
            long ipAsLong = bytesToLong(netAddress);
            long mask = -(1L << (32 - prefix));
            networkAddressAsLong = ipAsLong & mask;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return formatBytesAsIPv4(longToBytes(networkAddressAsLong));
    }

    public static long bytesToLong(byte[] bytes) {
        long result = 0;
        for (byte b : bytes) {
            result = (result << 8) | (b & 0xFF);
        }
        return result;
    }

    public static byte[] longToBytes(long value) {
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = (byte) ((value >> (24 - i * 8)) & 0xFF);
        }
        return bytes;
    }

    public static String formatBytesAsIPv4(byte[] bytes) {
        return ((bytes[0] & 0xFF) + "." + (bytes[1] & 0xFF) + "." + (bytes[2] & 0xFF) + "." + (bytes[3] & 0xFF));
    }
}
