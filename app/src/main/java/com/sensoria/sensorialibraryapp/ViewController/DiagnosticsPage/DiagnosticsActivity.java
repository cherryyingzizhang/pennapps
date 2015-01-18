package com.sensoria.sensorialibraryapp.ViewController.DiagnosticsPage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import com.philjay.circledisplay.CircleDisplay;
import com.sensoria.sensorialibrary.SAAnklet;
import com.sensoria.sensorialibrary.SAAnkletInterface;
import com.sensoria.sensorialibrary.SAFoundAnklet;
import com.sensoria.sensorialibraryapp.R;
import com.sensoria.sensorialibraryapp.UsefulClasses.AlgorithmMethods;
import com.sensoria.sensorialibraryapp.ViewController.MonitorPage.TestServiceActivity;

import java.util.ArrayList;

public class DiagnosticsActivity extends ActionBarActivity implements SAAnkletInterface
{
    boolean firstTime = false;
    SAAnklet anklet;
    boolean inStep = false;
    int inSteps = 0;
    int outSteps = 0;
    int numberOfStepsTotal = 10;
    public int numberOfStepsTaken = 0;
    private CircleDisplay mCircleDisplay; //circleDisplay

    ArrayList<Integer> poses = new ArrayList<Integer>();

    AlgorithmMethods algorithmMethods;

    TextView stepsLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostics);

        stepsLeft = (TextView) findViewById(R.id.tv_numberOfSteps);

        anklet = new SAAnklet(this);

        algorithmMethods = new AlgorithmMethods(this);

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

        onConnect();
    }

    @Override
    protected void onResume() {
        super.onResume();

        anklet.resume();
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

    public void onConnect() {

        Log.w("SensoriaLibrary", "Connect to " + selectedCode + " " + selectedMac);
        anklet.deviceCode = selectedCode;
        anklet.deviceMac = selectedMac;
        anklet.connect();
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


                if (!firstTime)
                {
                    firstTime = true;
                    Toast.makeText(DiagnosticsActivity.this, "connected, connect again", Toast.LENGTH_SHORT).show();
                    onConnect();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                selectedCode = null;
            }
        });
    }

    @Override
    public void didConnect()
    {

        Log.w("SensoriaLibrary", "Device Connected!");
        Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void didError(String message) {

        Log.e("SensoriaLibrary", message);

    }

    @Override
    public void didUpdateData()
    {

        poses.add(algorithmMethods.matchesPose(anklet.mtb1, anklet.mtb5, anklet.heel));

        if (!inStep)
        {
            if (anklet.accZ < 0.12)
            {
                inStep = true;
            }
        }
        else if (anklet.accZ > 0.12)
        {
            inStep = false;
            if (anklet.mtb1 <= 430 &&
                    anklet.mtb5 > 650)
            {
                outSteps++;
            }
            else if (anklet.mtb1 > 470 &&
                    anklet.mtb5 <= 600)
            {
                inSteps++;
            }
            else
            {
                if (numberOfStepsTaken != numberOfStepsTotal)
                {
                    numberOfStepsTaken++;
                    if (stepsLeft != null)
                    {
                        stepsLeft.setText(numberOfStepsTotal - numberOfStepsTaken + " Steps");
                        mCircleDisplay.showValue(numberOfStepsTaken, numberOfStepsTotal, true);
                        mCircleDisplay.invalidate();
                    }
                }
                else
                {
                    //TODO GO TO RESULTS OF DIAGNOSIS PAGE
                    anklet.disconnect();
                    Intent intent = new Intent(DiagnosticsActivity.this, DiagnosticResultsActivity.class);
                    intent.putIntegerArrayListExtra("poses", poses);
                    intent.putExtra("result", getAppropriateResult());
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

    public String getAppropriateResult()
    {
        if (inSteps > outSteps)
        {
            if (inSteps > 5)
            {
                return "You walk with lots of pressure in your inner soles. Maintain balance on your soles.";
            }
            else if (inSteps >= 3)
            {
                return "You walk with some amount of pressure in your inner soles. Maintain balance on your soles.";
            }
        }
        else
        {
            if (outSteps > 5)
            {
                return "You walk with lots of pressure in your outer soles. Maintain balance on your soles.";
            }
            else if (outSteps >= 3)
            {
                return "You walk with some amount of pressure in your outer soles. Maintain balance on your soles.";
            }
        }

        return "your gait is fine!!!";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diagnostics, menu);
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
