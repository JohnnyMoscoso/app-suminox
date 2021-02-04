package com.sales.views.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sales.R;
import com.sales.config.ConstValue;
import com.sales.utils.Common;
import com.sales.views.activities.MainActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddCustomerFragment extends Fragment {

    Activity activity;
    Context context;
    public View rootView;
    public Common common;

    RelativeLayout saveCustomer;
    EditText inputName, inputCode, inputAddress, inputRepresentant, inputPhone;
    TextView selectDay;
    String daysInput = "";

    ProgressDialog inspectDialog;

    public AddCustomerFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_add_customer, container, false);
        activity = getActivity();
        context = getActivity();
        common = new Common();

        inputName = (EditText) rootView.findViewById(R.id.tx_input_username);
        inputCode = (EditText) rootView.findViewById(R.id.tx_input_code);
        inputAddress = (EditText) rootView.findViewById(R.id.tx_input_address);
        inputRepresentant = (EditText) rootView.findViewById(R.id.tx_input_representant);
        inputPhone = (EditText) rootView.findViewById(R.id.tx_input_phone);
        selectDay = (TextView) rootView.findViewById(R.id.tx_select_days);
        saveCustomer = (RelativeLayout) rootView.findViewById(R.id.layout_save_customer);

        selectDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChooseDays(context, selectDay);
            }
        });

        saveCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(inputName.getText().toString().equals("") || inputCode.getText().toString().equals("") ||
                        inputAddress.getText().toString().equals("") || inputRepresentant.getText().toString().equals("") ||
                        inputPhone.getText().toString().equals("") || selectDay.getText().toString().equals("Toque para seleccionar días.")){

                    Toast.makeText(context, "Complete todos los campos antes de guardar a un cliente.", Toast.LENGTH_SHORT).show();

                }
                else{
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.alert_info);
                    ImageView image = (ImageView) dialog.findViewById(R.id.alert_info);
                    image.setImageResource(R.drawable.ic_alert_info);
                    TextView head = (TextView) dialog.findViewById(R.id.alert_info_title);
                    head.setText("CloudSales - Nuevo Cliente");
                    TextView content = (TextView) dialog.findViewById(R.id.alert_info_content);
                    content.setText("¿Está seguro de guardar y enviar el nuevo cliente al servidor?");
                    Button dbOk = (Button) dialog.findViewById(R.id.alert_ok);
                    dbOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new addCustomerTask().execute(true);
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
        });

        return rootView;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void dialogChooseDays(final Context context, final TextView selectedDays){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_multiple_choice);

        TextView head = (TextView) dialog.findViewById(R.id.multiple_option_title);
        head.setText("CloudSales");
        final TextView content = (TextView) dialog.findViewById(R.id.multiple_option_content);
        content.setText("Por favor seleccione uno o más días: ");

        final ListView itemListViewMul = (ListView) dialog.findViewById(R.id.multiple_options);
        itemListViewMul.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        ArrayList<String> days = new ArrayList<>();
        days.add("LUNES"); days.add("MARTES"); days.add("MIERCOLES");
        days.add("JUEVES"); days.add("VIERNES"); days.add("SABADO"); days.add("DOMINGO");
        ArrayAdapter<String> adapter_multiple_choice = new ArrayAdapter<String>(dialog.getContext(), android.R.layout.simple_list_item_multiple_choice, days);
        itemListViewMul.setAdapter(adapter_multiple_choice);

        Button dialogButtonOk = (Button) dialog.findViewById(R.id.alert_ok);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.alert_cancel);

        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daysInput = "";
                SparseBooleanArray sparseBooleanArray = itemListViewMul.getCheckedItemPositions();
                if(itemListViewMul.getCheckedItemCount() > 0){
                    for(int i = 0; i < 7; i++){
                        if(sparseBooleanArray.get(i)){ daysInput += itemListViewMul.getItemAtPosition(i).toString().substring(0,2) + "-"; }
                    }
                    selectedDays.setText(daysInput);
                    selectedDays.setTextColor(Color.BLACK);
                }
                else{
                    Toast.makeText(context, "Seleccione alguna opcion por favor.", Toast.LENGTH_SHORT).show();
                }
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


    class addCustomerTask extends AsyncTask<Boolean, Void, String> {
        @Override
        protected String doInBackground(Boolean... params) {
            // TODO Auto-generated method stub
            String responseString = null;
            try {
                JSONObject jsonobj;
                common = new Common();
                jsonobj = new JSONObject();
                jsonobj.put("code", inputCode.getText().toString());
                jsonobj.put("name", inputName.getText().toString());
                jsonobj.put("address", inputAddress.getText().toString());
                jsonobj.put("representative", inputRepresentant.getText().toString());
                jsonobj.put("phone", inputPhone.getText().toString());
                jsonobj.put("day", selectDay.getText().toString());

                List <NameValuePair> nvps = new ArrayList<NameValuePair>();
                nvps.add(new BasicNameValuePair("result", jsonobj.toString()));
                System.out.println("JSON A ENVIAR " + jsonobj.toString());

                JSONObject jObj = common.sendJsonData(ConstValue.JSON_SEND_CUSTOMER, nvps);
                if(jObj.getString("response").equalsIgnoreCase("success")){
                    System.out.println("Se envio con exito");
                    responseString = null;
                }else{
                    responseString = jObj.getString("response");
                }

            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }

            System.out.println("MI RESPONSE STRING QUE RECIBE EL POSTEXECUTE: " + responseString);
            return responseString;
        }



        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            inspectDialog = ProgressDialog.show(context, "", "Enviando nuevo cliente al servidor. Por favor, espere ...", true);
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            System.out.println("LA VARIABLE RESULT" + result);
            if (result != null) {
                Toast.makeText(context, "CloudSales " + result, Toast.LENGTH_LONG).show();
            }
            else {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.alert_info);
                ImageView image = (ImageView) dialog.findViewById(R.id.alert_info);
                image.setImageResource(R.drawable.ic_alert_info);
                TextView head = (TextView) dialog.findViewById(R.id.alert_info_title);
                head.setText("CloudSales - Nuevo Cliente");
                TextView content = (TextView) dialog.findViewById(R.id.alert_info_content);
                content.setText("El cliente se guardó éxito. ");
                Button dbOk = (Button) dialog.findViewById(R.id.alert_ok);
                dbOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent(context, MainActivity.class);
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
            inspectDialog.dismiss();
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
