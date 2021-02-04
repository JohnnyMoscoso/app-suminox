package com.sales.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sales.R;
import com.sales.config.ConstValue;
import com.sales.db.SqliteClass;
import com.sales.models.CustomerClass;
import com.sales.models.ItemClass;
import com.sales.models.PaperClass;
import com.sales.models.SynOrderItemClass;
import com.sales.models.SynVisitClass;
import com.sales.views.activities.LoginActivity;
import com.sales.views.fragments.LoadOrderFragment;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Util {

    /* REQUEST_CODE for android 6.0 gps permission */
    public static final int REQUEST_PERMISSION_CODE_LOCATION = 255;
    public static final int REQUEST_PERMISSION_CODE_CAMERA = 265;

    public static ArrayList<SynOrderItemClass> orderItemPrint;

    public static void logout(final Activity activity, Context context){
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
                Intent login=new Intent(activity, LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                dialog.dismiss();
                activity.startActivity(login);
                activity.finish();
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

    public static void infoDialog(final Activity activity, Context context, String title, String field){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_info);
        ImageView image = (ImageView) dialog.findViewById(R.id.alert_info);
        image.setImageResource(R.drawable.ic_alert_info);
        TextView head = (TextView) dialog.findViewById(R.id.alert_info_title);
        head.setText(title);
        TextView content = (TextView) dialog.findViewById(R.id.alert_info_content);
        content.setText(field);
        Button dbOk = (Button) dialog.findViewById(R.id.alert_ok);
        dbOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    public static void loadChangeReturnItems(Context context, CustomerClass customer) {
        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
        List<ItemClass> itemList = SqliteClass.getInstance(context).databasehelp.pricelistsql.getItemsByPriceList(customer.getPriceListCode());

        int colorBackground_1 = R.color.colorCategory;
        int colorBackground_2 = R.color.colorSecondCategory;

        int lastColor = colorBackground_1;
        String category = itemList.get(0).getCategoryCode();

        for(int i = 0; i < itemList.size(); i++){
            int color = lastColor;
            HashMap<String, String> map = new HashMap<String, String>();
            ItemClass cc = itemList.get(i);
            map.put("description", cc.getDescription());

            if(!category.equals(cc.getCategoryCode())){
                category = cc.getCategoryCode();
                if(lastColor == colorBackground_2){ color = colorBackground_1; }
                else { color = colorBackground_2; }

                lastColor = color;
            }

            map.put("color", String.valueOf(lastColor));
            map.put("category", cc.getCategoryCode());
            map.put("price", SqliteClass.getInstance(context).databasehelp.pricelistsql.getPriceByItemCode(cc.getCode(), customer.getPriceListCode()));

            data.add(map);
        }
        ConstValue.clearChangeReturn();
        ConstValue.addChangeReturnItems(data);
    }

    public static void  loadOrderItems(Context context) {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        List<ItemClass> itemList = SqliteClass.getInstance(context).databasehelp.pricelistsql.getItemsByPriceList(ConstValue.getDefaultPriceListCode());
        int colorBackground_1 = R.color.colorCategory;
        int colorBackground_2 = R.color.colorSecondCategory;
        int lastColor = colorBackground_1;
        String category = itemList.get(0).getCategoryCode();

        for (int i = 0; i < itemList.size(); i++) {
            int color = lastColor;
            HashMap<String, String> map = new HashMap<String, String>();
            ItemClass cc = itemList.get(i);
            map.put("description", cc.getDescription());
            if (!category.equals(cc.getCategoryCode())) {
                category = cc.getCategoryCode();
                if (lastColor == colorBackground_2) {
                    color = colorBackground_1;
                } else {
                    color = colorBackground_2;
                }
                lastColor = color;
            }
            map.put("color", String.valueOf(lastColor));
            map.put("category", cc.getCategoryCode());
            map.put("price", SqliteClass.getInstance(context).databasehelp.pricelistsql.getPriceByItemCode(cc.getCode(), ConstValue.getDefaultPriceListCode()));
            //ACA SE MANDA CODIGO DE RUTA, PORQUE TIENE EN VEZ DEL CODIGO DEL CLIENTE, EL CODIGO DE RUTA
            String suggested = SqliteClass.getInstance(context).databasehelp.suggestedsql.getSuggestedQuantityByItem(cc.getCode(), ConstValue.getUserRouteCode(), "PV");
            if (suggested.equals("")) { map.put("suggestedQuantity", "0"); }
            else {
                BigDecimal qty = new BigDecimal(suggested);
                BigDecimal value = qty.setScale(0, BigDecimal.ROUND_HALF_UP);
                map.put("suggestedQuantity", String.valueOf(value));
            }
            map.put("control", "false");
            data.add(map);
        }
        ConstValue.clearOrderItems();
        ConstValue.addOrderItems(data);
    }

    public static void loadDirectSaleItems(Context context, CustomerClass customer) {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        List<ItemClass> itemList = SqliteClass.getInstance(context).databasehelp.pricelistsql.getItemsByPriceList(customer.getPriceListCode());
        int colorBackground_1 = R.color.colorCategory;
        int colorBackground_2 = R.color.colorSecondCategory;
        int lastColor = colorBackground_1;
        String category = itemList.get(0).getCategoryCode();
        for(int i = 0; i < itemList.size(); i++){
            int color = lastColor;
            HashMap<String, String> map = new HashMap<String, String>();
            ItemClass cc = itemList.get(i);
            map.put("description", cc.getDescription());

            if(!category.equals(cc.getCategoryCode())){
                category = cc.getCategoryCode();
                if(lastColor == colorBackground_2){ color = colorBackground_1; }
                else { color = colorBackground_2; }

                lastColor = color;
            }
            map.put("color", String.valueOf(lastColor));
            map.put("category", cc.getCategoryCode());
            map.put("price", SqliteClass.getInstance(context).databasehelp.pricelistsql.getPriceByItemCode(cc.getCode(), customer.getPriceListCode()));
            if(ConstValue.getSuggestedState().equals("1")) {
                String suggested = SqliteClass.getInstance(context).databasehelp.suggestedsql.getSuggestedQuantityByItem(cc.getCode(), customer.getCode(), "VD");
                if(suggested.equals("")){
                    map.put("suggestedQuantity", "0");
                }
                else{
                    BigDecimal qty = new BigDecimal(suggested);
                    BigDecimal value = qty.setScale(0, BigDecimal.ROUND_HALF_UP);
                    map.put("suggestedQuantity", String.valueOf(value));
                }
            }
            map.put("control", "false");
            data.add(map);
        }
        ConstValue.clearOrderItems();
        ConstValue.addOrderItems(data);
    }

    public static void loadOrderCustomerItems(Context context, CustomerClass customer) {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        List<ItemClass> itemList;
        itemList = SqliteClass.getInstance(context).databasehelp.pricelistsql.getItemsByPriceList(customer.getPriceListCode());
        int colorBackground_1 = R.color.colorCategory;
        int colorBackground_2 = R.color.colorSecondCategory;

        int lastColor = colorBackground_1;
        String category = itemList.get(0).getCategoryCode();

        for(int i = 0; i < itemList.size(); i++){
            int color = lastColor;
            HashMap<String, String> map = new HashMap<String, String>();
            ItemClass cc = itemList.get(i);
            map.put("description", cc.getDescription());

            if(!category.equals(cc.getCategoryCode())){
                category = cc.getCategoryCode();
                if(lastColor == colorBackground_2){ color = colorBackground_1; }
                else { color = colorBackground_2; }

                lastColor = color;
            }

            map.put("color", String.valueOf(lastColor));
            map.put("category", cc.getCategoryCode());
            map.put("price", SqliteClass.getInstance(context).databasehelp.pricelistsql.getPriceByItemCode(cc.getCode(), customer.getPriceListCode()));
            if(ConstValue.getSuggestedState().equals("1")) {
                String suggested = SqliteClass.getInstance(context).databasehelp.suggestedsql.getSuggestedQuantityByItem(cc.getCode(), customer.getCode(), "PC");
                if(suggested.equals("")){
                    map.put("suggestedQuantity", "0");
                }
                else{
                    BigDecimal qty = new BigDecimal(suggested);
                    BigDecimal value = qty.setScale(0, BigDecimal.ROUND_HALF_UP);
                    map.put("suggestedQuantity", String.valueOf(value));
                }
            }
            map.put("control", "false");
            data.add(map);
        }

        ConstValue.clearOrderItems();
        ConstValue.addOrderItems(data);
    }

    /** Fill all item Prices for Direct Sale and others */
    public static void fillItemPrices(Context context, CustomerClass customer){
        ArrayList<ItemClass>itemList = SqliteClass.getInstance(context).databasehelp.pricelistsql.getItemsByPriceList(customer.getPriceListCode());
        ArrayList<String> fullPrices = new ArrayList<String>();
        for(int i=0; i < itemList.size(); i++){
            String price = SqliteClass.getInstance(context).databasehelp.pricelistsql.getPriceByItemCode(itemList.get(i).getCode(), customer.getPriceListCode());
            fullPrices.add(price);
        }
        ConstValue.clearItemPrices();
        ConstValue.addItemPrices(fullPrices);
    }
    /** Fill all item Taxes for Direct Sale and others */
    public static void fillItemTaxes(Context context, CustomerClass customer){
        ArrayList<ItemClass> itemList = SqliteClass.getInstance(context).databasehelp.pricelistsql.getItemsByPriceList(customer.getPriceListCode());
        ArrayList<String> fullTaxes = new ArrayList<String>();
        for(int i=0; i < itemList.size(); i++){
            String tax = SqliteClass.getInstance(context).databasehelp.itemsql.getTaxByItemCode(itemList.get(i).getCode());

            fullTaxes.add(tax);
        }
        ConstValue.clearItemTaxes();
        ConstValue.addItemTaxes(fullTaxes);
    }
    /** Fill all item Weight for Direct Sale and others */
    public static void fillItemWeight(Context context){
        ArrayList<ItemClass> itemList = SqliteClass.getInstance(context).databasehelp.pricelistsql.getItemsByPriceList(ConstValue.getDefaultPriceListCode());
        ArrayList<String> fullWeight = new ArrayList<String>();
        for(int i=0; i < itemList.size(); i++){
            String weight = SqliteClass.getInstance(context).databasehelp.itemsql.getWeightByItemCode(itemList.get(i).getCode());
            fullWeight.add(weight);
        }
        ConstValue.clearItemWeight();
        ConstValue.addItemWeight(fullWeight);
    }

    public static BigDecimal formatBigDecimal(BigDecimal data){
        return data.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static String getActualDateHourParse(){
        SimpleDateFormat dateFormatHour = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        SimpleDateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String _hour = dateFormatHour.format(date);
        String _date = dateFormatDate.format(date);
        return _date + " " + _hour;
    }

    public static String buildVisitCode(String type){
        SimpleDateFormat dateFormatHour = new SimpleDateFormat("HHmmss", Locale.getDefault());
        SimpleDateFormat dateFormatDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Date date = new Date();
        String _hour = dateFormatHour.format(date);
        String _date = dateFormatDate.format(date);
        return type + "-" + _date + "-" + _hour + "-" + ConstValue.getUserId();
    }

    public static boolean before(String dateHour){
        if(dateHour.equals("")){ /** There is nothing, the user can go on */
            return true;
        }
        else{ /** so we check if there's an order from this day */
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String date = dateHour.substring(0, dateHour.indexOf(" "));
            try {
                Date _date = sdf.parse(date);
                String aux = sdf.format(new Date());
                Date _today = sdf.parse(aux);
                long isToday = differenceBetween(_date, _today, "days");
                if(isToday <= -1){ return true; }
                else { return false; }
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static long differenceBetween(Date d1, Date d2, String type){
        long diff = d1.getTime() - d2.getTime();
        if(type.equals("seconds")) return diff / 1000 % 60;
        if(type.equals("minutes")) return diff / (60 * 1000) % 60;
        if(type.equals("hours")) return diff / (60 * 60 * 1000) % 24;
        if(type.equals("days")) return diff / (24 * 60 * 60 * 1000);
        return 0;
    }

    public static String getDayOfTheWeek(){
        Date d = new Date();
        String day = "";

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);
        if(cal.get(Calendar.DAY_OF_WEEK) == 1) day = "DO";
        else if(cal.get(Calendar.DAY_OF_WEEK) == 2) day = "LU";
        else if(cal.get(Calendar.DAY_OF_WEEK) == 3) day = "MA";
        else if(cal.get(Calendar.DAY_OF_WEEK) == 4) day = "MI";
        else if(cal.get(Calendar.DAY_OF_WEEK) == 5) day = "JU";
        else if(cal.get(Calendar.DAY_OF_WEEK) == 6) day = "VI";
        else if(cal.get(Calendar.DAY_OF_WEEK) == 7) day = "SA";
        return day;
    }


    public static String formatInvoice(int _number){
        String ret="";
        if(_number<10){ ret = "0000000"+ Integer.toString(_number); return ret;}
        if(_number<100){ ret = "000000"+ Integer.toString(_number); return ret;}
        if(_number<1000){ ret = "00000"+ Integer.toString(_number); return ret;}
        if(_number<10000){ ret = "0000"+ Integer.toString(_number); return ret;}
        if(_number<100000){ ret = "000"+ Integer.toString(_number); return ret;}
        if(_number<1000000){ ret = "00"+ Integer.toString(_number); return ret;}
        if(_number<10000000){ ret = "0"+ Integer.toString(_number); return ret;}
        ret = Integer.toString(_number);
        return ret;
    }

    public static String ticket(BigDecimal exemptAmount, BigDecimal taxableAmount, BigDecimal salesTax, BigDecimal total, String type){

        String linea="";
        String _total = Util.formatBigDecimal(total).toString();
        String[] arr = _total.split("\\.");
        //int size = StaticClass.getPaperInt();
        int size = 400;

        String result =
                " \r\n\r\n" +
                        "               EMPRESA DE LACTEOS S.A."+
                        //"              "+ StaticClass.dataSapPrint("sociedad") + " \r\n" +
                        "\r\n"+
                        //"CASA MATRIZ: PARQUE INDUSTRIAL KM 15 AREQUIPA - PERU TEL. (+51) 42765025 "+//+ StaticClass.dataSapPrint("phoneTwo") +" " +
                        "CASA MATRIZ: "+ ConstValue.dataPrint("address") +" TEL. "+ ConstValue.dataPrint("phoneTwo") +" " +
                        //"SUCURSAL "+" PARQUE INDUSTRAL KM 15 AREQUIPA - AREQUIPA SUR, PERU, "+" \r\n";
                        "SUCURSAL "+ ConstValue.dataPrint("sucursalName") +" "+ ConstValue.dataPrint("sucursalAddress") +" \r\n";
        result = result +
                //"EMAIL: "+ "atencionalcliente@lacteos.com" +" \r\n" +
                "EMAIL: "+ ConstValue.dataPrint("email").toLowerCase() +" \r\n" +
                //"123456789a123456789b123456789c123456789d123456789e123456789f \r\n" +
                ConstValue.dataPrint("headOne") +
                ConstValue.dataPrint("headTwo") +
                //"RUC: "+ "20004395200035-3423405" +" \r\n" +
                "RTN: "+ ConstValue.dataPrint("RTN") +" \r\n" +
                //"TELEFONO: "+ "054-886898" +" \r\n" +
                "TELEFONO: "+ ConstValue.dataPrint("phone") +" \r\n" +
                //"CAI: "+ StaticClass.dataWebDocument("CAI") +" \r\n" +
                //"RANGO AUTORIZADO: "+ StaticClass.dataWebDocument("lower") +"-"+ StaticClass.dataWebDocument("upper") +"  \r\n" +
                //"FECHA LIMITE EMISION: "+ StaticClass.dataWebDocument("date") +" \r\n" +

                "EMAIL REPRESENTANTE: "+ ConstValue.getUserEmail().toLowerCase() +" \r\n" +

                //"EMAIL REPRESENTANTE: "+ StaticClass.dataWebDocument("email").toLowerCase() +" \r\n" +
                "\r\n";
       if(type.equals("FAC")){ result+="                 FACTURA  \r\n" ; }
       else if(type.equals("BOL")){ result+="            BOLETA       \r\n" ; }

        if(ConstValue.getPrintStatus().equals("RePrint")){
            result = result+"                     COPIA\r\n" ;
        }
        else if(ConstValue.getPrintStatus().equals("FirstPrint")){
            result = result+"                   ORIGINAL\r\n" ;
        }
        result+="              "+ ConstValue.getDocumentNumber()+"\r\n" +
                "FECHA EMISION: "+ ConstValue.getOrderDateString() +" \r\n" ;
        linea = "";

        //if(StaticClass.getRouteSapRTNString().equals("") && !StaticClass.getRouteSapNameString().equals("CLIENTE EVENTUAL")){
        //    linea = "CLIENTE: CONSUMIDOR FINAL";
        //}
        //else{
        //    linea = "CLIENTE: "+ StaticClass.getRouteSapNameString();
        //}
        if(linea.length()>size){
            linea = linea.substring(0,size);
        }
        result = result + linea + "\r\n";
        result = result +
                "RUC: "+ "20004395200035-3423405" +" \r\n" +
                //"RTN: "+ StaticClass.getRouteSapRTNString() +" \r\n" +
                "VENDEDOR: "+ ConstValue.getUserName() +"\r\n"+
                "--------------------------------------------\r\n";
        result = result +
                "--------------------------------------------\r\n" +
                "CNT. \tDESC.                   \t\tPRECIO  TOTAL\r\n";
        result = result +
                "--------------------------------------------\r\n" ;
        orderItemPrint = new ArrayList<SynOrderItemClass>();
        orderItemPrint = ConstValue.getSynOrderItemList();


        for(int i=0; i<orderItemPrint.size();i++){
            String sTax="";
            if(new BigDecimal(orderItemPrint.get(i).getIgv().toString()).compareTo(new BigDecimal("0"))==0)
            {
                sTax ="E"; //Si no tiene impuestos ==> Exento
            }
            else{
                sTax ="G"; //Si tiene impuestos ==> Gravado
            }
            result = result +
                    formatQuantityInvoiceItem(Integer.parseInt(orderItemPrint.get(i).getQuantity()))+
                    " \t "+orderItemPrint.get(i).getDescriptionItem()+" \r\n"+
                    "                        "+
                    "    "+Util.formatBigDecimal(new BigDecimal(orderItemPrint.get(i).getPrice())).toString()+
                    "   "+Util.formatBigDecimal(new BigDecimal(orderItemPrint.get(i).getTotalPay())).toString()+" "+sTax+
                    " \r\n" ;
        }
        result = result +
                " \r\n" +
                "--------------------------------------------\r\n" ;
        result = result + "VALOR   EXENTO ISV.   "+ConstValue.getCurrency()+formatoColumna(Util.formatBigDecimal(exemptAmount).toString(), 18, "L")+"\r\n";
        result = result + "VALOR   GRAVADO ISV.  "+ConstValue.getCurrency()+formatoColumna(Util.formatBigDecimal(taxableAmount).toString(), 18, "L")+"\r\n";
        result = result + "IMPTO. S/VTAS         "+ConstValue.getCurrency()+formatoColumna(Util.formatBigDecimal(salesTax).toString(), 18, "L")+"\r\n";
        result = result + "TOTAL A PAGAR         "+ConstValue.getCurrency()+formatoColumna(Util.formatBigDecimal(total).toString(), 18, "L")+"\r\n";
        result = result +"--------------------------------------------\r\n";

        result = result+"    									   \r\n" +
                "CANTIDAD EN LETRAS:  \r\n"+
                ""+ConvertNumberToText.convertString(Integer.parseInt(arr[0]))+ " " + ConstValue.getNameCurrency() + " CON "+ConvertNumberToText.convertString(Integer.parseInt(arr[1]))+" CENTAVOS \r\n\r\n"+
                ConstValue.dataPrint("footerOne") +
                ConstValue.dataPrint("footerTwo") +
                "LA FACTURA ES BENEFICIO DE TODOS, EXIJALA. \r\n"+
                "ORIGINAL - CLIENTE \r\n"+
                "COPIA - ARCHIVO \r\n";

        return result;
    }

    /*
    public static String ticket(BigDecimal exemptAmount, BigDecimal taxableAmount, BigDecimal salesTax,BigDecimal total){
		String linea="";
		String _total = Util.formatBigDecimal(total).toString();
		String[] arr = _total.split("\\.");
		int size = StaticClass.getPaperInt();

		String result =
				" \r\n\r\n" +
				//"               EMPRESA DE LACTEOS S.A."+
				"              "+ StaticClass.dataSapPrint("sociedad") + " \r\n" +
				"\r\n"+
				//"CASA MATRIZ: PARQUE INDUSTRIAL KM 15 AREQUIPA - PERU TEL. (+51) 42765025 "+//+ StaticClass.dataSapPrint("phoneTwo") +" " +
				"CASA MATRIZ: "+ StaticClass.dataSapPrint("address") +" TEL. "+ StaticClass.dataSapPrint("phoneTwo") +" " +
				//"SUCURSAL "+" PARQUE INDUSTRAL KM 15 AREQUIPA - AREQUIPA SUR, PERU, "+" \r\n";
				"SUCURSAL "+ StaticClass.dataSapPrint("sucursalName") +" "+ StaticClass.dataSapPrint("sucursalAddress") +" \r\n";
				result = result +
				//"EMAIL: "+ "atencionalcliente@lacteos.com" +" \r\n" +
		        "EMAIL: "+ StaticClass.dataSapPrint("email").toLowerCase() +" \r\n" +
		        //"123456789a123456789b123456789c123456789d123456789e123456789f \r\n" +
				StaticClass.dataSapPrint("headOne") +
				StaticClass.dataSapPrint("headTwo") +
				"RTN: "+ StaticClass.dataSapPrint("RTN") +" \r\n" +
				"TELEFONO: "+ StaticClass.dataSapPrint("phone") +" \r\n" +
				"CAI: "+ StaticClass.dataWebDocument("CAI") +" \r\n" +
				"RANGO AUTORIZADO: "+ StaticClass.dataWebDocument("lower") +"-"+ StaticClass.dataWebDocument("upper") +"  \r\n" +
				"FECHA LIMITE EMISION: "+ StaticClass.dataWebDocument("date") +" \r\n" +
				//"EMAIL REPRESENTANTE: "+ "johnny.moscoso@lacteos.com" +" \r\n" +
				"EMAIL REPRESENTANTE: "+ StaticClass.dataWebDocument("email").toLowerCase() +" \r\n" +
				"\r\n";
				String _type = "";
				if(StaticClass.getRouteSapSalesTypeString().equals("CR")){ _type="CREDITO"; }
				else{ _type="CONTADO";}
				result+="                FACTURA "+_type+" \r\n" ;
				if(StaticClass.getsBefAct().equals("RePrint")){
					result = result+"                     COPIA\r\n" ;
				}else{
					result = result+"                   ORIGINAL\r\n" ;
				}
				result+="              "+ StaticClass.getInvoiceNumber()+"\r\n" +
				"FECHA EMISION: "+ StaticClass.getOrderDateString() +" \r\n" ;
				linea = "";
				if(StaticClass.getRouteSapRTNString().equals("") && !StaticClass.getRouteSapNameString().equals("CLIENTE EVENTUAL")){
					linea = "CLIENTE: CONSUMIDOR FINAL";
				}else{
					linea = "CLIENTE: "+ StaticClass.getRouteSapNameString();
				}
				if(linea.length()>size){
					linea = linea.substring(0,size);
				}
				result = result + linea + "\r\n";
				result = result +
				"RTN: "+ StaticClass.getRouteSapRTNString() +" \r\n" +
				"VENDEDOR: "+ StaticClass.getUserNameString() +"\r\n"+
				"--------------------------------------------\r\n";
				result = result +
	    				"--------------------------------------------\r\n" +
	    				"CNT. \tDESC.                   \t\tPRECIO  TOTAL\r\n";
				result = result +
	    				"--------------------------------------------\r\n" ;
				orderItemPrint = new ArrayList<SynInvoiceItemClass>();
				orderItemPrint = StaticClass.getListSynInvoiceItem();
				for(int i=0; i<orderItemPrint.size();i++){
					String sTax="";
					if(new BigDecimal(orderItemPrint.get(i).getcInvIteTax().toString()).compareTo(new BigDecimal("0"))==0)
					{
						sTax ="E";
					}
					else{
						sTax ="G";
					}
					result = result +
							formatQuantityInvoiceItem(Integer.parseInt(orderItemPrint.get(i).getcInvIteQty()))+
							" \t "+orderItemPrint.get(i).getcInvIteNam()+" \r\n"+
							"                        "+
							"    "+Util.formatBigDecimal(new BigDecimal(orderItemPrint.get(i).getcInvItePri())).toString()+
							"   "+Util.formatBigDecimal(new BigDecimal(orderItemPrint.get(i).getcInvIteTot())).toString()+" "+sTax+
							" \r\n" ;
				}
				result = result +
	    				" \r\n" +
	    				"--------------------------------------------\r\n" ;

				result = result + "DCTOS Y REBAJAS         L."  +  formatoColumna("0.00", 18, "L")+"\r\n";
				//result = result + "VALOR   EXENTO ISV.     L."+ formatoColumna(Util.formatBigDecimal(exemptAmount).toString(), 18, "L")+"\r\n";
				result = result + "IMPORTE EXENTO ISV.     L."+ formatoColumna(Util.formatBigDecimalComma(Util.formatBigDecimal(exemptAmount).toString()), 18, "L")+"\r\n";
				//result = result + "VALOR   EXONERADO       L."  +  formatoColumna("0.00", 18, "L")+"\r\n";
				result = result + "IMPORTE EXONERADO       L."  +  formatoColumna("0.00", 18, "L")+"\r\n";

				result = result + "IMPORTE GRAVADO ISV 15% L."+ formatoColumna(Util.formatBigDecimalComma(Util.formatBigDecimal(taxableAmount).toString()), 18, "L")+"\r\n";
		        result = result + "IMPORTE GRAVADO ISV 18% L."+ formatoColumna("0.00", 18, "L")+"\r\n";

		        BigDecimal subtotal = new BigDecimal(0);
		        subtotal = total.subtract(salesTax);

		        result = result + "SUB TOTAL               L."+ formatoColumna(Util.formatBigDecimalComma(Util.formatBigDecimal(subtotal).toString()), 18, "L")+"\r\n";   //total - impuestos

		        result = result + "IMPORTE   IMPUESTOS 15% L."+ formatoColumna(Util.formatBigDecimalComma(Util.formatBigDecimal(salesTax).toString()), 18, "L")+"\r\n";
		        result = result + "IMPORTE   IMPUESTOS 18% L."+ formatoColumna("0.00", 18, "L")+"\r\n";

		        result = result + "TOTAL        IMPUESTO   L."+formatoColumna(Util.formatBigDecimalComma(Util.formatBigDecimal(salesTax).toString()), 18, "L")+"\r\n";
		        result = result + "TOTAL A PAGAR           L."+formatoColumna(Util.formatBigDecimalComma(Util.formatBigDecimal(total).toString()), 18, "L")+"\r\n";

		        result = result +
	    				" \r\n" +
	    				"--------------------------------------------\r\n" ;

		        result = result + "DATOS DEL ADQUIRIENTE EXONERADO:"  +  "\r\n";

		        result = result + "NRO DE ORDEN COMPRA EXENTA:________" + "_______"  + "___" + "_____" + "\r\n";
		        result = result + "NRO DE CONSTANCIA RGTRO EXONERADO:_____"  + "_______" + "____" + "______" +"\r\n";
		        result = result + "NRO DE RGTRO DE LA SAG:__________" + "__________"  + "____" +"\r\n";

		        result = result +"--------------------------------------------\r\n";


		        result = result+"    									   \r\n" +
        		"CANTIDAD EN LETRAS:  \r\n"+
		        ""+ConvertNumberToText.convertString(Integer.parseInt(arr[0]))+" LEMPIRAS CON "+ConvertNumberToText.convertString(Integer.parseInt(arr[1]))+" CENTAVOS \r\n\r\n"+
		        StaticClass.dataSapPrint("footerOne") +
				StaticClass.dataSapPrint("footerTwo") +
				"LA FACTURA ES BENEFICIO DE TODOS, EXIJALA. \r\n"+
				"ORIGINAL - CLIENTE \r\n"+
				"COPIA - OBLIGADO TRIBUTARIO EMISOR \r\n";

		return result;
	}
     */
    public static String formatQuantityInvoiceItem(int _number){
        String ret="";
        if(_number<10){ ret = "0"+ Integer.toString(_number); return ret;}
        ret = Integer.toString(_number);
        return ret;
    }

    public static String formatoColumna(String texto, int nc,String type) {
        int longi = texto.length();
        longi = nc - longi;
        if(type.equals("L")){
            if(longi > 0){
                for(int d=0; d<longi; d++ ){
                    texto = " "+texto;
                }
            }
        }
        if(type.equals("R")){
            if(longi > 0){
                for(int d=0; d<longi; d++ ){
                    texto = texto+" ";
                }
            }
        }
        return texto;
    }

    public static String getLeftStock(String total, String used){
        int n_total = Integer.parseInt(total);
        int n_used = Integer.parseInt(used);

        return String.valueOf(n_total - n_used);
    }

    public static double getProximityRate(double latitude_1, double latitude_2,
                                          double longitude_1, double longitude_2){
        Location locationStart = new Location("start");
        locationStart.setLatitude(latitude_1);
        locationStart.setLongitude(longitude_1);

        Location locationEnd = new Location("end");
        locationEnd.setLatitude(latitude_2);
        locationEnd.setLongitude(longitude_2);

        double distance = locationStart.distanceTo(locationEnd);
        return distance;
    }

    public static String getProximityRateZone(double distanceCalc, double proximityRate){
        String result = "NO";
        if(distanceCalc <= proximityRate) {result = "SI"; }
        return result;
    }

    /**
     * This is gonna be static
     * It only works with the name of the classes Im gonna sync
     * Seems it obly accepts 9 maximum so we will have to reduce it
     */
    public static void getClassesList(Activity activity, ListView itemListView) {

        ArrayList<String> classes = new ArrayList<String>();
        classes.add("Clientes");
        classes.add("Categorías");
        classes.add("Productos");
        classes.add("Lista de Precios");
        classes.add("Lista de Descuentos");
        classes.add("Lista de Promociones");
        classes.add("Visitas");
        classes.add("Control de Activos");
        classes.add("Stock");
        classes.add("Documentos");
        classes.add("Ticket");
        classes.add("Catálogo Sugerido");
        classes.add("Configuración de Usuario");

        ArrayAdapter<String> adapter_multiple_choice = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_multiple_choice, classes);
        itemListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        itemListView.setAdapter(adapter_multiple_choice);
    }



    public static void enableLocation(Activity activity, Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_CODE_LOCATION);
            return;
        }
    }

    public static void enableCamera(Activity activity, Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CODE_CAMERA);
            return;
        }
    }

}
