package com.sales.views.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sales.R;
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
import com.sales.models.TypeDocumentClass;
import com.sales.models.UserClass;
import com.sales.models.VisitClass;
import com.sales.utils.Common;
import com.sales.utils.ConnectionDetector;
import com.sales.utils.JSONParser;
import com.sales.utils.Util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public ConnectionDetector cd;
    ProgressDialog dialog;
    public String _date;
    Activity activity;
    Common common;
    Button btnLogin;
    EditText et_user;
    EditText et_pass;
    Context context;
    boolean isOnline;
    TextView version;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private UserClass User; private ArrayList<UserClass> loadUser;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;
        cd = new ConnectionDetector(this);
        isOnline = cd.isConnectingToInternet();
        common = new Common();
        context = this;

        Util.enableLocation(activity, context);
        _date =  dateFormat.format(new Date());
        et_user = (EditText) findViewById(R.id.et_username);
        et_pass = (EditText) findViewById(R.id.et_password);

        version = (TextView) findViewById(R.id.tx_version);
        String versionApp = getString(R.string.app_version);
        version.setText(versionApp);
        btnLogin = (Button)findViewById(R.id.button_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(et_user.getText().toString().length()==0){
                    et_user.setError("Ingrese Usuario");
                }else if (et_pass.getText().toString().length()==0){
                    et_pass.setError("Ingrese Contraseña");
                } else {
                    if(SqliteClass.getInstance(context).databasehelp.checkDataBase()){
                        String user = et_user.getText().toString();
                        String password = et_pass.getText().toString();

                        if (SqliteClass.getInstance(context).databasehelp.usersql.isRegisterUser(user, password)) {
                            String _dateUser = SqliteClass.getInstance(context).databasehelp.usersql.getData(5, et_user.getText().toString());
                            if (_date.equals(_dateUser)) {
                                if (SqliteClass.getInstance(context).databasehelp.usersql.isRegisterUser(et_user.getText().toString(), et_pass.getText().toString())) {
                                    //TODO /**Cargar todos los valores constantes por un caso, claro, ya no se cargan porque no llamamos a login*/
                                    String username = SqliteClass.getInstance(context).databasehelp.usersql.getAnyField(1);
                                    ConstValue.setUserId(String.valueOf(SqliteClass.getInstance(context).databasehelp.usersql.getData(0, username))); //no carga porque no hay username
                                    ConstValue.setUserName(username);
                                    ConstValue.setUserPass(password);
                                    ConstValue.setUserRouteCode(SqliteClass.getInstance(context).databasehelp.usersql.getData(6, username));
                                    ConstValue.setUserEmail(SqliteClass.getInstance(context).databasehelp.usersql.getData(4, username));

                                    ConstValue.setProximateRange(Double.parseDouble(SqliteClass.getInstance(context).databasehelp.usersql.getData(14, username)));                              /** Falta agregar dentro de usuario **/ //metros Supongo que va en usuario. //DESDE USUARIO
                                    ConstValue.setLimitLts(SqliteClass.getInstance(context).databasehelp.usersql.getData(12, username));
                                    ConstValue.setPendingLimitLts(SqliteClass.getInstance(context).databasehelp.usersql.getData(13, username));

                                    ConstValue.setDefaultPriceListCode("LP000");   /** Falta agregar dentro de usuario segun backend se dice que nunca cambiara **/ //Default price list to get all items
                                    ConstValue.setPaperSize("48");

                                    String [] currency =  SqliteClass.getInstance(context).databasehelp.configurationsql.getData("Currency").split(" - ");
                                    ConstValue.setCurrency(currency[0]); //ConstValue.setCurrency("L. ");
                                    ConstValue.setNameCurrency(currency[1]); //ConstValue.setNameCurrency("LEMPIRAS");

                                    String [] unitMeasure = SqliteClass.getInstance(context).databasehelp.configurationsql.getData("MeasureUnit").split(" - ");
                                    ConstValue.setUnitMeasure(unitMeasure[0]); //ConstValue.setUnitMeasure("LTS./LBS. ");

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Util.infoDialog(LoginActivity.this, context, "CloudSales", "Credenciales inválidas. Por favor intente nuevamente.");

                                }
                            }else{
                                final Dialog dialog = new Dialog(context);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.alert_info);
                                ImageView image = (ImageView) dialog.findViewById(R.id.alert_info);
                                image.setImageResource(R.drawable.ic_alert_info);
                                TextView head = (TextView) dialog.findViewById(R.id.alert_info_title);
                                head.setText("CloudSales");
                                TextView content = (TextView) dialog.findViewById(R.id.alert_info_content);
                                content.setText("La fecha del teléfono: " + _date + " no corresponde con la del usuario: " + _dateUser);
                                Button dbOk = (Button) dialog.findViewById(R.id.alert_ok);
                                dbOk.setText("Iniciar Día");
                                dbOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SqliteClass.getInstance(context).databasehelp.deleteDataBase();
                                        if(isOnline){
                                            new loginTask().execute(true);
                                        }else{
                                            Util.infoDialog(LoginActivity.this, context, "CloudSales", "Su dispositivo no cuenta con conexión a internet.");

                                        }
                                        dialog.dismiss();
                                    }
                                });
                                Button dbCancel = (Button) dialog.findViewById(R.id.alert_cancel);
                                dbCancel.setText("Ajustar Fecha");
                                dbCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Util.infoDialog(LoginActivity.this, context, "CloudSales", "Configure la fecha en su dispositivo por favor.");
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }
                        } else {
                            Util.infoDialog(LoginActivity.this, context, "CloudSales", "Usuario y/o contraseña incorrecta. Por favor intente nuevamente.");
                        }
                    } else {
                        SqliteClass.getInstance(context).databasehelp.deleteDataBase();
                        if (isOnline) {
                            new loginTask().execute(true);
                        } else {
                            Util.infoDialog(LoginActivity.this, context, "CloudSales", "Su dispositivo no cuenta con conexión a internet.");
                        }
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        activity.finish();
    }

    class loginTask extends AsyncTask<Boolean, Void, String> {
        String user, password;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            user = et_user.getText().toString();
            password = et_pass.getText().toString();
            dialog = ProgressDialog.show(LoginActivity.this, "", getString(R.string.action_loading), true);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Toast.makeText(getApplicationContext(), "CloudSales " + result, Toast.LENGTH_LONG).show();
            } else {

                ConstValue.setDefaultPriceListCode("LP000");   /** Falta agregar dentro de usuario segun backend se dice que nunca cambiara **/ //Default price list to get all items
                ConstValue.setPaperSize("48");

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
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
            try {
                loadUser = new ArrayList<UserClass>();
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("user", user));
                nameValuePairs.add(new BasicNameValuePair("password", password));
                JSONObject jObj = common.sendJsonData(ConstValue.JSON_LOGIN, nameValuePairs);
                System.out.println("TU JSON User: " + jObj);
                try {
                    if (jObj.getString("response").equalsIgnoreCase("success")) {
                        JSONObject data = jObj.getJSONObject("data");
                        if (data.getString("Username").equalsIgnoreCase(user)) {

                            User = new UserClass(data.getInt("Id"),user, password, data.getString("Name"),
                                    data.getString("Email"), _date, data.getString("Route_Code"),
                                    data.getString("routeName"), data.getString("Sales_Center"),
                                    data.getString("Route_Type"), data.getString("Limit_return"),
                                    data.getString("State"), data.getString("Min_charge"), data.getString("Pending"), data.getString("proximityRange"));
                            loadUser.add(User);
                            ConstValue.setUserId(String.valueOf(User.getId()));
                            ConstValue.setUserName(user);
                            ConstValue.setUserPass(password);
                            ConstValue.setUserRouteCode(data.getString("Route_Code"));
                            ConstValue.setUserEmail(data.getString("Email"));

                            ConstValue.setProximateRange(Double.parseDouble(data.getString("proximityRange")));                              /** Falta agregar dentro de usuario **/ //metros Supongo que va en usuario. //DESDE USUARIO
                            ConstValue.setLimitLts(data.getString("Min_charge"));
                            ConstValue.setPendingLimitLts(data.getString("Pending"));


                            SqliteClass.getInstance(context).databasehelp.usersql.addUser(User);
                        }
                    } else {
                        responseString = "Login - " + jObj.getString("data");
                        return responseString;
                    }
                } catch (JSONException e) {
                    responseString = "Login - " + e.getMessage();
                    return responseString;
                }


                JSONParser jParser;
                JSONObject json;
                String url = "";

                loadCustomer =  new ArrayList<CustomerClass>();
                jParser = new JSONParser();
                url = ConstValue.JSON_CUSTOMER + "&route=" + ConstValue.getUserRouteCode();
                json = jParser.getJSONFromUrl(url);
                System.out.println("MI JSON CUSTOMER: " + json);
                if (json.has("data")){
                    if (json.get("data")instanceof JSONArray){
                        JSONArray jsonList = json.getJSONArray("data");
                        for(int j=0;j<jsonList.length(); j++){
                            JSONObject o =  jsonList.getJSONObject(j);
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
                    }
                }

                loadCategory =  new ArrayList<CategoryClass>();
                jParser = new JSONParser();
                url = ConstValue.JSON_CATEGORY;
                json = jParser.getJSONFromUrl(url);
                System.out.println("MI JSON CATEGORY: " + json);
                if (json.has("data")){
                    if (json.get("data")instanceof JSONArray){
                        JSONArray jsonList = json.getJSONArray("data");
                        for(int j=0;j<jsonList.length(); j++){
                            JSONObject o =  jsonList.getJSONObject(j);
                            Category =  new CategoryClass(o.getInt("Id"), o.getString("Code"), o.getString("Description"),
                                    o.getString("_Order"), o.getString("State"));
                            loadCategory.add(Category);
                            SqliteClass.getInstance(context).databasehelp.categorysql.addCategory(Category);
                        }
                    }
                }

                loadItem =  new ArrayList<ItemClass>();
                jParser = new JSONParser();
                url = ConstValue.JSON_ITEM;
                json = jParser.getJSONFromUrl(url);
                System.out.println("MI JSON ITEM: " + json);
                if (json.has("data")){
                    if (json.get("data")instanceof JSONArray){
                        JSONArray jsonList = json.getJSONArray("data");
                        for(int j=0;j<jsonList.length(); j++){
                            JSONObject o =  jsonList.getJSONObject(j);
                            Item =  new ItemClass(o.getInt("Id"), o.getString("Category"),
                                    o.getString("Code"), o.getString("Description"), o.getString("Tax"),
                                    o.getString("Measure_Unit"), o.getString("Liters_Lbs"), o.getString("_Order"),
                                    o.getString("CategoryName"), o.getString("MeasureName"), o.getString("State"),
                                    "", "", "", "");
                            loadItem.add(Item);
                            SqliteClass.getInstance(context).databasehelp.itemsql.addItem(Item);
                        }
                    }
                }

                loadPriceList =  new ArrayList<PriceListClass>();
                jParser = new JSONParser();
                url = ConstValue.JSON_PRICE_LIST;
                json = jParser.getJSONFromUrl(url);
                System.out.println("MI JSON PRICE LIST: " + json);
                if (json.has("data")){
                    if (json.get("data")instanceof JSONArray){
                        JSONArray jsonList = json.getJSONArray("data");
                        for(int j=0;j<jsonList.length(); j++){
                            JSONObject o =  jsonList.getJSONObject(j);
                            PriceList =  new PriceListClass(o.getInt("Id"), o.getString("Code"), o.getString("Code_Item"),
                                    o.getString("Price"), o.getString("Description_Item"), o.getString("Description_Price_List"),
                                    o.getString("State"));

                            loadPriceList.add(PriceList);
                            SqliteClass.getInstance(context).databasehelp.pricelistsql.addPriceList(PriceList);
                        }
                    }
                }

                /*
                loadDiscountList =  new ArrayList<DiscountListClass>();
                jParser = new JSONParser();
                url = ConstValue.JSON_DISCOUNT_LIST;
                json = jParser.getJSONFromUrl(url);
                System.out.println("MI JSON DISCOUNT LIST: " + json);
                if (json.has("data")){
                    if (json.get("data")instanceof JSONArray){
                        JSONArray jsonList = json.getJSONArray("data");
                        for(int j=0;j<jsonList.length(); j++){
                            JSONObject o =  jsonList.getJSONObject(j);
                            DiscountList =  new DiscountListClass(o.getInt("Id"), o.getString("Code"),
                                    o.getString("Code_Customer"),o.getString("Code_Category"),
                                    o.getString("Code_Item"), o.getString("Discount_Percentage"),
                                    o.getString("Date_Initial"), o.getString("Date_Finish"), o.getString("State"));


                            loadDiscountList.add(DiscountList);
                            SqliteClass.getInstance(context).databasehelp.discountListsql.addDiscountList(DiscountList);
                        }
                    }
                }
                */

                loadPromoList =  new ArrayList<PromoListClass>();
                jParser = new JSONParser();
                url = ConstValue.JSON_PROMO_LIST;
                json = jParser.getJSONFromUrl(url);
                System.out.println("MI JSON PROMO LIST: " + json);
                if (json.has("data")){
                    if (json.get("data")instanceof JSONArray){
                        JSONArray jsonList = json.getJSONArray("data");
                        for(int j=0;j<jsonList.length(); j++){
                            JSONObject o =  jsonList.getJSONObject(j);
                            PromoList =  new PromoListClass(
                                    o.getInt("Id"),
                                    o.getString("Code"),
                                    o.getString("Code_ItemOne"),
                                    o.getString("QuantityOne"),
                                    o.getString("UnitMeasureOne"),
                                    o.getString("Code_ItemTwo"),
                                    o.getString("QuantityTwo"),
                                    o.getString("UnitMeasureTwo"),
                                    o.getString("NameItem1"),
                                    o.getString("NameItem2"),
                                    o.getString("NameUnit1"),
                                    o.getString("NameUnit2"),
                                    o.getString("State"),
                                    o.getString("CategoryOne"),
                                    o.getString("CategoryTwo"));

                            loadPromoList.add(PromoList);
                            SqliteClass.getInstance(context).databasehelp.promolistsql.addPromoList(PromoList);
                        }
                    }
                }

                loadVisit =  new ArrayList<VisitClass>();
                jParser = new JSONParser();
                url = ConstValue.JSON_VISIT;
                json = jParser.getJSONFromUrl(url);
                System.out.println("MI JSON VISIT: " + json);
                if (json.has("data")){
                    if (json.get("data")instanceof JSONArray){
                        JSONArray jsonList = json.getJSONArray("data");
                        for(int j=0;j<jsonList.length(); j++){
                            JSONObject o =  jsonList.getJSONObject(j);
                            Visit =  new VisitClass(o.getInt("Id"), o.getString("Description"), o.getString("Code_Office"),
                                    o.getString("NameOffice"), o.getString("State"));

                            loadVisit.add(Visit);
                            SqliteClass.getInstance(context).databasehelp.visitSql.addVisit(Visit);
                        }
                    }
                }

                loadAssetControl =  new ArrayList<AssetControlClass>();
                jParser = new JSONParser();
                url = ConstValue.JSON_ASSET_CONTROL + "&iduser=" + ConstValue.getUserId();
                json = jParser.getJSONFromUrl(url);
                System.out.println("MI JSON ASSET CONTROL: " + json);
                if (json.has("data")){
                    if (json.get("data")instanceof JSONArray){
                        JSONArray jsonList = json.getJSONArray("data");
                        for(int j=0;j<jsonList.length(); j++){
                            JSONObject o =  jsonList.getJSONObject(j);

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
                    }
                }

                loadStock =  new ArrayList<StockClass>();
                jParser = new JSONParser();
                url = ConstValue.JSON_STOCK + "&croucodrou=" + ConstValue.getUserRouteCode();
                json = jParser.getJSONFromUrl(url);
                System.out.println("MI JSON STOCK: " + json);
                if (json.has("data")){
                    if (json.get("data")instanceof JSONArray){
                        JSONArray jsonList = json.getJSONArray("data");
                        for(int j=0;j<jsonList.length(); j++){
                            JSONObject o =  jsonList.getJSONObject(j);
                            StockClass =  new StockClass(0, o.getString("Code_Route"),
                                    o.getString("Code_Item"),o.getString("Quantity"));

                            loadStock.add(StockClass);
                            SqliteClass.getInstance(context).databasehelp.stocksql.addStock(StockClass);
                        }
                    }
                }
                //DOCUMENTOS
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
                            TypeDocument =  new TypeDocumentClass(o.getInt("Id"), o.getString("Code"),
                                    o.getString("Description"),o.getString("State"));

                            loadTypeDocument.add(TypeDocument);
                            SqliteClass.getInstance(context).databasehelp.typedocumentsql.addTypeDocument(TypeDocument);
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
                        Document =  new DocumentClass(0, o.getString("ccaicai"), o.getString("crouasi"),
                                o.getString("cfisdoc"), o.getString("cpredei"),
                                o.getString("clowlim"), o.getString("cupplim"), o.getString("cnowlim"),
                                o.getString("cdatlim"), o.getString("cemaleg"), o.getString("cdecnum"),
                                o.getString("ccaista"), o.getString("ccordei"));

                        loadDocument.add(Document);
                        SqliteClass.getInstance(context).databasehelp.documentsql.addDocument(Document);
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
                        Document =  new DocumentClass(0, o.getString("ccaicai"), o.getString("crouasi"),
                                o.getString("cfisdoc"), o.getString("cpredei"),
                                o.getString("clowlim"), o.getString("cupplim"), o.getString("cnowlim"),
                                o.getString("cdatlim"), o.getString("cemaleg"), o.getString("cdecnum"),
                                o.getString("ccaista"), o.getString("ccordei"));

                        loadDocument.add(Document);
                        SqliteClass.getInstance(context).databasehelp.documentsql.addDocument(Document);
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
                        Document =  new DocumentClass(0, o.getString("ccaicai"), o.getString("crouasi"),
                                o.getString("cfisdoc"), o.getString("cpredei"),
                                o.getString("clowlim"), o.getString("cupplim"), o.getString("cnowlim"),
                                o.getString("cdatlim"), o.getString("cemaleg"), o.getString("cdecnum"),
                                o.getString("ccaista"), o.getString("ccordei"));

                        loadDocument.add(Document);
                        SqliteClass.getInstance(context).databasehelp.documentsql.addDocument(Document);
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
                        Document =  new DocumentClass(0, o.getString("ccaicai"), o.getString("crouasi"),
                                o.getString("cfisdoc"), o.getString("cpredei"),
                                o.getString("clowlim"), o.getString("cupplim"), o.getString("cnowlim"),
                                o.getString("cdatlim"), o.getString("cemaleg"), o.getString("cdecnum"),
                                o.getString("ccaista"), o.getString("ccordei"));

                        loadDocument.add(Document);
                        SqliteClass.getInstance(context).databasehelp.documentsql.addDocument(Document);
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
                        Document =  new DocumentClass(0, o.getString("ccaicai"), o.getString("crouasi"),
                                o.getString("cfisdoc"), o.getString("cpredei"),
                                o.getString("clowlim"), o.getString("cupplim"), o.getString("cnowlim"),
                                o.getString("cdatlim"), o.getString("cemaleg"), o.getString("cdecnum"),
                                o.getString("ccaista"), o.getString("ccordei"));

                        loadDocument.add(Document);
                        SqliteClass.getInstance(context).databasehelp.documentsql.addDocument(Document);
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
                        Document =  new DocumentClass(0, o.getString("ccaicai"), o.getString("crouasi"),
                                o.getString("cfisdoc"), o.getString("cpredei"),
                                o.getString("clowlim"), o.getString("cupplim"), o.getString("cnowlim"),
                                o.getString("cdatlim"), o.getString("cemaleg"), o.getString("cdecnum"),
                                o.getString("ccaista"), o.getString("ccordei"));

                        loadDocument.add(Document);
                        SqliteClass.getInstance(context).databasehelp.documentsql.addDocument(Document);
                    }
                }

                //TICKET
                loadPaper =  new ArrayList<PaperClass>();
                jParser = new JSONParser();
                url = ConstValue.JSON_TICKET + "&route=" + ConstValue.getUserRouteCode();
                json = jParser.getJSONFromUrl(url);
                System.out.println("MI JSON TICKET: " + json);
                if (json.has("data")){
                    if (json.get("data")instanceof JSONArray){
                        JSONArray jsonList = json.getJSONArray("data");
                        for(int j=0;j<jsonList.length(); j++){
                            JSONObject o =  jsonList.getJSONObject(j);
                            Paper = new PaperClass(o.getInt("Id"), o.getString("Code"), o.getString("Code_Soc"), o.getString("Name_Soc"),
                                    o.getString("Address_Soc"), o.getString("Fis"), o.getString("Code_Suc"), o.getString("Name_Suc"),
                                    o.getString("Address_Suc"), o.getString("Email_Soc"), o.getString("Phone1_Soc"),
                                    o.getString("Phone2_Soc"), o.getString("Head1_Soc"), o.getString("Head2_Soc"),
                                    o.getString("Footer1_Soc"), o.getString("Footer2_Soc"),
                                    o.getString("Code_Office"), o.getString("Name_Office"), o.getString("State"));

                            loadPaper.add(Paper);
                            SqliteClass.getInstance(context).databasehelp.papersql.addPaper(Paper);
                        }
                    }
                }

                //Catalogo sugerido
                //VENTA DIRECTA
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
                            Suggested = new SuggestedClass(0, "VD", o.getString("Code_Customer"),
                                    o.getString("Code_Item"), o.getString("Description_Item"), o.getString("Category"),
                                    o.getString("Suggested"), "");
                            loadSuggested.add(Suggested);
                            SqliteClass.getInstance(context).databasehelp.suggestedsql.addSuggested(Suggested);
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
                            Suggested = new SuggestedClass(0, "PC", o.getString("Code_Customer"),
                                    o.getString("Code_Item"), o.getString("Description_Item"), o.getString("Category"),
                                    o.getString("Suggested"), "");
                            loadSuggested.add(Suggested);
                            SqliteClass.getInstance(context).databasehelp.suggestedsql.addSuggested(Suggested);
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
                            Suggested = new SuggestedClass(0, "PV", o.getString("Code_Route"),
                                    o.getString("Code_Item"), o.getString("Description_Item"), o.getString("Category"),
                                    o.getString("Suggested"), "");
                            loadSuggested.add(Suggested);
                            SqliteClass.getInstance(context).databasehelp.suggestedsql.addSuggested(Suggested);
                        }
                    }
                }
                //CONFIGURACION DE USUARIO
                loadConfiguration =  new ArrayList<ConfigurationClass>();
                jParser = new JSONParser();
                url = ConstValue.JSON_CONFIGURATION;
                json = jParser.getJSONFromUrl(url);
                System.out.println("MI JSON CONFIGURATION: " + json);
                if (json.has("data")){
                    if (json.get("data")instanceof JSONArray){
                        JSONArray jsonList = json.getJSONArray("data");
                        JSONObject o =  jsonList.getJSONObject(0);
                        Configuration = new ConfigurationClass(o.getInt("Id"), o.getString("Currency"), o.getString("UmWeight"), o.getString("Paper_Size"));
                        loadConfiguration.add(Configuration);
                        SqliteClass.getInstance(context).databasehelp.configurationsql.addConfiguration(Configuration);

                        String [] currency = o.getString("Currency").split(" - ");
                        ConstValue.setCurrency(currency[0]); //ConstValue.setCurrency("L. ");
                        ConstValue.setNameCurrency(currency[1]); //ConstValue.setNameCurrency("LEMPIRAS");
                        String [] unitMeasure = o.getString("UmWeight").split(" - ");
                        ConstValue.setUnitMeasure(unitMeasure[0]); //ConstValue.setUnitMeasure("LTS./LBS. ");
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                responseString = "CloudSales - Error al recuperar la información del portal.";
            }
            // TODO Auto-generated method stub
            return responseString;
        }
    }

}

