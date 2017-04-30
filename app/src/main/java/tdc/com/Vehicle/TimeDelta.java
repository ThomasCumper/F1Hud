package tdc.com.Vehicle;

/**
 * Created by Tomm1eGun2 on 01/05/2017.
 */

public class TimeDelta {

    private boolean hasUpdated = false;

    private float fastestLap=0;
    private float diff=0;

    public float getDelta(float latestLap,int currentSector){

        if(currentSector == 0 && !hasUpdated){
            if (fastestLap==0)
                fastestLap = latestLap;
            diff = latestLap-fastestLap;
            if(diff<0)
                fastestLap = latestLap;

            hasUpdated = true;
        }

        if(currentSector ==2){
            hasUpdated = false;
        }
        return diff;
    }
}