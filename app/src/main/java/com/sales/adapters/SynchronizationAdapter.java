package com.sales.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sales.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SynchronizationAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Context context;
    private ArrayList<HashMap<String, String>> data;

    public SynchronizationAdapter(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.row_sync, null);
        }

        TextView className = (TextView) convertView.findViewById(R.id.tx_class_name_sync);
        TextView dateHourStart= (TextView) convertView.findViewById(R.id.tx_date_start);
        TextView dateHourEnd = (TextView) convertView.findViewById(R.id.tx_date_end);
        TextView correctRegisters = (TextView) convertView.findViewById(R.id.tx_number_fields_correct);
        TextView failureRegisters = (TextView) convertView.findViewById(R.id.tx_number_fields_failure);
        ImageView checkbox = (ImageView) convertView.findViewById(R.id.ch_state_sync);

        final HashMap<String, String> map = data.get(position);


        className.setText(map.get("class_name"));
        dateHourStart.setText(map.get("date_hour_start"));
        dateHourEnd.setText(map.get("date_hour_end"));
        correctRegisters.setText(map.get("registers"));
        failureRegisters.setText(map.get("failures"));

        if(map.get("state").equals("1")){
            checkbox.setImageResource(R.drawable.ic_check_box_yes);
        }
        else{
            checkbox.setImageResource(R.drawable.ic_check_box_non);
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
