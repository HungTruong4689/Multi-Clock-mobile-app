package com.example.finalproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class WorldClock extends AppCompatActivity {

    WifiManager wifiManager;
    TextView wifiStatusTextView;
    Switch wifiSwitch;
    private RequestQueue queue;
    String timezone = "Europe/Berlin";
    String datetime = "2022-04-09T21:56:44.953279+02:00";
    //String url = "http://worldtimeapi.org/api/timezone/{$area}/{$location}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_clock);
        queue = Volley.newRequestQueue(this);

        //Defining Variables
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiStatusTextView = (TextView) findViewById(R.id.wifi_status_text_view);
        wifiSwitch = (Switch) findViewById(R.id.wifi_switch);

        if(wifiManager.isWifiEnabled()){
            wifiStatusTextView.setText("WIFI is ON");
            wifiSwitch.setChecked(true);
        }else{
            wifiStatusTextView.setText("WIFI is OFF");
            wifiSwitch.setChecked(false);
        }

        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(wifiSwitch.isChecked()==true){
                    wifiManager.setWifiEnabled(true);
                    wifiStatusTextView.setText("WIFI is ON");
                    Toast.makeText(WorldClock.this, "It takes a moment for turning on WIFI",Toast.LENGTH_LONG).show();

                }else{
                    wifiManager.setWifiEnabled(false);
                    wifiStatusTextView.setText("WIFI is OFF");
                }
            }
        });
    }
    protected void onSaveInstanceState( Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("TIMEZONE",timezone);
        savedInstanceState.putString("DATETIME",datetime);


    }
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        timezone =savedInstanceState.getString("TIMEZONE");
        datetime = savedInstanceState.getString("DATETIME");
        TextView location= findViewById(R.id.locationTextView);
        location.setText(timezone);

        TextView time = findViewById(R.id.datetimeTextView);
        time.setText("" + datetime);

    }
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }
    @Override
    protected void onPause() {
        super.onPause();

    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiStateExtra) {
                case WifiManager.WIFI_STATE_ENABLED:
                    wifiSwitch.setChecked(true);
                    wifiStatusTextView.setText("WiFi is ON");
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    wifiSwitch.setChecked(false);
                    wifiStatusTextView.setText("WiFi is OFF");
                    break;
            }
        }
    };
    public void getTimeData(View view) {
        EditText area = findViewById(R.id.editTextArea);
        EditText location = findViewById(R.id.editTextLocation);
        //String area = "Europe";
        //String location = "Helsinki";
        String url = "https://worldtimeapi.org/api/timezone/"+area.getText().toString()+"/"+location.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // Callback for successful response
                    //Toast.makeText(this, response, Toast.LENGTH_LONG).show();

                    // Parse the data from JSON object
                    parseJsonAndUpdateUI(response);
                }, error -> {
            // Callback for something went wrong with fetch
            Toast.makeText(this, "Some error!", Toast.LENGTH_LONG).show();
        }
        );
        // Send the request by adding it to the queue
        queue.add(stringRequest);
    }
    private void parseJsonAndUpdateUI(String response) {



        // Parse the data from response
        // 1. Convert the response to JSON Object
        try {
            JSONObject worldclock = new JSONObject(response);
            timezone = worldclock.getString("timezone");
            datetime= worldclock.getString("datetime");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Update the data to UI
        TextView location= findViewById(R.id.locationTextView);
        location.setText(timezone);

        TextView time = findViewById(R.id.datetimeTextView);
        time.setText("" + datetime);


    }

}