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

    public float [] getMultipleFloat (byte[] data){
        if(data.length!=16)
            return new float []{0,0,0,0};
        int asInt = (data[0] & 0xFF)
                | ((data[1] & 0xFF) << 8)
                | ((data[2] & 0xFF) << 16)
                | ((data[3] & 0xFF) << 24);
        float floatValue1 = Float.intBitsToFloat(asInt);
        asInt = (data[4] & 0xFF)
                | ((data[5] & 0xFF) << 8)
                | ((data[6] & 0xFF) << 16)
                | ((data[7] & 0xFF) << 24);
        float floatValue2 = Float.intBitsToFloat(asInt);
        asInt = (data[8] & 0xFF)
                | ((data[9] & 0xFF) << 8)
                | ((data[10] & 0xFF) << 16)
                | ((data[11] & 0xFF) << 24);
        float floatValue3 = Float.intBitsToFloat(asInt);
        asInt = (data[12] & 0xFF)
                | ((data[13] & 0xFF) << 8)
                | ((data[14] & 0xFF) << 16)
                | ((data[15] & 0xFF) << 24);
        float floatValue4 = Float.intBitsToFloat(asInt);

        return new float []{floatValue1,floatValue2,floatValue3,floatValue4};
    }
}