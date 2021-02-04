package com.sales.views.activities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sales.R;
import com.sales.config.ConstValue;
import com.sales.db.SqliteClass;
import com.sales.models.PaperClass;
import com.sales.models.SynOrderClass;
import com.sales.utils.Util;

@SuppressLint("HandlerLeak")
public class PrintActivity extends AppCompatActivity {
    private String customerName;
    public String print;
    private TextView txv_Print;
    private Context context;
    private static final String TAG = "MainActivity";
    private static final String BTAG = "BTThread";
    static final int MSG_BT_GOT_DATA = 1;
    static final int MSG_BT_STATUS_MSG = 2;
    static final int MSG_BT_FINISHED = 99;
    private Boolean key = false;
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothDevice btdevice = null;
    Thread bThread;
    BluetoothSocket bsocket;
    InputStream bis = null; //Bluetooth input stream
    OutputStream bos = null; //Bluetooth output stream
    private String MACAddress = "";
    private TextView printStatus;
    private TextView printMAC;
    private ImageButton printBtn;
    private boolean config = true;
    private String status = "desconectado";
    private static final int REQUEST_DETAIL = 99;

    private ActionBar actionBar;
    public SearchView searchView;
    private Menu menu;

    SynOrderClass synOrderClass;
    ArrayList<PaperClass> loadPrintConfiguration;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        System.gc();
        getSupportActionBar().setTitle("CloudSales");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        synOrderClass = SqliteClass.getInstance(context).databasehelp.synordersql.getSynOrder(ConstValue.getNumberDocumentSynOrder());


        ConstValue.setCustomerCode(synOrderClass.getCodeCustomer()); /** For paperConfiguration I need the customer code */
        System.out.println("DATA PAPER: " + SqliteClass.getInstance(context).databasehelp.papersql.getPaperData());
        loadPrintConfiguration = SqliteClass.getInstance(context).databasehelp.papersql.getPaperDataByCustomer(synOrderClass.getCodeCustomer());
        ConstValue.setPaperConfiguration(loadPrintConfiguration.get(0));


        print = Util.ticket(new BigDecimal(ConstValue.getExemptAmount()),
                new BigDecimal(ConstValue.getGravAmount()),
                new BigDecimal(ConstValue.getTaxableAmount()),
                new BigDecimal(ConstValue.getTotal()),
                synOrderClass.getType());

        TextView customer = (TextView) findViewById(R.id.tv_customer);

        customerName = synOrderClass.getNameCustomer();
        customer.setText(customerName);
        printStatus = (TextView) findViewById(R.id.tv_print_status);
        printMAC = (TextView) findViewById(R.id.tv_print_mac);
        printBtn = (ImageButton) findViewById(R.id.bt_print);
        txv_Print = (TextView) findViewById(R.id.tv_print_preview);
        txv_Print.setText(print);

