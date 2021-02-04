package com.sales.views.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Dash;
import com.sales.R;
import com.sales.config.ConstValue;
import com.sales.db.SqliteClass;
import com.sales.models.CustomerClass;
import com.sales.models.SynVisitClass;
import com.sales.models.VisitClass;
import com.sales.utils.Common;
import com.sales.utils.ConnectionDetector;
import com.sales.utils.SearchModel;
import com.sales.utils.Util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

public class DashboardActivity extends AppCompatActivity {

    Common common;
    Context context;
    ConnectionDetector cd;
    CardView collection, assetControl, directSale, order, changeProduct, _return;
    ImageView setVisit;
    ArrayList<VisitClass> loadVisit;
    SynVisitClass visitApp; ArrayList<SynVisitClass> loadVisitApp;
    ProgressDialog dialogLoading;
    boolean existsAnyVisitDone;
    CustomerClass cc;
    LocationManager mLocationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        context = this;

        cd =new ConnectionDetector(this);
        cc = SqliteClass.getInstance(context).databasehelp.customersql.getCustomer(ConstValue.getCustomerId());

        getSupportActionBar().setTitle(cc.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setVisit = (ImageView) findViewById(R.id.ic_set_visit);

        existsAnyVisitDone = SqliteClass.getInstance(context).databasehelp.synvisitsql.checkIfExists(ConstValue.getCustomerCode());
        if(existsAnyVisitDone){ setVisit.setImageResource(R.drawable.oscuro_40); }

        collection = (CardView) findViewById(R.id.cd_collection);
        assetControl = (CardView) findViewById(R.id.cd_asset_control);
        directSale = (CardView) findViewById(R.id.cd_direct_sale);
        order = (CardView) findViewById(R.id.cd_order);
        changeProduct = (CardView) findViewById(R.id.cd_change_product);
        _return = (CardView) findViewById(R.id.cd_return);

        loadVisit = SqliteClass.getInstance(context).databasehelp.visitSql.getVisitData();
        setVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SimpleSearchDialogCompat(context, "MARCAR VISITA",
                        "Seleccione una opción ...", null, initData(), new SearchResultListener<Searchable>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat baseSearchDialogCompat, Searchable searchable, int i) {
                        try{
                            mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
                            List<String> providers = mLocationManager.getProviders(true);
                            Location bestLocation = null;
                            for (String provider : providers) {
                                @SuppressLint("MissingPermission") Location l = mLocationManager.getLastKnownLocation(provider);
                                if (l == null) {
                                    continue;
                                }
                                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                                    bestLocation = l; // Found best last known location: %s", l);
                                }
                            }
                            Location location = bestLocation;

                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            double distanceCalc = Util.getProximityRate(latitude, Double.parseDouble(cc.getLatitude()),
                                    longitude, Double.parseDouble(cc.getLongitude()));
                            String zone = Util.getProximityRateZone(distanceCalc, ConstValue.getProximateRange());

                            int numberCustomerPending = SqliteClass.getInstance(context).databasehelp.customersql.getPendingCustomerCount(String.valueOf(ConstValue.getCustomerId()));

                            SynVisitClass visitApp = new SynVisitClass(0, ConstValue.getUserId(), cc.getCode(), cc.getName(),
                                    String.valueOf(loadVisit.get(i).getId()), loadVisit.get(i).getDescription(), cc.getLatitude(),
                                    cc.getLongitude(), String.valueOf(latitude), String.valueOf(longitude), zone, String.valueOf(distanceCalc),
                                    Util.getActualDateHourParse(), String.valueOf(numberCustomerPending), "0");

                            SqliteClass.getInstance(context).databasehelp.synvisitsql.addVisitApp(visitApp);
                            SqliteClass.getInstance(context).databasehelp.customersql.updateCustomerVisit(String.valueOf(cc.getId()), "2");

                            alertFinish();
                        }
                        catch(Exception e){
                            e.printStackTrace();
                            Toast.makeText(context, "Hubo un error al guardar la visita.", Toast.LENGTH_SHORT).show();

                        }
                        baseSearchDialogCompat.dismiss();
                    }
                }).show();

            }
        });

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Función no Disponible", Toast.LENGTH_SHORT).show();
            }
        });

        assetControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AssetControlActivity.class);
                startActivity(intent);
            }
        });

        directSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.alert_info);
                ImageView image = (ImageView) dialog.findViewById(R.id.alert_info);
                image.setImageResource(R.drawable.ic_alert_info);
                TextView head = (TextView) dialog.findViewById(R.id.alert_info_title);
                head.setText("CloudSales - Venta Sugerida");
                TextView content = (TextView) dialog.findViewById(R.id.alert_info_content);
                content.setText("¿Desea cargar el catálogo de Venta Sugerida?");
                Button dbOk = (Button) dialog.findViewById(R.id.alert_ok);
                dbOk.setText("CARGAR CATALOGO");
                dbOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConstValue.setSuggestedState("1");
                        new loadDirectSaleTask().execute(true);
                        dialog.dismiss();
                    }
                });
                Button dbCancel = (Button) dialog.findViewById(R.id.alert_cancel);
                dbCancel.setText("NO CARGAR");
                dbCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConstValue.setSuggestedState("0");
                        new loadDirectSaleTask().execute(true);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.alert_info);
                ImageView image = (ImageView) dialog.findViewById(R.id.alert_info);
                image.setImageResource(R.drawable.ic_alert_info);
                TextView head = (TextView) dialog.findViewById(R.id.alert_info_title);
                head.setText("CloudSales - Venta Sugerida");
                TextView content = (TextView) dialog.findViewById(R.id.alert_info_content);
                content.setText("¿Desea cargar el catálogo de Pedido de Cliente Sugerido ?");
                Button dbOk = (Button) dialog.findViewById(R.id.alert_ok);
                dbOk.setText("CARGAR CATALOGO");
                dbOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConstValue.setSuggestedState("1");
                        new loadOrderTask().execute(true);
                        dialog.dismiss();
                    }
                });
                Button dbCancel = (Button) dialog.findViewById(R.id.alert_cancel);
                dbCancel.setText("NO CARGAR");
                dbCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConstValue.setSuggestedState("0");
                        new loadOrderTask().execute(true);
                        dialog.dismiss();
                    }
                });
                dialog.show();



            }
        });

        changeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadReturnChangeTask task = new loadReturnChangeTask("C");
                task.execute(true);
            }
        });

        _return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadReturnChangeTask task = new loadReturnChangeTask("R");
                task.execute(true);
            }
        });

    }

    private ArrayList<SearchModel> initData(){
        ArrayList<SearchModel> items = new ArrayList<>();
        for(int i = 0; i < loadVisit.size(); i++){
            items.add(new SearchModel(loadVisit.get(i).getDescription(), loadVisit.get(i).getId()+""));
        }
        return items;
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
            Util.logout(DashboardActivity.this, context);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Util.logout(DashboardActivity.this, context);
    }

    public void alertFinish(){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_info);
        ImageView image = (ImageView) dialog.findViewById(R.id.alert_info);
        image.setImageResource(R.drawable.ic_alert_info);
        TextView head = (TextView) dialog.findViewById(R.id.alert_info_title);
        head.setText("CloudSales - Marcar Visita");
        TextView content = (TextView) dialog.findViewById(R.id.alert_info_content);
        content.setText("Visita generada con éxito. ¿Desea enviarla ahora? De no ser así, será guardada en Pendientes de Sincronización.");
        Button dbOk = (Button) dialog.findViewById(R.id.alert_ok);
        dbOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cd.isConnectingToInternet()){
                    new visitTask().execute(true);
                }
                else{
                    Toast.makeText(context, "El dispositivo no cuenta con conexión a Internet. " +
                            "La Visita será almacenada en Pendientes de Sincronización.", Toast.LENGTH_SHORT).show();
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

    /** Task for sending visits */
    class visitTask extends AsyncTask<Boolean, Void, String> {
        @Override
        protected String doInBackground(Boolean... params) {
            // TODO Auto-generated method stub
            String responseString = null;
            loadVisitApp = SqliteClass.getInstance(context).databasehelp.synvisitsql.getVisitAppPendingData();

            try {
                JSONObject jsonobj;
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
            } catch (JSONException e) {
                responseString = e.toString();
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }

            return responseString;
        }



        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            dialogLoading = ProgressDialog.show(context, "", "Enviando visita. Por favor, espere ...", true);
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Toast.makeText(context, "CloudSales " + result, Toast.LENGTH_LONG).show();
            } else {
                Util.infoDialog(DashboardActivity.this, context, "CloudSales - Visitas", "La visita se guardo con éxito.");
                setVisit.setImageResource(R.drawable.oscuro_40);
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

    class loadDirectSaleTask extends AsyncTask<Boolean, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            dialogLoading = ProgressDialog.show(context, "CloudSales", "Cargando Venta Directa. Espere ...", true);
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            //startService(new Intent(context, ServicePM.class));
            if (result != null) {
                Toast.makeText(context, "CloudSales " + result, Toast.LENGTH_LONG).show();
            } else {
                System.out.println(ConstValue.getOrderItems());
                Intent intent = new Intent(DashboardActivity.this, DirectSaleActivity.class);
                startActivity(intent);
            }
            // TODO Auto-generated method stub
            dialogLoading.dismiss();
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
        @Override
        protected String doInBackground(Boolean... params) {
            // TODO Auto-generated method stub
            String responseString = null;

            try {
                Util.loadDirectSaleItems(context, cc);
                Util.fillItemPrices(context, cc);
                //Util.fillItemTaxes(context, cc); //no lo llamo porque dentro de la funcion llamo al impuesto de cada producto


            } catch (Exception e) {
                responseString = e.toString();
                Log.e("LOADING ERROR", "Error loading " + e.toString());
            }
            return responseString;
        }
    }

    class loadOrderTask extends AsyncTask<Boolean, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            dialogLoading = ProgressDialog.show(context, "CloudSales", "Cargando Pedido. Espere ...", true);
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            //startService(new Intent(context, ServicePM.class));
            if (result != null) {
                Toast.makeText(context, "CloudSales " + result, Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(DashboardActivity.this, OrderActivity.class);
                startActivity(intent);
            }
            // TODO Auto-generated method stub
            dialogLoading.dismiss();
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
        @Override
        protected String doInBackground(Boolean... params) {
            // TODO Auto-generated method stub
            String responseString = null;

            try {
                Util.loadOrderCustomerItems(context, cc);
                Util.fillItemPrices(context, cc);
                //Util.fillItemTaxes(context, cc); //no lo llamo porque dentro de la funcion llamo al impuesto de cada producto y ademas es pedido, no es necesario!


            } catch (Exception e) {
                responseString = e.toString();
                Log.e("LOADING ERROR", "Error loading " + e.toString());
            }
            return responseString;
        }
    }

    class loadReturnChangeTask extends AsyncTask<Boolean, Void, String> {

        public String type;

        public loadReturnChangeTask(String type){
            this.type = type;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            if(type.equals("C")){
                dialogLoading = ProgressDialog.show(context, "CloudSales", "Cargando Cambio de Producto. Espere ...", true);
            }

            else if(type.equals("R")){
                dialogLoading = ProgressDialog.show(context, "CloudSales", "Cargando Retorno de Producto. Espere ...", true);
            }
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            //startService(new Intent(context, ServicePM.class));
            if (result != null) {
                Toast.makeText(context, "CloudSales " + result, Toast.LENGTH_LONG).show();
            } else {
                if(type.equals("C")){
                    Intent intent = new Intent(DashboardActivity.this, ChangeItemActivity.class);
                    startActivity(intent);
                }
                else if(type.equals("R")){
                    Intent intent = new Intent(DashboardActivity.this, ReturnActivity.class);
                    startActivity(intent);
                }

            }
            // TODO Auto-generated method stub
            dialogLoading.dismiss();
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
        @Override
        protected String doInBackground(Boolean... params) {
            // TODO Auto-generated method stub
            String responseString = null;

            try {
                Util.loadChangeReturnItems(context, cc);
                Util.fillItemPrices(context, cc);
                //Util.fillItemTaxes(context, cc); //no lo llamo porque dentro de la funcion llamo al impuesto de cada producto


            } catch (Exception e) {
                responseString = e.toString();
                Log.e("LOADING ERROR", "Error loading " + e.toString());
            }
            return responseString;
        }
    }


}
