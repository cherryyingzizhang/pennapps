package com.sensoria.sensorialibraryapp.ViewController.TrainingPage;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;
import com.philjay.circledisplay.CircleDisplay;
import com.sensoria.sensorialibrary.SAAnklet;
import com.sensoria.sensorialibrary.SAAnkletInterface;
import com.sensoria.sensorialibrary.SAFoundAnklet;
import com.sensoria.sensorialibraryapp.R;

import java.util.UUID;

public class TrainingActivity extends ActionBarActivity implements SAAnkletInterface
{

    boolean firstTime = false;

    int ERROR = -999;
    int numberOfStepsTotal;
    int numberOfStepsTaken;
    private CircleDisplay mCircleDisplay; //circleDisplay
    private TextView numberOfSteps, status; //tv_numberOfSteps
    private LinearLayout quitButton; //button_quitTraining
    private SAAnklet anklet;
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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        numberOfStepsTotal = getIntent().getIntExtra("numberOfSteps", ERROR);
        if (numberOfStepsTotal == ERROR)
        {
            Toast.makeText(getApplicationContext(),
                    "Error: number of steps inputted in popup could not be found.",
                    Toast.LENGTH_SHORT)
                    .show();
            onBackPressed();
        }

        anklet = new SAAnklet(this);

        numberOfStepsTaken = 0;

        mCircleDisplay = (CircleDisplay) findViewById(R.id.circleDisplay);
        mCircleDisplay.setAnimDuration(5);
        mCircleDisplay.setValueWidthPercent(20f);
        mCircleDisplay.setFormatDigits(0);
        mCircleDisplay.setDimAlpha(80);
        mCircleDisplay.setTouchEnabled(false);
        mCircleDisplay.setUnit("Steps");
        mCircleDisplay.setStepSize(0.5f);
        mCircleDisplay.setColor(Color.RED);
        mCircleDisplay.showValue(numberOfStepsTaken, numberOfStepsTotal, true);

        numberOfSteps = (TextView) findViewById(R.id.tv_numberOfSteps);
        numberOfSteps.setText(numberOfStepsTotal + " Steps");

        quitButton = (LinearLayout) findViewById(R.id.button_quitTraining);
        quitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        status = (TextView) findViewById(R.id.status);

        onConnect();
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
                                        Toast.makeText(TrainingActivity.this, "up", Toast.LENGTH_SHORT).show();
                                        break;
                                    case BUTTON_SELECT:
                                        Toast.makeText(TrainingActivity.this, "select", Toast.LENGTH_SHORT).show();
                                        break;
                                    case BUTTON_DOWN:
                                        Toast.makeText(TrainingActivity.this, "down", Toast.LENGTH_SHORT).show();
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

    private String selectedCode;
    private String selectedMac;

    public void onStartScan(View view) {
        anklet.startScan();
    }

    public void onStopScan(View view) {
        anklet.stopScan();
    }

    public void onConnect() {

        Log.w("SensoriaLibrary", "Connect to " + selectedCode + " " + selectedMac);
        anklet.deviceCode = selectedCode;
        anklet.deviceMac = selectedMac;
        anklet.connect();
    }

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

                Log.e("SensoriaLibrary", selectedCode + " " + selectedMac);

                if (!firstTime)
                {
                    firstTime = true;
                    Toast.makeText(TrainingActivity.this, "connected, connect again", Toast.LENGTH_SHORT).show();
                    onConnect();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCode = null;

                if (!firstTime)
                {
                    firstTime = true;
                    Toast.makeText(TrainingActivity.this, "connected, connect again", Toast.LENGTH_SHORT).show();
                    onConnect();
                }
            }
        });
    }

    @Override
    public void didConnect() {

        Log.w("SensoriaLibrary", "Device Connected!");
        if (!firstTime)
        {
            firstTime = true;
            Toast.makeText(TrainingActivity.this, "connected, connect again", Toast.LENGTH_SHORT).show();
            onConnect();
        }

    }

    @Override
    public void didError(String message) {

        Log.e("SensoriaLibrary", message);
        if (!firstTime)
        {
            firstTime = true;
            Toast.makeText(TrainingActivity.this, "connected, connect again", Toast.LENGTH_SHORT).show();
            onConnect();
        }
    }

    @Override
    public void didUpdateData()
    {

        if (!inStep)
        {
            if (anklet.accZ < 0.12)
            {
                inStep = true;
            }
        }
        else if (anklet.accZ > 0.15)
        {
            inStep = false;
            if (anklet.mtb1 <= 430 &&
                    anklet.mtb5 > 650)
            {
                Toast.makeText(TrainingActivity.this, "feet are out... move them inwards", Toast.LENGTH_SHORT).show();
                PebbleDictionary out = new PebbleDictionary();
                out.addInt32(KEY_VIBRATE, 0);
                PebbleKit.sendDataToPebble(getApplicationContext(), WATCHAPP_UUID, out);
            }
            else if (anklet.mtb1 > 470 &&
                    anklet.mtb5 <= 600)
            {
                Toast.makeText(TrainingActivity.this, "feet are in... move them outwards", Toast.LENGTH_SHORT).show();
                PebbleDictionary out = new PebbleDictionary();
                out.addInt32(KEY_VIBRATE, 0);
                PebbleKit.sendDataToPebble(getApplicationContext(), WATCHAPP_UUID, out);
            }
            else
            {
                if (numberOfStepsTaken != numberOfStepsTotal)
                {
                    numberOfStepsTaken++;

                    numberOfSteps.setText(numberOfStepsTotal - numberOfStepsTaken + " Steps");

                    mCircleDisplay.showValue(numberOfStepsTaken, numberOfStepsTotal, true);
                    mCircleDisplay.invalidate();
                }
                else
                {
                    status.setText("Complete!");
                    anklet.disconnect();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_training, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
