package com.sales.views.activities;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import com.sales.R;
import com.sales.config.ConstValue;
import com.sales.db.SqliteClass;
import com.sales.models.SynOrderClass;
import com.sales.models.SynOrderItemClass;
import com.sales.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;

public class DocumentDetailActivity extends AppCompatActivity {

    SynOrderClass synOrderClass;
    Context context;

    TableRow rowCustomer;
    TextView documentType, numberDocument, dateHour, customerName;

    ListView itemListView;
    ArrayList<SynOrderItemClass> itemList;
    ArrayList<HashMap<String,String>> data;
    BluetoothAdapter bAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_detail);

        getSupportActionBar().setTitle("CloudSales");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        synOrderClass = SqliteClass.getInstance(context).databasehelp.synordersql.getSynOrder(ConstValue.getNumberDocumentSynOrder());

        rowCustomer = (TableRow) findViewById(R.id.row_customer);
        documentType = (TextView) findViewById(R.id.tx_doc_type);

        itemList = SqliteClass.getInstance(context).databasehelp.synorderitemsql.getSynOrderItemData(synOrderClass.getNumberDocument());
        itemListView = (ListView) findViewById(R.id.ls_item_detail);

        getList();

        numberDocument = (TextView) findViewById(R.id.tx_number_document);
        numberDocument.setText("Documento: " + synOrderClass.getNumberDocument());

        dateHour = (TextView) findViewById(R.id.tx_date_hour_doc);
        dateHour.setText("Realizado: " + synOrderClass.getDateHour());

        customerName = (TextView) findViewById(R.id.tx_customer_name);

        if(synOrderClass.getType().equals("PC")){ documentType.setText("Pedido "); }
        else if(synOrderClass.getType().equals("FAC")){ documentType.setText("Factura "); }
        else if(synOrderClass.getType().equals("BOL")){ documentType.setText("Boleta "); }
        else if(synOrderClass.getType().equals("PV")){ documentType.setText("Pedido de Carga "); }

        if(synOrderClass.getLoad().equals("1")){ documentType.setText(documentType.getText() + "(sincronizado)"); }
        else{ documentType.setText(documentType.getText() + "(falta sincronizar)");}

        if(!synOrderClass.getType().equals("PV")){
            rowCustomer.setVisibility(View.VISIBLE);
            customerName.setText("Cliente: " + synOrderClass.getNameCustomer());
        }
    }

    public void getList() {
        data = new ArrayList<HashMap<String,String>>();

        for(int i=0; i < itemList.size(); i++){
            SynOrderItemClass cc = itemList.get(i);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("description", cc.getDescriptionItem());
            map.put("price", cc.getPrice());
            map.put("subtotal", cc.getTotalPay());
            map.put("quantity", cc.getQuantity());
            data.add(map);
        }

        ItemAdapter adapter = new ItemAdapter(this, data);
        itemListView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_print, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        else if(id==R.id.action_logout){
            Util.logout(this, context);
        } else if (id == R.id.action_print) {

            if(synOrderClass.getType().equals("FAC") || synOrderClass.getType().equals("BOL")){
                bAdapter = BluetoothAdapter.getDefaultAdapter();
                if(bAdapter.isEnabled()){
                    ConstValue.setPrintStatus("RePrint");
                    ConstValue.clearSynOrderItemList();
                    ConstValue.addSynOrderItemList(SqliteClass.getInstance(context).databasehelp.synorderitemsql.getSynOrderItemData(synOrderClass.getNumberDocument()));

                    ConstValue.setExemptAmount(synOrderClass.getOperationExonerated());
                    ConstValue.setTaxableAmount(synOrderClass.getOperationTaxed());
                    ConstValue.setGravAmount(synOrderClass.getIgv());
                    ConstValue.setTotal(synOrderClass.getTotalPay());

                    Intent intent = new Intent(DocumentDetailActivity.this, PrintActivity.class);
                    startActivity(intent);
                }
                else{
                    Util.infoDialog(this, context, "CloudSales", "Bluetooth apagado. Por favor enciéndalo para poder proceder con la impresión.");
                }

            }
            else{
                Util.infoDialog(this, context, "CloudSales", "Impresión de Documentos solo habilitada para Boleta o Factura en Venta Directa.");
            }


        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Util.logout(this, context);
    }

    public class ItemAdapter extends BaseAdapter {

        private LayoutInflater inflater = null;
        private Context context;
        private ArrayList<HashMap<String, String>> data;


        TextView itemDescription, unit, inputValue, subtotal, inputSubtotal;
        EditText inputQuantity;

        public ItemAdapter(Context context, ArrayList<HashMap<String, String>> data) {
            this.context = context;
            this.data = data;
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.row_detail_document, null);
            }

            final HashMap<String, String> map = data.get(position);

            itemDescription = (TextView) convertView.findViewById(R.id.tx_item_description);
            itemDescription.setText(map.get("description"));

            unit = (TextView) convertView.findViewById(R.id.tx_unit);
            unit.setText(ConstValue.getCurrency());

            inputValue = (TextView) convertView.findViewById(R.id.tx_input_value);
            inputValue.setText(map.get("price"));

            subtotal = (TextView) convertView.findViewById(R.id.tx_subtotal);
            subtotal.setText("SUB TOTAL: " + ConstValue.getCurrency());

            inputSubtotal = (TextView) convertView.findViewById(R.id.tx_input_subtotal);
            inputSubtotal.setText(map.get("subtotal"));

            inputQuantity = (EditText) convertView.findViewById(R.id.tx_input_quantity);
            inputQuantity.setText(map.get("quantity"));

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

}
