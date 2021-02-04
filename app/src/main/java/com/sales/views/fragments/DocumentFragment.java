package com.sales.views.fragments;

import android.app.Activity;
import android.app.Dialog;
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

import com.sales.R;
import com.sales.adapters.GeneralSpinnerAdapter;
import com.sales.adapters.DocumentAdapter;
import com.sales.config.ConstValue;
import com.sales.db.SqliteClass;
import com.sales.models.SynOrderClass;
import com.sales.utils.Common;
import com.sales.views.activities.DocumentDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DocumentFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    public View rootView;
    public Common common;

    List<SynOrderClass> itemList;
    Activity activity;
    ListView itemListView;
    Context context;
    TextView emptyTextView;

    Spinner sp_document_type;
    public ArrayList<String> documentTypes;
    static ArrayList<HashMap<String, String>> data;

    private static final String FRAGMENT_NAME = "DocumentFragment";


    public DocumentFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        documentTypes = new ArrayList<String>();
        documentTypes.add("FACTURAS"); documentTypes.add("BOLETAS"); documentTypes.add("P. VENDEDOR"); documentTypes.add("P. CLIENTE");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_print, container, false);
        data = new ArrayList<HashMap<String,String>>();
        common = new Common();
        activity = getActivity();
        context = getActivity();
        itemListView = (ListView) rootView.findViewById(R.id.list_documents);
        emptyTextView = (TextView) rootView.findViewById(android.R.id.empty);

        itemList = SqliteClass.getInstance(context).databasehelp.synordersql.getSynOrderData("FAC"); //Default to avoid null exception

        sp_document_type = (Spinner) rootView.findViewById(R.id.sp_document_type);

        sp_document_type.getBackground().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        sp_document_type.setAdapter(new GeneralSpinnerAdapter(context, R.layout.spinner_row, documentTypes, FRAGMENT_NAME));

        sp_document_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> spn, android.view.View v, int position, long id) {
                switch (position){
                    case 0: //FACTURAS
                        itemList = SqliteClass.getInstance(context).databasehelp.synordersql.getSynOrderData("FAC");
                        getList();
                        if(itemList.size()>0) emptyTextView.setVisibility(View.GONE); else emptyTextView.setVisibility(View.VISIBLE);

                        break;
                    case 1: //BOLETAS
                        itemList = SqliteClass.getInstance(context).databasehelp.synordersql.getSynOrderData("BOL");
                        getList();
                        if(itemList.size()>0) emptyTextView.setVisibility(View.GONE); else emptyTextView.setVisibility(View.VISIBLE);
                        break;
                    case 2: //P. VENDEDOR
                        itemList = SqliteClass.getInstance(context).databasehelp.synordersql.getSynOrderData("PV");
                        getList();
                        if(itemList.size()>0) emptyTextView.setVisibility(View.GONE); else emptyTextView.setVisibility(View.VISIBLE);
                        break;
                    case 3: //P. CLIENTE
                        itemList = SqliteClass.getInstance(context).databasehelp.synordersql.getSynOrderData("PC");
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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ConstValue.setNumberDocumentSynOrder(itemList.get(position).getNumberDocument());
                Intent intent = new Intent(getActivity(), DocumentDetailActivity.class);
                startActivity(intent);

            }
        });

        return rootView;
    }

    public void getList() {
        data = new ArrayList<HashMap<String,String>>();

        for(int i=0; i < itemList.size(); i++){
            SynOrderClass cc = itemList.get(i);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", cc.getId()+"");
            map.put("numberDocument", cc.getNumberDocument());
            map.put("total", cc.getTotalPay());
            map.put("load", cc.getLoad());
            map.put("date_hour", cc.getDateHour());
            data.add(map);
        }

        DocumentAdapter adapter = new DocumentAdapter(activity, data);
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
        List<SynOrderClass> filteredValues = new ArrayList<SynOrderClass>(itemList);
        for (SynOrderClass value : itemList) {
            if (!value.getNumberDocument().toLowerCase().contains(newText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }
        data = new ArrayList<HashMap<String, String>>();
        for(int z=0; z< filteredValues.size(); z++){
            SynOrderClass cc = filteredValues.get(z);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", cc.getId()+"");
            map.put("numberDocument", cc.getNumberDocument());
            map.put("total", cc.getTotalPay());
            map.put("load", cc.getLoad());
            map.put("date_hour", cc.getDateHour());
            data.add(map);
        }
        DocumentAdapter adapter = new DocumentAdapter(activity, data);
        itemListView.setAdapter(adapter);

        return false;
    }

    public void resetSearch() {

        data = new ArrayList<HashMap<String,String>>();
        for(int z=0; z < itemList.size(); z++){
            SynOrderClass cc = itemList.get(z);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", cc.getId()+"");
            map.put("numberDocument", cc.getNumberDocument());
            map.put("total", cc.getTotalPay());
            map.put("load", cc.getLoad());
            map.put("date_hour", cc.getDateHour());
            data.add(map);
        }
        DocumentAdapter adapter = new DocumentAdapter(activity, data);
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


}
