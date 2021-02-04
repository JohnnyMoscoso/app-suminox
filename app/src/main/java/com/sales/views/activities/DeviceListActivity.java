package com.sales.views.activities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
/*import java.io.PrintWriter;
import java.io.StringWriter;*/

import java.util.Properties;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.sales.R;
import com.sales.utils.Util;

public class DeviceListActivity extends AppCompatActivity {
    private ActionBar actionBar;
    Context context = this;
    public static final String TAG = "DeviceListActivity";
    public static final boolean D = true;

    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    public BluetoothAdapter mBtAdapter;
    public ArrayAdapter<String> mPairedDevicesArrayAdapter;
    public ArrayAdapter<String> mNewDevicesArrayAdapter;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_device_list);
        getSupportActionBar().setTitle("CloudSales");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        System.gc();

        context = this;
        setResult(Activity.RESULT_CANCELED);
        Button scanButton = (Button) findViewById(R.id.device_scan);
        scanButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                doDiscovery();
                v.setVisibility(View.GONE);
            }
        });

        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            findViewById(R.id.tv_device_connect).setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) {
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = getResources().getText(R.string.alert_device_connect_no_found).toString();
            mPairedDevicesArrayAdapter.add(noDevices);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }
        this.unregisterReceiver(mReceiver);
    }

    public void doDiscovery() {
        //progressDialog =  ProgressDialog.show(context, "CloudSales", "Buscando dispositivos. Espere ...", true);
        if (D) Log.d(TAG, "doDiscovery()");
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }
        mBtAdapter.startDiscovery();
        //progressDialog.dismiss();
    }

    public OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        @SuppressWarnings("deprecation")
        @SuppressLint("WorldWriteableFiles")
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            mBtAdapter.cancelDiscovery();
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);
            try {
                FileOutputStream fos = openFileOutput("pprinter.properties", Context.MODE_WORLD_WRITEABLE);
                FileInputStream fis = openFileInput("pprinter.properties");
                Properties pp = new Properties();
                pp.load(fis);
                pp.setProperty("btprinter", address);
                pp.store(fos, null);
            }catch (FileNotFoundException e) {
                // Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }catch (IOException e) {
                // Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(context, "IO Error", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(context, "Impresora Configurada", Toast.LENGTH_SHORT).show();
            Intent i = new Intent();
            setResult(RESULT_OK, i);
            finish();
        }
    };

    public final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
                setTitle(R.string.alert_device_select);
                if (mNewDevicesArrayAdapter.getCount() == 0) {
                    String noDevices = getResources().getText(R.string.alert_device_connect_no_found).toString();
                    mNewDevicesArrayAdapter.add(noDevices);
                }
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_find_devices, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        else if(id == R.id.action_find){
            doDiscovery();
        }
        else if(id==R.id.action_logout){
            Util.logout(DeviceListActivity.this, context);
        }

        return super.onOptionsItemSelected(item);
    }

}
