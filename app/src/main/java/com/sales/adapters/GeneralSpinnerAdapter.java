package com.sales.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sales.R;

import java.util.ArrayList;


public class GeneralSpinnerAdapter extends ArrayAdapter<String> {


    private static LayoutInflater inflater = null;
    private Context context;
    private ArrayList<String> data;
    private String className;


    public GeneralSpinnerAdapter(Context context, int textViewResourceId, ArrayList<String> objects, String className) {
        // TODO Auto-generated constructor stub
        super(context, textViewResourceId, objects);
        this.context = context;
        this.data = objects;
        this.className = className;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spinner_row, null);
        }

        TextView label = (TextView) convertView.findViewById(R.id.sp_type);
        label.setText(data.get(position));
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);

        if(className.equals("CustomerFragment")){
            if (data.get(position).equals("RUTA")){
                icon.setImageResource(R.drawable.ic_route_white);
            }
            else if(data.get(position).equals("VISITADOS")){
                icon.setImageResource(R.drawable.ic_visited_white);
            }
            else if(data.get(position).equals("EXTRA RUTA")){
                icon.setImageResource(R.drawable.ic_extra_route_white);
            }
        }
        else if(className.equals("IndicatorFragment")){
            if (data.get(position).equals("TOTAL VENTAS")){
                icon.setImageResource(R.drawable.ic_total_sells_white);
            }
            else if(data.get(position).equals("INVENTARIO")){
                icon.setImageResource(R.drawable.ic_inventary_white);
            }
            else if(data.get(position).equals("CAMBIOS")){
                icon.setImageResource(R.drawable.ic_change_white);
            }
            else if(data.get(position).equals("DEVOLUCIONES")){
                icon.setImageResource(R.drawable.ic_return_white);
            }
        }
        else if(className.equals("DocumentFragment")){
            if (data.get(position).equals("FACTURAS")){
                icon.setImageResource(R.drawable.ic_facture_white);
            }
            else if(data.get(position).equals("BOLETAS")){
                icon.setImageResource(R.drawable.ic_customer_order_white);
            }
            else if(data.get(position).equals("P. VENDEDOR")){
                icon.setImageResource(R.drawable.ic_load_order_seller_white);
            }
            else if(data.get(position).equals("P. CLIENTE")){
                icon.setImageResource(R.drawable.ic_load_order_customer_white);
            }
        }
        else if(className.equals("SyncFragment")){
            if (data.get(position).equals("SINCRONIZADAS")){
                icon.setImageResource(R.drawable.ic_already_sync);
            }
            else if(data.get(position).equals("SINCRONIZAR")){
                icon.setImageResource(R.drawable.ic_still_sync_white);
            }
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spinner_row, null);
        }


        TextView label = (TextView) convertView.findViewById(R.id.sp_type);
        label.setText(data.get(position));
        label.setTextColor(Color.BLACK);
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);

        if(className.equals("CustomerFragment")){
            if (data.get(position).equals("RUTA")){
                icon.setImageResource(R.drawable.ic_route_black);
            }
            else if(data.get(position).equals("VISITADOS")){
                icon.setImageResource(R.drawable.ic_visited_black);
            }
            else if(data.get(position).equals("EXTRA RUTA")){
                icon.setImageResource(R.drawable.ic_extra_route_black);
            }
        }
        else if(className.equals("IndicatorFragment")){
            if (data.get(position).equals("TOTAL VENTAS")){
                icon.setImageResource(R.drawable.ic_total_sells_black);
            }
            else if(data.get(position).equals("INVENTARIO")){
                icon.setImageResource(R.drawable.ic_inventary_black);
            }
            else if(data.get(position).equals("CAMBIOS")){
                icon.setImageResource(R.drawable.ic_change_black);
            }
            else if(data.get(position).equals("DEVOLUCIONES")){
                icon.setImageResource(R.drawable.ic_return_black);
            }
        }
        else if(className.equals("DocumentFragment")){
            if (data.get(position).equals("FACTURAS")){
                icon.setImageResource(R.drawable.ic_facture_black);
            }
            else if(data.get(position).equals("BOLETAS")){
                icon.setImageResource(R.drawable.ic_customer_order_black);
            }
            else if(data.get(position).equals("P. VENDEDOR")){
                icon.setImageResource(R.drawable.ic_load_order_seller_black);
            }
            else if(data.get(position).equals("P. CLIENTE")){
                icon.setImageResource(R.drawable.ic_load_order_customer_black);
            }
        }
        else if(className.equals("SyncFragment")){
            if (data.get(position).equals("SINCRONIZADAS")){
                icon.setImageResource(R.drawable.ic_already_sync_black);
            }
            else if(data.get(position).equals("SINCRONIZAR")){
                icon.setImageResource(R.drawable.ic_still_sync_black);
            }
        }


        return convertView;
    }


}