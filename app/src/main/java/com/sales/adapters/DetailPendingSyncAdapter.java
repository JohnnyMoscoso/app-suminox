package com.sales.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.sales.R;
import com.sales.config.ConstValue;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailPendingSyncAdapter extends BaseAdapter {


    private static LayoutInflater inflater = null;
    private Context context;
    private ArrayList<HashMap<String, String>> data;
    private String className;

    private static final String VISIT_CLASS = "Visitas";
    private static final String ASSET_CONTROL_CLASS = "Control de Activos";
    private static final String SYN_ORDER_CLASS_SELLER = "Pedido de Carga";
    private static final String SYN_ORDER_CLASS_CUSTOMER = "Pedido de Cliente";
    private static final String SYN_ORDER_CLASS_FAC = "Factura";
    private static final String SYN_ORDER_CLASS_BOL = "Boleta";
    private static final String CHANGE_ITEM = "Cambio de Producto";
    private static final String RETURN_ITEM = "Devolucion de Producto";
    private static final String STOCK_BREAK = "Quiebres de Inventario";
    /** Should be more ... */




    public DetailPendingSyncAdapter(Context context, ArrayList<HashMap<String, String>> data, String className) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.className = className;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            if(className.equals(VISIT_CLASS)){
                convertView = mInflater.inflate(R.layout.row_visit_pending_sync, null);
            }
            else if(className.equals(ASSET_CONTROL_CLASS)){
                convertView = mInflater.inflate(R.layout.row_asset_pending_sync, null);
            }
            else if(className.equals(SYN_ORDER_CLASS_SELLER) || className.equals(SYN_ORDER_CLASS_CUSTOMER) ||
                    className.equals(SYN_ORDER_CLASS_FAC) || className.equals(SYN_ORDER_CLASS_BOL) || className.equals(CHANGE_ITEM) ||
                    className.equals(RETURN_ITEM)){
                convertView = mInflater.inflate(R.layout.row_order_general, null);
            }
            else if(className.equals(STOCK_BREAK)){
                convertView = mInflater.inflate(R.layout.row_stock_break, null);
            }
            /** The rest of classes for this adapter ... */
        }


        if(className.equals(VISIT_CLASS)){
            TextView inputVisitNumber = (TextView) convertView.findViewById(R.id.tx_visit_number);
            TextView inputDate = (TextView) convertView.findViewById(R.id.tx_date_start_input);
            TextView inputDescription = (TextView) convertView.findViewById(R.id.tx_description_input);

            final HashMap<String, String> map = data.get(position);

            inputVisitNumber.setText(map.get("customerName"));
            inputDate.setText(map.get("dateHour"));
            inputDescription.setText(map.get("description"));
            return convertView;
        }
        else if(className.equals(ASSET_CONTROL_CLASS)){
            TextView inputDate = (TextView) convertView.findViewById(R.id.tx_date_start_input);
            TextView equipment = (TextView) convertView.findViewById(R.id.tx_equipment_input);
            TextView answer = (TextView) convertView.findViewById(R.id.tx_answer_input);

            final HashMap<String, String> map = data.get(position);

            inputDate.setText(map.get("dateDone"));
            equipment.setText(map.get("equipment"));

            if(map.get("observation").equals("True")){
                TableRow row = (TableRow) convertView.findViewById(R.id.row_observation);
                TableRow equipmentRow = (TableRow) convertView.findViewById(R.id.row_equipment);

                equipmentRow.setVisibility(View.GONE);
                row.setVisibility(View.VISIBLE);
                answer.setText(map.get("answer"));
            }
            return convertView;

        }
        else if(className.equals(SYN_ORDER_CLASS_SELLER)){
            TextView numberDocument= (TextView) convertView.findViewById(R.id.tx_number_document);
            TextView date = (TextView) convertView.findViewById(R.id.tx_date_start_input);
            TextView total = (TextView) convertView.findViewById(R.id.tx_description_input);

            HashMap<String, String> map = data.get(position);
            numberDocument.setText(map.get("numberDocument"));
            date.setText("Realizado: " + map.get("dateHour"));
            total.setText("Total: " + ConstValue.getCurrency() + " " + map.get("total"));
            return convertView;

        }
        else if(className.equals(SYN_ORDER_CLASS_CUSTOMER) || className.equals(SYN_ORDER_CLASS_FAC) ||
                className.equals(SYN_ORDER_CLASS_BOL) || className.equals(CHANGE_ITEM) || className.equals(RETURN_ITEM)){
            TableRow rowCustomer = (TableRow) convertView.findViewById(R.id.row_customer);
            rowCustomer.setVisibility(View.VISIBLE);
            TextView numberDocument= (TextView) convertView.findViewById(R.id.tx_number_document);
            TextView date = (TextView) convertView.findViewById(R.id.tx_date_start_input);
            TextView total = (TextView) convertView.findViewById(R.id.tx_description_input);
            TextView customer = (TextView) convertView.findViewById(R.id.tx_customer_input);

            HashMap<String, String> map = data.get(position);
            numberDocument.setText(map.get("numberDocument"));
            date.setText(map.get("dateHour"));
            total.setText(ConstValue.getCurrency() + " " + map.get("total"));
            customer.setText(map.get("customerName"));
            return convertView;
        }
        else if(className.equals(STOCK_BREAK)){
            TextView codeStockBreak = (TextView) convertView.findViewById(R.id.tx_code_stock_break);
            TextView dateHour = (TextView) convertView.findViewById(R.id.tx_date_start_input);
            TextView codeItem = (TextView) convertView.findViewById(R.id.tx_item_code_input);
            TextView nameItem = (TextView) convertView.findViewById(R.id.tx_item_name_input);

            HashMap<String, String> map = data.get(position);
            codeStockBreak.setText(map.get("code"));
            dateHour.setText(map.get("dateHour"));
            codeItem.setText(map.get("codeItem"));
            nameItem.setText(map.get("nameItem"));
            return convertView;

        }
        /** The rest of classes for this adapter ... */

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
