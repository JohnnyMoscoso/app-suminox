package com.sales.views.fragments;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.sales.R;
import com.sales.adapters.CustomerAdapter;
import com.sales.adapters.GeneralSpinnerAdapter;
import com.sales.config.ConstValue;
import com.sales.db.SqliteClass;
import com.sales.models.CustomerClass;
import com.sales.utils.Common;
import com.sales.utils.Util;
import com.sales.views.activities.DashboardActivity;
import com.sales.views.activities.LoginActivity;
import com.sales.views.activities.MainActivity;

public class CustomerFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    View rootView;
    Activity activity;
    ListView itemListView;
    CustomerAdapter adapter;
    Context context;
    static ArrayList<HashMap<String, String>> data;
    ArrayList<String> burnData;

    ProgressDialog dialog;
    Common common;

    List<CustomerClass> itemList;

    private CustomerClass Customer;
    private ArrayList<CustomerClass> loadCustomer;
    public ArrayList<String> customerList;
    Spinner sp_customer_type;
    TextView totalCustomers, totalVisited;

    private static final String FRAGMENT_NAME = "CustomerFragment";
    SimpleDateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());


    public CustomerFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getActivity();
        customerList = new ArrayList<String>();
        customerList.add("RUTA"); customerList.add("VISITADOS"); customerList.add("EXTRA RUTA");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_customer, container, false);
        data = new ArrayList<HashMap<String,String>>();
        common = new Common();
        activity = getActivity();
        itemListView = (ListView) rootView.findViewById(R.id.lista_nueva);
        itemList = SqliteClass.getInstance(context).databasehelp.customersql.getCustomerData();
        final TextView emptyTextView = (TextView) rootView.findViewById(android.R.id.empty);

        totalCustomers = (TextView) rootView.findViewById(R.id.total_this_day);
        totalCustomers.setText(String.valueOf(SqliteClass.getInstance(context).databasehelp.customersql.countCustomers()));
        totalVisited = (TextView) rootView.findViewById(R.id.total_visited_day);
        totalVisited.setText(String.valueOf(SqliteClass.getInstance(context).databasehelp.customersql.countCustomersVisited()));

        sp_customer_type = (Spinner) rootView.findViewById(R.id.sp_customer_type);

        sp_customer_type.getBackground().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        sp_customer_type.setAdapter(new GeneralSpinnerAdapter(context, R.layout.spinner_row, customerList, FRAGMENT_NAME));

        sp_customer_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> spn, android.view.View v, int position, long id) {

                String today = Util.getDayOfTheWeek();
                switch (position){
                    case 0: //RUTA

                        itemList = SqliteClass.getInstance(context).databasehelp.customersql.getCustomerDataByDay(today);
                        getList();
                        if(itemList.size()>0) emptyTextView.setVisibility(View.GONE); else emptyTextView.setVisibility(View.VISIBLE);

                        break;
                    case 1: //VISITADOS

                        itemList = SqliteClass.getInstance(context).databasehelp.customersql.getCustomerVisited();
                        getList();
                        if(itemList.size()>0) emptyTextView.setVisibility(View.GONE); else emptyTextView.setVisibility(View.VISIBLE);

                        break;
                    case 2: //EXTRA RUTA

                        itemList = SqliteClass.getInstance(context).databasehelp.customersql.getCustomerDataNotByDay(today);
                        getList();
                        if(itemList.size()>0) emptyTextView.setVisibility(View.GONE); else emptyTextView.setVisibility(View.VISIBLE);

                        break;
                    default:
                        break;
                }
            }
            public void onNothingSelected(AdapterView<?> spn) {
                return;
            }
        });

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String lastDate = SqliteClass.getInstance(context).databasehelp.synchronizationsql.getDateLastSyncStock();
                if(lastDate.equals("")){
                    Util.infoDialog(activity, context, "CloudSales", "Falta sincronizar Stock.");
                }
                else{
                    lastDate = lastDate.substring(0, lastDate.indexOf(" "));
                    Date date = new Date();
                    String _date = dateFormatDate.format(date);

                    if(lastDate.equals(_date)){
                        ConstValue.setCustomerId(itemList.get(arg2).getId());
                        ConstValue.setCustomerCode(itemList.get(arg2).getCode());
                        Intent intent = new Intent(context, DashboardActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Util.infoDialog(activity, context, "CloudSales", "Sincronice Stock antes de comenzar.");
                    }
                }


            }
        });
        return rootView;
    }
    public void getList() {
        data = new ArrayList<HashMap<String,String>>();
        for(int z=0; z < itemList.size(); z++){
            CustomerClass cc = itemList.get(z);

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", cc.getId()+"");
            map.put("code", cc.getCode());
            map.put("bussiness", cc.getName());
            map.put("representative", cc.getRepresentative());
            map.put("phone", cc.getPhone());
            map.put("mobilePhone", cc.getMobilePhone());
            data.add(map);

        }
        CustomerAdapter adapter = new CustomerAdapter(activity, data);
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
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }
        List<CustomerClass> filteredValues = new ArrayList<CustomerClass>(itemList);
        for (CustomerClass value : itemList) {
            if (!value.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }
        data = new ArrayList<HashMap<String, String>>();
        for(int z=0; z< filteredValues.size(); z++){
            CustomerClass cc = filteredValues.get(z);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", cc.getId()+"");
            map.put("code", cc.getCode());
            map.put("bussiness", cc.getName());
            map.put("representative", cc.getRepresentative());
            map.put("phone", cc.getPhone());
            map.put("mobilePhone", cc.getMobilePhone());
            data.add(map);
        }
        adapter = new CustomerAdapter(activity, data);
        itemListView.setAdapter(adapter);
        return false;
    }


    public void resetSearch() {
        data = new ArrayList<HashMap<String,String>>();
        for(int z=0; z < itemList.size(); z++){
            CustomerClass cc = itemList.get(z);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", cc.getId()+"");
            map.put("code", cc.getCode());
            map.put("bussiness", cc.getName());
            map.put("representative", cc.getRepresentative());
            map.put("phone", cc.getPhone());
            map.put("mobilePhone", cc.getMobilePhone());
            data.add(map);
        }
        adapter = new CustomerAdapter(activity, data);
        itemListView.setAdapter(adapter);

    }

    public interface OnItem1SelectedListener {
        void OnItem1SelectedListener(String item);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }


}