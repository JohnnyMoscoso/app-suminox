package com.sales.views.activities;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.sales.R;
import com.sales.adapters.AssetControlAdapter;
import com.sales.config.ConstValue;
import com.sales.db.SqliteClass;
import com.sales.models.AssetControlClass;
import com.sales.models.CustomerClass;
import com.sales.utils.Common;
import com.sales.utils.ConnectionDetector;
import com.sales.utils.Util;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AssetControlActivity extends AppCompatActivity {


    public Common common;

    ConnectionDetector cd;
    List<AssetControlClass> itemList;
    Activity activity;
    ListView itemListView;
    Context context;
    TextView emptyTextView;
    CustomerClass customer;
    static ArrayList<HashMap<String, String>> data;
    EditText inputEquipment, inputCodeEquipment;

    ProgressDialog dialogLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_control);

        activity = this;
        context = this;
        cd = new ConnectionDetector(this);
        customer = SqliteClass.getInstance(context).databasehelp.customersql.getCustomer(ConstValue.getCustomerId());

        getSupportActionBar().setTitle(customer.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemList = SqliteClass.getInstance(context).databasehelp.assetcontrolsql.getAssetControlDataByCustomer(customer.getCode());
        itemListView = (ListView) findViewById(R.id.ls_asset_control);
        getList();
        emptyTextView = (TextView) findViewById(android.R.id.empty);
        if(itemList.size()>0) emptyTextView.setVisibility(View.GONE); else emptyTextView.setVisibility(View.VISIBLE);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(itemList.get(position).getStateTask().equals("1")){
                    ConstValue.setAssetControlId(itemList.get(position).getId());
                    Intent intent = new Intent(AssetControlActivity.this, AssetControlDetailActivity.class);
                    startActivity(intent);
                }
                else{
                    if(itemList.get(position).getAnswer().equals(null) || itemList.get(position).getAnswer().equals("null")){
                        Util.infoDialog(AssetControlActivity.this, context, "CloudSales", "No tiene asignada una tarea.");
                    }
                    else{
                        Util.infoDialog(AssetControlActivity.this, context, "CloudSales",
                                "Observación realizada:\n" + itemList.get(position).getAnswer());
                    }
                }
            }
        });
    }


    public void getList() {
        data = new ArrayList<HashMap<String,String>>();
        for(int i = 0; i < itemList.size(); i++){

            HashMap<String, String> map = new HashMap<String, String>();
            AssetControlClass cc = itemList.get(i);
            map.put("codeAssetControl", cc.getCode());
            map.put("dateInit", cc.getDateTask());
            map.put("dateEnd", cc.getDateEndTask());
            map.put("equipmentAssetControl", cc.getAssignment());
            map.put("state", cc.getStateTask());

            data.add(map);
        }

        AssetControlAdapter adapter = new AssetControlAdapter(activity, data);
        itemListView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_asset, menu);
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
        }
        else if(id==R.id.action_add){
            dialogCreateAssetControl(context);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Util.logout(this, context);
    }

    public void dialogCreateAssetControl(final Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_asset_control_work);

        TextView head = (TextView) dialog.findViewById(R.id.text_title);
        head.setText("Nuevo Control de Activos");
        TextView content = (TextView) dialog.findViewById(R.id.text_content);
        content.setText("Ingrese los siguientes datos:");
        inputEquipment = (EditText) dialog.findViewById(R.id.tx_input_equipment);
        inputCodeEquipment = (EditText) dialog.findViewById(R.id.tx_input_id);

        inputCodeEquipment.setFilters(new InputFilter[] {new InputFilter.AllCaps()});



        Button dialogButtonOk = (Button) dialog.findViewById(R.id.alert_ok);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.alert_cancel);

        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(inputCodeEquipment.getText().toString().equals("") || inputEquipment.getText().toString().equals("")){
                    Toast.makeText(context, "No puede dejar ningun campo vacio.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(cd.isConnectingToInternet()){
                        new newAssetControlTask().execute(true);
                    }
                    else{
                        AssetControlClass asset = new AssetControlClass();
                        asset.setId(SqliteClass.getInstance(context).databasehelp.assetcontrolsql.getAssetControlData().size());
                        asset.setCode(inputCodeEquipment.getText().toString());
                        asset.setAssignment(inputEquipment.getText().toString());
                        asset.setCodeBranch(customer.getCodeBranch());
                        asset.setCodeOffice(customer.getCodeOffice());
                        asset.setNameRoute(customer.getCodeRoute());
                        asset.setCodeCustomer(customer.getCode());
                        asset.setDateTask(Util.getActualDateHourParse());
                        asset.setOrigin("M");
                        asset.setType("Instance");
                        asset.setLoad("0");

                        SqliteClass.getInstance(context).databasehelp.assetcontrolsql.addAssetControl(asset);
                        Toast.makeText(context, "No hay conexión a Internet. Se guardará en Pendientes de Sincronización", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
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

    class newAssetControlTask extends AsyncTask<Boolean, Void, String> {
        @Override
        protected String doInBackground(Boolean... params) {
            // TODO Auto-generated method stub
            String responseString = null;

            try {
                JSONObject jsonobj;
                common = new Common();
                jsonobj = new JSONObject();

                jsonobj.put("code", inputCodeEquipment.getText().toString());
                jsonobj.put("assignment", inputEquipment.getText().toString());
                jsonobj.put("date", Util.getActualDateHourParse());
                jsonobj.put("branch", customer.getCodeBranch());
                jsonobj.put("office", customer.getCodeOffice());
                jsonobj.put("route", customer.getCodeRoute());
                jsonobj.put("customer", customer.getCode());
                jsonobj.put("user", ConstValue.getUserId());

                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                nvps.add(new BasicNameValuePair("result", jsonobj.toString()));
                System.out.println("JSON ASSET CONTROL A ENVIAR " + jsonobj.toString());

                JSONObject jObj = common.sendJsonData(ConstValue.JSON_SEND_ASSET_CONTROL, nvps);

                if(jObj.getString("response").equalsIgnoreCase("success")){}
                else {
                    responseString = jObj.getString("response");
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
            dialogLoading = ProgressDialog.show(context, "", "Enviando control de activos. Por favor, espere ...", true);
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Toast.makeText(context, "CloudSales " + result, Toast.LENGTH_LONG).show();
            } else {
                Util.infoDialog(AssetControlActivity.this, context, "CloudSales - Control de Activos", "El control de activos se guardo con éxito.");
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



}
