package tdc.com.ui;

import tdc.com.Vehicle.VehicleData;

/**
 * Created by Tomm1eGun2 on 01/05/2017.
 */

public class FlagLightFlash implements Runnable {

        private MainActivity activity;
        private VehicleData vehicle;

        public FlagLightFlash (MainActivity activity, VehicleData vehicle){

            this.activity = activity;
            this.vehicle  = vehicle;
        }

        @Override
        public void run(){

            while (true){
                try {
                        if(vehicle.getCurrentFlag()>0){
                            activity.flagLightFlash(1);
                            Thread.currentThread().sleep(250);
                            activity.flagLightFlash(0);
                            Thread.currentThread().sleep(250);
                        }else{
                            Thread.currentThread().sleep(500);
                            activity.flagLightFlash(0);
                        }

                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }


}
