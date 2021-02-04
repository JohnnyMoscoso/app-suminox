package com.sales.views.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sales.R;
import com.sales.config.ConstValue;
import com.sales.db.SqliteClass;
import com.sales.models.ItemClass;
import com.sales.models.SynOrderClass;
import com.sales.models.SynOrderItemClass;
import com.sales.utils.Common;
import com.sales.utils.ConnectionDetector;
import com.sales.utils.Util;
import com.sales.views.activities.MainActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class LoadOrderFragment extends Fragment {

    View rootView;
    Common common;

    List<ItemClass> itemList;
    Activity activity;
    ListView itemListView;
    Context context;

    ConnectionDetector cd;

    static ArrayList<HashMap<String, String>> data;

    TextView labelTotal, labelMinimum, labelPending;
    TextView inputTotal, inputMinimum, inputPending;

    TextView inputSubtotal; //from adapter

    TextView emptyTextView;

    BigDecimal minimum = new BigDecimal(0);
    BigDecimal pending = new BigDecimal(0);
    BigDecimal _subtotal = new BigDecimal(0); //each item
    BigDecimal total = new BigDecimal(0); //whole acivity

    BigDecimal restingLtsLbs;
    ProgressDialog dialogLoading;
    ArrayList<SynOrderClass> synOrderLoad;
    ArrayList<SynOrderItemClass> synOrderItemLoad;
    ArrayList<String> weightLoad = new ArrayList<String>();
    LocationManager mLocationManager;


    public LoadOrderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_load_order, container, false);

        common = new Common();
        activity = getActivity();
        context = getActivity();

        cd = new ConnectionDetector(context);
        emptyTextView = (TextView) rootView.findViewById(android.R.id.empty);
        itemListView = (ListView) rootView.findViewById(R.id.list_order_load);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        labelMinimum = (TextView) rootView.findViewById(R.id.tx_label_minimum);
        labelMinimum.setText("MINIMO " + ConstValue.getUnitMeasure());
        labelTotal = (TextView) rootView.findViewById(R.id.tx_label_total);
        labelTotal.setText("TOTAL " + ConstValue.getUnitMeasure());
        labelPending = (TextView) rootView.findViewById(R.id.tx_label_pending);
        labelPending.setText("PENDIENTE " + ConstValue.getUnitMeasure());

        minimum = new BigDecimal(ConstValue.getLimitLts());
        inputMinimum = (TextView) rootView.findViewById(R.id.tx_input_minimum);
        inputMinimum.setText(Util.formatBigDecimal(minimum).toString());

        inputPending = (TextView) rootView.findViewById(R.id.tx_input_pending);
        inputPending.setText(Util.formatBigDecimal(pending).toString());
        inputTotal = (TextView) rootView.findViewById(R.id.tx_input_total);
        inputTotal.setText(Util.formatBigDecimal(total).toString());

        itemList = SqliteClass.getInstance(context).databasehelp.pricelistsql.getItemsByPriceList(ConstValue.getDefaultPriceListCode());
        data = ConstValue.getOrderItems();
        ItemAdapter adapter = new ItemAdapter(activity, data);
        itemListView.setAdapter(adapter);

        if (itemList.size() > 0) emptyTextView.setVisibility(View.GONE);
        else emptyTextView.setVisibility(View.VISIBLE);


        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            verify();

        }
        return super.onOptionsItemSelected(item);
    }


    public class ItemAdapter extends BaseAdapter {

        private LayoutInflater inflater = null;
        private Context context;
        private ArrayList<HashMap<String, String>> data;

        RelativeLayout layout;
        TextView itemDescription, unit, inputValue, subtotal;
        EditText inputQuantity;

        public ItemAdapter(Context context, ArrayList<HashMap<String, String>> data) {
            this.context = context;
            this.data = data;
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ItemClass item = itemList.get(position);
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.row_load_order, null);

                inputQuantity = (EditText) convertView.findViewById(R.id.tx_input_quantity);
                inputSubtotal = (TextView) convertView.findViewById(R.id.tx_input_subtotal);

                inputQuantity.addTextChangedListener(new ItemTextWatcher(convertView));
            }

            final HashMap<String, String> map = data.get(position);

            layout = (RelativeLayout) convertView.findViewById(R.id.loadOrder);
            layout.setBackgroundResource(Integer.parseInt(map.get("color")));

            itemDescription = (TextView) convertView.findViewById(R.id.tx_item_description);
            itemDescription.setText(map.get("description"));

            inputValue = (TextView) convertView.findViewById(R.id.tx_input_value);
            inputValue.setText(map.get("price"));

            inputQuantity = (EditText) convertView.findViewById(R.id.tx_input_quantity);
            inputQuantity.setTag(item);
            if (ConstValue.getSuggestedState().equals("1") && map.get("control").equals("false")) {
                if(!map.get("suggestedQuantity").equals("0")) {
                    inputQuantity.setText(map.get("suggestedQuantity"));
                    map.put("control", "true");
                }

            }

            inputSubtotal = (TextView) convertView.findViewById(R.id.tx_input_subtotal);

            if (item.getTotal().equals("0") || item.getTotal().isEmpty()) {
                inputSubtotal.setText(Util.formatBigDecimal(_subtotal).toString());
            } else {
                inputSubtotal.setText(Util.formatBigDecimal(new BigDecimal(item.getTotal())).toString());

            }


            if (!item.getQuantity().isEmpty() && !item.getQuantity().equals("0") &&
                    !item.getQuantity().equals("0.00") && !item.getQuantity().equals(".")) {
                inputQuantity.setText(String.valueOf(item.getQuantity()));
            } else {
                inputQuantity.setText("");
            }
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


    public class ItemTextWatcher implements TextWatcher {
        public View view;

        public ItemTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //do nothing
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //do nothing
        }

        public void afterTextChanged(Editable s) {
            if (s.toString().contains(".") || s.toString().isEmpty() || s.toString().contains(",")) {
                EditText quantityView = (EditText) view.findViewById(R.id.tx_input_quantity);
                ItemClass item = (ItemClass) quantityView.getTag();
                String a = item.getDescription();
                System.out.println(a);
                setter("Cantidad", Integer.parseInt(item.getPosition()), "0");
            } else {
                String quantityString = s.toString();
                int quantity = Integer.parseInt(quantityString);
                System.out.println("QUANTITY: " + quantity);
                EditText quantityView = (EditText) view.findViewById(R.id.tx_input_quantity);
                ItemClass item = (ItemClass) quantityView.getTag();
                if (item.getQuantity().isEmpty()) {
                    setter("Cantidad", Integer.parseInt(item.getPosition()), quantityString);
                } else {
                    if (quantity != Integer.parseInt(item.getQuantity())) {
                        setter("Cantidad", Integer.parseInt(item.getPosition()), quantityString);
                    }
                }
            }
            return;
        }
    }

    public void setter(String type, int location, String quantity) {
        int size = itemList.size();

        if (type.equals("Cantidad")) {
            if (Integer.parseInt(quantity) > 0) {
                itemList.get(location).setQuantity(quantity);
                for (int i = 0; i < size; i++) {
                    if (itemList.get(location).getCode().equals(itemList.get(i).getCode())) {
                        itemList.get(i).setQuantity(quantity.toString());
                    }
                }
                calculate();
            } else {
                if (!itemList.get(location).getQuantity().isEmpty()) {
                    itemList.get(location).setQuantity("");
                    for (int i = 0; i < size; i++) {
                        if (itemList.get(location).getCode().equals(itemList.get(i).getCode())) {
                            itemList.get(i).setQuantity("");
                        }
                    }
                    calculate();
                }
            }
        }
        if (type.equals("Total")) {
            itemList.get(location).setTotal(quantity);
            for (int i = 0; i < size; i++) {
                if (itemList.get(location).getCode().equals(itemList.get(i).getCode())) {
                    itemList.get(i).setTotal(quantity.toString());
                }
            }
        }
    }

    public static String getDateHourActual() {
        SimpleDateFormat dateFormatHour = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        SimpleDateFormat dateFormatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        String _hour = dateFormatHour.format(date);
        String _date = dateFormatDate.format(date);
        return _date + " " + _hour;
    }


    public void calculate() {
        weightLoad = ConstValue.getItemWeight();
        int size = itemList.size();
        pending = new BigDecimal(0);
        total = new BigDecimal(0);
        for (int i = 0; i < size; i++) {
            if (!itemList.get(i).getQuantity().equals("")) {
                //String weight = SqliteClass.getInstance(context).databasehelp.itemsql.getWeightByItemCode(itemList.get(i).getCode());
                String weight = weightLoad.get(i);
                BigDecimal itemQuantity = new BigDecimal(itemList.get(i).getQuantity());
                BigDecimal itemPrice = new BigDecimal(weight);
                BigDecimal itemTotal = itemQuantity.multiply(itemPrice);


                total = total.add(itemTotal);
                pending = minimum.subtract(total);
                setter("Total", Integer.parseInt(itemList.get(i).getPosition()), itemTotal.toString());
            }
        }

        inputPending.setText(Util.formatBigDecimal(pending).toString());
        inputTotal.setText(Util.formatBigDecimal(total).toString());
    }

    public void verify() {
        int size = itemList.size();
        int cnt = 0;
        for (int i = 0; i < size; i++) {
            if (!itemList.get(i).getQuantity().isEmpty()) {
                cnt++;
            }
        }
        if (cnt > 0) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_save);

            TextView head = (TextView) dialog.findViewById(R.id.alert_info_title);
            head.setText("CloudSales - Pedido de Carga");
            TextView content = (TextView) dialog.findViewById(R.id.alert_save_content);
            content.setText("Está seguro de generar el Pedido de Carga?");

            Button dialogButtonOk = (Button) dialog.findViewById(R.id.alert_ok);
            dialogButtonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String numberDocument = "";
                    int numberOrders = SqliteClass.getInstance(context).databasehelp.synordersql.countDocuments("PV");
                    String numberSerial = SqliteClass.getInstance(context).databasehelp.documentsql.getDocument("PV").getDocumentSerial();

                    if (numberOrders == 0) {
                        if (SqliteClass.getInstance(context).databasehelp.documentsql.getDocument("PV").getActualLimit().equals("null") ||
                                SqliteClass.getInstance(context).databasehelp.documentsql.getDocument("PV").getActualLimit().equals(null)) {
                            numberDocument = SqliteClass.getInstance(context).databasehelp.documentsql.getDocument("PV").getLowLimit();
                        } else {
                            numberDocument = SqliteClass.getInstance(context).databasehelp.documentsql.getDocument("PV").getActualLimit();
                        }
                    } else {
                        numberDocument = Integer.parseInt(SqliteClass.getInstance(context).databasehelp.synordersql.getLastOrderNumeration("PV")) + 1 + "";
                    }
                    ConstValue.setDocumentNumeration(numberDocument);
                    int _number = Integer.parseInt(numberDocument);
                    ConstValue.setDocumentNumber(numberSerial + " " + Util.formatInvoice(_number));
                    String dateHour = getDateHourActual();
                    ConstValue.setOrderDateString(dateHour);
                    ConstValue.setTotal(String.valueOf(total));
                    if (endOrder()) {
                        validationOrder();
                    }
                    dialog.dismiss();
                }
            });
            Button dialogButtonCancel = (Button) dialog.findViewById(R.id.alert_cancel);
            dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else {
            Util.infoDialog(getActivity(), context, "CloudSales", "No hay productos relacionados al Pedido de Carga.");
        }
    }

    public void validationOrder() {
        BigDecimal pending = new BigDecimal(ConstValue.getPendingLimitLts());
        BigDecimal currentLtsLbs = minimum.subtract(total);
        currentLtsLbs = currentLtsLbs.multiply(new BigDecimal(-1));
        restingLtsLbs = pending.subtract(currentLtsLbs);

        int _res = restingLtsLbs.compareTo(new BigDecimal(0));
        if (_res == 1) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.alert_info);
            TextView head = (TextView) dialog.findViewById(R.id.alert_info_title);
            head.setText("CloudSales - Atraso");
            TextView content = (TextView) dialog.findViewById(R.id.alert_info_content);
            content.setText("Usted tiene un atraso acumulado en Lts./Lbs.  con respecto a su cuota mensual de: " +
                    ConstValue.getPendingLimitLts() + " " + ConstValue.getUnitMeasure() + "\nSe sugiere cargar más Productos.");
            Button dialogButtonOk = (Button) dialog.findViewById(R.id.alert_ok);
            dialogButtonOk.setText("Cargar Más");
            dialogButtonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            Button dialogButtonCancel = (Button) dialog.findViewById(R.id.alert_cancel);
            dialogButtonCancel.setText("Ignorar");
            dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (saveOrder()) {
                        alertFinish();
                    } else {
                        Util.infoDialog(getActivity(), context, "CloudSales", "El pedido presenta errores.");
                    }
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else {
            if (saveOrder()) {
                alertFinish();
            } else {
                Util.infoDialog(getActivity(), context, "CloudSales", "El pedido presenta errores.");
            }
        }
    }

    public boolean endOrder() {
        ArrayList<SynOrderItemClass> orderItemLoadHelp = new ArrayList<SynOrderItemClass>();
        SynOrderItemClass orderItemHelp;
        boolean endedOrder = false;
        int res = minimum.compareTo(total);
        if (res == 0 || res == -1) {
            for (int i = 0; i < itemList.size(); i++) {
                if (itemList.get(i).getQuantity().equals("") || itemList.get(i).getQuantity().equals("0")) {
                } else {
                    String price = SqliteClass.getInstance(context).databasehelp.pricelistsql.getPriceByItemCode(itemList.get(i).getCode(), ConstValue.getDefaultPriceListCode());
                    BigDecimal _price = new BigDecimal(price);
                    BigDecimal _quantity = new BigDecimal(itemList.get(i).getQuantity());
                    String priceItemTotal = String.valueOf(_price.multiply(_quantity));

                    orderItemHelp = new SynOrderItemClass(
                            0,
                            ConstValue.getDocumentNumber(),
                            ConstValue.getOrderDateString(),
                            itemList.get(i).getCategoryCode(),
                            itemList.get(i).getCode(),
                            itemList.get(i).getDescription(),
                            itemList.get(i).getQuantity(),
                            price,
                            itemList.get(i).getMeasureUnitCode(),
                            "", //TODO
                            String.valueOf(_price.multiply(_quantity)),
                            "", //TODO
                            "", //TODO
                            "0");
                    orderItemLoadHelp.add(orderItemHelp);
                }
            }
            ConstValue.clearSynOrderItemList();
            ConstValue.addSynOrderItemList(orderItemLoadHelp);
            endedOrder = true;
        } else if (res == 1) {
            Util.infoDialog(getActivity(), context, "CloudSales - Aviso", "LTS./LBS. No cumplen con el mínimo.");
            endedOrder = false;
        }
        return endedOrder;
    }


    //DETALLE
    public Boolean saveOrder() {
        ArrayList<SynOrderItemClass> orderItemLoadHelp;
        orderItemLoadHelp = ConstValue.getSynOrderItemList();
        SynOrderItemClass OrderItem;
        for (int i = 0; i < orderItemLoadHelp.size(); i++) {
            OrderItem = new SynOrderItemClass(
                    orderItemLoadHelp.get(i).getId(),
                    orderItemLoadHelp.get(i).getNumberDocument(),
                    orderItemLoadHelp.get(i).getDateHour(),
                    orderItemLoadHelp.get(i).getCategory(),
                    orderItemLoadHelp.get(i).getCodeItem(),
                    orderItemLoadHelp.get(i).getDescriptionItem(),
                    orderItemLoadHelp.get(i).getQuantity(),
                    orderItemLoadHelp.get(i).getPrice(),
                    orderItemLoadHelp.get(i).getUnitMeasure(),
                    orderItemLoadHelp.get(i).getIgv(),
                    orderItemLoadHelp.get(i).getTotalPay(),
                    orderItemLoadHelp.get(i).getState(),
                    orderItemLoadHelp.get(i).getStateCharge(),
                    orderItemLoadHelp.get(i).getLoad()
            );
            SqliteClass.getInstance(context).databasehelp.synorderitemsql.addSynOrderItem(OrderItem);
        }

        //CABECERA
        SynOrderClass Order = new SynOrderClass(0,
                "PV", //Codigo del tipo de documento de la web
                "", "",
                ConstValue.getDocumentNumber(),
                ConstValue.getDocumentNumeration(), //para contar el numero en el que nos quedamos
                ConstValue.getUserRouteCode(),
                ConstValue.getUserId(),
                ConstValue.getOrderDateString(),
                "", //vacio
                "", //nose
                "", "", "",
                ConstValue.getTotal(),
                "", "0");

        SqliteClass.getInstance(context).databasehelp.synordersql.addSynOrder(Order);

        SqliteClass.getInstance(context).databasehelp.usersql.updateField(ConstValue.getUserId(), "au_user_pen_lts_lbs", restingLtsLbs.toString());
        ConstValue.setPendingLimitLts(restingLtsLbs.toString());
        return true;
    }


    public void alertFinish() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_info);
        ImageView image = (ImageView) dialog.findViewById(R.id.alert_info);
        image.setImageResource(R.drawable.ic_alert_info);
        TextView head = (TextView) dialog.findViewById(R.id.alert_info_title);
        head.setText("CloudSales");
        TextView content = (TextView) dialog.findViewById(R.id.alert_info_content);
        content.setText("Pedido de Carga generado con éxito. ¿Desea enviarlo ahora? De no ser así, será guardado en Pendientes de Sincronización.");
        Button dbOk = (Button) dialog.findViewById(R.id.alert_ok);
        dbOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.isConnectingToInternet()) {
                    new loadOrderTask().execute(true);
                } else {
                    Toast.makeText(context, "El dispositivo no cuenta con conexión a Internet. " +
                            "El Pedido de Carga será almacenado en Pendientes de Sincronización.", Toast.LENGTH_SHORT).show();
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


    class loadOrderTask extends AsyncTask<Boolean, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            dialogLoading = ProgressDialog.show(context, "CloudSales", "Enviando Pedido de Carga. Espere ...", true);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            //startService(new Intent(context, ServicePM.class));
            if (result != null) {
                Toast.makeText(context, "CloudSales " + result, Toast.LENGTH_LONG).show();
            } else {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.alert_info);
                ImageView image = (ImageView) dialog.findViewById(R.id.alert_info);
                image.setImageResource(R.drawable.ic_alert_info);
                TextView head = (TextView) dialog.findViewById(R.id.alert_info_title);
                head.setText("CloudSales - Pedido de Carga");
                TextView content = (TextView) dialog.findViewById(R.id.alert_info_content);
                content.setText("El Pedido de Carga " + ConstValue.getDocumentNumber() + " se envió con éxito. \n ¿Desea regresar a la principal?");
                Button dbOk = (Button) dialog.findViewById(R.id.alert_ok);
                dbOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
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

            synOrderLoad = new ArrayList<SynOrderClass>();
            synOrderLoad = SqliteClass.getInstance(context).databasehelp.synordersql.getPendingSynOrderData("PV");
            common = new Common();

            try {
                JSONObject json;
                JSONObject jsonHead;
                for (int z = 0; z < synOrderLoad.size(); z++) {
                    jsonHead = new JSONObject();
                    jsonHead.put("Type", "PV");
                    jsonHead.put("Code_Customer", synOrderLoad.get(z).getCodeCustomer());
                    jsonHead.put("Name_Customer", synOrderLoad.get(z).getNameCustomer());
                    jsonHead.put("Number_Document", synOrderLoad.get(z).getNumberDocument());
                    jsonHead.put("Numeration", synOrderLoad.get(z).getNumeration());
                    jsonHead.put("Code_Route", synOrderLoad.get(z).getCodeRoute());
                    jsonHead.put("Id_Userapp", synOrderLoad.get(z).getIdUserApp());
                    jsonHead.put("Date_Hour", synOrderLoad.get(z).getDateHour());
                    jsonHead.put("RUC", synOrderLoad.get(z).getRuc());
                    jsonHead.put("State_Nubefact", synOrderLoad.get(z).getStateNubeFact());
                    jsonHead.put("Op_Exonerated", synOrderLoad.get(z).getOperationExonerated());
                    jsonHead.put("Op_Taxed", synOrderLoad.get(z).getOperationTaxed());
                    jsonHead.put("Igv", synOrderLoad.get(z).getIgv());
                    jsonHead.put("Total_Pay", synOrderLoad.get(z).getTotalPay());
                    jsonHead.put("State_Charge", synOrderLoad.get(z).getStateCharge());

                    JSONArray jsonArrayItemsDetail = new JSONArray();
                    synOrderItemLoad = new ArrayList<SynOrderItemClass>();
                    synOrderItemLoad = SqliteClass.getInstance(context).databasehelp.synorderitemsql.getPendingSynOrderItemData(synOrderLoad.get(z).getNumberDocument());
                    for (int i = 0; i < synOrderItemLoad.size(); i++) {
                        JSONObject itemDetail = new JSONObject();
                        itemDetail.put("Number_Document", synOrderItemLoad.get(i).getNumberDocument());
                        itemDetail.put("Date_Hour", synOrderItemLoad.get(i).getDateHour());
                        itemDetail.put("Category", synOrderItemLoad.get(i).getCategory());
                        itemDetail.put("Code_Item", synOrderItemLoad.get(i).getCodeItem()); //no se genera
                        itemDetail.put("Description_Item", synOrderItemLoad.get(i).getDescriptionItem());
                        itemDetail.put("Quantity", synOrderItemLoad.get(i).getQuantity());
                        itemDetail.put("Price", synOrderItemLoad.get(i).getPrice());
                        itemDetail.put("Unit_Measure", synOrderItemLoad.get(i).getUnitMeasure());
                        itemDetail.put("Igv", synOrderItemLoad.get(i).getIgv());
                        itemDetail.put("Total_Pay", synOrderItemLoad.get(i).getTotalPay());
                        itemDetail.put("State", synOrderItemLoad.get(i).getState());
                        itemDetail.put("State_Charge", synOrderItemLoad.get(i).getStateCharge());
                        jsonArrayItemsDetail.put(itemDetail);
                    }
                    jsonHead.put("lInvoiceItem", jsonArrayItemsDetail);

                    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                    System.out.println("JSON A ENVIAR: " + jsonHead.toString());
                    nvps.add(new BasicNameValuePair("result", jsonHead.toString()));
                    json = common.sendJsonData(ConstValue.JSON_SEND_DOCUMENT, nvps);
                    if (json.getString("response").equalsIgnoreCase("success")) {
                        /** Actualizar todos mis loads a 1 para que no sean enviados en ningun otro lado, pero igual puedan mostrarse */
                        SqliteClass.getInstance(context).databasehelp.synordersql.updateOrderLoad(synOrderLoad.get(z).getNumberDocument());
                        SqliteClass.getInstance(context).databasehelp.synorderitemsql.updateOrderLoad(synOrderLoad.get(z).getNumberDocument());

                    } else {
                        responseString = json.getString("response");
                    }
                }
            } catch (JSONException e) {
                responseString = e.toString();
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            return responseString;
        }
    }
}
