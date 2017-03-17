package com.example.lxd.bluetoothfindaddbond;

import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * 蓝牙控制器
 */

public class BlueToothContriller {
    private BluetoothAdapter mAdapter;

    public BlueToothContriller() {
        this.mAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public boolean isSupportBlueTooth() {
        if (mAdapter != null) {
            return true;
        } else {
            return false;
        }


    }

    public boolean getBluetoothStatus() {

        return mAdapter != null ? mAdapter.isEnabled() : false;

    }

    public void turnOnBluetooth(Activity activity, int requestCode) {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(intent, requestCode);
        //  mAdapter.enable();

    }

    public void enableVisibly(Context context) {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        context.startActivity(discoverableIntent);
    }

    public void findDevice() {
        if (mAdapter != null) {
            mAdapter.startDiscovery();


        }
    }

    public List<BluetoothDevice> getBondedDeviceList(){
        return new ArrayList<>(mAdapter.getBondedDevices());


    }

    public void turnOffBluetooth() {
        mAdapter.disable();


    }
}
