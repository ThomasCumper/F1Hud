package tdc.com.udp;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by Tomm1eGun2 on 30/04/2017.
 */

public class Socket {

    public static DatagramSocket getSocket(int port) throws SocketException {
        return new DatagramSocket(port); // listen to UDP port
    }

}