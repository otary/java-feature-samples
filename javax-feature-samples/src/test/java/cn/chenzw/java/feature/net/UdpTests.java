package cn.chenzw.java.feature.net;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.net.*;

/**
 * UDP传输
 */
@RunWith(JUnit4.class)
public class UdpTests {

    @Test
    public void test() throws IOException {
        DatagramSocket udpSocket = new DatagramSocket(9100);

        byte[] sendData = "hello!".getBytes();
        DatagramPacket udpPacket = new DatagramPacket(sendData, 0, sendData.length);
        udpPacket.setAddress(InetAddress.getByName("dht.transmissionbt.com"));
        udpPacket.setPort(6881);

        udpSocket.send(udpPacket);
    }

}
