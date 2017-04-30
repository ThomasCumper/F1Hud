package tdc.com.udp;

/**
 * Created by Tomm1eGun2 on 30/04/2017.
 */

public class ByteToFloat {

    // unpacks the byte array and returns integer value
    public float getFloat(byte[] data){
        if(data.length!=4)
            return 0; // return 0 if byte array incorrect length
        int asInt = (data[0] & 0xFF)
                | ((data[1] & 0xFF) << 8)
                | ((data[2] & 0xFF) << 16)
                | ((data[3] & 0xFF) << 24);
        return Float.intBitsToFloat(asInt);
    }
}