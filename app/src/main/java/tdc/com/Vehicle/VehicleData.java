package tdc.com.Vehicle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import tdc.com.udp.ByteToFloat;
import tdc.com.udp.UdpCapture;
import tdc.com.ui.FlagLightFlash;
import tdc.com.ui.MainActivity;

/**
 * Created by Tomm1eGun2 on 30/04/2017.
 */

public class VehicleData implements Runnable{

    boolean run = true;
    byte[] data = new byte [280];

    UdpCapture udp;
    TimeDelta delta;
    MainActivity activity;

    private final float _MAXRPM = 13500;

    public VehicleData(UdpCapture udp, MainActivity activity) {
        this.udp = udp;
        this.activity = activity;
        delta = new TimeDelta();
        FlagLightFlash flf = new FlagLightFlash(activity,this);
        Thread t1 = new Thread(flf);
        t1.start();
    }

    @Override
    public void run() {

        while(run) {
            getUDPPacket();
            updateGear();
            updateRPM();
            updateLastLap();
            updateFlagLights();
            updateLap();
            updateBrakeTemp();
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

    private void updateLap(){

        float fValue = getPacket(new int[]{144,145,146,147});
        int lap = (int) fValue+1;
        activity.setLap(lap);
    }

    private String parseLastLap(float lapTime){

        long millis = (long) (lapTime *1000);
        SimpleDateFormat formatter = new SimpleDateFormat("m:ss.SSS", Locale.getDefault());
        return formatter.format(new Date(millis));
    }

    private void updateRPM(){

        float fValue = getPacket(new int[]{148,149,150,151});
        int RPM = (int) fValue;
        int rpmPercent = (int) ((fValue / _MAXRPM)*100);
        activity.setRPM(RPM, rpmPercent);
    }

    public void updateFlagLights(){

        float fValue = getPacket(new int[]{276,277,278,279});
        int curFlag = getCurrentFlag();
        activity.setFlagLights(curFlag);
    }

    public int getCurrentFlag(){
        float fValue = getPacket(new int[]{276,277,278,279});
        return (int) fValue;
    }

    private void updateBrakeTemp(){

        float [] fValue = getLargePacket(new int[]{204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219});
        for (int i=0;i<fValue.length;i++)
                fValue[i]-=273;
        activity.setBrakeTemp(fValue);
    }

    private void getUDPPacket(){
        data = udp.getUdpData();
    }

    private float getPacket(int [] arrayIndex){

        ByteToFloat btf = new ByteToFloat();
        return btf.getFloat(new byte[]{data[arrayIndex[0]],data[arrayIndex[1]],data[arrayIndex[2]],data[arrayIndex[3]]}); // pass 4 bytes to unpack into float
    }

    private float [] getLargePacket(int [] arrayIndex){
        ByteToFloat btf = new ByteToFloat();
        return btf.getMultipleFloat(new byte[]{data[arrayIndex[0]],data[arrayIndex[1]],data[arrayIndex[2]],data[arrayIndex[3]],data[arrayIndex[4]],data[arrayIndex[5]],
                            data[arrayIndex[6]],data[arrayIndex[7]],data[arrayIndex[8]],data[arrayIndex[9]],data[arrayIndex[10]],data[arrayIndex[11]],
                            data[arrayIndex[12]],data[arrayIndex[13]],data[arrayIndex[14]],data[arrayIndex[15]],});
    }
}