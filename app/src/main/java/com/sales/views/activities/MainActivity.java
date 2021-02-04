package com.sales.views.activities;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.sales.R;
import com.sales.adapters.MenuAdapter;
import com.sales.config.ConstValue;
import com.sales.db.SqliteClass;
import com.sales.models.StockClass;
import com.sales.models.SynOrderClass;
import com.sales.models.SynOrderItemClass;
import com.sales.models.SynchronizationClass;
import com.sales.utils.Common;
import com.sales.utils.JSONParser;
import com.sales.utils.Util;
import com.sales.views.fragments.AddCustomerFragment;
import com.sales.views.fragments.CustomerFragment;
import com.sales.utils.ConnectionDetector;
import com.sales.views.fragments.IndicatorFragment;
import com.sales.views.fragments.LoadOrderFragment;
import com.sales.views.fragments.PendingSyncFragment;
import com.sales.views.fragments.ProfileFragment;
import com.sales.views.fragments.DocumentFragment;
import com.sales.views.fragments.SyncFragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {
    ActionBar actionBar;
    public static final String TAG = "MainActivity";
    // Whether the Log Fragment is currently shown
    private boolean mLogShown;
    public Context context;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mDeawerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    ArrayList<HashMap<String, Integer>> menuArray;
    ListView mListView;
    MenuAdapter mAdapter;

    public SharedPreferences settings;
    public ConnectionDetector cd;

    private StockClass StockClass; private ArrayList<StockClass> loadStock;
    ProgressDialog dialogLoading;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private BroadcastReceiver mRegistrationBroadcastReceiver;


    Fragment fragment = null;
    Intent intent = null;

    Bundle args;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        settings = getSharedPreferences(ConstValue.MAIN_PREF, 0);
        cd=new ConnectionDetector(this);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("CloudSales");

        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.action_bar_title, null);
        actionBar.setCustomView(v);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mTitle = mDrawerTitle = getTitle();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDeawerView = (LinearLayout)findViewById(R.id.left_drawer); /** Menu de los fragments */
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START); /** Un fondito mas o menos */

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle("Home");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle("User");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);
        // getSupportActionBar().setBackgroundDrawable(R.drawable.background_01);

        menuArray = new ArrayList<HashMap<String,Integer>>();
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        map.put("option", R.string.navigation_customers);
        map.put("image", R.drawable.ic_customers_menu);
        menuArray.add(map);

        map = new HashMap<String, Integer>();
        map.put("option", R.string.navigation_print_documents);
        map.put("image", R.drawable.ic_print_menu);
        menuArray.add(map);

        map = new HashMap<String, Integer>();
        map.put("option", R.string.navigation_indicators);
        map.put("image", R.drawable.ic_indicators_menu);
        menuArray.add(map);

        map = new HashMap<String, Integer>();
        map.put("option", R.string.navigation_new_customer);
        map.put("image", R.drawable.ic_new_customer_menu);
        menuArray.add(map);

        map = new HashMap<String, Integer>();
        map.put("option", R.string.navigation_order_charge);
        map.put("image", R.drawable.ic_order_charge_menu);
        menuArray.add(map);

        map = new HashMap<String, Integer>();
        map.put("option", R.string.navigation_pending);
        map.put("image", R.drawable.ic_pending_menu);
        menuArray.add(map);

        map = new HashMap<String, Integer>();
        map.put("option", R.string.navigation_profile);
        map.put("image", R.drawable.ic_person_profile);
        menuArray.add(map);

        map = new HashMap<String, Integer>();
        map.put("option", R.string.navigation_sync);
        map.put("image", R.drawable.ic_sync_menu);
        menuArray.add(map);

        map = new HashMap<String, Integer>();
        map.put("option", R.string.navigation_logout);
        map.put("image", R.drawable.ic_exit_menu_accent);
        menuArray.add(map);

        mListView = (ListView)findViewById(R.id.list_navigability);
        mAdapter = new MenuAdapter(getApplicationContext(), menuArray);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                set_fragment_page(arg2);

            }
        });

        if(getIntent().hasExtra("fragment_position")){
            set_fragment_page(getIntent().getExtras().getInt("fragment_position"));
        }else {
            set_fragment_page(0);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Util.logout(MainActivity.this, context);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onTabSelected(ActionBar.Tab arg0,
                              android.support.v4.app.FragmentTransaction arg1) {
        // TODO Auto-generated method stub
        set_fragment_page(Integer.parseInt(arg0.getTag().toString()));
    }

    @Override
    public void onTabUnselected(ActionBar.Tab arg0,
                                android.support.v4.app.FragmentTransaction arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTabReselected(ActionBar.Tab arg0,
                                android.support.v4.app.FragmentTransaction arg1) {
        // TODO Auto-generated method stub
    }
    public void set_fragment_page(int position){

        switch (position) {
            case 0:
                fragment = new CustomerFragment();
                args = new Bundle();
                fragment.setArguments(args);
                break;
            case 1:
                fragment = new DocumentFragment();
                args = new Bundle();
                fragment.setArguments(args);
                break;
            case 2:
                ConstValue.setInventarySearch("0");
                fragment = new IndicatorFragment();
                args = new Bundle();
                fragment.setArguments(args);
                break;
            case 3:
                fragment = new AddCustomerFragment();
                args = new Bundle();
                fragment.setArguments(args);
                break;
            case 4:
                String date = SqliteClass.getInstance(context).databasehelp.synordersql.getDateLastSynOrderData("PV");
                if(Util.before(date)){
                    new loadSuggestedOrderTask().execute(true);
                }
                else{
                    Util.infoDialog(this, context, "CloudSales", "Pedido de Carga ya generado anteriormente.");
                }
                break;
            case 5:
                fragment = new PendingSyncFragment();
                args = new Bundle();
                fragment.setArguments(args);
                break;
            case 6:
                fragment = new ProfileFragment();
                args = new Bundle();
                fragment.setArguments(args);
                break;
            case 7:
                fragment = new SyncFragment();
                args = new Bundle();
                fragment.setArguments(args);
                break;
            case 8:
                Util.logout(MainActivity.this, context);
                break;



            default:
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
        if (fragment!=null) {
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getFragmentManager();
            if (position==0) {
                fragmentManager.beginTransaction()
                        .replace(R.id.sample_content_fragment, fragment)
                        .commit();
            }else{
                fragmentManager.beginTransaction()
                        .replace(R.id.sample_content_fragment, fragment)
                        .addToBackStack(null)
                        .commit();
            }
            mDrawerLayout.closeDrawer(mDeawerView);
        }
    }

    @Override
    public void onBackPressed() {
        Util.logout(MainActivity.this, context);
    }


    class loadSuggestedOrderTask extends AsyncTask<Boolean, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            dialogLoading = ProgressDialog.show(context, "CloudSales", "Cargando Pedido Sugerido. Espere ...", true);
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            //startService(new Intent(context, ServicePM.class));
            if (result != null) {
                Toast.makeText(context, "CloudSales " + result, Toast.LENGTH_LONG).show();
            } else {
                ConstValue.setSuggestedState("1");
                fragment = new LoadOrderFragment();
                args = new Bundle();
                fragment.setArguments(args);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                    .replace(R.id.sample_content_fragment, fragment)
                    .addToBackStack(null)
                    .commit();

                mDrawerLayout.closeDrawer(mDeawerView);
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
                Util.loadOrderItems(context);
                Util.fillItemWeight(context);

            } catch (Exception e) {
                responseString = e.toString();
                Log.e("LOADING ERROR", "Error loading " + e.toString());
            }
            return responseString;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
