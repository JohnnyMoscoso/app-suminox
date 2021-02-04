package com.sales.views.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sales.R;
import com.sales.config.ConstValue;
import com.sales.db.SqliteClass;
import com.sales.models.UserClass;
import com.sales.utils.Common;
import com.sales.views.activities.LoginActivity;
import com.sales.views.activities.MainActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProfileFragment extends Fragment {


    public Context context;
    public View rootView;
    public Common common;
    ProgressDialog inspectDialog;

    RelativeLayout changePass;

    TextView userName, nameLastName, email, routeName, routeCode;
    EditText lastPassword, newPassword1, newPassword2;

    ArrayList<UserClass> userData;

    public ProfileFragment() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        context = getActivity();

        userData = SqliteClass.getInstance(context).databasehelp.usersql.getUserData();

        userName = (TextView) rootView.findViewById(R.id.tx_input_username);
        userName.setText(userData.get(0).getUsername());
        nameLastName = (TextView) rootView.findViewById(R.id.tx_input_name_lastname);
        nameLastName.setText(userData.get(0).getName());
        email = (TextView) rootView.findViewById(R.id.tx_input_email);
        email.setText(userData.get(0).getEmail());
        routeName = (TextView) rootView.findViewById(R.id.tx_input_route);
        routeName.setText(userData.get(0).getRouteName());
        routeCode = (TextView) rootView.findViewById(R.id.tx_input_route_code);
        routeCode.setText(userData.get(0).getRouteCode());
        changePass = (RelativeLayout) rootView.findViewById(R.id.layout_change_pass) ;
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChangePassword(context);
            }
        });

        return rootView;
    }


    @Override
    public void onDetach() { super.onDetach(); }

    public void dialogChangePassword(final Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_change_password);

        TextView head = (TextView) dialog.findViewById(R.id.text_title);
        head.setText("CloudSales - Cambio de Contraseña");
        final TextView content = (TextView) dialog.findViewById(R.id.text_content);
        content.setText("Ingrese los datos: ");

        lastPassword = (EditText) dialog.findViewById(R.id.tx_last_password);
        newPassword1 = (EditText) dialog.findViewById(R.id.tx_new_password_1);
        newPassword2 = (EditText) dialog.findViewById(R.id.tx_new_password_2);
        Button dialogButtonOk = (Button) dialog.findViewById(R.id.alert_ok);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.alert_cancel);


        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(lastPassword.getText().toString().equals("") || newPassword1.getText().toString().equals("") || newPassword2.getText().toString().equals("")){
                    Toast.makeText(context, "Hay uno o más campos vacíos.", Toast.LENGTH_SHORT).show();
                }
                else if(!lastPassword.getText().toString().equals(ConstValue.getUserPass())){
                    Toast.makeText(context, "La contraseña antigua no coincide.", Toast.LENGTH_SHORT).show();
                }
                else if(!newPassword1.getText().toString().equals(newPassword2.getText().toString())){
                    Toast.makeText(context, "La nueva contraseña no coincide.", Toast.LENGTH_SHORT).show();
                }
                else{
                    new dataTask().execute(true);
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

    class dataTask extends AsyncTask<Boolean, Void, String> {
        @Override
        protected String doInBackground(Boolean... params) {
            // TODO Auto-generated method stub
            String responseString = null;

            common = new Common();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

            nameValuePairs.add(new BasicNameValuePair("id", ConstValue.getUserId()));
            nameValuePairs.add(new BasicNameValuePair("newpassword", newPassword1.getText().toString()));

            JSONObject jObj = common.sendJsonData(ConstValue.JSON_CHANGE_PASSWORD, nameValuePairs);
            System.out.println("TU JSON CHANGE PASS: " + jObj);
                try {
                    if (!jObj.getString("response").equalsIgnoreCase("success")) {
                        return responseString;
                    }
                } catch (JSONException e) {
                    responseString = "CloudSales - " + e.getMessage();
                    return responseString;
                }

            return responseString;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            inspectDialog = ProgressDialog.show(context, "", "Cambiando contraseña. Espere ...", true);
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Toast.makeText(context, "CloudSales " + result, Toast.LENGTH_LONG).show();
            } else {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.alert_info_change_password);
                ImageView image = (ImageView) dialog.findViewById(R.id.alert_info);
                image.setImageResource(R.drawable.ic_alert_info);
                TextView head = (TextView) dialog.findViewById(R.id.alert_info_title);
                head.setText("CloudSales");
                TextView content = (TextView) dialog.findViewById(R.id.alert_info_content);
                content.setText("Su contraseña ha sido modificada. La aplicación se reiniciará.");
                Button dbOk = (Button) dialog.findViewById(R.id.alert_ok);
                dbOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
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
