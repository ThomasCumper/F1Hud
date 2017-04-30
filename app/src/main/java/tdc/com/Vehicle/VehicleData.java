package tdc.com.Vehicle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import tdc.com.udp.ByteToFloat;
import tdc.com.udp.UdpCapture;
import tdc.com.ui.MainActivity;

/**
 * Created by Tomm1eGun2 on 30/04/2017.
 */

public class VehicleData implements Runnable{

    boolean run = true;

    UdpCapture udp;
    TimeDelta delta;
    MainActivity activity;

    public VehicleData(UdpCapture udp, MainActivity activity) {
        this.udp = udp;
        this.activity = activity;
        delta = new TimeDelta();
    }

    @Override
    public void run() {

        while(run){
            updateGear();
            updateRPM();
            updateLastLap();
        }
    }

    private void updateGear(){

        float fValue = getPacket(new int[]{132, 133, 134, 135});
        final int gearValue= (int) fValue;
        String gear = parseGearValue(gearValue);
        activity.setGear(gear);
    }

    private String parseGearValue(int gearValue){
        String gear;
        // Convert gear integer to correct value (R = Reverse, N = Neutral) default integer value -1
        switch (gearValue){
            case 0 :
                gear = String.valueOf('R');
                break;
            case 1 :
                gear = String.valueOf('N');
                break;
            default :
                gear = String.valueOf(gearValue-1);
                break;
        }
        return gear;
    }

    private void updateLastLap(){

        float fValue = getPacket(new int[]{248,249,250,251});
        String lastLap = parseLastLap(fValue);
        activity.setLastLap(lastLap);
        float fDelta = delta.getDelta(fValue,getCurrentSector());
        activity.setDelta(fDelta);
    }

    private int getCurrentSector(){

        float fValue = getPacket(new int[]{192,193,194,195});
        return (int) fValue;
    }

    private String parseLastLap(float lapTime){

        long millis = (long) (lapTime *1000);
        SimpleDateFormat formatter = new SimpleDateFormat("m:ss.SSS", Locale.getDefault());
        return formatter.format(new Date(millis));
    }

    private void updateRPM(){

        float fValue = getPacket(new int[]{148,149,150,151});
        int RPM = (int) fValue;
        activity.setRPM(RPM);
    }

    private float getPacket(int [] arrayIndex){

        ByteToFloat btf = new ByteToFloat();
        byte [] b = udp.getUdpData(); // get byte array from udp packet
        return btf.getFloat(new byte[]{b[arrayIndex[0]],b[arrayIndex[1]],b[arrayIndex[2]],b[arrayIndex[3]]}); // pass 4 bytes to unpack into float
    }
}