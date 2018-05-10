package com.example.fqa.sqlserversample.Util;

import android.app.Activity;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Locale;

public class NetworkPing {
    public void networkPing(Log log) throws IOException {
        String interfaceName = "wlan0";
        NetworkInterface networkInterface = NetworkInterface.getByName(interfaceName);
        Enumeration<InetAddress> addressEnumeration = networkInterface.getInetAddresses();
        while (addressEnumeration.hasMoreElements()){
            InetAddress currentAddress = addressEnumeration.nextElement();
            log.logInfo(currentAddress.getHostAddress());
            if(currentAddress instanceof Inet4Address && !currentAddress.isLoopbackAddress())
            {
                byte[] ip = currentAddress.getAddress();
                for (int i = 1; i <= 254; i++) {
                    ip[3] = (byte) i;
                    InetAddress address = InetAddress.getByAddress(ip);
                    if (address.isReachable(1000)) {
                        System.out.println(address + " maquina esta ligada e pode ser pingada");
                    } else if (!address.getHostAddress().equals(address.getHostName())) {
                        System.out.println(address + " maquina reconhecida por um DNSLookup");
                    } else {
                        System.out.println(address + " o endereço de host e o nome do host são iguais, o host name não pode ser resolvido.");
                    }
                }
            }
        }
    }
}
