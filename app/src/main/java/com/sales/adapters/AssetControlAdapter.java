package com.sales.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sales.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AssetControlAdapter extends BaseAdapter {


    private static LayoutInflater inflater = null;
    private Context context;
    private ArrayList<HashMap<String, String>> data;
    TextView code, dateInit, dateEnd, nameEquipment;
    LinearLayout linearLayout;


    public AssetControlAdapter(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.row_asset_control, null);
        }

        code = (TextView) convertView.findViewById(R.id.tx_input_code_asset_control);
        dateInit = (TextView) convertView.findViewById(R.id.tx_input_date_init);
        dateEnd = (TextView) convertView.findViewById(R.id.tx_input_date_end);
        nameEquipment = (TextView) convertView.findViewById(R.id.tx_equipment_name);
        linearLayout = (LinearLayout) convertView.findViewById(R.id.layout_state);

        final HashMap<String, String> map = data.get(position);
        code.setText(map.get("codeAssetControl"));
        dateInit.setText(map.get("dateInit"));
        dateEnd.setText(map.get("dateEnd"));
        nameEquipment.setText(map.get("equipmentAssetControl"));

        if(map.get("state").equals(null) || map.get("state").equals("null")){
            linearLayout.setBackgroundResource(R.color.colorGray); // Sin observacion por realizar
            dateInit.setVisibility(View.GONE); dateEnd.setVisibility(View.GONE);
        }
        else if(map.get("state").equals("1")){
            linearLayout.setBackgroundResource(R.color.colorBlueSpecial); //Con observacion por hacer
            dateInit.setVisibility(View.VISIBLE); dateEnd.setVisibility(View.VISIBLE);
        }
        else{
            linearLayout.setBackgroundResource(R.color.colorPrimary); //Si es diferente ya esta hecha
            dateInit.setVisibility(View.VISIBLE); dateEnd.setVisibility(View.VISIBLE);
        }

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
