package com.sales.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sales.R;
import com.sales.config.ConstValue;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class DocumentAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Context context;
    private ArrayList<HashMap<String, String>> data;


    public DocumentAdapter(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.row_print_document, null);
        }

        ImageView syncedStatus = (ImageView) convertView.findViewById(R.id.ic_synced_status);
        TextView numberDocument = (TextView) convertView.findViewById(R.id.tx_number_document);
        TextView totalPay = (TextView) convertView.findViewById(R.id.tx_total_pay);
        TextView date = (TextView) convertView.findViewById(R.id.tx_date_input);
        TextView syncStatus = (TextView) convertView.findViewById(R.id.tx_synced_status);

        final HashMap<String, String> map = data.get(position);
        numberDocument.setText(map.get("numberDocument"));
        totalPay.setText(ConstValue.getCurrency() + " " + map.get("total"));

        if(map.get("load").equals("1")){
            syncedStatus.setImageResource(R.drawable.ic_synced_document);
            syncStatus.setText("SINCRONIZADO");
        }
        else{
            syncedStatus.setImageResource(R.drawable.ic_synced_non_document);
            syncStatus.setText("NO SINCRONIZADO");
        }

        date.setText(map.get("date_hour"));

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
