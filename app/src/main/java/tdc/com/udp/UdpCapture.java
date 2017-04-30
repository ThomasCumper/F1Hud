package tdc.com.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Tomm1eGun2 on 30/04/2017.
 */

public class UdpCapture {

    private DatagramSocket socket = null;

    public UdpCapture(DatagramSocket socket) {

        this.socket = socket; //initialise Datagram Socket
    }

    public byte[] getUdpData() {

        byte[] data = new byte[280]; // New byte array to accept packet of fixed length
        DatagramPacket packet = new DatagramPacket(data, data.length);

        try {
            socket.receive(packet); // recieve udp packet
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new byte[]{0, 0, 0, 0}; // return byte[4] if error thrown for consistency
        }

        return data; // return recieved packet as a byte array
    }

}