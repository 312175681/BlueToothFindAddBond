package com.example.lxd.bluetoothfindaddbond;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lxd on 2017/3/16.
 */

public class DeviceAdapter extends BaseAdapter {
    private List<BluetoothDevice> mData;
    private Context mContext;

   public  DeviceAdapter (List<BluetoothDevice> data,Context context){
       this.mData=data;
       this.mContext=context.getApplicationContext();



   }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
View itemView =convertView;
        if(itemView==null){
            itemView= LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_2,parent,false);
        }
        TextView line1=(TextView)itemView.findViewById(android.R.id.text1);
        TextView line2=(TextView)itemView.findViewById(android.R.id.text2);

       BluetoothDevice device=(BluetoothDevice) getItem(position);
       line1.setText(device.getName());
        line2.setText(device.getAddress());
        return itemView;
    }

    public void refresh (List<BluetoothDevice> data){
        mData=data;
        notifyDataSetChanged();

    }

}
