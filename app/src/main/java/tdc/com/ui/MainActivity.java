package tdc.com.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import tdc.com.Vehicle.VehicleData;
import tdc.com.f1hud.R;
import tdc.com.udp.Socket;
import tdc.com.udp.UdpCapture;

import static android.graphics.Color.rgb;

public class MainActivity extends AppCompatActivity {

    ImageView ivFlagLeft= (ImageView)findViewById(R.id.ivFlagLeft); ;
    ImageView ivFlagRight = (ImageView)findViewById(R.id.ivFlagRight);
    TextView txtRPM = (TextView) findViewById(R.id.txtRPM);
    ProgressBar pbRPM = (ProgressBar) findViewById(R.id.pbRPM);;
    TextView txtLastLap  = (TextView) findViewById(R.id.txtLastLap);
    TextView txtLap = (TextView) findViewById(R.id.txtLap);
    TextView txtDelta = (TextView)findViewById(R.id.txtDelta);
    TextView txtFlTemp = (TextView)findViewById(R.id.txtFlTemp);
    TextView txtFrTemp = (TextView)findViewById(R.id.txtFrTemp);
    TextView txtRlTemp = (TextView)findViewById(R.id.txtRlTemp);
    TextView txtRrTemp = (TextView)findViewById(R.id.txtRrTemp);

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

    public void setRPM(final int rpm, final int rpmPercent){
        // Update rpm text
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                txtRPM.setText(String.valueOf(rpm));
                pbRPM.setProgress(rpmPercent);
            }});

    }

    public void setLastLap(final String lastLap) {
        // Update last lap text
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                txtLastLap.setText(lastLap);
            }});
    }

    public void setLap (final int lap){

        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                txtLap.setText(String.valueOf(lap));
            }
        });

    }

    public void setDelta(final float delta){
        // Update delta time text
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
        String sDelta = String.format("%.02f", delta);
        if (delta >0){
            txtDelta.setText("+"+sDelta);
            txtDelta.setTextColor(rgb(248,136,136));
        }else{
            txtDelta.setText(sDelta);
            txtDelta.setTextColor(rgb(140,248,136));
        }
            }});
    }

    public void setFlagLights(final int curFlag){

        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                switch (curFlag) {
                    case 1:
                        ivFlagLeft.setImageResource(R.drawable.flag_green);
                        ivFlagRight.setImageResource(R.drawable.flag_green);
                        break;
                    case 2:
                        ivFlagLeft.setImageResource(R.drawable.flag_blue);
                        ivFlagRight.setImageResource(R.drawable.flag_blue);
                        break;
                    case 3:
                        ivFlagLeft.setImageResource(R.drawable.flag_yellow);
                        ivFlagRight.setImageResource(R.drawable.flag_yellow);
                        break;
                    default:
                        ivFlagLeft.setImageResource(R.drawable.flag_green);
                        ivFlagRight.setImageResource(R.drawable.flag_green);
                        break;
                }
            }
            });
    }

    public void setBrakeTemp(final float [] temp){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtFlTemp.setText(String.valueOf((int)temp[0]));
                txtFrTemp.setText(String.valueOf((int)temp[1]));
                txtRlTemp.setText(String.valueOf((int)temp[2]));
                txtRrTemp.setText(String.valueOf((int)temp[3]));
            }
        });
    }

    public void flagLightFlash (final int flash){

        runOnUiThread(new Runnable(){
            @Override
            public void run() {

                if (flash == 1) {
                    ivFlagLeft.setVisibility(View.VISIBLE);
                    ivFlagRight.setVisibility(View.VISIBLE);
                } else {
                    ivFlagLeft.setVisibility(View.INVISIBLE);
                    ivFlagRight.setVisibility(View.INVISIBLE);
                }
            }
            });

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