        readPrinter();
    }


    private void readPrinter(){
        printStatus.setText("MAC IMPRESORA");
        Properties pp = new Properties();
        FileInputStream fis;
        try {
            fis = openFileInput("pprinter.properties");
            pp.load(fis);
            MACAddress = pp.getProperty("btprinter");
            if(MACAddress.isEmpty()){}else{loadingPrinter();}
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            printStatus.setText("IMPRESORA NO CONFIGURADA...");
            config = false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            printStatus.setText("IMPRESORA NO CONFIGURADA...");
            config = false;
        }
        if(MACAddress != null){
            if(MACAddress.equals("null")){
                config = false;
            }
        }else{
            printStatus.setText("IMPRESORA NO CONFIGURADA...");
            config = false;
        }
        printBtn.setOnClickListener(btnSendListener);
    }

    private void loadingPrinter(){
        printMAC.setText("CARGANDO IMPRESORA...");
        printMAC.setText(MACAddress);
        if(config){
            try{
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                startImpresionService();
                while(!status.equals("conectado")){
                }
                //SendDataToBluetooth(print);
            }catch(Exception e){
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Error " + stackTraceToString(e));
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    private void startImpresionService(){
        if(status.equals("desconectado")){
            try{
                Log.i(TAG, "PMOBILE: CONECTANDO");
                if(mBluetoothAdapter == null){
                    /**
                     * No hay Bluetooh
                     **/
                    Log.e(TAG, "getDefaultAdapter returned null");
                    printStatus.setText("BLUETOOTH INACTIVO...");

                }else{
                    if(!mBluetoothAdapter.isEnabled()){
                        /**
                         * Bluetooth esta deshabilitado
                         **/
                        Log.e(TAG, "PMOBILE: BLUETOOTH APAGADO");
                        printStatus.setText("BLUETOOTH DESHABILITADO...");
                    }else{
                        Log.i(TAG, "PMOBILE: CONECTADO A " + MACAddress);
                        if(MACAddress.contentEquals(" No Encontrado(s)")){
                            MACAddress = "00:00:00:00:00:00";
                        }
                        if(MACAddress.contentEquals("No Encontrado(s)")){
                            MACAddress = "00:00:00:00:00:00";
                        }
                        if(MACAddress.length()<17){
                            MACAddress = "00:00:00:00:00:00";
                        }
                        btdevice = mBluetoothAdapter.getRemoteDevice(MACAddress);
                        Log.i(TAG, "PMOBILE: DISPOSITIVO " + btdevice.getName());
                        Log.i(TAG, "Intentando Conectar...");
                        printStatus.setText("CONECTADO IMPRESORA...");
                        Log.i(TAG, "PMOBILE: INICIO DE HILO");
                        try {
                            bThread = new Thread(new BluetoothClient(btdevice, true));
                            bThread.start();

                        } catch (IOException e) {
                            Log.e(TAG, "PMOBILE: FALLO AL INICIAR HILO " + e);
                            printStatus.setText("NO FUE POSIBLE INICIAR BLUETOOTH...");
                        }
                        status = "conectado";
                    }
                }
            }catch(Exception e){
                //TODO: mostrar dialogo con numero de pedido
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Error " + stackTraceToString(e));
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        } else {
            Log.i(TAG, "PMOBILE: DISPOSITIVO DESCONECTADO");
        }
    }

    public String stackTraceToString(Throwable e) {
        String retValue = null;
        StringWriter sw = null;
        PrintWriter pw = null;
        try{
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            retValue = sw.toString();
        }finally{
            try{
                if(pw != null)  pw.close();
                if(sw != null)  sw.close();
            }catch (IOException ignore) {}
        }
        return retValue;
    }

    public class BluetoothClient implements Runnable {
        public BluetoothClient(BluetoothDevice device, boolean IsAnHTCDevice) throws IOException {
            if(IsAnHTCDevice){
                //This is a workaround for HTC devices, but it likes to throw an IOException "Connection timed out"
                try{
                    Method m = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
                    bsocket = (BluetoothSocket) m.invoke(device, Integer.valueOf(1));
                }catch (Exception e) {
                    Log.e(BTAG, "PMOBILE: ERROR AT HTC/createRfcommSocket: " + e);
                    e.printStackTrace();
                    handler.sendMessage(handler.obtainMessage(MSG_BT_STATUS_MSG, "MethodException: " + e));
                }
            }else{
                try{
                    UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                    bsocket = device.createRfcommSocketToServiceRecord(MY_UUID);
                }catch (Exception e) {
                    Log.e(BTAG, "Error en createRfcommSocketToServiceRecord: " + e);
                    e.printStackTrace();
                    handler.sendMessage(handler.obtainMessage(MSG_BT_STATUS_MSG, "MethodException: " + e));
                }
            }
        }
        public void run() {
            try {
                Log.i(BTAG, "PMOBILE: CANCELA BUSQUEDA");
                mBluetoothAdapter.cancelDiscovery();
                Log.i(BTAG, "PMOBILE: CONECTANDO SOCKET");
                bsocket.connect();
                bis = bsocket.getInputStream();
                bos = bsocket.getOutputStream();
                Log.i(BTAG, "PMOBILE: CREACIÓN DE SOCKET");
                handler.sendMessage(handler.obtainMessage(MSG_BT_STATUS_MSG, "CONECTADO"));
                Log.i(BTAG, "Esperando datos...");
                byte[] buffer = new byte[4096];
                int read = bis.read(buffer, 0, 4096); //
                Log.i(BTAG, "PMOBILE: PROCESANDO DATOS");
                while (read != -1) {
                    byte[] tempdata = new byte[read];
                    System.arraycopy(buffer, 0, tempdata, 0, read);
                    handler.sendMessage(handler.obtainMessage(MSG_BT_GOT_DATA, tempdata));
                    read = bis.read(buffer, 0, 4096); //
                }
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Log.i(BTAG, "Fin");
                handler.sendMessage(handler.obtainMessage(MSG_BT_FINISHED));
            }
        }
    }
    public void SendDataToBluetooth(String cmd) {
        try {
            if (bsocket != null) {
                bos.write(cmd.getBytes());
            }
            printStatus.setText("BLUETOOTH TRANSACCIÓN COMPLETA.");
        } catch (Exception e) {
            Log.e("SendDataToBluetooth", "PMOBILE:  FALLO ENVIO  " + e);
            printStatus.setText("BLUETOOTH TRANSACCIÓN INCOMPLETA.");
        } finally {
            //printStatus.setText("Conectado ");
        }
    }

    public Handler handler = new Handler() {
        /**
         * Manejador de datos procedentes de los conectores de red y Bluetooth
         **/
        @Override
        public void handleMessage(Message msg) {
            try{
                switch (msg.what) {
                    case MSG_BT_GOT_DATA:
                        Log.i("handleMessage", "MSG_BT_GOT_DATA: " + (String) msg.obj);
                        printStatus.setText((String) msg.obj);
                        break;
                    case MSG_BT_STATUS_MSG:
                        Log.i("handleMessage", "MSG_BT_STATUS_MSG: " + (String) msg.obj);
                        printStatus.setText((String) msg.obj);
                        break;
                    case MSG_BT_FINISHED:
                        Log.i("handleMessage", "MSG_BT_FINISHED");
                        //btnStart.setText("Connect");
                        break;
                    default:
                        super.handleMessage(msg);
                }
            }catch(Exception e){

            }
        }
    };
    @Override
    protected void onDestroy() {
        if(key){
            super.onDestroy();
        }
        if (bThread != null) {
            /**
             * Si el hilo se está ejecutando, cierre o interrumpe la toma de conexiones.
             **/
            Log.i(BTAG, "mata hilo BT");
            try {
                bis.close();
                bos.close();
                bsocket.close();
                bsocket = null;
            } catch (IOException e) {
                Log.e(BTAG, "IOException");
                e.printStackTrace();
            } catch (Exception e) {
                Log.e(BTAG, "Exception");
                e.printStackTrace();
            }
            try {
                Thread moribund = bThread;
                bThread = null;
                moribund.interrupt();
            } catch (Exception e) {}
            Log.i(BTAG, "BT Thread Killed");
        }
    }
    private OnClickListener btnSendListener = new OnClickListener() {
        public void onClick(View v){
            try{
                printStatus.setText("IMPRIMIENDO...");
                SendDataToBluetooth(print);
                printStatus.setText("IMPRESION FINALIZADA");
            }catch(Exception e){
                /**
                 * Mostrar dialogo con numero de pedido
                 **/
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Error " + stackTraceToString(e));
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if(requestCode == REQUEST_DETAIL){
            if (resultCode == RESULT_OK){
                config = true;
                readPrinter();
                loadingPrinter();
            }else if(resultCode == RESULT_CANCELED){}
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_printing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home){
            key = true;
            finish(); /** Calls onDestroy() itself */
        }
        else if(id==R.id.action_find_devices){
            Intent intent = new Intent(PrintActivity.this, DeviceListActivity.class);
            PrintActivity.this.startActivityForResult(intent, REQUEST_DETAIL);
            return true;
        }
        else if(id == R.id.action_logout){
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.alert_logout);

            ImageView image = (ImageView) dialog.findViewById(R.id.alert_logout);
            image.setImageResource(R.drawable.ic_exit_menu_accent);

            TextView head = (TextView) dialog.findViewById(R.id.alert_logout_title);
            head.setText("CloudSales");
            TextView content = (TextView) dialog.findViewById(R.id.alert_logout_content);
            content.setText("Está seguro de cerrar sesión?");

            Button dialogButtonOk = (Button) dialog.findViewById(R.id.alert_ok);
            dialogButtonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    key = true;
                    finish();
                    Intent login=new Intent(PrintActivity.this, LoginActivity.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    dialog.dismiss();
                    startActivity(login);


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
        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_logout);

        ImageView image = (ImageView) dialog.findViewById(R.id.alert_logout);
        image.setImageResource(R.drawable.ic_exit_menu_accent);

        TextView head = (TextView) dialog.findViewById(R.id.alert_logout_title);
        head.setText("CloudSales");
        TextView content = (TextView) dialog.findViewById(R.id.alert_logout_content);
        content.setText("Está seguro de cerrar sesión?");

        Button dialogButtonOk = (Button) dialog.findViewById(R.id.alert_ok);
        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = true;
                finish();
                Intent login=new Intent(PrintActivity.this, LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                dialog.dismiss();
                startActivity(login);


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
    }
}
