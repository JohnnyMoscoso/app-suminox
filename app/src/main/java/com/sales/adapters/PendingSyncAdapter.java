package com.sales.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sales.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PendingSyncAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Context context;
    private ArrayList<HashMap<String, String>> data;

    private static String VISITS = "Visitas";
    private static String ASSET_CONTROL = "Control de Activos";
    private static String ORDER_SELLER = "Pedido de Carga";
    private static String ORDER_CUSTOMER = "Pedido de Cliente";
    private static String FACTURES = "Facturas";
    private static String VOUCHERS = "Boletas";
    private static String CHANGE_ITEM = "Cambio de Producto";
    private static String RETURN_ITEM = "Devolución de Producto";
    private static String STOCK_BREAK = "Quiebres de Inventario";

    public PendingSyncAdapter(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.row_pending, null);
        }

        TextView quantity = (TextView) convertView.findViewById(R.id.tx_quantity);
        TextView quantityDone = (TextView) convertView.findViewById(R.id.tx_quantity_done);
        TextView className = (TextView) convertView.findViewById(R.id.tx_class_name_sync);
        TextView nonSynced = (TextView) convertView.findViewById(R.id.tx_non_synced);
        TextView synced = (TextView) convertView.findViewById(R.id.tx_synced_done);
        ImageView iconResource = (ImageView) convertView.findViewById(R.id.ic_icon_resource);

        final HashMap<String, String> map = data.get(position);
        quantity.setText(map.get("number"));
        quantityDone.setText(map.get("numberDone"));

        if(Integer.parseInt(map.get("number")) > 0){
            quantity.setBackgroundResource(R.drawable.rounded_textview);
            nonSynced.setTextColor(Color.parseColor("#EF4E56"));
        }
        else{ quantity.setBackgroundResource(R.drawable.rounded_textview_empty); }

        if(Integer.parseInt(map.get("numberDone")) > 0){
            quantityDone.setBackgroundResource(R.drawable.rounded_textview_done);
            synced.setTextColor(Color.parseColor("#ff99cc00"));
        }
        else{ quantityDone.setBackgroundResource(R.drawable.rounded_textview_empty); }

        className.setText(map.get("className"));


        if(map.get("className").equals(VISITS)){ iconResource.setImageResource(R.drawable.visit_orange);}
        else if(map.get("className").equals(ASSET_CONTROL)){ iconResource.setImageResource(R.drawable.asset_orange);}
        else if(map.get("className").equals(ORDER_SELLER)){iconResource.setImageResource(R.drawable.ic_order_orange);}
        else if(map.get("className").equals(ORDER_CUSTOMER)){iconResource.setImageResource(R.drawable.order_customer_orange);}
        else if(map.get("className").equals(FACTURES)){ iconResource.setImageResource(R.drawable.ic_facture_orange);}
        else if(map.get("className").equals(VOUCHERS)){ iconResource.setImageResource(R.drawable.ic_voucher_orange);}
        else if(map.get("className").equals(CHANGE_ITEM)){ iconResource.setImageResource(R.drawable.change_item_orange);}
        else if(map.get("className").equals(RETURN_ITEM)){ iconResource.setImageResource(R.drawable.return_item_orange);}
        else if(map.get("className").equals(STOCK_BREAK)){ iconResource.setImageResource(R.drawable.ic_stock_break_orange);}


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
