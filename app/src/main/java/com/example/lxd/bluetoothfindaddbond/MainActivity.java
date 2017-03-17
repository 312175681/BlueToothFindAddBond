package com.example.lxd.bluetoothfindaddbond;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 0;
    private BlueToothContriller mController = new BlueToothContriller();
    private Toast mToast;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private List<BluetoothDevice> mDeviceList = new ArrayList<>();
    private List<BluetoothDevice> BondedDeviceList = new ArrayList<>();
    private ListView mListView;
    private DeviceAdapter mAdapter;

    private Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.enable_visiblity) {
                mController.enableVisibly(MainActivity.this);

            } else if (id == R.id.find_device) {
                mAdapter.refresh(mDeviceList);
                mController.findDevice();
                mListView.setOnClickListener(bindDeviceClick);
            } else if (id == R.id.bonded_device) {

            }
            return true;
        }
    };

    private AdapterView.OnItemClickListener bindDeviceClick=new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BluetoothDevice device =mDeviceList.get(position);
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
                device.createBond();


            }
        }
    };
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action)) {
                int scanMode = intent.getIntExtra(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED, 0);
                if (scanMode == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                    progressBar.setVisibility(View.VISIBLE);


                } else {
                    progressBar.setVisibility(View.GONE);


                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initUI();

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);

        registerReceiver(mReceiver, filter);
        mController.turnOnBluetooth(this, REQUEST_CODE);
    }

    private void initUI() {
        mListView = (ListView) findViewById(R.id.device_list);
        mAdapter = new DeviceAdapter(mDeviceList, this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public void initToolbar() {
        progressBar = (ProgressBar) findViewById(R.id.pd);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.manu_main);
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);


    }


    private void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }
}
