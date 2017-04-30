package tdc.com.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import tdc.com.Vehicle.VehicleData;
import tdc.com.f1hud.R;
import tdc.com.udp.Socket;
import tdc.com.udp.UdpCapture;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setScreenStyle(getWindow().getDecorView()); // pass current view

        try {
            UdpCapture udp = new UdpCapture(Socket.getSocket(20777)); //Create new udp instance passing Datagram socket
            VehicleData vehicle = new VehicleData(udp,this);
            (new Thread(vehicle)).start(); // Create new thread for Vehicle
        } catch (Exception e) {
            System.out.println("ERROR: "+e.getMessage()+"--"+e.getClass().getName());
        }
    }

    public void setGear(final String gear){
        // Update gear text
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                TextView txtGear = (TextView) findViewById(R.id.txtGear);
                txtGear.setText(gear);
            }});
    }

    public void setLastLap(final String lastLap) {
        // Update last lap text
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                TextView txtLastLap = (TextView) findViewById(R.id.txtLastLap);
                txtLastLap.setText(lastLap);
            }});
    }

    private void setScreenStyle(View decorView){

        // set application to full screen and 'hide' notification panel and OS navigation buttons
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // Stops screen sleep
    }


}
