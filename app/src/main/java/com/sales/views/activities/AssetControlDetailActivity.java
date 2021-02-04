package com.sales.views.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sales.R;
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

public class AssetControlDetailActivity extends AppCompatActivity {

    Common common;
    Context context;
    ConnectionDetector cd;
    ProgressDialog dialogLoading;
    EditText equipment, codeEquipment, dateAssignment, dateStart, dateEnd;
    AssetControlClass assetControlClass;
    CustomerClass cc;
    EditText inputObservation;

    FloatingActionButton fabEvent, fabAddObservation;
    boolean isFABOpen = false;
    FrameLayout flObservation;
    TextView txObservation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_control_detail);
        cd = new ConnectionDetector(this);
        cc = SqliteClass.getInstance(context).databasehelp.customersql.getCustomer(ConstValue.getCustomerId());
        getSupportActionBar().setTitle(cc.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        final int idAssetControl = ConstValue.getAssetControlId();
        assetControlClass = SqliteClass.getInstance(context).databasehelp.assetcontrolsql.getAssetControl(String.valueOf(idAssetControl));

        fabEvent = (FloatingActionButton) findViewById(R.id.action_principal);
        fabAddObservation = (FloatingActionButton) findViewById(R.id.action_add_observation);
        flObservation = (FrameLayout) findViewById(R.id.frame_observation);
        txObservation = (TextView) findViewById(R.id.text_observation); txObservation.setVisibility(View.GONE);

        fabEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFABOpen){
                    showFABMenu();
                }
                else{
                    closeFABMenu();
                }
            }
        });

        fabAddObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCreateAssetControl(context, idAssetControl);
            }
        });

        equipment = (EditText) findViewById(R.id.tx_input_equipment);
        codeEquipment = (EditText) findViewById(R.id.tx_input_equipment_code);
        dateAssignment = (EditText) findViewById(R.id.tx_input_date_assignment);
        dateStart = (EditText) findViewById(R.id.tx_input_date_start);
        dateEnd = (EditText) findViewById(R.id.tx_input_date_end);

        equipment.setText(assetControlClass.getAssignment());
        codeEquipment.setText(assetControlClass.getCode());
        dateAssignment.setText(assetControlClass.getDateAssign());
        dateStart.setText(assetControlClass.getDateTask() + " " + assetControlClass.getHourTask());
        dateEnd.setText(assetControlClass.getDateEndTask() + " " + assetControlClass.getHourEndTask());


    }

    private void showFABMenu(){
        isFABOpen=true;
        flObservation.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        txObservation.setVisibility(View.VISIBLE);

    }

    private void closeFABMenu(){
        isFABOpen=false;
        flObservation.animate().translationY(0);
        txObservation.setVisibility(View.GONE);
    }

    public void dialogCreateAssetControl(final Context context, final int idAssetControl){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_task_asset_control);

        TextView head = (TextView) dialog.findViewById(R.id.text_title);
        head.setText("CloudSales - Observación");
        TextView content = (TextView) dialog.findViewById(R.id.text_content);
        content.setText(assetControlClass.getTask());
        inputObservation = (EditText) dialog.findViewById(R.id.tx_input_observation);

        Button dialogButtonOk = (Button) dialog.findViewById(R.id.alert_ok);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.alert_cancel);

        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(inputObservation.getText().toString().equals("")){
                    Toast.makeText(context, "No puede dejar ningun campo vacío.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(cd.isConnectingToInternet()){
                        new observationAssetControlTask().execute(true);
                    }
                    else{
                        AssetControlClass asset = new AssetControlClass();
                        asset.setId(SqliteClass.getInstance(context).databasehelp.assetcontrolsql.getAssetControlData().size());
                        asset.setCode(assetControlClass.getCode());
                        asset.setAnswer(inputObservation.getText().toString());
                        asset.setIdTask(assetControlClass.getIdTask());
                        asset.setDateTask(Util.getActualDateHourParse());
                        asset.setOrigin("M");
                        asset.setType("Observation");
                        asset.setLoad("0");

                        SqliteClass.getInstance(context).databasehelp.assetcontrolsql.updateAnswerTaskById(idAssetControl, inputObservation.getText().toString(),"2");
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

    class observationAssetControlTask extends AsyncTask<Boolean, Void, String> {
        @Override
        protected String doInBackground(Boolean... params) {
            // TODO Auto-generated method stub
            String responseString = null;

            try {
                JSONObject jsonobj;
                common = new Common();
                jsonobj = new JSONObject();

                jsonobj.put("id_task", assetControlClass.getIdTask());
                jsonobj.put("answer", inputObservation.getText().toString());

                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                nvps.add(new BasicNameValuePair("result", jsonobj.toString()));
                System.out.println("JSON TASK CONTROL A ENVIAR " + jsonobj.toString());

                JSONObject jObj = common.sendJsonData(ConstValue.JSON_SEND_TASK, nvps);

                if(jObj.getString("response").equalsIgnoreCase("success")){
                    SqliteClass.getInstance(context).databasehelp.assetcontrolsql.updateAnswerTaskById(ConstValue.getAssetControlId(),inputObservation.getText().toString(),"0");
                }
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
                Util.infoDialog(AssetControlDetailActivity.this, context, "CloudSales - Control de Activos", "Su observación se guardó con éxito.");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        else if(id==R.id.action_logout){
            Util.logout(AssetControlDetailActivity.this, context);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Util.logout(this, context);
    }
}
