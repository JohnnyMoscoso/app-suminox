package com.sales.views.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sales.R;
import com.sales.adapters.GeneralSpinnerAdapter;
import com.sales.adapters.IndicatorAdapter;
import com.sales.config.ConstValue;
import com.sales.db.SqliteClass;
import com.sales.models.CustomerClass;
import com.sales.models.ItemClass;
import com.sales.models.SynOrderClass;
import com.sales.utils.Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.ToIntBiFunction;

public class IndicatorFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{

    public View rootView;
    public Common common;
    Activity activity;
    ListView itemListView;
    Context context;
    TextView emptyTextView;

    List<SynOrderClass> itemList;
    List<ItemClass> _itemList;

    Spinner sp_indicator_type;
    public ArrayList<String> indicatorTypes;
    static ArrayList<HashMap<String, String>> data;
    private static final String FRAGMENT_NAME = "IndicatorFragment";


    public IndicatorFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        indicatorTypes = new ArrayList<String>();
        indicatorTypes.add("CAMBIOS"); indicatorTypes.add("DEVOLUCIONES");
        indicatorTypes.add("INVENTARIO"); indicatorTypes.add("TOTAL VENTAS");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_indicator, container, false);
        data = new ArrayList<HashMap<String,String>>();
        common = new Common();
        activity = getActivity();
        context = getActivity();
        emptyTextView = (TextView) rootView.findViewById(android.R.id.empty);
        itemListView = (ListView) rootView.findViewById(R.id.list_indicators);


        sp_indicator_type = (Spinner) rootView.findViewById(R.id.sp_indicator_type);
        sp_indicator_type.getBackground().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        sp_indicator_type.setAdapter(new GeneralSpinnerAdapter(context, R.layout.spinner_row, indicatorTypes, FRAGMENT_NAME));

        sp_indicator_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> spn, android.view.View v, int position, long id) {
                switch (position){
                    case 0: {//CAMBIOS mostrar cambios de producto
                        ConstValue.setInventarySearch("0");
                        itemList = SqliteClass.getInstance(context).databasehelp.synordersql.getSynOrderData("C");
                        getList();
                        if (itemList.size() > 0) emptyTextView.setVisibility(View.GONE);else emptyTextView.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 1: { //Devoluciones mostrar devolucions
                        ConstValue.setInventarySearch("0");
                        itemList = SqliteClass.getInstance(context).databasehelp.synordersql.getSynOrderData("R");
                        getList();
                        if (itemList.size() > 0) emptyTextView.setVisibility(View.GONE);else emptyTextView.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 2: {//INVENTARIO DEL CAMION mostrar productos con el stock
                        ConstValue.setInventarySearch("1");
                        _itemList = SqliteClass.getInstance(context).databasehelp.pricelistsql.getItemsByPriceList(ConstValue.getDefaultPriceListCode());
                        getListInventary();
                        if (_itemList.size() > 0) emptyTextView.setVisibility(View.GONE);else emptyTextView.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 3: { //Total ventas >> mostrar facturas y boletas de venta directa
                        ConstValue.setInventarySearch("0");
                        itemList = SqliteClass.getInstance(context).databasehelp.synordersql.getSynOrderDataSales();
                        getList();
                        if (itemList.size() > 0) emptyTextView.setVisibility(View.GONE);else emptyTextView.setVisibility(View.VISIBLE);
                        break;
                    }
                    default:
                        break;
                }
            }
            public void onNothingSelected(AdapterView<?> spn) {
                return;
            }
        });


        return rootView;
    }
    public void getList() {
        data = new ArrayList<HashMap<String,String>>();
        for(int i = 0; i < itemList.size(); i++){
            SynOrderClass cc = itemList.get(i);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", cc.getId()+"");
            map.put("dateHour", cc.getDateHour());
            map.put("totalPay", cc.getTotalPay());
            map.put("type", cc.getType());
            map.put("numberDocument", cc.getNumberDocument());
            map.put("load", cc.getLoad());
            data.add(map);
        }
        IndicatorAdapter adapter = new IndicatorAdapter(activity, data);
        itemListView.setAdapter(adapter);
    }

    public void getListInventary(){
        data = new ArrayList<HashMap<String,String>>();
        for(int i = 0; i < _itemList.size(); i++){
            ItemClass cc = _itemList.get(i);
            final String stockLeft = SqliteClass.getInstance(context).databasehelp.stocksql.getStockLeft(cc.getCode());
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", cc.getId()+"");
            map.put("codeItem", cc.getCode());
            map.put("nameItem", cc.getDescription());
            map.put("stock", stockLeft);
            map.put("categoryName", cc.getCategoryName());
            map.put("ltsLbs", cc.getLitersLbs());
            data.add(map);
        }
        InventaryAdapter adapter = new InventaryAdapter(activity, data);
        itemListView.setAdapter(adapter);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(ConstValue.getInventarySearch().equals("1")){
            if (newText == null || newText.trim().isEmpty()) {
                resetSearch();
                return false;
            }
            List<ItemClass> filteredValues = new ArrayList<ItemClass>(_itemList);
            for (ItemClass value : _itemList) {
                if (!value.getDescription().toLowerCase().contains(newText.toLowerCase())) {
                    filteredValues.remove(value);
                }
            }
            data = new ArrayList<HashMap<String, String>>();
            for(int z=0; z< filteredValues.size(); z++){
                ItemClass cc = filteredValues.get(z);
                HashMap<String, String> map = new HashMap<String, String>();
                final String stockLeft = SqliteClass.getInstance(context).databasehelp.stocksql.getStockLeft(cc.getCode());
                map.put("id", cc.getId()+"");
                map.put("codeItem", cc.getCode());
                map.put("nameItem", cc.getDescription());
                map.put("stock", stockLeft);
                map.put("categoryName", cc.getCategoryName());
                map.put("ltsLbs", cc.getLitersLbs());
                data.add(map);
            }
            InventaryAdapter adapter = new InventaryAdapter(activity, data);
            itemListView.setAdapter(adapter);
        }
        return false;
    }

    public void resetSearch() {

        data = new ArrayList<HashMap<String, String>>();
        for(int z=0; z< _itemList.size(); z++){
            ItemClass cc = _itemList.get(z);
            HashMap<String, String> map = new HashMap<String, String>();
            final String stockLeft = SqliteClass.getInstance(context).databasehelp.stocksql.getStockLeft(cc.getCode());
            map.put("id", cc.getId()+"");
            map.put("codeItem", cc.getCode());
            map.put("nameItem", cc.getDescription());
            map.put("stock", stockLeft);
            map.put("categoryName", cc.getCategoryName());
            map.put("ltsLbs", cc.getLitersLbs());
            data.add(map);
        }
        InventaryAdapter adapter = new InventaryAdapter(activity, data);
        itemListView.setAdapter(adapter);


    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }


    @Override
    public void onDetach() { super.onDetach(); }


    public class InventaryAdapter extends BaseAdapter {

        private LayoutInflater inflater = null;
        private Context context;
        private ArrayList<HashMap<String, String>> data;
        ImageView priority;



        public InventaryAdapter(Context context, ArrayList<HashMap<String, String>> data) {
            this.context = context;
            this.data = data;
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.row_inventary, null);
                priority = (ImageView) convertView.findViewById(R.id.ic_priority);

            }
            final HashMap<String, String> map = data.get(position);

            TextView nameItem = (TextView) convertView.findViewById(R.id.tx_item_name_input);
            nameItem.setText(map.get("nameItem"));
            TextView codeItem = (TextView) convertView.findViewById(R.id.tx_input_code);
            codeItem.setText(map.get("codeItem"));
            TextView weightItem = (TextView) convertView.findViewById(R.id.tx_input_weight);
            weightItem.setText("LTS./LBS. " + map.get("ltsLbs"));
            TextView stockItem = (TextView) convertView.findViewById(R.id.tx_stock_input);
            stockItem.setText("Stock: " + map.get("stock"));

            priority = (ImageView) convertView.findViewById(R.id.ic_priority);
            final int stockLeft = Integer.parseInt(map.get("stock"));
            if(stockLeft > 0 && stockLeft <= 10){
                priority.setImageResource(R.drawable.ic_priority_yellow);
            }
            else if(stockLeft == 0){
                priority.setImageResource(R.drawable.ic_priority_red);
            }
            else{
                priority.setImageResource(R.drawable.ic_check_box_yes);
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(stockLeft > 0 && stockLeft <= 10){
                        Toast.makeText(context, "Stock de Producto bajo. Se recomienda hacer un Pedido de Carga", Toast.LENGTH_SHORT).show();
                    }
                    else if(stockLeft == 0){
                        Toast.makeText(context, "Stock de Producto Agotado.", Toast.LENGTH_SHORT).show();
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

}

