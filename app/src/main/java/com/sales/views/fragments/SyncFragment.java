package com.sales.views.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sales.R;
import com.sales.adapters.GeneralSpinnerAdapter;
import com.sales.adapters.SynchronizationAdapter;
import com.sales.config.ConstValue;
import com.sales.db.SqliteClass;
import com.sales.models.AssetControlClass;
import com.sales.models.CategoryClass;
import com.sales.models.ConfigurationClass;
import com.sales.models.CustomerClass;
import com.sales.models.DiscountListClass;
import com.sales.models.DocumentClass;
import com.sales.models.ItemClass;
import com.sales.models.PaperClass;
import com.sales.models.PriceListClass;
import com.sales.models.PromoListClass;
import com.sales.models.StockClass;
import com.sales.models.SuggestedClass;
import com.sales.models.SynchronizationClass;
import com.sales.models.TypeDocumentClass;
import com.sales.models.VisitClass;
import com.sales.utils.Common;
import com.sales.utils.ConnectionDetector;
import com.sales.utils.JSONParser;
import com.sales.utils.Util;
import com.sales.views.activities.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.spec.ECField;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SyncFragment extends Fragment {

    Activity activity;
    Context context;
    Common common;
    ConnectionDetector cd;
    static ArrayList<HashMap<String, String>> data;
    ListView listView;
    ArrayList<String> syncOptions;

    ProgressDialog dialog;

    Spinner spSyncOptions;
    View rootView;

    ListView itemListView;
    List<SynchronizationClass> itemList;

    boolean isFABOpen = false, allChecked = false;
    FrameLayout flOrderNewEvent, flOrderPhoto;
    TextView tvOrderNewEvent, tvOrderPhoto;

    private static final String FRAGMENT_NAME = "SyncFragment";


    private CustomerClass Customer; private ArrayList<CustomerClass> loadCustomer;
    private CategoryClass Category; private ArrayList<CategoryClass> loadCategory;
    private ItemClass Item; private ArrayList<ItemClass> loadItem;
    private PriceListClass PriceList; private ArrayList<PriceListClass> loadPriceList;
    private DiscountListClass DiscountList; private ArrayList<DiscountListClass> loadDiscountList;
    private PromoListClass PromoList; private ArrayList<PromoListClass> loadPromoList;
    private VisitClass Visit; private ArrayList<VisitClass> loadVisit;
    private AssetControlClass AssetControl; private ArrayList<AssetControlClass> loadAssetControl;
    private StockClass StockClass; private ArrayList<StockClass> loadStock;

    private TypeDocumentClass TypeDocument; private ArrayList<TypeDocumentClass> loadTypeDocument;
    private DocumentClass Document; private ArrayList<DocumentClass> loadDocument;
    private PaperClass Paper; private ArrayList<PaperClass> loadPaper;
    private SuggestedClass Suggested; private ArrayList<SuggestedClass> loadSuggested;
    private ConfigurationClass Configuration; private ArrayList<ConfigurationClass> loadConfiguration;


    public SyncFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        syncOptions = new ArrayList<String>();
        syncOptions.add("SINCRONIZADAS"); syncOptions.add("SINCRONIZAR");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        common = new Common();
        activity = getActivity();
        cd = new ConnectionDetector(getActivity());
        rootView = inflater.inflate(R.layout.fragment_sync, container, false);

        data = new ArrayList<HashMap<String,String>>();
        itemListView = (ListView) rootView.findViewById(R.id.lst_classes_sync);
        itemList = SqliteClass.getInstance(context).databasehelp.synchronizationsql.getSynchronizationData();
        final TextView emptyTextView = (TextView) rootView.findViewById(android.R.id.empty);

        final FloatingActionButton fabEvent = (FloatingActionButton) rootView.findViewById(R.id.action_new_event_trip);
        flOrderNewEvent = (FrameLayout)rootView.findViewById(R.id.frame_order_new_event);
        flOrderPhoto = (FrameLayout)rootView.findViewById(R.id.frame_order_photo);
        tvOrderNewEvent = (TextView) rootView.findViewById(R.id.text_order_new_event); tvOrderNewEvent.setVisibility(View.GONE);
        tvOrderPhoto = (TextView) rootView.findViewById(R.id.text_order_potho); tvOrderPhoto.setVisibility(View.GONE);

        fabEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        spSyncOptions = (Spinner) rootView.findViewById(R.id.sp_sync_options);
        spSyncOptions.setAdapter(new GeneralSpinnerAdapter(context, R.layout.spinner_row, syncOptions, FRAGMENT_NAME));
        spSyncOptions.getBackground().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);


        spSyncOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> spn, android.view.View v, int position, long id) {
                switch (position) {
                    case 0:
                        fabEvent.setVisibility(View.GONE); flOrderNewEvent.setVisibility(View.GONE); flOrderPhoto.setVisibility(View.GONE);//fabSync.setVisibility(View.GONE); syncFab.setVisibility(View.GONE);
                        itemList = SqliteClass.getInstance(context).databasehelp.synchronizationsql.getSynchronizationData();
                        getList();
                        if(itemList.size()>0) emptyTextView.setVisibility(View.GONE); else emptyTextView.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        fabEvent.setVisibility(View.VISIBLE); flOrderNewEvent.setVisibility(View.VISIBLE); flOrderPhoto.setVisibility(View.VISIBLE);
                        Util.getClassesList(activity, itemListView);
                        emptyTextView.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        flOrderNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CANTIDAD DE HIJOS "  +  itemListView.getChildCount());
                if(allChecked){
                    for ( int i=0; i < itemListView.getChildCount(); i++) {
                        itemListView.setItemChecked(i, false);
                    }
                    allChecked = false;
                }
                else{
                    for ( int i=0; i < itemListView.getChildCount(); i++) {
                        itemListView.setItemChecked(i, true);
                    }
                    allChecked = true;
                }
            }
        });

        flOrderPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemListView.getCheckedItemCount() > 0){
                    final SparseBooleanArray sparseBooleanArray = itemListView.getCheckedItemPositions();

                    if(sparseBooleanArray.get(0)) { ConstValue.setCheckedCustomers("1"); }
                    else { ConstValue.setCheckedCustomers("0"); }
                    if(sparseBooleanArray.get(1)) { ConstValue.setCheckedCategories("1"); }
                    else { ConstValue.setCheckedCategories("0"); }
                    if(sparseBooleanArray.get(2)) { ConstValue.setCheckedItems("1"); }
                    else { ConstValue.setCheckedItems("0"); }
                    if(sparseBooleanArray.get(3)) { ConstValue.setCheckedPriceList("1"); }
                    else { ConstValue.setCheckedPriceList("0"); }
                    if(sparseBooleanArray.get(4)) { ConstValue.setCheckedDiscountList("1"); }
                    else { ConstValue.setCheckedDiscountList("0"); }
                    if(sparseBooleanArray.get(5)) { ConstValue.setCheckedPromoList("1"); }
                    else { ConstValue.setCheckedPromoList("0"); }
                    if(sparseBooleanArray.get(6)) { ConstValue.setCheckedVisits("1"); }
                    else { ConstValue.setCheckedVisits("0"); }
                    if(sparseBooleanArray.get(7)) { ConstValue.setCheckedAssetControl("1"); }
                    else { ConstValue.setCheckedAssetControl("0"); }
                    if(sparseBooleanArray.get(8)) { ConstValue.setCheckedStock("1"); }
                    else { ConstValue.setCheckedStock("0"); }
                    if(sparseBooleanArray.get(9)) { ConstValue.setCheckedDocuments("1");}
                    else { ConstValue.setCheckedDocuments("0");}
                    if(sparseBooleanArray.get(10)) { ConstValue.setCheckedTickets("1");}
                    else { ConstValue.setCheckedTickets("0"); }
                    if(sparseBooleanArray.get(11)) { ConstValue.setCheckedCatalogue("1");}
                    else { ConstValue.setCheckedCatalogue("0"); }
                    if(sparseBooleanArray.get(12)) { ConstValue.setCheckedConfiguration("1");}
                    else { ConstValue.setCheckedConfiguration("0"); }
                    String message = "";
                    if(sparseBooleanArray.get(0)) message += "\n - \tClientes";
                    if(sparseBooleanArray.get(1)) message += "\n - \tCategorías";
                    if(sparseBooleanArray.get(2)) message += "\n - \tProductos";
                    if(sparseBooleanArray.get(3)) message += "\n - \tLista de Precios";
                    if(sparseBooleanArray.get(4)) message += "\n - \tLista de Descuentos";
                    if(sparseBooleanArray.get(5)) message += "\n - \tLista de Promociones";
                    if(sparseBooleanArray.get(6)) message += "\n - \tVisitas";
                    if(sparseBooleanArray.get(7)) message += "\n - \tControl de Activos";
                    if(sparseBooleanArray.get(8)) message += "\n - \tStock";
                    if(sparseBooleanArray.get(9)) message += "\n - \tDocumentos";
                    if(sparseBooleanArray.get(10)) message += "\n - \tTicket";
                    if(sparseBooleanArray.get(11)) message += "\n - \tCatálogos Sugeridos";
                    if(sparseBooleanArray.get(12)) message += "\n - \tConfiguración de Usuario";

                    final Dialog dialog = new Dialog(activity);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.alert_info);
                    TextView head = (TextView) dialog.findViewById(R.id.alert_info_title);
                    head.setText("Cloud Sales - Sincronizar Información");
                    TextView content = (TextView) dialog.findViewById(R.id.alert_info_content);
                    content.setText("¿Esta seguro de descargar nueva información del servidor? \n" +
                            "Se descargaran los siguientes datos: \n" + message);

                    Button dbOk = (Button) dialog.findViewById(R.id.alert_ok);
                    dbOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(cd.isConnectingToInternet()){ new loadTask().execute(true); }
                            else{
                                Util.infoDialog(getActivity(), context, "CloudSales", "No es posible sincronizar. Su dispositivo no cuenta con conexión a internet.");
                            }
                            dialog.dismiss();
                        }
                    });
                    Button dbCancel = (Button) dialog.findViewById(R.id.alert_cancel);
                    dbCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) { dialog.dismiss(); }
                    });
                    dialog.show();
                }
                else{ Toast.makeText(context, "Seleccione alguna opción para sincronizar.", Toast.LENGTH_SHORT).show(); }
            }
        });

        return rootView;
    }

    public void getList() {
        data = new ArrayList<HashMap<String,String>>();
        itemListView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        for(int z=0; z < itemList.size(); z++){
            SynchronizationClass cc = itemList.get(z);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", cc.getId()+"");
            map.put("class_name", cc.getClassName());
            map.put("date_hour_start", cc.getDateHourStart());
            map.put("date_hour_end", cc.getDateHourEnd());
            map.put("registers", cc.getRegisterNumber());
            map.put("failures", cc.getFailureNumber());
            map.put("state", cc.getState());
            data.add(map);
        }
        SynchronizationAdapter adapter = new SynchronizationAdapter(activity, data);
        itemListView.setAdapter(adapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void showFABMenu(){
        isFABOpen=true;
        flOrderNewEvent.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        tvOrderNewEvent.setVisibility(View.VISIBLE);
        flOrderPhoto.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        tvOrderPhoto.setVisibility(View.VISIBLE);
    }
    private void closeFABMenu(){
        isFABOpen=false;
        flOrderNewEvent.animate().translationY(0);
        tvOrderNewEvent.setVisibility(View.GONE);
        flOrderPhoto.animate().translationY(0);
        tvOrderPhoto.setVisibility(View.GONE);
    }

    class loadTask extends AsyncTask<Boolean, Void, String> {

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(activity, "", "Sincronizando, espere por favor...", true);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Toast.makeText(activity, "CloudSales " + result, Toast.LENGTH_LONG).show();
            } else {
                ConstValue.setCheckedCustomers("0");
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.alert_info);
                ImageView image = (ImageView) dialog.findViewById(R.id.alert_info);
                image.setImageResource(R.drawable.ic_alert_info);
                TextView head = (TextView) dialog.findViewById(R.id.alert_info_title);
                head.setText("CloudSales - Sincronización");
                TextView content = (TextView) dialog.findViewById(R.id.alert_info_content);
                content.setText("La sincronización fue exitosa.");
                Button dbOk = (Button) dialog.findViewById(R.id.alert_ok);
                dbOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("fragment_position", 7);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                Button dbCancel = (Button) dialog.findViewById(R.id.alert_cancel);
                dbCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { dialog.dismiss(); }
                });
                dialog.show();
            }
            // TODO Auto-generated method stub
            dialog.dismiss();
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
            String responseString = null;
            SimpleDateFormat dateFormatHour = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            SimpleDateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();
            String _date_sta = dateFormatDate.format(date);
            String _hour_sta = dateFormatHour.format(date);
            try {
                JSONParser jParser;
                JSONObject json;
                String url = "";
                if(ConstValue.getCheckedCustomers().equals("1")){
                    SqliteClass.getInstance(context).databasehelp.customersql.deleteCustomeNotVisited(); //Elimino todos los que tenian visita en 0
                    loadCustomer =  new ArrayList<CustomerClass>();
                    jParser = new JSONParser();
                    url = ConstValue.JSON_CUSTOMER + "&route=" + ConstValue.getUserRouteCode();
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("MI JSON CUSTOMER: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");
                            int correctRegister = 0;
                            int failureRegister = 0;
                            for(int j=0;j<jsonList.length(); j++){
                                JSONObject o =  jsonList.getJSONObject(j);
                                int idComing = o.getInt("Id");
                                boolean exists = SqliteClass.getInstance(context).databasehelp.customersql.checkIfExists(String.valueOf(idComing));
                                try{
                                    if(exists){
                                        CustomerClass cc;
                                        cc = SqliteClass.getInstance(context).databasehelp.customersql.getCustomer(idComing);
                                        if(cc.getVisited().equals("2")){
                                            System.out.println("ENTRO A LA PARTE DE VISITA 1 ACTUALIZAR");

                                            Customer =  new CustomerClass(o.getInt("Id"), o.getString("Code"), o.getString("Name"),
                                                    o.getString("RUC"), o.getString("Area"), o.getString("Address"), o.getString("Code_Country"),
                                                    o.getString("Code_Department"), o.getString("Code_City"), o.getString("Code_Colony"),
                                                    o.getString("Representative"), o.getString("Day"), o.getString("Phone"),
                                                    o.getString("Mobile_Phone"), o.getString("Code_Route"), o.getString("Weight"), o.getString("Latitude"),
                                                    o.getString("Longitude"), o.getString("Type"), o.getString("_Class"), o.getString("Priority"),
                                                    o.getString("Pro"), o.getString("Code_Branch"), o.getString("Code_Office"), o.getString("NameCity"),
                                                    o.getString("NameColony"), o.getString("NameOffice"), o.getString("NameRoute"),
                                                    o.getString("Code_PriceList"), o.getString("Code_Discount"),o.getString("Code_Promotion"),
                                                    o.getString("Active"), o.getString("State"), "0", "0");


                                            SqliteClass.getInstance(context).databasehelp.customersql.updateCustomer(idComing, Customer);
                                        }
                                    }
                                    else{
                                        System.out.println("ENTRO A LA PARTE DE DESCARGAR UNO NUEVO");
                                        Customer =  new CustomerClass(o.getInt("Id"), o.getString("Code"), o.getString("Name"),
                                                o.getString("RUC"), o.getString("Area"), o.getString("Address"), o.getString("Code_Country"),
                                                o.getString("Code_Department"), o.getString("Code_City"), o.getString("Code_Colony"),
                                                o.getString("Representative"), o.getString("Day"), o.getString("Phone"),
                                                o.getString("Mobile_Phone"), o.getString("Code_Route"), o.getString("Weight"), o.getString("Latitude"),
                                                o.getString("Longitude"), o.getString("Type"), o.getString("_Class"), o.getString("Priority"),
                                                o.getString("Pro"), o.getString("Code_Branch"), o.getString("Code_Office"), o.getString("NameCity"),
                                                o.getString("NameColony"), o.getString("NameOffice"), o.getString("NameRoute"),
                                                o.getString("Code_PriceList"), o.getString("Code_Discount"),o.getString("Code_Promotion"),
                                                o.getString("Active"), o.getString("State"), "0", "0");
                                        loadCustomer.add(Customer);
                                        SqliteClass.getInstance(context).databasehelp.customersql.addCustomer(Customer);

                                    }
                                    correctRegister++;
                                }
                                catch(Exception e){
                                    responseString = e.toString();
                                    e.printStackTrace();
                                    failureRegister++;
                                }
                            }
                            Date _date = new Date();
                            String _date_end = dateFormatDate.format(_date);
                            String _hour_end = dateFormatHour.format(_date);
                            /** Crear la sincronizacion */
                            String state = "1";
                            if(failureRegister > 0){ state = "0"; }
                            SynchronizationClass synchronizationClass = new SynchronizationClass(0, "Clientes", _date_sta +" "+_hour_sta,
                                    _date_end+" "+_hour_end, correctRegister+"", failureRegister+"", state);
                            SqliteClass.getInstance(context).databasehelp.synchronizationsql.addSynchronization(synchronizationClass);
                        }
                    }
                }

                if(ConstValue.getCheckedCategories().equals("1")){
                    loadCategory =  new ArrayList<CategoryClass>();
                    jParser = new JSONParser();
                    url = ConstValue.JSON_CATEGORY;
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("MI JSON CATEGORY: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");
                            int correctRegister = 0;
                            int failureRegister = 0;
                            for(int j=0;j<jsonList.length(); j++){
                                JSONObject o =  jsonList.getJSONObject(j);
                                int idComing = o.getInt("Id");
                                boolean exists = SqliteClass.getInstance(context).databasehelp.categorysql.checkIfExists(String.valueOf(idComing));
                                try{
                                    if(exists){
                                        System.out.println("ACTUALIZO EL REGISTRO CATEGORY");
                                        Category =  new CategoryClass(o.getInt("Id"), o.getString("Code"), o.getString("Description"),
                                                o.getString("_Order"), o.getString("State"));
                                        loadCategory.add(Category);
                                        SqliteClass.getInstance(context).databasehelp.categorysql.updateCategory(idComing, Category);
                                    }
                                    else{
                                        System.out.println("DESCARGO UNO NUEVO CATEGORY");
                                        Category =  new CategoryClass(o.getInt("Id"), o.getString("Code"), o.getString("Description"),
                                                o.getString("_Order"), o.getString("State"));
                                        loadCategory.add(Category);
                                        SqliteClass.getInstance(context).databasehelp.categorysql.addCategory(Category);
                                    }
                                    correctRegister++;
                                }
                                catch (Exception e){
                                    responseString = e.toString();
                                    e.printStackTrace();
                                    failureRegister++;
                                }
                            }
                            Date _date = new Date();
                            String _date_end = dateFormatDate.format(_date);
                            String _hour_end = dateFormatHour.format(_date);
                            /** Crear la sincronizacion */
                            String state = "1";
                            if(failureRegister > 0){ state = "0"; }
                            SynchronizationClass synchronizationClass = new SynchronizationClass(0, "Categorías", _date_sta +" "+_hour_sta,
                                    _date_end+" "+_hour_end, correctRegister+"", failureRegister+"", state);
                            SqliteClass.getInstance(context).databasehelp.synchronizationsql.addSynchronization(synchronizationClass);
                        }
                    }
                }

                if(ConstValue.getCheckedItems().equals("1")){

                    loadItem =  new ArrayList<ItemClass>();
                    jParser = new JSONParser();
                    url = ConstValue.JSON_ITEM;
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("MI JSON ITEM: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");
                            int correctRegister = 0;
                            int failureRegister = 0;
                            for(int j=0;j<jsonList.length(); j++){
                                JSONObject o =  jsonList.getJSONObject(j);
                                int idComing = o.getInt("Id");
                                boolean exists = SqliteClass.getInstance(context).databasehelp.itemsql.checkIfExists(String.valueOf(idComing));
                                try{
                                    if(exists){
                                        System.out.println("ACTUALIZO EL REGISTRO ITEM");
                                        Item =  new ItemClass(o.getInt("Id"), o.getString("Category"),
                                                o.getString("Code"), o.getString("Description"), o.getString("Tax"),
                                                o.getString("Measure_Unit"), o.getString("Liters_Lbs"), o.getString("_Order"),
                                                o.getString("CategoryName"), o.getString("MeasureName"), o.getString("State"),
                                                "", "", "", "");
                                        loadItem.add(Item);
                                        SqliteClass.getInstance(context).databasehelp.itemsql.updateItem(idComing, Item);
                                    }
                                    else{
                                        System.out.println("DESCARGO UN NUEVO ITEM");
                                        Item =  new ItemClass(o.getInt("Id"), o.getString("Category"),
                                                o.getString("Code"), o.getString("Description"), o.getString("Tax"),
                                                o.getString("Measure_Unit"), o.getString("Liters_Lbs"), o.getString("_Order"),
                                                o.getString("CategoryName"), o.getString("MeasureName"), o.getString("State"),
                                                "", "", "", "");
                                        loadItem.add(Item);
                                        SqliteClass.getInstance(context).databasehelp.itemsql.addItem(Item);
                                    }
                                    correctRegister++;
                                }
                                catch(Exception e){
                                    responseString = e.toString();
                                    e.printStackTrace();
                                    failureRegister++;
                                }
                            }
                            Date _date = new Date();
                            String _date_end = dateFormatDate.format(_date);
                            String _hour_end = dateFormatHour.format(_date);
                            /** Crear la sincronizacion */
                            String state = "1";
                            if(failureRegister > 0){ state = "0"; }
                            SynchronizationClass synchronizationClass = new SynchronizationClass(0, "Productos", _date_sta +" "+_hour_sta,
                                    _date_end+" "+_hour_end, correctRegister+"", failureRegister+"", state);
                            SqliteClass.getInstance(context).databasehelp.synchronizationsql.addSynchronization(synchronizationClass);
                        }
                    }
                }

                if(ConstValue.getCheckedPriceList().equals("1")){
                    loadPriceList =  new ArrayList<PriceListClass>();
                    jParser = new JSONParser();
                    url = ConstValue.JSON_PRICE_LIST;
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("MI JSON PRICE LIST: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");
                            int correctRegister = 0;
                            int failureRegister = 0;

                            for(int j=0;j<jsonList.length(); j++){
                                JSONObject o =  jsonList.getJSONObject(j);
                                int idComing = o.getInt("Id");
                                boolean exists = SqliteClass.getInstance(context).databasehelp.pricelistsql.checkIfExists(String.valueOf(idComing));
                                try{
                                    if(exists){
                                        System.out.println("ACTUALIZO EL REGISTRO PRICELIST");
                                        PriceList =  new PriceListClass(o.getInt("Id"), o.getString("Code"), o.getString("Code_Item"),
                                                o.getString("Price"), o.getString("Description_Item"), o.getString("Description_Price_List"), o.getString("State"));

                                        loadPriceList.add(PriceList);
                                        SqliteClass.getInstance(context).databasehelp.pricelistsql.updatePriceList(idComing, PriceList);
                                    }
                                    else{
                                        System.out.println("DESCARGO UNO NUEVO");
                                        PriceList =  new PriceListClass(o.getInt("Id"), o.getString("Code"), o.getString("Code_Item"),
                                                o.getString("Price"), o.getString("Description_Item"), o.getString("Description_Price_List"), o.getString("State"));

                                        loadPriceList.add(PriceList);
                                        SqliteClass.getInstance(context).databasehelp.pricelistsql.addPriceList(PriceList);
                                    }
                                    correctRegister++;
                                }
                                catch(Exception e){
                                    responseString = e.toString();
                                    e.printStackTrace();
                                    failureRegister++;
                                }
                            }
                            Date _date = new Date();
                            String _date_end = dateFormatDate.format(_date);
                            String _hour_end = dateFormatHour.format(_date);
                            /** Crear la sincronizacion */
                            String state = "1";
                            if(failureRegister > 0){ state = "0"; }
                            SynchronizationClass synchronizationClass = new SynchronizationClass(0, "Lista de Precios", _date_sta +" "+_hour_sta,
                                    _date_end+" "+_hour_end, correctRegister+"", failureRegister+"", state);
                            SqliteClass.getInstance(context).databasehelp.synchronizationsql.addSynchronization(synchronizationClass);
                        }
                    }
                }

                if(ConstValue.getCheckedDiscountList().equals("1")){

                    /*
                    loadDiscountList =  new ArrayList<DiscountListClass>();
                    jParser = new JSONParser();
                    url = ConstValue.JSON_DISCOUNT_LIST;
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("MI JSON DISCOUNT LIST: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");

                            int correctRegister = 0;
                            int failureRegister = 0;

                            for(int j=0;j<jsonList.length(); j++){
                                JSONObject o =  jsonList.getJSONObject(j);
                                int idComing = o.getInt("Id");
                                boolean exists = SqliteClass.getInstance(context).databasehelp.discountListsql.checkIfExists(String.valueOf(idComing));
                                try{
                                    if(exists){
                                        System.out.println("ACTUALIZO EL REGISTRO DISCOUNT");
                                        DiscountList =  new DiscountListClass(o.getInt("Id"), o.getString("Code"),
                                                o.getString("Code_Customer"),o.getString("Code_Category"),
                                                o.getString("Code_Item"), o.getString("Discount_Percentage"),
                                                o.getString("Date_Initial"), o.getString("Date_Finish"), o.getString("State"));

                                        loadDiscountList.add(DiscountList);
                                        SqliteClass.getInstance(context).databasehelp.discountListsql.updateDiscountList(idComing, DiscountList);
                                    }
                                    else{
                                        DiscountList =  new DiscountListClass(o.getInt("Id"), o.getString("Code"),
                                                o.getString("Code_Customer"),o.getString("Code_Category"),
                                                o.getString("Code_Item"), o.getString("Discount_Percentage"),
                                                o.getString("Date_Initial"), o.getString("Date_Finish"), o.getString("State"));

                                        loadDiscountList.add(DiscountList);
                                        SqliteClass.getInstance(context).databasehelp.discountListsql.addDiscountList(DiscountList);
                                    }
                                    correctRegister++;
                                }
                                catch (Exception e){
                                responseString = e.toString();
                                    e.printStackTrace();
                                    failureRegister++;
                                }
                            }
                            Date _date = new Date();
                            String _date_end = dateFormatDate.format(_date);
                            String _hour_end = dateFormatHour.format(_date);

                            String state = "1";
                            if(failureRegister > 0){ state = "0"; }
                            SynchronizationClass synchronizationClass = new SynchronizationClass(0, "Lista de Descuentos", _date_sta +" "+_hour_sta,
                                    _date_end+" "+_hour_end, correctRegister+"", failureRegister+"", state);
                            SqliteClass.getInstance(context).databasehelp.synchronizationsql.addSynchronization(synchronizationClass);
                        }
                    }
                    */
                }

                if(ConstValue.getCheckedPromoList().equals("1")){
                    loadPromoList =  new ArrayList<PromoListClass>();
                    jParser = new JSONParser();
                    url = ConstValue.JSON_PROMO_LIST;
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("MI JSON PROMO LIST: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");

                            int correctRegister = 0;
                            int failureRegister = 0;

                            for(int j=0;j<jsonList.length(); j++){
                                JSONObject o =  jsonList.getJSONObject(j);
                                int idComing = o.getInt("Id");
                                boolean exists = SqliteClass.getInstance(context).databasehelp.promolistsql.checkIfExists(String.valueOf(idComing));
                                try{
                                    if(exists){
                                        System.out.println("ACTUALIZO EL REGISTRO PROMO");
                                        PromoList =  new PromoListClass(o.getInt("Id"), o.getString("Code"),
                                                o.getString("Code_ItemOne"),  o.getString("QuantityOne"),
                                                o.getString("UnitMeasureOne"), o.getString("Code_ItemTwo"),
                                                o.getString("QuantityTwo"), o.getString("UnitMeasureTwo"),
                                                o.getString("NameItem1"), o.getString("NameItem2"), o.getString("NameUnit1"),
                                                o.getString("NameUnit2"), o.getString("State"), o.getString("CategoryOne"), o.getString("CategoryTwo"));

                                        loadPromoList.add(PromoList);
                                        SqliteClass.getInstance(context).databasehelp.promolistsql.updatePromoList(idComing, PromoList);
                                    }
                                    else{
                                        PromoList =  new PromoListClass(o.getInt("Id"), o.getString("Code"),
                                                o.getString("Code_ItemOne"),  o.getString("QuantityOne"),
                                                o.getString("UnitMeasureOne"), o.getString("Code_ItemTwo"),
                                                o.getString("QuantityTwo"), o.getString("UnitMeasureTwo"),
                                                o.getString("NameItem1"), o.getString("NameItem2"), o.getString("NameUnit1"),
                                                o.getString("NameUnit2"), o.getString("State"), o.getString("CategoryOne"), o.getString("CategoryTwo"));
                                        loadPromoList.add(PromoList);
                                        SqliteClass.getInstance(context).databasehelp.promolistsql.addPromoList(PromoList);
                                    }
                                    correctRegister++;
                                }
                                catch(Exception e) {
                                    responseString = e.toString();
                                    e.printStackTrace();
                                    failureRegister++;
                                }
                            }
                            Date _date = new Date();
                            String _date_end = dateFormatDate.format(_date);
                            String _hour_end = dateFormatHour.format(_date);
                            /** Crear la sincronizacion */
                            String state = "1";
                            if(failureRegister > 0){ state = "0"; }
                            SynchronizationClass synchronizationClass = new SynchronizationClass(0, "Lista de Promociones", _date_sta +" "+_hour_sta,
                                    _date_end+" "+_hour_end, correctRegister+"", failureRegister+"", state);
                            SqliteClass.getInstance(context).databasehelp.synchronizationsql.addSynchronization(synchronizationClass);
                        }
                    }
                }



                if(ConstValue.getCheckedVisits().equals("1")){
                    loadVisit =  new ArrayList<VisitClass>();
                    jParser = new JSONParser();
                    url = ConstValue.JSON_VISIT;
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("MI JSON VISIT: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");

                            int correctRegister = 0;
                            int failureRegister = 0;

                            for(int j=0;j<jsonList.length(); j++){
                                JSONObject o =  jsonList.getJSONObject(j);

                                int idComing = o.getInt("Id");
                                boolean exists = SqliteClass.getInstance(context).databasehelp.visitSql.checkIfExists(String.valueOf(idComing));
                                try{
                                    if(exists){
                                        System.out.println("ACTUALIZO EL REGISTRO VISIT");
                                        Visit =  new VisitClass(o.getInt("Id"), o.getString("Description"), o.getString("Code_Office"),
                                                o.getString("NameOffice"), o.getString("State"));

                                        loadVisit.add(Visit);
                                        SqliteClass.getInstance(context).databasehelp.visitSql.updateVisit(idComing, Visit);

                                    }
                                    else{
                                        System.out.println("DESCARGO UN VISIT NUEVA");
                                        Visit =  new VisitClass(o.getInt("Id"), o.getString("Description"), o.getString("Code_Office"),
                                                o.getString("NameOffice"), o.getString("State"));

                                        loadVisit.add(Visit);
                                        SqliteClass.getInstance(context).databasehelp.visitSql.addVisit(Visit);
                                    }
                                    correctRegister++;
                                }
                                catch(Exception e){
                                    responseString = e.toString();
                                    e.printStackTrace();
                                    failureRegister++;
                                }
                            }
                            Date _date = new Date();
                            String _date_end = dateFormatDate.format(_date);
                            String _hour_end = dateFormatHour.format(_date);
                            /** Crear la sincronizacion */
                            String state = "1";
                            if(failureRegister > 0){ state = "0"; }
                            SynchronizationClass synchronizationClass = new SynchronizationClass(0, "Visitas", _date_sta +" "+_hour_sta,
                                    _date_end+" "+_hour_end, correctRegister+"", failureRegister+"", state);
                            SqliteClass.getInstance(context).databasehelp.synchronizationsql.addSynchronization(synchronizationClass);
                        }
                    }
                }

                if(ConstValue.getCheckedAssetControl().equals("1")){
                    loadAssetControl =  new ArrayList<AssetControlClass>();
                    jParser = new JSONParser();
                    url = ConstValue.JSON_ASSET_CONTROL + "&iduser=" + ConstValue.getUserId();
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("MI JSON ASSET CONTROL: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");

                            int correctRegister = 0;
                            int failureRegister = 0;

                            for(int j=0;j<jsonList.length(); j++){
                                JSONObject o =  jsonList.getJSONObject(j);

                                String idComing = o.getString("Code");
                                boolean exists = SqliteClass.getInstance(context).databasehelp.assetcontrolsql.checkIfExists(String.valueOf(idComing));

                                try{
                                    if(exists){
                                        System.out.println("ACTUALIZO EL REGISTRO ASSET CONTROL");
                                        AssetControl =  new AssetControlClass(o.getInt("Id"), o.getString("Code"),
                                                o.getString("Assignment"), o.getString("Date_Assig"), o.getString("Code_Branch"),
                                                o.getString("Code_Office"), o.getString("Code_Route"), o.getString("Code_Customer"),
                                                o.getString("Id_Userapp"), o.getString("NameOffice"), o.getString("NameBranch"),
                                                o.getString("NameRoute"), o.getString("NameCustomer"), o.getString("State"),
                                                o.getString("Date_Task"), o.getString("Hour_Task"), o.getString("Date_EndTask"),
                                                o.getString("Hour_EndTask"), o.getString("Origin"),
                                                o.getString("Equipment"), o.getString("Type"), o.getString("Task"), o.getString("Answer"),
                                                o.getString("Code_Customer_Task"), o.getString("State_Task"), o.getString("Id_Task"), "0");

                                        loadAssetControl.add(AssetControl );
                                        SqliteClass.getInstance(context).databasehelp.assetcontrolsql.updateAssetControl(idComing, AssetControl);
                                    }
                                    else{
                                        AssetControl =  new AssetControlClass(o.getInt("Id"), o.getString("Code"),
                                                o.getString("Assignment"), o.getString("Date_Assig"), o.getString("Code_Branch"),
                                                o.getString("Code_Office"), o.getString("Code_Route"), o.getString("Code_Customer"),
                                                o.getString("Id_Userapp"), o.getString("NameOffice"), o.getString("NameBranch"),
                                                o.getString("NameRoute"), o.getString("NameCustomer"), o.getString("State"),
                                                o.getString("Date_Task"), o.getString("Hour_Task"), o.getString("Date_EndTask"),
                                                o.getString("Hour_EndTask"), o.getString("Origin"),
                                                o.getString("Equipment"), o.getString("Type"), o.getString("Task"), o.getString("Answer"),
                                                o.getString("Code_Customer_Task"), o.getString("State_Task"), o.getString("Id_Task"), "0");

                                        loadAssetControl.add(AssetControl);
                                        SqliteClass.getInstance(context).databasehelp.assetcontrolsql.addAssetControl(AssetControl);
                                    }
                                    correctRegister++;
                                }
                                catch(Exception e) {
                                    responseString = e.toString();
                                    e.printStackTrace();
                                    failureRegister++;
                                }
                            }
                            Date _date = new Date();
                            String _date_end = dateFormatDate.format(_date);
                            String _hour_end = dateFormatHour.format(_date);
                            /** Crear la sincronizacion */
                            String state = "1";
                            if(failureRegister > 0){ state = "0"; }
                            SynchronizationClass synchronizationClass = new SynchronizationClass(0, "Control de Activos", _date_sta +" "+_hour_sta,
                                    _date_end+" "+_hour_end, correctRegister+"", failureRegister+"", state);
                            SqliteClass.getInstance(context).databasehelp.synchronizationsql.addSynchronization(synchronizationClass);

                        }
                    }
                }

                if(ConstValue.getCheckedStock().equals("1")){
                    loadStock =  new ArrayList<StockClass>();
                    jParser = new JSONParser();
                    url = ConstValue.JSON_STOCK + "&croucodrou=" + ConstValue.getUserRouteCode();
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("MI JSON STOCK: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");

                            int correctRegister = 0;
                            int failureRegister = 0;

                            for(int j=0;j<jsonList.length(); j++){
                                JSONObject o =  jsonList.getJSONObject(j);

                                String idComing = o.getString("Code_Item");
                                boolean exists = SqliteClass.getInstance(context).databasehelp.stocksql.checkIfExists(String.valueOf(idComing));
                                try{
                                    if(exists){
                                        System.out.println("ACTUALIZO EL REGISTRO STOCK");
                                        StockClass =  new StockClass(0, o.getString("Code_Route"), o.getString("Code_Item"),o.getString("Quantity"));
                                        loadStock.add(StockClass);
                                        SqliteClass.getInstance(context).databasehelp.stocksql.updateStock(idComing, StockClass);

                                    }
                                    else{
                                        StockClass =  new StockClass(0, o.getString("Code_Route"), o.getString("Code_Item"),o.getString("Quantity"));
                                        loadStock.add(StockClass);
                                        SqliteClass.getInstance(context).databasehelp.stocksql.addStock(StockClass);
                                    }
                                    correctRegister++;
                                }
                                catch(Exception e){
                                    responseString = e.toString();
                                    e.printStackTrace();
                                    failureRegister++;
                                }
                            }
                            Date _date = new Date();
                            String _date_end = dateFormatDate.format(_date);
                            String _hour_end = dateFormatHour.format(_date);
                            /** Crear la sincronizacion */
                            String state = "1";
                            if(failureRegister > 0){ state = "0"; }
                            SynchronizationClass synchronizationClass = new SynchronizationClass(0, "Stock", _date_sta +" "+_hour_sta,
                                    _date_end+" "+_hour_end, correctRegister+"", failureRegister+"", state);
                            SqliteClass.getInstance(context).databasehelp.synchronizationsql.addSynchronization(synchronizationClass);
                        }
                    }
                }


                if(ConstValue.getCheckedTickets().equals("1")){
                    loadPaper =  new ArrayList<PaperClass>();
                    jParser = new JSONParser();
                    url = ConstValue.JSON_TICKET + "&route=" + ConstValue.getUserRouteCode();
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("MI JSON TICKET: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");

                            int correctRegister = 0;
                            int failureRegister = 0;

                            for(int j=0;j<jsonList.length(); j++){
                                JSONObject o =  jsonList.getJSONObject(j);

                                String idComing = String.valueOf(o.getInt("Id"));
                                boolean exists = SqliteClass.getInstance(context).databasehelp.papersql.checkIfExists(String.valueOf(idComing));
                                try{
                                    if(exists){
                                        System.out.println("ACTUALIZO EL REGISTRO DE TICKETS");
                                        Paper = new PaperClass(o.getInt("Id"), o.getString("Code"), o.getString("Code_Soc"), o.getString("Name_Soc"),
                                                o.getString("Address_Soc"), o.getString("Fis"), o.getString("Code_Suc"), o.getString("Name_Suc"),
                                                o.getString("Address_Suc"), o.getString("Email_Soc"), o.getString("Phone1_Soc"),
                                                o.getString("Phone2_Soc"), o.getString("Head1_Soc"), o.getString("Head2_Soc"),
                                                o.getString("Footer1_Soc"), o.getString("Footer2_Soc"),
                                                o.getString("Code_Office"), o.getString("Name_Office"), o.getString("State"));

                                        loadPaper.add(Paper);
                                        SqliteClass.getInstance(context).databasehelp.papersql.updatePaper(idComing, Paper);
                                    }
                                    else{
                                        Paper = new PaperClass(o.getInt("Id"), o.getString("Code"), o.getString("Code_Soc"), o.getString("Name_Soc"),
                                                o.getString("Address_Soc"), o.getString("Fis"), o.getString("Code_Suc"), o.getString("Name_Suc"),
                                                o.getString("Address_Suc"), o.getString("Email_Soc"), o.getString("Phone1_Soc"),
                                                o.getString("Phone2_Soc"), o.getString("Head1_Soc"), o.getString("Head2_Soc"),
                                                o.getString("Footer1_Soc"), o.getString("Footer2_Soc"),
                                                o.getString("Code_Office"), o.getString("Name_Office"), o.getString("State"));

                                        loadPaper.add(Paper);
                                        SqliteClass.getInstance(context).databasehelp.papersql.addPaper(Paper);
                                    }
                                    correctRegister++;
                                }
                                catch (Exception e){
                                    responseString = e.toString();
                                    e.printStackTrace();
                                    failureRegister++;
                                }
                            }
                            Date _date = new Date();
                            String _date_end = dateFormatDate.format(_date);
                            String _hour_end = dateFormatHour.format(_date);
                            /** Crear la sincronizacion */
                            String state = "1";
                            if(failureRegister > 0){ state = "0"; }
                            SynchronizationClass synchronizationClass = new SynchronizationClass(0, "Tickets", _date_sta +" "+_hour_sta,
                                    _date_end+" "+_hour_end, correctRegister+"", failureRegister+"", state);
                            SqliteClass.getInstance(context).databasehelp.synchronizationsql.addSynchronization(synchronizationClass);
                        }
                    }
                }

                if (ConstValue.getCheckedDocuments().equals("1")) {

                    int correctRegister = 0;
                    int failureRegister = 0;

                    loadTypeDocument =  new ArrayList<TypeDocumentClass>();
                    jParser = new JSONParser();
                    url = ConstValue.JSON_TYPE_DOCUMENT;
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("MI JSON TYPE DOCUMENT: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");
                            for(int j=0;j<jsonList.length(); j++){
                                JSONObject o =  jsonList.getJSONObject(j);

                                String idComing = String.valueOf(o.getString("Code"));
                                boolean exists = SqliteClass.getInstance(context).databasehelp.typedocumentsql.checkIfExists(idComing);

                                try{
                                    if(exists){
                                        System.out.println("ACTUALIZO EL TIPO DE DOCUMENTOS");
                                        TypeDocument =  new TypeDocumentClass(o.getInt("Id"), o.getString("Code"),
                                                o.getString("Description"),o.getString("State"));

                                        loadTypeDocument.add(TypeDocument);
                                        SqliteClass.getInstance(context).databasehelp.typedocumentsql.updateTypeDocument(idComing, TypeDocument);
                                    }
                                    else{
                                        System.out.println("DESCARGO NUEVO TIPO DE DOCUMENTOS");
                                        TypeDocument =  new TypeDocumentClass(o.getInt("Id"), o.getString("Code"),
                                                o.getString("Description"),o.getString("State"));

                                        loadTypeDocument.add(TypeDocument);
                                        SqliteClass.getInstance(context).databasehelp.typedocumentsql.addTypeDocument(TypeDocument);
                                    }
                                    correctRegister++;
                                }
                                catch (Exception e){
                                    responseString = e.toString();
                                    e.printStackTrace();
                                    failureRegister++;
                                }
                            }
                        }
                    }

                    //FACTURA
                    loadDocument = new ArrayList<DocumentClass>();
                    jParser = new JSONParser();
                    url = ConstValue.JSON_DOCUMENT + "&route=" + ConstValue.getUserRouteCode() + "&doctype=FAC";
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("Mi JSON DOCUMENT: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");
                            JSONObject o =  jsonList.getJSONObject(0);

                            String idComing = String.valueOf(o.getString("ccaicai"));
                            boolean exists = SqliteClass.getInstance(context).databasehelp.documentsql.checkIfExists(idComing);

                            try{
                                if(exists){
                                    System.out.println("ACTUALIZO FACTURAS");
                                    Document =  new DocumentClass(0, o.getString("ccaicai"), o.getString("crouasi"),
                                            o.getString("cfisdoc"), o.getString("cpredei"),
                                            o.getString("clowlim"), o.getString("cupplim"), o.getString("cnowlim"),
                                            o.getString("cdatlim"), o.getString("cemaleg"), o.getString("cdecnum"),
                                            o.getString("ccaista"), o.getString("ccordei"));

                                    loadDocument.add(Document);
                                    SqliteClass.getInstance(context).databasehelp.documentsql.updateDocument(idComing, Document);
                                }
                                else {
                                    System.out.println("DESCARGO NUEVOS REGISTROS DE FACTURAS");
                                    Document =  new DocumentClass(0, o.getString("ccaicai"), o.getString("crouasi"),
                                            o.getString("cfisdoc"), o.getString("cpredei"),
                                            o.getString("clowlim"), o.getString("cupplim"), o.getString("cnowlim"),
                                            o.getString("cdatlim"), o.getString("cemaleg"), o.getString("cdecnum"),
                                            o.getString("ccaista"), o.getString("ccordei"));

                                    loadDocument.add(Document);
                                    SqliteClass.getInstance(context).databasehelp.documentsql.addDocument(Document);
                                }
                                correctRegister++;
                            }
                            catch (Exception e){
                                responseString = e.toString();
                                e.printStackTrace();
                                failureRegister++;
                            }
                        }
                    }

                    //BOLETA
                    url = ConstValue.JSON_DOCUMENT + "&route=" + ConstValue.getUserRouteCode() + "&doctype=BOL";
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("Mi JSON DOCUMENT: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");
                            JSONObject o =  jsonList.getJSONObject(0);

                            String idComing = String.valueOf(o.getString("ccaicai"));
                            boolean exists = SqliteClass.getInstance(context).databasehelp.documentsql.checkIfExists(idComing);
                            try{
                                if(exists){
                                    System.out.println("ACTUALIZO REGISTRO DE BOLETAS");
                                    Document =  new DocumentClass(0, o.getString("ccaicai"), o.getString("crouasi"),
                                            o.getString("cfisdoc"), o.getString("cpredei"),
                                            o.getString("clowlim"), o.getString("cupplim"), o.getString("cnowlim"),
                                            o.getString("cdatlim"), o.getString("cemaleg"), o.getString("cdecnum"),
                                            o.getString("ccaista"), o.getString("ccordei"));

                                    loadDocument.add(Document);
                                    SqliteClass.getInstance(context).databasehelp.documentsql.updateDocument(idComing, Document);
                                }
                                else {
                                    System.out.println("DESCARGO REGISTRO DE BOLETAS");

                                    Document =  new DocumentClass(0, o.getString("ccaicai"), o.getString("crouasi"),
                                            o.getString("cfisdoc"), o.getString("cpredei"),
                                            o.getString("clowlim"), o.getString("cupplim"), o.getString("cnowlim"),
                                            o.getString("cdatlim"), o.getString("cemaleg"), o.getString("cdecnum"),
                                            o.getString("ccaista"), o.getString("ccordei"));

                                    loadDocument.add(Document);
                                    SqliteClass.getInstance(context).databasehelp.documentsql.addDocument(Document);
                                }
                                correctRegister++;
                            }
                            catch (Exception e){
                                responseString = e.toString();
                                e.printStackTrace();
                                failureRegister++;
                            }
                        }
                    }

                    //PEDIDO VENDEDOR
                    url = ConstValue.JSON_DOCUMENT + "&route=" + ConstValue.getUserRouteCode() + "&doctype=PV";
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("Mi JSON DOCUMENT: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");
                            JSONObject o =  jsonList.getJSONObject(0);

                            String idComing = String.valueOf(o.getString("ccaicai"));
                            boolean exists = SqliteClass.getInstance(context).databasehelp.documentsql.checkIfExists(idComing);
                            try{
                                if(exists){
                                    System.out.println("ACTUALIZO REGISTRO DE PEDIDO DE VENDEDOR");

                                    Document =  new DocumentClass(0, o.getString("ccaicai"), o.getString("crouasi"),
                                            o.getString("cfisdoc"), o.getString("cpredei"),
                                            o.getString("clowlim"), o.getString("cupplim"), o.getString("cnowlim"),
                                            o.getString("cdatlim"), o.getString("cemaleg"), o.getString("cdecnum"),
                                            o.getString("ccaista"), o.getString("ccordei"));

                                    loadDocument.add(Document);
                                    SqliteClass.getInstance(context).databasehelp.documentsql.updateDocument(idComing, Document);
                                }
                                else {
                                    System.out.println("DESCARGO REGISTRO DE PEDIDO DE VENDEDOR");

                                    Document =  new DocumentClass(0, o.getString("ccaicai"), o.getString("crouasi"),
                                            o.getString("cfisdoc"), o.getString("cpredei"),
                                            o.getString("clowlim"), o.getString("cupplim"), o.getString("cnowlim"),
                                            o.getString("cdatlim"), o.getString("cemaleg"), o.getString("cdecnum"),
                                            o.getString("ccaista"), o.getString("ccordei"));

                                    loadDocument.add(Document);
                                    SqliteClass.getInstance(context).databasehelp.documentsql.addDocument(Document);
                                }
                                correctRegister++;
                            }
                            catch (Exception e){
                                responseString = e.toString();
                                e.printStackTrace();
                                failureRegister++;
                            }
                        }
                    }

                    //PEDIDO CLIENTE
                    url = ConstValue.JSON_DOCUMENT + "&route=" + ConstValue.getUserRouteCode() + "&doctype=PC";
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("Mi JSON DOCUMENT: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");
                            JSONObject o =  jsonList.getJSONObject(0);

                            String idComing = String.valueOf(o.getString("ccaicai"));
                            boolean exists = SqliteClass.getInstance(context).databasehelp.documentsql.checkIfExists(idComing);
                            try{
                                if(exists){
                                    System.out.println("ACTUALIZO REGISTRO DE PEDIDO DE CLIENTE");

                                    Document =  new DocumentClass(0, o.getString("ccaicai"), o.getString("crouasi"),
                                            o.getString("cfisdoc"), o.getString("cpredei"),
                                            o.getString("clowlim"), o.getString("cupplim"), o.getString("cnowlim"),
                                            o.getString("cdatlim"), o.getString("cemaleg"), o.getString("cdecnum"),
                                            o.getString("ccaista"), o.getString("ccordei"));

                                    loadDocument.add(Document);
                                    SqliteClass.getInstance(context).databasehelp.documentsql.updateDocument(idComing, Document);
                                }
                                else {
                                    System.out.println("DESCARGO REGISTRO DE PEDIDO DE VENDEDOR");

                                    Document =  new DocumentClass(0, o.getString("ccaicai"), o.getString("crouasi"),
                                            o.getString("cfisdoc"), o.getString("cpredei"),
                                            o.getString("clowlim"), o.getString("cupplim"), o.getString("cnowlim"),
                                            o.getString("cdatlim"), o.getString("cemaleg"), o.getString("cdecnum"),
                                            o.getString("ccaista"), o.getString("ccordei"));

                                    loadDocument.add(Document);
                                    SqliteClass.getInstance(context).databasehelp.documentsql.addDocument(Document);
                                }
                                correctRegister++;
                            }
                            catch (Exception e){
                                responseString = e.toString();
                                e.printStackTrace();
                                failureRegister++;
                            }
                        }
                    }

                    //CAMBIO DE PRODUCTO
                    url = ConstValue.JSON_DOCUMENT + "&route=" + ConstValue.getUserRouteCode() + "&doctype=C";
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("Mi JSON DOCUMENT: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");
                            JSONObject o =  jsonList.getJSONObject(0);

                            String idComing = String.valueOf(o.getString("ccaicai"));
                            boolean exists = SqliteClass.getInstance(context).databasehelp.documentsql.checkIfExists(idComing);
                            try{
                                if(exists){
                                    System.out.println("ACTUALIZO REGISTRO DE CAMBIO DE PRODUCTO");

                                    Document =  new DocumentClass(0, o.getString("ccaicai"), o.getString("crouasi"),
                                            o.getString("cfisdoc"), o.getString("cpredei"),
                                            o.getString("clowlim"), o.getString("cupplim"), o.getString("cnowlim"),
                                            o.getString("cdatlim"), o.getString("cemaleg"), o.getString("cdecnum"),
                                            o.getString("ccaista"), o.getString("ccordei"));

                                    loadDocument.add(Document);
                                    SqliteClass.getInstance(context).databasehelp.documentsql.updateDocument(idComing, Document);
                                }
                                else {
                                    System.out.println("DESCARGO REGISTRO DE CAMBIO DE PRODUCTO");

                                    Document =  new DocumentClass(0, o.getString("ccaicai"), o.getString("crouasi"),
                                            o.getString("cfisdoc"), o.getString("cpredei"),
                                            o.getString("clowlim"), o.getString("cupplim"), o.getString("cnowlim"),
                                            o.getString("cdatlim"), o.getString("cemaleg"), o.getString("cdecnum"),
                                            o.getString("ccaista"), o.getString("ccordei"));

                                    loadDocument.add(Document);
                                    SqliteClass.getInstance(context).databasehelp.documentsql.addDocument(Document);
                                }
                                correctRegister++;
                            }
                            catch (Exception e){
                                responseString = e.toString();
                                e.printStackTrace();
                                failureRegister++;
                            }
                        }
                    }

                    //DEVOLUCION
                    url = ConstValue.JSON_DOCUMENT + "&route=" + ConstValue.getUserRouteCode() + "&doctype=R";
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("Mi JSON DOCUMENT: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");
                            JSONObject o =  jsonList.getJSONObject(0);

                            String idComing = String.valueOf(o.getString("ccaicai"));
                            boolean exists = SqliteClass.getInstance(context).databasehelp.documentsql.checkIfExists(idComing);
                            try{
                                if(exists){
                                    System.out.println("ACTUALIZO REGISTRO DE DEVOLUCION");

                                    Document =  new DocumentClass(0, o.getString("ccaicai"), o.getString("crouasi"),
                                            o.getString("cfisdoc"), o.getString("cpredei"),
                                            o.getString("clowlim"), o.getString("cupplim"), o.getString("cnowlim"),
                                            o.getString("cdatlim"), o.getString("cemaleg"), o.getString("cdecnum"),
                                            o.getString("ccaista"), o.getString("ccordei"));

                                    loadDocument.add(Document);
                                    SqliteClass.getInstance(context).databasehelp.documentsql.updateDocument(idComing, Document);
                                }
                                else {
                                    System.out.println("DESCARGO REGISTRO DE DEVOLUCION");

                                    Document =  new DocumentClass(0, o.getString("ccaicai"), o.getString("crouasi"),
                                            o.getString("cfisdoc"), o.getString("cpredei"),
                                            o.getString("clowlim"), o.getString("cupplim"), o.getString("cnowlim"),
                                            o.getString("cdatlim"), o.getString("cemaleg"), o.getString("cdecnum"),
                                            o.getString("ccaista"), o.getString("ccordei"));

                                    loadDocument.add(Document);
                                    SqliteClass.getInstance(context).databasehelp.documentsql.addDocument(Document);
                                }
                                correctRegister++;
                            }
                            catch (Exception e){
                                responseString = e.toString();
                                e.printStackTrace();
                                failureRegister++;
                            }
                        }
                    }

                    Date _date = new Date();
                    String _date_end = dateFormatDate.format(_date);
                    String _hour_end = dateFormatHour.format(_date);
                    /** Crear la sincronizacion */
                    String state = "1";
                    if(failureRegister > 0){ state = "0"; }
                    SynchronizationClass synchronizationClass = new SynchronizationClass(0, "Documentos", _date_sta +" "+_hour_sta,
                            _date_end+" "+_hour_end, correctRegister+"", failureRegister+"", state);
                    SqliteClass.getInstance(context).databasehelp.synchronizationsql.addSynchronization(synchronizationClass);


                }

                if(ConstValue.getCheckedCatalogue().equals("1")){

                    int correctRegister = 0;
                    int failureRegister = 0;

                    loadSuggested =  new ArrayList<SuggestedClass>();
                    jParser = new JSONParser();
                    url = ConstValue.JSON_SUGGESTED_SALE + "&route=" + ConstValue.getUserRouteCode();
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("MI JSON SUGGESTED SALE: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");
                            for(int j=0;j<jsonList.length(); j++){
                                JSONObject o =  jsonList.getJSONObject(j);

                                //String idComing = String.valueOf(o.getInt("Id"));
                                boolean exists = SqliteClass.getInstance(context).databasehelp.suggestedsql.checkIfExists(o.getString("Code_Customer"), o.getString("Code_Item"), "VD");
                                try{
                                    if(exists){
                                        System.out.println("ACTUALIZO EL SUGERIDO VENTA DIRECTA");
                                        Suggested = new SuggestedClass(0, "VD", o.getString("Code_Customer"),
                                                o.getString("Code_Item"), o.getString("Description_Item"), o.getString("Category"),
                                                o.getString("Suggested"), "");
                                        loadSuggested.add(Suggested);
                                        SqliteClass.getInstance(context).databasehelp.suggestedsql.updateSuggested(o.getString("Code_Customer"), o.getString("Code_Item"), "VD", Suggested);
                                    }
                                    else{
                                        Suggested = new SuggestedClass(0, "VD", o.getString("Code_Customer"),
                                                o.getString("Code_Item"), o.getString("Description_Item"), o.getString("Category"),
                                                o.getString("Suggested"), "");
                                        loadSuggested.add(Suggested);
                                        SqliteClass.getInstance(context).databasehelp.suggestedsql.addSuggested(Suggested);
                                    }
                                    correctRegister++;
                                }
                                catch(Exception e){
                                    responseString = e.toString();
                                    e.printStackTrace();
                                    failureRegister++;
                                }
                            }
                        }
                    }
                    //PEDIDO CLIENTE
                    loadSuggested =  new ArrayList<SuggestedClass>();
                    jParser = new JSONParser();
                    url = ConstValue.JSON_SUGGESTED_ORDER + "&route=" + ConstValue.getUserRouteCode();
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("MI JSON SUGGESTED ORDER CUSTOMER: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");
                            for(int j=0;j<jsonList.length(); j++){
                                JSONObject o =  jsonList.getJSONObject(j);

                                //String idComing = String.valueOf(o.getInt("Id"));
                                boolean exists = SqliteClass.getInstance(context).databasehelp.suggestedsql.checkIfExists(o.getString("Code_Customer"), o.getString("Code_Item"), "PC");
                                try{
                                    if(exists){
                                        System.out.println("ACTUALIZO EL SUGERIDO PEDIDO CLIENTE");
                                        Suggested = new SuggestedClass(0, "PC", o.getString("Code_Customer"),
                                                o.getString("Code_Item"), o.getString("Description_Item"), o.getString("Category"),
                                                o.getString("Suggested"), "");
                                        loadSuggested.add(Suggested);
                                        SqliteClass.getInstance(context).databasehelp.suggestedsql.updateSuggested(o.getString("Code_Customer"), o.getString("Code_Item"), "PC",Suggested);
                                    }
                                    else{
                                        Suggested = new SuggestedClass(0, "PC", o.getString("Code_Customer"),
                                                o.getString("Code_Item"), o.getString("Description_Item"), o.getString("Category"),
                                                o.getString("Suggested"), "");
                                        loadSuggested.add(Suggested);
                                        SqliteClass.getInstance(context).databasehelp.suggestedsql.addSuggested(Suggested);
                                    }
                                    correctRegister++;
                                }
                                catch (Exception e){
                                    responseString = e.toString();
                                    e.printStackTrace();
                                    failureRegister++;
                                }

                            }
                        }
                    }

                    //PEDIDO VENDEDOR (CARGA)
                    loadSuggested =  new ArrayList<SuggestedClass>();
                    jParser = new JSONParser();
                    url = ConstValue.JSON_SUGGESTED_CHARGE + "&route=" + ConstValue.getUserRouteCode();
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("MI JSON SUGGESTED CHARGE: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");
                            for(int j=0;j<jsonList.length(); j++){
                                JSONObject o =  jsonList.getJSONObject(j);

                                //String idComing = String.valueOf(o.getInt("Id"));
                                boolean exists = SqliteClass.getInstance(context).databasehelp.suggestedsql.checkIfExists(o.getString("Code_Route"), o.getString("Code_Item"), "PV");
                                try{
                                    if(exists){
                                        System.out.println("ACTUALIZO EL SUGERIDO PEDIDO VENDEDOR");
                                        Suggested = new SuggestedClass(0, "PV", o.getString("Code_Route"),
                                                o.getString("Code_Item"), o.getString("Description_Item"), o.getString("Category"),
                                                o.getString("Suggested"), "");
                                        loadSuggested.add(Suggested);
                                        SqliteClass.getInstance(context).databasehelp.suggestedsql.updateSuggested(o.getString("Code_Route"), o.getString("Code_Item"), "PV", Suggested);
                                    }
                                    else{
                                        Suggested = new SuggestedClass(0, "PV", o.getString("Code_Route"),
                                                o.getString("Code_Item"), o.getString("Description_Item"), o.getString("Category"),
                                                o.getString("Suggested"), "");
                                        loadSuggested.add(Suggested);
                                        SqliteClass.getInstance(context).databasehelp.suggestedsql.addSuggested(Suggested);
                                    }
                                    correctRegister++;
                                }
                                catch (Exception e){
                                    responseString = e.toString();
                                    e.printStackTrace();
                                    failureRegister++;
                                }
                            }
                        }
                    }

                    Date _date = new Date();
                    String _date_end = dateFormatDate.format(_date);
                    String _hour_end = dateFormatHour.format(_date);
                    /** Crear la sincronizacion */
                    String state = "1";
                    if(failureRegister > 0){ state = "0"; }
                    SynchronizationClass synchronizationClass = new SynchronizationClass(0, "Catálogo Sugerido", _date_sta +" "+_hour_sta,
                            _date_end+" "+_hour_end, correctRegister+"", failureRegister+"", state);
                    SqliteClass.getInstance(context).databasehelp.synchronizationsql.addSynchronization(synchronizationClass);

                }

                if(ConstValue.getCheckedConfiguration().equals("1")){
                    loadConfiguration =  new ArrayList<ConfigurationClass>();
                    jParser = new JSONParser();
                    url = ConstValue.JSON_CONFIGURATION;
                    json = jParser.getJSONFromUrl(url);
                    System.out.println("MI JSON CONFIGURATION: " + json);
                    if (json.has("data")){
                        if (json.get("data")instanceof JSONArray){
                            JSONArray jsonList = json.getJSONArray("data");

                            int correctRegister = 0;
                            int failureRegister = 0;

                            JSONObject o =  jsonList.getJSONObject(0);

                            String idComing = String.valueOf(o.getInt("Id"));
                            boolean exists = SqliteClass.getInstance(context).databasehelp.configurationsql.checkIfExists(String.valueOf(idComing));
                            try{
                                if(exists){
                                    Configuration = new ConfigurationClass(o.getInt("Id"), o.getString("Currency"), o.getString("UmWeight"), o.getString("Paper_Size"));
                                    loadConfiguration.add(Configuration);
                                    SqliteClass.getInstance(context).databasehelp.configurationsql.updateConfiguration(idComing, Configuration);
                                }
                                else{
                                    Configuration = new ConfigurationClass(o.getInt("Id"), o.getString("Currency"), o.getString("UmWeight"), o.getString("Paper_Size"));
                                    loadConfiguration.add(Configuration);
                                    SqliteClass.getInstance(context).databasehelp.configurationsql.addConfiguration(Configuration);
                                }
                                correctRegister++;
                            }
                            catch (Exception e){
                                responseString = e.toString();
                                e.printStackTrace();
                                failureRegister++;
                            }
                            String [] currency = o.getString("Currency").split(" - ");
                            ConstValue.setCurrency(currency[0]); //ConstValue.setCurrency("L. ");
                            ConstValue.setNameCurrency(currency[1]); //ConstValue.setNameCurrency("LEMPIRAS");
                            String [] unitMeasure = o.getString("UmWeight").split(" - ");
                            ConstValue.setUnitMeasure(unitMeasure[0]); //ConstValue.setUnitMeasure("LTS./LBS. ");

                            Date _date = new Date();
                            String _date_end = dateFormatDate.format(_date);
                            String _hour_end = dateFormatHour.format(_date);
                            /** Crear la sincronizacion */
                            String state = "1";
                            if(failureRegister > 0){ state = "0"; }
                            SynchronizationClass synchronizationClass = new SynchronizationClass(0, "Configuración de Usuario", _date_sta +" "+_hour_sta,
                                    _date_end+" "+_hour_end, correctRegister+"", failureRegister+"", state);
                            SqliteClass.getInstance(context).databasehelp.synchronizationsql.addSynchronization(synchronizationClass);
                        }
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
                responseString = e.toString();
                e.printStackTrace();
                responseString = "CloudSales - Error al recuperar la información del portal.";
            }
            // TODO Auto-generated method stub
            return responseString;
        }
    }

}
