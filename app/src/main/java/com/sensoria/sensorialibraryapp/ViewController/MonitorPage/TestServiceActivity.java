package com.sensoria.sensorialibraryapp.ViewController.MonitorPage;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sensoria.sensorialibrary.SAAnkletService;
import com.sensoria.sensorialibraryapp.R;


public class TestServiceActivity extends ActionBarActivity {

    private final static String TAG = TestServiceActivity.class.getSimpleName();

    private SAAnkletService mBluetoothLeService;

    private boolean mIsBound = false;

    class AnkletBroadcastReceiver extends BroadcastReceiver {

        private static final String tag = "BroadcastReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("Type", SAAnkletService.GENERIC_MESSAGE)) {
                case SAAnkletService.CONNECT_MESSAGE:
                    Log.w(tag, "onConnect");
                    break;
                case SAAnkletService.DISCOVER_MESSAGE:
                    Log.w(tag, "onDiscover");
                    break;
                case SAAnkletService.ERROR_MESSAGE:
                    String message = intent.getExtras().getString("Message");
                    Log.w(tag, "onError: " + message);
                    break;
                case SAAnkletService.DATA_MESSAGE:
                    TextView tick = (TextView) findViewById(R.id.tick);
                    TextView heel = (TextView) findViewById(R.id.heel);
                    TextView mtb1 = (TextView) findViewById(R.id.mtb1);
                    TextView mtb5 = (TextView) findViewById(R.id.mtb5);
                    TextView accX = (TextView) findViewById(R.id.accX);
                    TextView accY = (TextView) findViewById(R.id.accY);
                    TextView accZ = (TextView) findViewById(R.id.accZ);

                    tick.setText(String.format("%d", intent.getIntExtra("tick", -1)));
                    heel.setText(String.format("%d", intent.getIntExtra("heel", -1)));
                    mtb1.setText(String.format("%d", intent.getIntExtra("mtb1", -1)));
                    mtb5.setText(String.format("%d", intent.getIntExtra("mtb5", -1)));
                    accX.setText(String.format("%f",  intent.getFloatExtra("accX", 0.0f)));
                    accY.setText(String.format("%f",  intent.getFloatExtra("accY", 0.0f)));
                    accZ.setText(String.format("%f",  intent.getFloatExtra("accZ", 0.0f)));
                default:
            }
        }
    }

    private AnkletBroadcastReceiver receiver;
    private ServiceConnection bleServiceConnection;

    @Override
    public void onDestroy() {
        super.onDestroy();

        unbindService(bleServiceConnection);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_service);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            TextView macAddr = (TextView) findViewById(R.id.macAddrView);
            macAddr.setText(extras.getString("macAddr"));
        }

        IntentFilter filter = new IntentFilter(SAAnkletService.broadcastAction);
        receiver = new AnkletBroadcastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

        // Code to manage Service lifecycle.
        bleServiceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName componentName, IBinder service) {
                mBluetoothLeService = ((SAAnkletService.LocalBinder) service).getService();
                if (!mBluetoothLeService.initialize()) {
                    Toast.makeText(getApplicationContext(), "Unable to initialize Service", Toast.LENGTH_SHORT).show();
                    finish();
                }
                // Automatically connects to the device upon successful start-up initialization.
                //mBluetoothLeService.connect(mDeviceAddress);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mBluetoothLeService = null;
            }
        };

        Intent deviceIntent = new Intent(this, SAAnkletService.class);
        bindService(deviceIntent, bleServiceConnection, BIND_AUTO_CREATE);
        mIsBound = true;
    }

    public void onConnectService(View view) {
        TextView macAddr = (TextView) findViewById(R.id.macAddrView);
        mBluetoothLeService.connect(macAddr.getText().toString());
    }

    public void onDisconnectService(View view) {
        mBluetoothLeService.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
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
}
