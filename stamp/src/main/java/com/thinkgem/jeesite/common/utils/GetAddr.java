package com.thinkgem.jeesite.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.*;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能描述:获取地址(IP 和 MAC)工具类
 * @auther: bb
 * @date: 2018-07-17 15:19
 */
public class GetAddr {

    private static String sRemoteAddr;
    private static int iRemotePort = 137;
    private static byte[] buffer = new byte[1024];
    private static DatagramSocket ds = null;

    /**
     * 获取IP地址
     * @param request
     * @throws Exception
     * @auther: bb
     * @date: 2018-07-17 15:19
     */
    public static String getIp(HttpServletRequest request) throws Exception {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null) {
            if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
                int index = ip.indexOf(",");
                if (index != -1) {
                    return ip.substring(0, index);
                } else {
                    return ip;
                }
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (ip != null) {
            if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        ip = request.getHeader("Proxy-Client-IP");
        if (ip != null) {
            if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip != null) {
            if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        ip =  request.getRemoteAddr();
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }


    /**
     * 获取MAC地址
     * @auther: bb
     * @date: 2018-07-17 15:19
     */
    public static String getMacAddress(String ipAddr) {
        String macAddr = "";
        if (ipAddr.equals("127.0.0.1") || ipAddr.equals("localhost")) {
            macAddr = getMACAddressFromNIF();
        } else {
            macAddr = getMACFromIP(ipAddr);
        }

        return macAddr;
    }

    private static String getMACFromIP(String ipAddr) {
        String macAddress = "";
        try {
            // InetAddress address = InetAddress.getLocalHost();
            InetAddress address = InetAddress.getByName(ipAddr);
			/*
			 * Get NetworkInterface for the current host and then read the
			 * hardware address.
			 */
            NetworkInterface ni = NetworkInterface.getByInetAddress(address);
            if (ni != null) {
                byte[] mac = ni.getHardwareAddress();
                if (mac != null) {
                    macAddress = transformMACtoHex(mac);
                } else {
                    System.out.println("Address doesn't exist or is not "
                            + "accessible.");
                }
            } else {
                System.out.println("Network Interface for the specified "
                        + "address is not found.");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } finally {
            System.out.println("MAC address is:" + macAddress);
        }
        return macAddress;
    }

    private static String getMACAddressFromNIF() {
        StringBuilder macAddress = new StringBuilder();
        Enumeration<NetworkInterface> interfaces;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();

            //itereate
            while (interfaces.hasMoreElements()) {
                NetworkInterface nif = interfaces.nextElement();
                String displayName=nif.getDisplayName();
                System.out.println(displayName + "\t\t"
                        + nif.getName());
                byte[] lBytes = nif.getHardwareAddress();
                if (lBytes != null) {
                    String str = transformMACtoHex(lBytes);
                    if(str!=null){
                        //macAddress.append(str).append("\n");
                        macAddress.append(str);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return macAddress.toString();
    }
    /*
	 * Extract each array of mac address and convert it to hexa with the
	 * following format 08-00-27-DC-4A-9E.
	 */
    private static String transformMACtoHex(byte[] mac) {
        String macAddr;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            String form = String.format("%02X%s", mac[i],
                    (i < mac.length - 1) ? "-" : "");
            sb.append(form);
        }
        macAddr = verifyMAC(sb.toString());
        return macAddr;
    }

    private static String verifyMAC(String mac) {
        if (mac != null) {
            if (mac.startsWith("00-00-00-00-00")) {
                System.out.println("Not valid MAC address!");
                return null;
            } else {
                return mac;
            }
        }
        return null;
    }


    public static void main(String args[]) throws Exception{

    }

}
