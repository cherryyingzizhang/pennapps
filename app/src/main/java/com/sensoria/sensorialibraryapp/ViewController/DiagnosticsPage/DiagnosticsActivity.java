package com.sensoria.sensorialibraryapp.ViewController.DiagnosticsPage;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.sensoria.sensorialibrary.SAAnklet;
import com.sensoria.sensorialibrary.SAAnkletInterface;
import com.sensoria.sensorialibrary.SAFoundAnklet;
import com.sensoria.sensorialibraryapp.ViewController.MonitorPage.TestServiceActivity;
import com.sensoria.sensorialibraryapp.R;

public class DiagnosticsActivity extends ActionBarActivity implements SAAnkletInterface
{
    boolean firstTime = false;

    SAAnklet anklet;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostics);

        anklet = new SAAnklet(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        Take10StepsFragment take10StepsFragment = new Take10StepsFragment();
        fragmentTransaction.add(R.id.fragment_container, take10StepsFragment).commit();

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
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCode = null;
            }
        });
    }

    @Override
    public void didConnect() {

        Log.w("SensoriaLibrary", "Device Connected!");
        Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void didError(String message) {

        Log.e("SensoriaLibrary", message);

    }

    @Override
    public void didUpdateData() {

        //todo
//        tick.setText(String.format("%d", anklet.tick));
//        mtb1.setText(String.format("%d", anklet.mtb1));
//        mtb5.setText(String.format("%d", anklet.mtb5));
//        heel.setText(String.format("%d", anklet.heel));
//        accX.setText(String.format("%f", anklet.accX));
//        accY.setText(String.format("%f", anklet.accY));
//        accZ.setText(String.format("%f", anklet.accZ));

        //todo
//        mFootView.setMtb1(anklet.mtb1);
//        mFootView.setMtb5(anklet.mtb5);
//        mFootView.setHeel(anklet.heel);
//        mFootView.invalidate();
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
