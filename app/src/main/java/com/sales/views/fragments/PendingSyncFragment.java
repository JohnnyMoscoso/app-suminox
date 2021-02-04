package com.sales.views.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sales.R;
import com.sales.adapters.DetailPendingSyncAdapter;
import com.sales.adapters.PendingSyncAdapter;
import com.sales.config.ConstValue;
import com.sales.db.SqliteClass;
import com.sales.models.AssetControlClass;
import com.sales.models.SynBreakStockClass;
import com.sales.models.SynOrderClass;
import com.sales.models.SynOrderItemClass;
import com.sales.models.SynVisitClass;
import com.sales.utils.Common;
import com.sales.utils.ConnectionDetector;
import com.sales.utils.Util;
import com.sales.views.activities.MainActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PendingSyncFragment extends Fragment {

    public View rootView;
    public Common common;


    Activity activity;
    Context context;
    ConnectionDetector cd;
    public ArrayList<String> indicatorTypes;
    static ArrayList<HashMap<String, String>> data;

    TextView emptyTextView;
    ListView itemListView;

    ProgressDialog dialogLoading;
    SynVisitClass visitApp; ArrayList<SynVisitClass> loadVisitApp;
    AssetControlClass assetControlClass_instance; ArrayList<AssetControlClass> loadAssetControl_instance;
    AssetControlClass assetControlClass_observation; ArrayList<AssetControlClass> loadAssetControl_observation;
    ArrayList<SynOrderClass> synOrderLoad_pv; ArrayList<SynOrderItemClass> synOrderItemLoad_pv;
    ArrayList<SynOrderClass> synOrderLoad_pc; ArrayList<SynOrderItemClass> synOrderItemLoad_pc;
    ArrayList<SynOrderClass> synOrderLoad_fac; ArrayList<SynOrderItemClass> synOrderItemLoad_fac;
    ArrayList<SynOrderClass> synOrderLoad_bol; ArrayList<SynOrderItemClass> synOrderItemLoad_bol;
    ArrayList<SynOrderClass> synOrderLoad_change; ArrayList<SynOrderItemClass> synOrderItemLoad_change;
    ArrayList<SynOrderClass> synOrderLoad_return; ArrayList<SynOrderItemClass> synOrderItemLoad_return;
    ArrayList<SynBreakStockClass> synBreakStockClass; ArrayList<SynBreakStockClass> synBreakStockLoad;


    final int numberVisits = SqliteClass.getInstance(context).databasehelp.synvisitsql.countPending();
    final int numberAssetControl = SqliteClass.getInstance(context).databasehelp.assetcontrolsql.getAssetControlAppInstance().size() +  SqliteClass.getInstance(context).databasehelp.assetcontrolsql.getAssetControlAppObservation().size();

    final int numberSynOrderSeller_pv = SqliteClass.getInstance(context).databasehelp.synordersql.countPendingDocuments("PV");
    final int numberSynOrderSeller_pc = SqliteClass.getInstance(context).databasehelp.synordersql.countPendingDocuments("PC");
    final int numberFactures = SqliteClass.getInstance(context).databasehelp.synordersql.countPendingDocuments("FAC");
    final int numberBol = SqliteClass.getInstance(context).databasehelp.synordersql.countPendingDocuments("BOL");
    final int numberChangeItem = SqliteClass.getInstance(context).databasehelp.synordersql.countPendingDocuments("C");
    final int numberReturn = SqliteClass.getInstance(context).databasehelp.synordersql.countPendingDocuments("R");
    final int numberStockBreak = SqliteClass.getInstance(context).databasehelp.synbreakstocksql.countPending();

    public PendingSyncFragment() {}



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_pending_sync, container, false);
        data = new ArrayList<HashMap<String,String>>();
        common = new Common();
        activity = getActivity();
        context = getActivity();
        cd = new ConnectionDetector(context);

        itemListView = (ListView) rootView.findViewById(R.id.lst_pendings);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0: {
                        ConstValue.setClassName("Visitas");
                        dialogDetailPending(context, ConstValue.getClassName());
                        break;
                    }
                    case 1:{
                        ConstValue.setClassName("Control de Activos");
                        dialogDetailPending(context, ConstValue.getClassName());
                        break;
                    }
                    case 2:{
                        ConstValue.setClassName("Pedido de Carga");
                        dialogDetailPending(context, ConstValue.getClassName());
                        break;
                    }
                    case 3:{
                        ConstValue.setClassName("Pedido de Cliente");
                        dialogDetailPending(context, ConstValue.getClassName());
                        break;
                    }
                    case 4:{
                        ConstValue.setClassName("Factura");
                        dialogDetailPending(context, ConstValue.getClassName());
                        break;
                    }
                    case 5:{
                        ConstValue.setClassName("Boleta");
                        dialogDetailPending(context, ConstValue.getClassName());
                        break;
                    }
                    case 6:{
                        ConstValue.setClassName("Cambio de Producto");
                        dialogDetailPending(context, ConstValue.getClassName());
                        break;
                    }
                    case 7:{
                        ConstValue.setClassName("Devolucion de Producto");
                        dialogDetailPending(context, ConstValue.getClassName());
                        break;
                    }
                    case 8:{
                        ConstValue.setClassName("Quiebres de Inventario");
                        dialogDetailPending(context, ConstValue.getClassName());
                        break;
                    }
                    default:
                        break;
                }
            }
        });

        getList();
        return rootView;
    }

    public void getList() {
        data = new ArrayList<HashMap<String,String>>();

        //Element 01
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("number", SqliteClass.getInstance(context).databasehelp.synvisitsql.countPending()+"");
        map.put("numberDone", SqliteClass.getInstance(context).databasehelp.synvisitsql.countDone()+"");
        map.put("className", "Visitas");
        data.add(map);

        //Element 02
        map = new HashMap<String, String>();
        //map.put("number", SqliteClass.getInstance(context).databasehelp.assetcontrolsql.countAssetControlApp() + "");
        map.put("number", String.valueOf(numberAssetControl));
        map.put("numberDone", "0");
        map.put("className", "Control de Activos");
        data.add(map);

        //Element 03
        map = new HashMap<String, String>();
        map.put("number", SqliteClass.getInstance(context).databasehelp.synordersql.countPendingDocuments("PV") + "");
        map.put("numberDone", SqliteClass.getInstance(context).databasehelp.synordersql.countDoneDocuments("PV") + "");
        map.put("className", "Pedido de Carga");
        data.add(map);

        //Element 04
        map = new HashMap<String, String>();
        map.put("number", SqliteClass.getInstance(context).databasehelp.synordersql.countPendingDocuments("PC") + "");
        map.put("numberDone", SqliteClass.getInstance(context).databasehelp.synordersql.countDoneDocuments("PC") + "");
        map.put("className", "Pedido de Cliente");
        data.add(map);

        //Element 05
        map = new HashMap<String, String>();
        map.put("number", SqliteClass.getInstance(context).databasehelp.synordersql.countPendingDocuments("FAC") + "");
        map.put("numberDone", SqliteClass.getInstance(context).databasehelp.synordersql.countDoneDocuments("FAC") + "");
        map.put("className", "Facturas");
        data.add(map);

        //Element 06
        map = new HashMap<String, String>();
        map.put("number", SqliteClass.getInstance(context).databasehelp.synordersql.countPendingDocuments("BOL") + "");
        map.put("numberDone", SqliteClass.getInstance(context).databasehelp.synordersql.countDoneDocuments("BOL") + "");
        map.put("className", "Boletas");
        data.add(map);

        //Element 07
        map = new HashMap<String, String>();
        map.put("number", SqliteClass.getInstance(context).databasehelp.synordersql.countPendingDocuments("C") + "");
        map.put("numberDone", SqliteClass.getInstance(context).databasehelp.synordersql.countDoneDocuments("C") + "");
        map.put("className", "Cambio de Producto");
        data.add(map);

        //Element 08
        map = new HashMap<String, String>();
        map.put("number", SqliteClass.getInstance(context).databasehelp.synordersql.countPendingDocuments("R") + "");
        map.put("numberDone", SqliteClass.getInstance(context).databasehelp.synordersql.countDoneDocuments("R") + "");
        map.put("className", "Devolución de Producto");
        data.add(map);

        //Element 09
        map = new HashMap<String, String>();
        map.put("number", SqliteClass.getInstance(context).databasehelp.synbreakstocksql.countPending() + "");
        map.put("numberDone", SqliteClass.getInstance(context).databasehelp.synbreakstocksql.countDone() + "");
        map.put("className", "Quiebres de Inventario");
        data.add(map);

        PendingSyncAdapter adapter = new PendingSyncAdapter(activity, data);
        itemListView.setAdapter(adapter);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sync, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sync) {

            if(numberVisits == 0 && numberAssetControl == 0 && numberSynOrderSeller_pv == 0 && numberSynOrderSeller_pc == 0 &&
                    numberFactures == 0 && numberBol == 0 && numberChangeItem == 0 && numberReturn == 0 && numberStockBreak == 0){
                //Toast.makeText(context, "No tiene pendientes por sincronizar", Toast.LENGTH_SHORT).show();
                Util.infoDialog(getActivity(), context, "CloudSales", "No tiene pendientes por sincronizar.");
            }
            else{
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.alert_info);
                TextView head = (TextView) dialog.findViewById(R.id.alert_info_title);
                head.setText("Cloud Sales - Envío de Información");
                TextView content = (TextView) dialog.findViewById(R.id.alert_info_content);

                content.setText("¿Esta seguro de enviar todos los pendientes de sincronización al servidor?");

                Button dbOk = (Button) dialog.findViewById(R.id.alert_ok);
                dbOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(cd.isConnectingToInternet()){
                            new syncAllTask().execute(true);
                        }
                        else{
                            Toast.makeText(context, "El dispositivo no está conectado a Internet. Intente de nuevo cuando tenga conexión.", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
                Button dbCancel = (Button) dialog.findViewById(R.id.alert_cancel);
                dbCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }


        }
        return super.onOptionsItemSelected(item);
    }

    public void dialogDetailPending(final Context context, String className){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_detail_pending_sync);

        TextView head = (TextView) dialog.findViewById(R.id.multiple_option_title);
        head.setText("CloudSales - Detalle" );
        final TextView content = (TextView) dialog.findViewById(R.id.multiple_option_content);
        content.setText("Detalle de las sincronizaciones pendientes: ");

        final ListView itemListView = (ListView) dialog.findViewById(R.id.multiple_options);
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String,String>>();

        if(className.equals("Visitas")){

            final List<SynVisitClass> itemList = SqliteClass.getInstance(context).databasehelp.synvisitsql.getVisitAppPendingData();
            if(itemList.size() == 0){
                Toast.makeText(context, "No hay Visitas Pendientes.", Toast.LENGTH_SHORT).show();
                return;
            }
            for(int z=0; z < itemList.size(); z++){
                SynVisitClass cc = itemList.get(z);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", cc.getId()+"");
                map.put("nameCustomer", cc.getNameCustomer());
                map.put("dateHour", cc.getDateHour());
                map.put("description", cc.getNameVisit());
                data.add(map);
            }

            DetailPendingSyncAdapter adapter = new DetailPendingSyncAdapter(context, data, "Visitas");
            itemListView.setAdapter(adapter);
        }
        else if(className.equals("Control de Activos")){
            final List<AssetControlClass> itemList = SqliteClass.getInstance(context).databasehelp.assetcontrolsql.getAssetControlApp();
            if(itemList.size() == 0){
                Toast.makeText(context, "No hay Controles de Activos pendientes.", Toast.LENGTH_SHORT).show();
                return;
            }
            for(int z=0; z < itemList.size(); z++){
                AssetControlClass cc = itemList.get(z);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("dateDone", cc.getDateTask());
                if(cc.getType().equals("Instance")){
                    map.put("code", cc.getCode());
                    map.put("equipment", cc.getAssignment());
                    map.put("observation", "False");
                    data.add(map);
                }
                else{
                    map.put("answer", cc.getAnswer());
                    map.put("observation", "True");
                    data.add(map);
                }
            }

            DetailPendingSyncAdapter adapter = new DetailPendingSyncAdapter(context, data, "Control de Activos");
            itemListView.setAdapter(adapter);
        }
        else if(className.equals("Pedido de Carga")){
            final List<SynOrderClass> itemList = SqliteClass.getInstance(context).databasehelp.synordersql.getPendingSynOrderData("PV");
            if(itemList.size() == 0){
                Toast.makeText(context, "No hay Pedidos de Carga pendientes.", Toast.LENGTH_SHORT).show();
                return;
            }
            for(int i = 0; i < itemList.size(); i++){
                SynOrderClass cc = itemList.get(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("numberDocument", cc.getNumberDocument());
                map.put("dateHour", cc.getDateHour());
                map.put("total", cc.getTotalPay());
                data.add(map);
            }
            DetailPendingSyncAdapter adapter = new DetailPendingSyncAdapter(context, data, "Pedido de Carga");
            itemListView.setAdapter(adapter);

        }
        else if(className.equals("Pedido de Cliente")){
            final List<SynOrderClass> itemList = SqliteClass.getInstance(context).databasehelp.synordersql.getPendingSynOrderData("PC");
            if(itemList.size() == 0){
                Toast.makeText(context, "No hay Pedidos de Cliente pendientes.", Toast.LENGTH_SHORT).show();
                return;
            }
            for(int i = 0; i < itemList.size(); i++){
                SynOrderClass cc = itemList.get(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("numberDocument", cc.getNumberDocument());
                map.put("dateHour", cc.getDateHour());
                map.put("total", cc.getTotalPay());
                map.put("customerName", cc.getNameCustomer());
                data.add(map);
            }
            DetailPendingSyncAdapter adapter = new DetailPendingSyncAdapter(context, data, "Pedido de Cliente");
            itemListView.setAdapter(adapter);

        }
        else if(className.equals("Factura")){
            final List<SynOrderClass> itemList = SqliteClass.getInstance(context).databasehelp.synordersql.getPendingSynOrderData("FAC");
            if(itemList.size() == 0){
                Toast.makeText(context, "No hay Facturas pendientes.", Toast.LENGTH_SHORT).show();
                return;
            }
            for(int i = 0; i < itemList.size(); i++){
                SynOrderClass cc = itemList.get(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("numberDocument", cc.getNumberDocument());
                map.put("dateHour", cc.getDateHour());
                map.put("total", cc.getTotalPay());
                map.put("customerName", cc.getNameCustomer());
                data.add(map);
            }
            DetailPendingSyncAdapter adapter = new DetailPendingSyncAdapter(context, data, "Factura");
            itemListView.setAdapter(adapter);
        }
        else if(className.equals("Boleta")){
            final List<SynOrderClass> itemList = SqliteClass.getInstance(context).databasehelp.synordersql.getPendingSynOrderData("BOL");
            if(itemList.size() == 0){
                Toast.makeText(context, "No hay Boletas pendientes.", Toast.LENGTH_SHORT).show();
                return;
            }
            for(int i = 0; i < itemList.size(); i++){
                SynOrderClass cc = itemList.get(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("numberDocument", cc.getNumberDocument());
                map.put("dateHour", cc.getDateHour());
                map.put("total", cc.getTotalPay());
                map.put("customerName", cc.getNameCustomer());
                data.add(map);
            }
            DetailPendingSyncAdapter adapter = new DetailPendingSyncAdapter(context, data, "Boleta");
            itemListView.setAdapter(adapter);
        }
        else if(className.equals("Cambio de Producto")){
            final List<SynOrderClass> itemList = SqliteClass.getInstance(context).databasehelp.synordersql.getPendingSynOrderData("C");
            if(itemList.size() == 0){
                Toast.makeText(context, "No hay Cambios de Producto pendientes.", Toast.LENGTH_SHORT).show();
                return;
            }
            for(int i = 0; i < itemList.size(); i++){
                SynOrderClass cc = itemList.get(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("numberDocument", cc.getNumberDocument());
                map.put("dateHour", cc.getDateHour());
                map.put("total", cc.getTotalPay());
                map.put("customerName", cc.getNameCustomer());
                data.add(map);
            }
            DetailPendingSyncAdapter adapter = new DetailPendingSyncAdapter(context, data, "Cambio de Producto");
            itemListView.setAdapter(adapter);
        }
        else if(className.equals("Devolucion de Producto")){
            final List<SynOrderClass> itemList = SqliteClass.getInstance(context).databasehelp.synordersql.getPendingSynOrderData("R");
            if(itemList.size() == 0){
                Toast.makeText(context, "No hay Devoluciones de Producto pendientes.", Toast.LENGTH_SHORT).show();
                return;
            }
            for(int i = 0; i < itemList.size(); i++){
                SynOrderClass cc = itemList.get(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("numberDocument", cc.getNumberDocument());
                map.put("dateHour", cc.getDateHour());
                map.put("total", cc.getTotalPay());
                map.put("customerName", cc.getNameCustomer());
                data.add(map);
            }
            DetailPendingSyncAdapter adapter = new DetailPendingSyncAdapter(context, data, "Devolucion de Producto");
            itemListView.setAdapter(adapter);
        }
        else if(className.equals("Quiebres de Inventario")){
            final List<SynBreakStockClass> itemList = SqliteClass.getInstance(context).databasehelp.synbreakstocksql.getPendingSynBreakStockData();
            if(itemList.size() == 0){
                Toast.makeText(context, "No hay Quiebres de Inventario pendientes.", Toast.LENGTH_SHORT).show();
                return;
            }
            for(int i = 0; i < itemList.size(); i++){
                SynBreakStockClass cc = itemList.get(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("code", cc.getCode());
                map.put("dateHour", cc.getDateHour());
                map.put("codeItem", cc.getCodeItem());
                map.put("nameItem", cc.getNameItem());
                data.add(map);
            }
            DetailPendingSyncAdapter adapter = new DetailPendingSyncAdapter(context, data, "Quiebres de Inventario");
            itemListView.setAdapter(adapter);
        }
        else{}
        Button dialogButtonOk = (Button) dialog.findViewById(R.id.alert_ok);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.alert_cancel);

        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    class syncAllTask extends AsyncTask<Boolean, Void, String> {
        @Override
        protected String doInBackground(Boolean... params) {
            // TODO Auto-generated method stub
            String responseString = null;
            loadVisitApp = SqliteClass.getInstance(context).databasehelp.synvisitsql.getVisitAppPendingData();
            loadAssetControl_instance = SqliteClass.getInstance(context).databasehelp.assetcontrolsql.getAssetControlAppInstance();
            loadAssetControl_observation = SqliteClass.getInstance(context).databasehelp.assetcontrolsql.getAssetControlAppObservation();
            synOrderLoad_pv = SqliteClass.getInstance(context).databasehelp.synordersql.getPendingSynOrderData("PV");
            synOrderLoad_pc = SqliteClass.getInstance(context).databasehelp.synordersql.getPendingSynOrderData("PC");
            synOrderLoad_fac = SqliteClass.getInstance(context).databasehelp.synordersql.getPendingSynOrderData("FAC");
            synOrderLoad_bol = SqliteClass.getInstance(context).databasehelp.synordersql.getPendingSynOrderData("BOL");
            synOrderLoad_change = SqliteClass.getInstance(context).databasehelp.synordersql.getPendingSynOrderData("C");
            synOrderLoad_return = SqliteClass.getInstance(context).databasehelp.synordersql.getPendingSynOrderData("R");
            synBreakStockLoad = SqliteClass.getInstance(context).databasehelp.synbreakstocksql.getPendingSynBreakStockData();


            try {
                JSONObject jsonobj;

                if(numberVisits > 0){
                    for(int x=0; x<loadVisitApp.size(); x++){
                        SynVisitClass cc = loadVisitApp.get(x);
                        common = new Common();
                        jsonobj = new JSONObject();

                        jsonobj.put("id", cc.getId());
                        jsonobj.put("idUser", cc.getIdUser());
                        jsonobj.put("codeCustomer", cc.getCodeCustomer());
                        jsonobj.put("nameCustomer", cc.getNameCustomer());
                        jsonobj.put("codeVisit", cc.getCodeVisit());
                        jsonobj.put("nameVisit", cc.getNameVisit());
                        jsonobj.put("codeRoute", ConstValue.getUserRouteCode());
                        jsonobj.put("lat_customer", cc.getLatitudeCustomer());
                        jsonobj.put("lon_customer", cc.getLongitudeCustomer());
                        jsonobj.put("latitude", cc.getLatitude());
                        jsonobj.put("longitude", cc.getLongitude());
                        jsonobj.put("zone", cc.getZone());
                        jsonobj.put("rate", cc.getRate());
                        jsonobj.put("dateHour", cc.getDateHour());
                        jsonobj.put("number_customer_pending", cc.getNumberCustomerPending());

                        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                        nvps.add(new BasicNameValuePair("result", jsonobj.toString()));
                        System.out.println("JSON A ENVIAR " + jsonobj.toString());

                        JSONObject jObj = common.sendJsonData(ConstValue.JSON_SEND_VISIT_APP, nvps);

                        if(jObj.getString("response").equalsIgnoreCase("success")){
                            //SqliteClass.getInstance(context).databasehelp.synvisitsql.deleteVisitAppById(String.valueOf(cc.getId()));
                            SqliteClass.getInstance(context).databasehelp.synvisitsql.updateLoadSynVisit(String.valueOf(cc.getId()), "1");
                        }else{
                            responseString = jObj.getString("response");
                        }
                    }
                }

                if(numberAssetControl > 0){
                    for(int x=0; x<loadAssetControl_observation.size(); x++){

                        AssetControlClass cc = loadAssetControl_observation.get(x);
                        common = new Common();
                        jsonobj = new JSONObject();

                        jsonobj.put("id_task", cc.getIdTask());
                        jsonobj.put("answer", cc.getAnswer());

                        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                        nvps.add(new BasicNameValuePair("result", jsonobj.toString()));
                        System.out.println("JSON TASK CONTROL A ENVIAR " + jsonobj.toString());

                        JSONObject jObj = common.sendJsonData(ConstValue.JSON_SEND_TASK, nvps);

                        if(jObj.getString("response").equalsIgnoreCase("success")){
                            //SqliteClass.getInstance(context).databasehelp.assetcontrolsql.deleteAssetControlById(String.valueOf(cc.getId()));
                            SqliteClass.getInstance(context).databasehelp.assetcontrolsql.updateLoad(String.valueOf(cc.getId()));
                        }
                        else {
                            responseString = jObj.getString("response");
                        }
                    }

                    for(int x=0; x<loadAssetControl_instance.size(); x++){

                        common = new Common();
                        jsonobj = new JSONObject();
                        AssetControlClass cc = loadAssetControl_instance.get(x);

                        jsonobj.put("code", cc.getCode());
                        jsonobj.put("assignment", cc.getAssignment());
                        jsonobj.put("date", Util.getActualDateHourParse());
                        jsonobj.put("branch", cc.getCodeBranch());
                        jsonobj.put("office", cc.getCodeOffice());
                        jsonobj.put("route", cc.getCodeRoute());
                        jsonobj.put("customer", cc.getCodeCustomer());
                        jsonobj.put("user", ConstValue.getUserId());

                        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                        nvps.add(new BasicNameValuePair("result", jsonobj.toString()));
                        System.out.println("JSON ASSET CONTROL A ENVIAR " + jsonobj.toString());

                        JSONObject jObj = common.sendJsonData(ConstValue.JSON_SEND_ASSET_CONTROL, nvps);

                        if(jObj.getString("response").equalsIgnoreCase("success")){
                            //SqliteClass.getInstance(context).databasehelp.assetcontrolsql.deleteAssetControlById(String.valueOf(cc.getId()));
                            SqliteClass.getInstance(context).databasehelp.assetcontrolsql.updateLoad(String.valueOf(cc.getId()));
                        }
                        else {
                            responseString = jObj.getString("response");
                        }
                    }
                }

                if(numberSynOrderSeller_pv > 0){
                    JSONObject json;
                    JSONObject jsonHead;
                    for (int z = 0; z < synOrderLoad_pv.size(); z++) {
                        jsonHead = new JSONObject();
                        jsonHead.put("Type", "PV");
                        jsonHead.put("Code_Customer", synOrderLoad_pv.get(z).getCodeCustomer());
                        jsonHead.put("Name_Customer", synOrderLoad_pv.get(z).getNameCustomer());
                        jsonHead.put("Number_Document", synOrderLoad_pv.get(z).getNumberDocument());
                        jsonHead.put("Numeration", synOrderLoad_pv.get(z).getNumeration());
                        jsonHead.put("Code_Route", synOrderLoad_pv.get(z).getCodeRoute());
                        jsonHead.put("Id_Userapp", synOrderLoad_pv.get(z).getIdUserApp());
                        jsonHead.put("Date_Hour", synOrderLoad_pv.get(z).getDateHour());
                        jsonHead.put("RUC", synOrderLoad_pv.get(z).getRuc());
                        jsonHead.put("State_Nubefact", synOrderLoad_pv.get(z).getStateNubeFact());
                        jsonHead.put("Op_Exonerated", synOrderLoad_pv.get(z).getOperationExonerated());
                        jsonHead.put("Op_Taxed", synOrderLoad_pv.get(z).getOperationTaxed());
                        jsonHead.put("Igv", synOrderLoad_pv.get(z).getIgv());
                        jsonHead.put("Total_Pay", synOrderLoad_pv.get(z).getTotalPay());
                        jsonHead.put("State_Charge", synOrderLoad_pv.get(z).getStateCharge());

                        JSONArray jsonArrayItemsDetail = new JSONArray();
                        synOrderItemLoad_pv = new ArrayList<SynOrderItemClass>();
                        synOrderItemLoad_pv =  SqliteClass.getInstance(context).databasehelp.synorderitemsql.getPendingSynOrderItemData(synOrderLoad_pv.get(z).getNumberDocument());
                        for (int i = 0; i < synOrderItemLoad_pv.size(); i++){
                            JSONObject itemDetail = new JSONObject();
                            itemDetail.put("Number_Document", synOrderItemLoad_pv.get(i).getNumberDocument());
                            itemDetail.put("Date_Hour", synOrderItemLoad_pv.get(i).getDateHour());
                            itemDetail.put("Category", synOrderItemLoad_pv.get(i).getCategory());
                            itemDetail.put("Code_Item", synOrderItemLoad_pv.get(i).getCodeItem()); //no se genera
                            itemDetail.put("Description_Item", synOrderItemLoad_pv.get(i).getDescriptionItem());
                            itemDetail.put("Quantity", synOrderItemLoad_pv.get(i).getQuantity());
                            itemDetail.put("Price", synOrderItemLoad_pv.get(i).getPrice());
                            itemDetail.put("Unit_Measure", synOrderItemLoad_pv.get(i).getUnitMeasure());
                            itemDetail.put("Igv", synOrderItemLoad_pv.get(i).getIgv());
                            itemDetail.put("Total_Pay", synOrderItemLoad_pv.get(i).getTotalPay());
                            itemDetail.put("State", synOrderItemLoad_pv.get(i).getState());
                            itemDetail.put("State_Charge", synOrderItemLoad_pv.get(i).getStateCharge());
                            jsonArrayItemsDetail.put(itemDetail);
                        }
                        jsonHead.put("lInvoiceItem", jsonArrayItemsDetail);

                        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                        System.out.println("JSON A ENVIAR: " + jsonHead.toString());
                        nvps.add(new BasicNameValuePair("result", jsonHead.toString()));
                        json = common.sendJsonData(ConstValue.JSON_SEND_DOCUMENT, nvps);
                        if (json.getString("response").equalsIgnoreCase("success")) {
                            /** Actualizar todos mis loads a 1 para que no sean enviados en ningun otro lado, pero igual puedan mostrarse */
                            SqliteClass.getInstance(context).databasehelp.synordersql.updateOrderLoad(synOrderLoad_pv.get(z).getNumberDocument());
                            SqliteClass.getInstance(context).databasehelp.synorderitemsql.updateOrderLoad(synOrderLoad_pv.get(z).getNumberDocument());

                        } else {
                            responseString = json.getString("response");
                        }
                    }
                }

                if(numberSynOrderSeller_pc > 0){
                    JSONObject json;
                    JSONObject jsonHead;
                    for (int z = 0; z < synOrderLoad_pc.size(); z++) {
                        jsonHead = new JSONObject();
                        jsonHead.put("Type", "PC");
                        jsonHead.put("Code_Customer", synOrderLoad_pc.get(z).getCodeCustomer());
                        jsonHead.put("Name_Customer", synOrderLoad_pc.get(z).getNameCustomer());
                        jsonHead.put("Number_Document", synOrderLoad_pc.get(z).getNumberDocument());
                        jsonHead.put("Numeration", synOrderLoad_pc.get(z).getNumeration());
                        jsonHead.put("Code_Route", synOrderLoad_pc.get(z).getCodeRoute());
                        jsonHead.put("Id_Userapp", synOrderLoad_pc.get(z).getIdUserApp());
                        jsonHead.put("Date_Hour", synOrderLoad_pc.get(z).getDateHour());
                        jsonHead.put("RUC", synOrderLoad_pc.get(z).getRuc());
                        jsonHead.put("State_Nubefact", synOrderLoad_pc.get(z).getStateNubeFact());
                        jsonHead.put("Op_Exonerated", synOrderLoad_pc.get(z).getOperationExonerated());
                        jsonHead.put("Op_Taxed", synOrderLoad_pc.get(z).getOperationTaxed());
                        jsonHead.put("Igv", synOrderLoad_pc.get(z).getIgv());
                        jsonHead.put("Total_Pay", synOrderLoad_pc.get(z).getTotalPay());
                        jsonHead.put("State_Charge", synOrderLoad_pc.get(z).getStateCharge());

                        JSONArray jsonArrayItemsDetail = new JSONArray();
                        synOrderItemLoad_pc = new ArrayList<SynOrderItemClass>();
                        synOrderItemLoad_pc =  SqliteClass.getInstance(context).databasehelp.synorderitemsql.getPendingSynOrderItemData(synOrderLoad_pc.get(z).getNumberDocument());
                        for (int i = 0; i < synOrderItemLoad_pc.size(); i++){
                            JSONObject itemDetail = new JSONObject();
                            itemDetail.put("Number_Document", synOrderItemLoad_pc.get(i).getNumberDocument());
                            itemDetail.put("Date_Hour", synOrderItemLoad_pc.get(i).getDateHour());
                            itemDetail.put("Category", synOrderItemLoad_pc.get(i).getCategory());
                            itemDetail.put("Code_Item", synOrderItemLoad_pc.get(i).getCodeItem());
                            itemDetail.put("Description_Item", synOrderItemLoad_pc.get(i).getDescriptionItem());
                            itemDetail.put("Quantity", synOrderItemLoad_pc.get(i).getQuantity());
                            itemDetail.put("Price", synOrderItemLoad_pc.get(i).getPrice());
                            itemDetail.put("Unit_Measure", synOrderItemLoad_pc.get(i).getUnitMeasure());
                            itemDetail.put("Igv", synOrderItemLoad_pc.get(i).getIgv());
                            itemDetail.put("Total_Pay", synOrderItemLoad_pc.get(i).getTotalPay());
                            itemDetail.put("State", synOrderItemLoad_pc.get(i).getState());
                            itemDetail.put("State_Charge", synOrderItemLoad_pc.get(i).getStateCharge());
                            jsonArrayItemsDetail.put(itemDetail);
                        }
                        jsonHead.put("lInvoiceItem", jsonArrayItemsDetail);

                        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                        System.out.println("JSON A ENVIAR: " + jsonHead.toString());
                        nvps.add(new BasicNameValuePair("result", jsonHead.toString()));
                        json = common.sendJsonData(ConstValue.JSON_SEND_DOCUMENT, nvps);
                        if (json.getString("response").equalsIgnoreCase("success")) {
                            /** Actualizar todos mis loads a 1 para que no sean enviados en ningun otro lado, pero igual puedan mostrarse */
                            SqliteClass.getInstance(context).databasehelp.synordersql.updateOrderLoad(synOrderLoad_pc.get(z).getNumberDocument());
                            SqliteClass.getInstance(context).databasehelp.synorderitemsql.updateOrderLoad(synOrderLoad_pc.get(z).getNumberDocument());

                        } else {
                            responseString = json.getString("response");
                        }
                    }
                }
                if(numberFactures > 0){
                    JSONObject json;
                    JSONObject jsonHead;
                    for (int z = 0; z < synOrderLoad_fac.size(); z++) {
                        jsonHead = new JSONObject();
                        jsonHead.put("Type", "FAC");
                        jsonHead.put("Code_Customer", synOrderLoad_fac.get(z).getCodeCustomer());
                        jsonHead.put("Name_Customer", synOrderLoad_fac.get(z).getNameCustomer());
                        jsonHead.put("Number_Document", synOrderLoad_fac.get(z).getNumberDocument());
                        jsonHead.put("Numeration", synOrderLoad_fac.get(z).getNumeration());
                        jsonHead.put("Code_Route", synOrderLoad_fac.get(z).getCodeRoute());
                        jsonHead.put("Id_Userapp", synOrderLoad_fac.get(z).getIdUserApp());
                        jsonHead.put("Date_Hour", synOrderLoad_fac.get(z).getDateHour());
                        jsonHead.put("RUC", synOrderLoad_fac.get(z).getRuc());
                        jsonHead.put("State_Nubefact", synOrderLoad_fac.get(z).getStateNubeFact());
                        jsonHead.put("Op_Exonerated", synOrderLoad_fac.get(z).getOperationExonerated());
                        jsonHead.put("Op_Taxed", synOrderLoad_fac.get(z).getOperationTaxed());
                        jsonHead.put("Igv", synOrderLoad_fac.get(z).getIgv());
                        jsonHead.put("Total_Pay", synOrderLoad_fac.get(z).getTotalPay());
                        jsonHead.put("State_Charge", synOrderLoad_fac.get(z).getStateCharge());

                        JSONArray jsonArrayItemsDetail = new JSONArray();
                        synOrderItemLoad_fac = new ArrayList<SynOrderItemClass>();
                        synOrderItemLoad_fac =  SqliteClass.getInstance(context).databasehelp.synorderitemsql.getPendingSynOrderItemData(synOrderLoad_fac.get(z).getNumberDocument());
                        for (int i = 0; i < synOrderItemLoad_fac.size(); i++){
                            JSONObject itemDetail = new JSONObject();
                            itemDetail.put("Number_Document", synOrderItemLoad_fac.get(i).getNumberDocument());
                            itemDetail.put("Date_Hour", synOrderItemLoad_fac.get(i).getDateHour());
                            itemDetail.put("Category", synOrderItemLoad_fac.get(i).getCategory());
                            itemDetail.put("Code_Item", synOrderItemLoad_fac.get(i).getCodeItem());
                            itemDetail.put("Description_Item", synOrderItemLoad_fac.get(i).getDescriptionItem());
                            itemDetail.put("Quantity", synOrderItemLoad_fac.get(i).getQuantity());
                            itemDetail.put("Price", synOrderItemLoad_fac.get(i).getPrice());
                            itemDetail.put("Unit_Measure", synOrderItemLoad_fac.get(i).getUnitMeasure());
                            itemDetail.put("Igv", synOrderItemLoad_fac.get(i).getIgv());
                            itemDetail.put("Total_Pay", synOrderItemLoad_fac.get(i).getTotalPay());
                            itemDetail.put("State", synOrderItemLoad_fac.get(i).getState());
                            itemDetail.put("State_Charge", synOrderItemLoad_fac.get(i).getStateCharge());
                            jsonArrayItemsDetail.put(itemDetail);
                        }
                        jsonHead.put("lInvoiceItem", jsonArrayItemsDetail);

                        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                        System.out.println("JSON A ENVIAR: " + jsonHead.toString());
                        nvps.add(new BasicNameValuePair("result", jsonHead.toString()));
                        json = common.sendJsonData(ConstValue.JSON_SEND_DOCUMENT, nvps);
                        if (json.getString("response").equalsIgnoreCase("success")) {
                            /** Actualizar todos mis loads a 1 para que no sean enviados en ningun otro lado, pero igual puedan mostrarse */
                            SqliteClass.getInstance(context).databasehelp.synordersql.updateOrderLoad(synOrderLoad_fac.get(z).getNumberDocument());
                            SqliteClass.getInstance(context).databasehelp.synorderitemsql.updateOrderLoad(synOrderLoad_fac.get(z).getNumberDocument());

                        } else {
                            responseString = json.getString("response");
                        }
                    }
                }
                if(numberBol > 0){
                    JSONObject json;
                    JSONObject jsonHead;
                    for (int z = 0; z < synOrderLoad_bol.size(); z++) {
                        jsonHead = new JSONObject();
                        jsonHead.put("Type", "BOL");
                        jsonHead.put("Code_Customer", synOrderLoad_bol.get(z).getCodeCustomer());
                        jsonHead.put("Name_Customer", synOrderLoad_bol.get(z).getNameCustomer());
                        jsonHead.put("Number_Document", synOrderLoad_bol.get(z).getNumberDocument());
                        jsonHead.put("Numeration", synOrderLoad_bol.get(z).getNumeration());
                        jsonHead.put("Code_Route", synOrderLoad_bol.get(z).getCodeRoute());
                        jsonHead.put("Id_Userapp", synOrderLoad_bol.get(z).getIdUserApp());
                        jsonHead.put("Date_Hour", synOrderLoad_bol.get(z).getDateHour());
                        jsonHead.put("RUC", synOrderLoad_bol.get(z).getRuc());
                        jsonHead.put("State_Nubefact", synOrderLoad_bol.get(z).getStateNubeFact());
                        jsonHead.put("Op_Exonerated", synOrderLoad_bol.get(z).getOperationExonerated());
                        jsonHead.put("Op_Taxed", synOrderLoad_bol.get(z).getOperationTaxed());
                        jsonHead.put("Igv", synOrderLoad_bol.get(z).getIgv());
                        jsonHead.put("Total_Pay", synOrderLoad_bol.get(z).getTotalPay());
                        jsonHead.put("State_Charge", synOrderLoad_bol.get(z).getStateCharge());

                        JSONArray jsonArrayItemsDetail = new JSONArray();
                        synOrderItemLoad_bol = new ArrayList<SynOrderItemClass>();
                        synOrderItemLoad_bol =  SqliteClass.getInstance(context).databasehelp.synorderitemsql.getPendingSynOrderItemData(synOrderLoad_bol.get(z).getNumberDocument());
                        for (int i = 0; i < synOrderItemLoad_bol.size(); i++){
                            JSONObject itemDetail = new JSONObject();
                            itemDetail.put("Number_Document", synOrderItemLoad_bol.get(i).getNumberDocument());
                            itemDetail.put("Date_Hour", synOrderItemLoad_bol.get(i).getDateHour());
                            itemDetail.put("Category", synOrderItemLoad_bol.get(i).getCategory());
                            itemDetail.put("Code_Item", synOrderItemLoad_bol.get(i).getCodeItem());
                            itemDetail.put("Description_Item", synOrderItemLoad_bol.get(i).getDescriptionItem());
                            itemDetail.put("Quantity", synOrderItemLoad_bol.get(i).getQuantity());
                            itemDetail.put("Price", synOrderItemLoad_bol.get(i).getPrice());
                            itemDetail.put("Unit_Measure", synOrderItemLoad_bol.get(i).getUnitMeasure());
                            itemDetail.put("Igv", synOrderItemLoad_bol.get(i).getIgv());
                            itemDetail.put("Total_Pay", synOrderItemLoad_bol.get(i).getTotalPay());
                            itemDetail.put("State", synOrderItemLoad_bol.get(i).getState());
                            itemDetail.put("State_Charge", synOrderItemLoad_bol.get(i).getStateCharge());
                            jsonArrayItemsDetail.put(itemDetail);
                        }
                        jsonHead.put("lInvoiceItem", jsonArrayItemsDetail);

                        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                        System.out.println("JSON A ENVIAR: " + jsonHead.toString());
                        nvps.add(new BasicNameValuePair("result", jsonHead.toString()));
                        json = common.sendJsonData(ConstValue.JSON_SEND_DOCUMENT, nvps);
                        if (json.getString("response").equalsIgnoreCase("success")) {
                            /** Actualizar todos mis loads a 1 para que no sean enviados en ningun otro lado, pero igual puedan mostrarse */
                            SqliteClass.getInstance(context).databasehelp.synordersql.updateOrderLoad(synOrderLoad_bol.get(z).getNumberDocument());
                            SqliteClass.getInstance(context).databasehelp.synorderitemsql.updateOrderLoad(synOrderLoad_bol.get(z).getNumberDocument());

                        } else {
                            responseString = json.getString("response");
                        }
                    }
                }
                if(numberChangeItem > 0){
                    JSONObject json;
                    JSONObject jsonHead;
                    for (int z = 0; z < synOrderLoad_change.size(); z++) {
                        jsonHead = new JSONObject();
                        jsonHead.put("Type", "C");
                        jsonHead.put("Code_Route", synOrderLoad_change.get(z).getCodeRoute());
                        jsonHead.put("Id_Userapp", synOrderLoad_change.get(z).getIdUserApp());
                        jsonHead.put("Numeration", synOrderLoad_change.get(z).getNumberDocument());
                        jsonHead.put("Date_Hour", synOrderLoad_change.get(z).getDateHour());
                        jsonHead.put("Code_Customer", synOrderLoad_change.get(z).getCodeCustomer());
                        jsonHead.put("Name_Customer", synOrderLoad_change.get(z).getNameCustomer());
                        jsonHead.put("Total_Pay", synOrderLoad_change.get(z).getTotalPay());
                        jsonHead.put("State_Charge", synOrderLoad_change.get(z).getStateCharge());

                        JSONArray jsonArrayItemsDetail = new JSONArray();
                        synOrderItemLoad_change = new ArrayList<SynOrderItemClass>();
                        synOrderItemLoad_change =  SqliteClass.getInstance(context).databasehelp.synorderitemsql.getPendingSynOrderItemData(synOrderLoad_change.get(z).getNumberDocument());
                        for (int i = 0; i < synOrderItemLoad_change.size(); i++){
                            JSONObject itemDetail = new JSONObject();
                            itemDetail.put("Numeration", synOrderItemLoad_change.get(i).getNumberDocument());
                            itemDetail.put("Date_Hour", synOrderItemLoad_change.get(i).getDateHour());
                            itemDetail.put("Category", synOrderItemLoad_change.get(i).getCategory());
                            itemDetail.put("Code_Item", synOrderItemLoad_change.get(i).getCodeItem()); //no se genera
                            itemDetail.put("Description_Item",synOrderItemLoad_change.get(i).getDescriptionItem());
                            itemDetail.put("Quantity", synOrderItemLoad_change.get(i).getQuantity());
                            itemDetail.put("Price", synOrderItemLoad_change.get(i).getPrice());
                            itemDetail.put("Unit_Measure", synOrderItemLoad_change.get(i).getUnitMeasure());
                            itemDetail.put("Total_Pay", synOrderItemLoad_change.get(i).getTotalPay());
                            itemDetail.put("State", synOrderItemLoad_change.get(i).getState());
                            itemDetail.put("State_Charge", synOrderItemLoad_change.get(i).getStateCharge());
                            //itemDetail.put("Igv", synOrderItemLoad.get(i).getIgv());
                            jsonArrayItemsDetail.put(itemDetail);
                        }
                        jsonHead.put("lReturnItem", jsonArrayItemsDetail);

                        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                        System.out.println("JSON A ENVIAR: " + jsonHead.toString());
                        nvps.add(new BasicNameValuePair("result", jsonHead.toString()));
                        json = common.sendJsonData(ConstValue.JSON_CHANGE_RETURN, nvps);
                        if (json.getString("response").equalsIgnoreCase("success")) {
                            /** Actualizar todos mis loads a 1 para que no sean enviados en ningun otro lado, pero igual puedan mostrarse */
                            SqliteClass.getInstance(context).databasehelp.synordersql.updateOrderLoad(synOrderLoad_change.get(z).getNumberDocument());
                            SqliteClass.getInstance(context).databasehelp.synorderitemsql.updateOrderLoad(synOrderLoad_change.get(z).getNumberDocument());

                        } else {
                            responseString = json.getString("response");
                        }
                    }
                }
                if(numberReturn > 0){
                    JSONObject json;
                    JSONObject jsonHead;
                    for (int z = 0; z < synOrderLoad_return.size(); z++) {
                        jsonHead = new JSONObject();
                        jsonHead.put("Type", "R");
                        jsonHead.put("Code_Route", synOrderLoad_return.get(z).getCodeRoute());
                        jsonHead.put("Id_Userapp", synOrderLoad_return.get(z).getIdUserApp());
                        jsonHead.put("Numeration", synOrderLoad_return.get(z).getNumberDocument());
                        jsonHead.put("Date_Hour", synOrderLoad_return.get(z).getDateHour());
                        jsonHead.put("Code_Customer", synOrderLoad_return.get(z).getCodeCustomer());
                        jsonHead.put("Name_Customer", synOrderLoad_return.get(z).getNameCustomer());
                        jsonHead.put("Total_Pay", synOrderLoad_return.get(z).getTotalPay());
                        jsonHead.put("State_Charge", synOrderLoad_return.get(z).getStateCharge());

                        JSONArray jsonArrayItemsDetail = new JSONArray();
                        synOrderItemLoad_return  = new ArrayList<SynOrderItemClass>();
                        synOrderItemLoad_return =  SqliteClass.getInstance(context).databasehelp.synorderitemsql.getPendingSynOrderItemData(synOrderLoad_return.get(z).getNumberDocument());
                        for (int i = 0; i < synOrderItemLoad_return.size(); i++){
                            JSONObject itemDetail = new JSONObject();
                            itemDetail.put("Numeration", synOrderItemLoad_return.get(i).getNumberDocument());
                            itemDetail.put("Date_Hour", synOrderItemLoad_return.get(i).getDateHour());
                            itemDetail.put("Category", synOrderItemLoad_return.get(i).getCategory());
                            itemDetail.put("Code_Item", synOrderItemLoad_return.get(i).getCodeItem()); //no se genera
                            itemDetail.put("Description_Item",synOrderItemLoad_return.get(i).getDescriptionItem());
                            itemDetail.put("Quantity", synOrderItemLoad_return.get(i).getQuantity());
                            itemDetail.put("Price", synOrderItemLoad_return.get(i).getPrice());
                            itemDetail.put("Unit_Measure", synOrderItemLoad_return.get(i).getUnitMeasure());
                            itemDetail.put("Total_Pay", synOrderItemLoad_return.get(i).getTotalPay());
                            itemDetail.put("State", synOrderItemLoad_return.get(i).getState());
                            itemDetail.put("State_Charge", synOrderItemLoad_return.get(i).getStateCharge());
                            //itemDetail.put("Igv", synOrderItemLoad.get(i).getIgv());
                            jsonArrayItemsDetail.put(itemDetail);
                        }
                        jsonHead.put("lReturnItem", jsonArrayItemsDetail);

                        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                        System.out.println("JSON A ENVIAR: " + jsonHead.toString());
                        nvps.add(new BasicNameValuePair("result", jsonHead.toString()));
                        json = common.sendJsonData(ConstValue.JSON_CHANGE_RETURN, nvps);
                        if (json.getString("response").equalsIgnoreCase("success")) {
                            /** Actualizar todos mis loads a 1 para que no sean enviados en ningun otro lado, pero igual puedan mostrarse */
                            SqliteClass.getInstance(context).databasehelp.synordersql.updateOrderLoad(synOrderLoad_return.get(z).getNumberDocument());
                            SqliteClass.getInstance(context).databasehelp.synorderitemsql.updateOrderLoad(synOrderLoad_return.get(z).getNumberDocument());

                        } else {
                            responseString = json.getString("response");
                        }
                    }
                }
                if(numberStockBreak > 0){
                    for(int x=0; x<synBreakStockLoad.size(); x++){
                        SynBreakStockClass cc = synBreakStockLoad.get(x);
                        common = new Common();
                        jsonobj = new JSONObject();

                        jsonobj.put("Code", cc.getCode());
                        jsonobj.put("Code_Route", cc.getCodeRoute());
                        jsonobj.put("Id_Userapp", cc.getCodeRoute());
                        jsonobj.put("Code_Item", cc.getCodeItem());
                        jsonobj.put("Name_Item", cc.getNameItem());
                        jsonobj.put("DateHour", cc.getDateHour());
                        jsonobj.put("Price", cc.getPrice());
                        jsonobj.put("number_customer_pending", cc.getNumberCustomerPending());

                        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                        nvps.add(new BasicNameValuePair("result", jsonobj.toString()));
                        System.out.println("JSON A ENVIAR " + jsonobj.toString());

                        JSONObject jObj = common.sendJsonData(ConstValue.JSON_SEND_STOCK_BREAK, nvps);

                        if(jObj.getString("response").equalsIgnoreCase("success")){
                            SqliteClass.getInstance(context).databasehelp.synbreakstocksql.updateLoadSynBreakStock(cc.getCode(), "1");
                        }else{
                            responseString = jObj.getString("response");
                        }
                    }
                }
            } catch (JSONException e) {
                responseString = e.toString();
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            return responseString;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            dialogLoading = ProgressDialog.show(context, "", "Sincronizando pendientes. Por favor, espere ...", true);
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Toast.makeText(context, "CloudSales " + result, Toast.LENGTH_LONG).show();
            } else {
                infoDialog(context, "CloudSales", "Se sincronizaron todos los Pendientes con éxito.");
            }
            // TODO Auto-generated method stub
            dialogLoading.dismiss();

        }

        private ArrayList<HashMap<String, String>> get_items() {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }
        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            super.onCancelled();
        }

        @Override
        protected void onCancelled(String result) {
            // TODO Auto-generated method stub
            super.onCancelled(result);
        }
    }
    public void infoDialog(Context context, String title, String field){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_info);
        ImageView image = (ImageView) dialog.findViewById(R.id.alert_info);
        image.setImageResource(R.drawable.ic_alert_info);
        TextView head = (TextView) dialog.findViewById(R.id.alert_info_title);
        head.setText(title);
        TextView content = (TextView) dialog.findViewById(R.id.alert_info_content);
        content.setText(field);
        Button dbOk = (Button) dialog.findViewById(R.id.alert_ok);
        dbOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("fragment_position", 5);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        Button dbCancel = (Button) dialog.findViewById(R.id.alert_cancel);
        dbCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
