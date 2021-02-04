package com.sales.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sales.R;

import java.util.ArrayList;
import java.util.HashMap;

public class IndicatorAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Context context;
    private ArrayList<HashMap<String, String>> data;


    public IndicatorAdapter(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.row_indicator_sales, null);
        }

        final HashMap<String, String> map = data.get(position);

        final TextView typeDocument = (TextView) convertView.findViewById(R.id.tx_type_document);
        final TextView typeDocumentLabel = (TextView) convertView.findViewById(R.id.tx_fac_bol_input);

        if(map.get("type").equals("R")){ typeDocument.setText("D"); }
        if(map.get("type").equals("BOL") || map.get("type").equals("FAC")){
            typeDocument.setText("VD");
            if(map.get("type").equals("BOL")){ typeDocumentLabel.setText("BOLETA"); }
            else { typeDocumentLabel.setText("FACTURA"); }
        }
        else{
            typeDocumentLabel.setVisibility(View.GONE);
        }

        TextView numberDocument = (TextView) convertView.findViewById(R.id.tx_number_document);
        numberDocument.setText(map.get("numberDocument"));


        TextView dateHour = (TextView) convertView.findViewById(R.id.tx_input_date);
        dateHour.setText(map.get("dateHour"));
        TextView total = (TextView) convertView.findViewById(R.id.tx_input_total);
        total.setText(map.get("totalPay"));
        LinearLayout stateSync = (LinearLayout) convertView.findViewById(R.id.ly_state_sync);
        if(map.get("load").equals("0")){ stateSync.setBackgroundResource(R.color.colorGray); }
        else {stateSync.setBackgroundResource(R.color.colorAccent); }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(map.get("load").equals("0")){
                    Toast.makeText(context, typeDocumentLabel.getText().toString() + " no sincronizada. \n" +
                            "Ir a Pendientes de Sincronización para proceder.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
