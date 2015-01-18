package com.sensoria.sensorialibraryapp.ViewController.MonitorPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;
import com.parse.Parse;
import com.parse.ParseObject;
import com.sensoria.sensorialibrary.SAAnklet;
import com.sensoria.sensorialibrary.SAAnkletInterface;
import com.sensoria.sensorialibrary.SAFoundAnklet;
import com.sensoria.sensorialibraryapp.Model.FootView;
import com.sensoria.sensorialibraryapp.R;

import java.util.UUID;

//class from the sample app from sensoria... this and TestServiceActivity are from the sample.
public class MonitorActivity extends ActionBarActivity implements SAAnkletInterface {

    int numSteps = 0;

    SAAnklet anklet;

    FootView mFootView;

    boolean inStep = false;

    private static final UUID WATCHAPP_UUID = UUID.fromString("6092637b-8f58-4199-94d8-c606b1e45040");
    private static final String WATCHAPP_FILENAME = "android-example.pbw";

    private static final int
            KEY_BUTTON = 0,
            KEY_VIBRATE = 1,
            BUTTON_UP = 0,
            BUTTON_SELECT = 1,
            BUTTON_DOWN = 2;

    private Handler handler = new Handler();

    private PebbleKit.PebbleDataReceiver appMessageReciever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_monitor);

        //sending data to our backend and analyzing the metadata.
        Parse.initialize(this, "p6UR5hKjTNIa78j8HykqFR2zsvI5nbrwZAJvvWlC", "3dfrd51jLIiGjYTIirGKwl6GtQpfupZg30LaPzBI");

        anklet = new SAAnklet(this);

        mFootView =  (FootView) findViewById(R.id.footview);
    }

    @Override
    protected void onResume() {
        super.onResume();

        anklet.resume();
        pebbleOnResume();
    }

    private void pebbleOnResume()
    {
        // Define AppMessage behavior
        if(appMessageReciever == null) {
            appMessageReciever = new PebbleKit.PebbleDataReceiver(WATCHAPP_UUID) {

                @Override
                public void receiveData(Context context, int transactionId, PebbleDictionary data) {
                    // Always ACK
                    PebbleKit.sendAckToPebble(context, transactionId);

                    // What message was received?
                    if(data.getInteger(KEY_BUTTON) != null) {
                        // KEY_BUTTON was received, determine which button
                        final int button = data.getInteger(KEY_BUTTON).intValue();

                        // Update UI on correct thread
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                switch(button) {
                                    case BUTTON_UP:
                                        Toast.makeText(MonitorActivity.this, "up", Toast.LENGTH_SHORT).show();
                                        break;
                                    case BUTTON_SELECT:
                                        Toast.makeText(MonitorActivity.this, "select", Toast.LENGTH_SHORT).show();
                                        break;
                                    case BUTTON_DOWN:
                                        Toast.makeText(MonitorActivity.this, "down", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(getApplicationContext(), "Unknown button: " + button, Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }

                        });
                    }
                }
            };

            // Add AppMessage capabilities
            PebbleKit.registerReceivedDataHandler(this, appMessageReciever);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        anklet.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        anklet.disconnect();
    }

    public void onTestService(View view) {
        Intent intent = new Intent(this, TestServiceActivity.class);
        intent.putExtra("macAddr", selectedMac);
        startActivity(intent);
    }

    public void onStartScan(View view) {
        anklet.startScan();
    }

    public void onStopScan(View view) {
        anklet.stopScan();
    }

    public void onConnect(View view) {

        Log.w("SensoriaLibrary", "Connect to " + selectedCode + " " + selectedMac);
        anklet.deviceCode = selectedCode;
        anklet.deviceMac = selectedMac;
        anklet.connect();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String selectedCode;
    private String selectedMac;

    @Override
    public void didDiscoverDevice() {

        Log.w("SensoriaLibrary", "Device Discovered!");

        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, anklet.deviceDiscoveredList);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                SAFoundAnklet deviceDiscovered = anklet.deviceDiscoveredList.get(position);
                selectedCode = deviceDiscovered.deviceCode;
                selectedMac = deviceDiscovered.deviceMac;

                Log.d("SensoriaLibrary", selectedCode + " " + selectedMac);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCode = null;
            }
        });
    }

    @Override
    public void didConnect() {

        Log.w("SensoriaLibrary", "Device Connected!");

    }

    @Override
    public void didError(String message) {

        Log.e("SensoriaLibrary", message);

    }

    @Override
    public void didUpdateData() {

        TextView tick = (TextView) findViewById(R.id.tickValue);
        TextView mtb1 = (TextView) findViewById(R.id.mtb1Value);
        TextView mtb5 = (TextView) findViewById(R.id.mtb5Value);
        TextView heel = (TextView) findViewById(R.id.heelValue);
        TextView accX = (TextView) findViewById(R.id.accXValue);
        TextView accY = (TextView) findViewById(R.id.accYValue);
        TextView accZ = (TextView) findViewById(R.id.accZValue);

        tick.setText(String.format("%d", anklet.tick));
        mtb1.setText(String.format("%d", anklet.mtb1));
        mtb5.setText(String.format("%d", anklet.mtb5));
        heel.setText(String.format("%d", anklet.heel));
        accX.setText(String.format("%f", anklet.accX));
        accY.setText(String.format("%f", anklet.accY));
        accZ.setText(String.format("%f", anklet.accZ));

        mFootView.setMtb1(anklet.mtb1);
        mFootView.setMtb5(anklet.mtb5);
        mFootView.setHeel(anklet.heel);
        mFootView.invalidate();

        if (!inStep)
        {
            if (anklet.accZ < 0.12)
            {
                inStep = true;
            }
        }
        else if (anklet.accZ > 0.12)
        {
            numSteps++;
            //TODO: @Fan you can change the ui and add the number of steps and update them in real time
            //using textview or something instead of a toast
//            Toast.makeText(MonitorActivity.this, "numsteps " + numSteps, Toast.LENGTH_SHORT).show();
            inStep = false;
        }

        if (anklet.tick%50 == 0)
        {
            ParseObject dataPoint = new ParseObject("DataPoint");
            dataPoint.put("mtb1", anklet.mtb1);
            dataPoint.put("mtb5", anklet.mtb5);
            dataPoint.put("heel", anklet.heel);
            dataPoint.saveInBackground();
        }

        if (mFootView.getMtb1Color() == FootView.ColorOfArea.GREEN &&
                mFootView.getMtb5Color() == FootView.ColorOfArea.RED)
        {
            Toast.makeText(MonitorActivity.this, "feet are out... move them inwards", Toast.LENGTH_SHORT).show();
            PebbleDictionary out = new PebbleDictionary();
            out.addInt32(KEY_VIBRATE, 0);
            PebbleKit.sendDataToPebble(getApplicationContext(), WATCHAPP_UUID, out);
        }
        else if (mFootView.getMtb1Color() == FootView.ColorOfArea.RED &&
                mFootView.getMtb5Color() == FootView.ColorOfArea.GREEN)
        {
            Toast.makeText(MonitorActivity.this, "feet are in... move them outwards", Toast.LENGTH_SHORT).show();
            PebbleDictionary out = new PebbleDictionary();
            out.addInt32(KEY_VIBRATE, 0);
            PebbleKit.sendDataToPebble(getApplicationContext(), WATCHAPP_UUID, out);
        }
    }
}
