package com.sales.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sales.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerAdapter extends BaseAdapter {


    private static LayoutInflater inflater = null;
    private Context context;
    private ArrayList<HashMap<String, String>> data;


    public CustomerAdapter(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.row_customer, null);
        }


        TextView businessName = (TextView) convertView.findViewById(R.id.tx_business_name);
        TextView code = (TextView) convertView.findViewById(R.id.tx_code);
        TextView phone = (TextView) convertView.findViewById(R.id.tx_phone);
        TextView representative = (TextView) convertView.findViewById(R.id.tx_representative);

        final HashMap<String, String> map = data.get(position);

        businessName.setText(map.get("bussiness"));
        code.setText(map.get("code"));
        phone.setText(map.get("phone"));
        representative.setText(map.get("representative"));
        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
