package com.sales.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.sales.models.*;

public class SqliteClass {
    public DatabaseHelper databasehelp;
    private static SqliteClass SqliteInstance = null;

    private SqliteClass(Context context) {
        databasehelp = new DatabaseHelper(context);
    }

    public static SqliteClass getInstance(Context context) {
        if (SqliteInstance == null) {
            SqliteInstance = new SqliteClass(context);
        }
        return SqliteInstance;
    }

    public class DatabaseHelper extends SQLiteOpenHelper {

        public Context context;
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "app_cloud_sales.db";


        /* @TABLE_APP_USER */
        public static final String TABLE_APP_USER = "app_user";
        public static final String KEY_USEID = "au_user_id";
        public static final String KEY_USEUSE = "au_user_use";
        public static final String KEY_USEPAS = "au_user_pas";
        public static final String KEY_USENAM = "au_user_nam";
        public static final String KEY_USEEMA = "au_user_ema";
        public static final String KEY_USEDAT = "au_user_dat";
        public static final String KEY_USEROUCOD = "au_user_rou_cod";
        public static final String KEY_USEROUNAM = "au_user_rou_nam";
        public static final String KEY_USESALCEN = "au_user_sal_cen";
        public static final String KEY_USEROUTYP = "au_user_rou_typ";
        public static final String KEY_USELIMRET = "au_user_lim_ret";
        public static final String KEY_USESTA = "au_user_sta";
        public static final String KEY_USELIMLTSLBS = "au_user_lts_lbs";
        public static final String KEY_USEPENLTSLBS = "au_user_pen_lts_lbs";
        public static final String KEY_USEPRORAN = "au_user_pro_ran";

        /* @TABLE_APP_CUSTOMER */
        public static final String TABLE_APP_CUSTOMER = "app_customer";
        public static final String KEY_CUSID = "ac_id";
        public static final String KEY_CUSCOD = "ac_cod";
        public static final String KEY_CUSNAM= "ac_nam";
        public static final String KEY_CUSRUC= "ac_ruc";
        public static final String KEY_CUSARE= "ac_are";
        public static final String KEY_CUSADD= "ac_add";
        public static final String KEY_CUSCODCOU= "ac_cod_cou";
        public static final String KEY_CUSCODDEP= "ac_cod_dep";
        public static final String KEY_CUSCODCIT= "ac_cod_cit";
        public static final String KEY_CUSCODCOL= "ac_cod_col";
        public static final String KEY_CUSREP= "ac_rep";
        public static final String KEY_CUSDAY= "ac_day";
        public static final String KEY_CUSPHO= "ac_pho";
        public static final String KEY_CUSMOBPHO= "ac_mob_pho";
        public static final String KEY_CUSCODROU= "ac_cod_rou";
        public static final String KEY_CUSWEI = "ac_wei";
        public static final String KEY_CUSLAT = "ac_lat";
        public static final String KEY_CUSLON = "ac_lon";
        public static final String KEY_CUSTYP = "ac_type";
        public static final String KEY_CUSCLA= "ac_cla";
        public static final String KEY_CUSPRI = "ac_prio";
        public static final String KEY_CUSPRO = "ac_pro";
        public static final String KEY_CUSCODBRA = "ac_cod_bra";
        public static final String KEY_CUSCODOFF= "ac_cod_off";
        public static final String KEY_CUSNAMCIT = "ac_nam_cit";
        public static final String KEY_CUSNAMCOL = "ac_nam_col";
        public static final String KEY_CUSNAMOFF = "ac_nam_off";
        public static final String KEY_CUSNAMROU = "ac_nam_rou";
        public static final String KEY_CUSPRILISCOD = "ac_pri_lis_cod";
        public static final String KEY_CUSDISLISCOD = "ac_dis_lis_cod";
        public static final String KEY_CUSPROLISCOD = "ac_pro_lis_cod";
        public static final String KEY_CUSACT = "ac_act";
        public static final String KEY_CUSSTA = "ac_sta";
        public static final String KEY_CUSVIS = "ac_vis";
        public static final String KEY_CUSLOA = "ac_loa";

        /* @TABLE_APP_SYNCHRONIZATION */
        public static final String TABLE_APP_SYNCHRONIZATION = "app_synchronization";
        public static final String KEY_SYNID = "as_id";
        public static final String KEY_SYNCLANAM = "as_cla_nam";
        public static final String KEY_SYNDATHOUSTA = "as_dat_hou_sta";
        public static final String KEY_SYNDATHOUEND = "as_dat_hou_end";
        public static final String KEY_SYNREGNUM = "as_reg_num";
        public static final String KEY_SYNFAINUM = "as_fai_num";
        public static final String KEY_SYNSTA = "as_sta";

        /* @TABLE_APP_CATEGORY */
        public static final String TABLE_APP_CATEGORY = "app_category";
        public static final String KEY_CATID = "aca_id";
        public static final String KEY_CATCOD = "aca_cod";
        public static final String KEY_CATDES = "aca_des";
        public static final String KEY_CATORD = "aca_ord";
        public static final String KEY_CATSTA = "aca_sta";

        /* @TABLE_APP_ITEM */
        public static final String TABLE_APP_ITEM = "app_item";
        public static final String KEY_ITEID = "ai_id";
        public static final String KEY_ITECATCOD ="ai_cat_cod";
        public static final String KEY_ITECOD ="ai_cod";
        public static final String KEY_ITEDES ="ai_des";
        public static final String KEY_ITETAX ="ai_tax";
        public static final String KEY_ITEMEAUNICOD ="ai_mea_uni_cod";
        public static final String KEY_ITELITLBS ="ai_lit_lbs";
        public static final String KEY_ITEORD ="ai_ord";
        public static final String KEY_ITECATNAM ="ai_cat_nam";
        public static final String KEY_ITEMEAUNINAM ="ai_mea_uni_nam";
        public static final String KEY_ITESTA ="ai_sta";
        public static final String KEY_ITESTO = "ai_sto";
        public static final String KEY_ITEQUA ="ai_qua";
        public static final String KEY_ITETOT ="ai_tot";
        public static final String KEY_ITEPOS ="ai_pos";
        //app_item.ai_qua, app_item.ai_tot, app_item.ai_pos

        /* @TABLE_APP_PRICE_LIST */
        public static final String TABLE_APP_PRICE_LIST = "app_price_list";
        public static final String KEY_PRILISID = "apl_id";
        public static final String KEY_PRILISCOD = "apl_cod";
        public static final String KEY_PRILISITECOD = "apl_ite_cod";
        public static final String KEY_PRILISPRI = "apl_pri";
        public static final String KEY_PRILISITEDES = "apl_ite_des";
        public static final String KEY_PRILISPRILISDES = "apl_pri_lis_des";
        public static final String KEY_PRILISSTA = "apl_sta";

        /* @TABLE_APP_DISCOUNT_LIST */
        public static final String TABLE_APP_DISCOUNT_LIST = "app_discount_list";
        public static final String KEY_DISLISID = "adl_id";
        public static final String KEY_DISLISCOD = "adl_cod";
        public static final String KEY_DISLISCODCUS = "adl_cod_cus";
        public static final String KEY_DISLISCODCAT = "adl_cod_cat";
        public static final String KEY_DISLISCODITE = "adl_cod_ite";
        public static final String KEY_DISLISDISPER = "adl_dis_per";
        public static final String KEY_DISLISDATINI = "adl_dat_ini";
        public static final String KEY_DISLISDATFIN = "adl_dat_fin";
        public static final String KEY_DISLISSTA = "adl_sta";

        /* @TABLE_APP_PROMO_LIST */
        public static final String TABLE_APP_PROMO_LIST = "app_promo_list";
        public static final String KEY_PROLISID = "aprl_id";
        public static final String KEY_PROLISCOD = "aprl_code";
        public static final String KEY_PROLISCODITEONE = "aprl_cod_ite_one";
        public static final String KEY_PROLISQUAONE = "aprl_qua_one";
        public static final String KEY_PROLISUNIMEAONE = "aprl_uni_mea_one";
        public static final String KEY_PROLISCODITETWO = "aprl_cod_ite_two";
        public static final String KEY_PROLISQUATWO = "aprl_qua_two";
        public static final String KEY_PROLISUNIMEATWO = "aprl_uni_mea_two";
        public static final String KEY_PROLISNAMITEONE = "aprl_nam_ite_one";
        public static final String KEY_PROLISNAMITETWO = "aprl_nam_ite_two";
        public static final String KEY_PROLISNAMUNIONE = "aprl_nam_uni_one";
        public static final String KEY_PROLISNAMUNITWO = "aprl_nam_uni_two";
        public static final String KEY_PROLISSTA = "aprl_sta";
        public static final String KEY_PROLISCATONE = "aprl_cat_one";
        public static final String KEY_PROLISCATTWO = "aprl_cat_two";

        /* @TABLE_APP_VISIT*/
        public static final String TABLE_APP_VISIT = "app_visit";
        public static final String KEY_VISID = "av_id";
        public static final String KEY_VISDES = "av_des";
        public static final String KEY_VISOFFCOD = "av_off_cod";
        public static final String KEY_VISOFFNAM = "av_off_nam";
        public static final String KEY_VISSTA = "av_sta";

        /* @TABLE_APP_SYN_VISIT */
        public static final String TABLE_APP_SYN_VISIT = "app_syn_visit";
        public static final String KEY_VISAPPID = "ava_id";
        public static final String KEY_VISAPPIDUSE = "ava_id_use";
        public static final String KEY_VISAPPCODCUS = "ava_cod_cus";
        public static final String KEY_VISAPPNAMCUS = "ava_nam_cus";
        public static final String KEY_VISAPPCODVIS = "ava_cod_vis";
        public static final String KEY_VISAPPNAMVIS = "ava_nam_vis";
        public static final String KEY_VISAPPLATCUS = "ava_lat_cus";
        public static final String KEY_VISAPPLONCUS = "ava_lon_cus";
        public static final String KEY_VISAPPLAT = "ava_lat";
        public static final String KEY_VISAPPLON = "ava_lon";
        public static final String KEY_VISAPPZON = "ava_zon";
        public static final String KEY_VISAPPRAT = "ava_rat";
        public static final String KEY_VISAPPDATHOU = "ava_dat_hou";
        public static final String KEY_VISAPPNUMCUSPEN = "ava_num_cus_pen";
        public static final String KEY_VISAPPLOA = "ava_loa";

        /* @TABLE_APP_ASSET_CONTROL*/
        public static final String TABLE_APP_ASSET_CONTROL = "app_asset_control";
        public static final String KEY_ASSCONID = "asc_id";
        public static final String KEY_ASSCONCOD = "asc_cod";
        public static final String KEY_ASSCONASS = "asc_ass";
        public static final String KEY_ASSCONDATASS = "asc_dat_ass";
        public static final String KEY_ASSCONCODBRA = "asc_cod_bra";
        public static final String KEY_ASSCONCODOFF = "asc_cod_off";
        public static final String KEY_ASSCONCODROU = "asc_cod_rou";
        public static final String KEY_ASSCONCODCUS = "asc_cod_cus";
        public static final String KEY_ASSCONIDUSEAPP = "asc_id_use_app";
        public static final String KEY_ASSCONNAMOFF = "asc_nam_off";
        public static final String KEY_ASSCONNAMBRA = "asc_nam_bra";
        public static final String KEY_ASSCONNAMROU = "asc_nam_rou";
        public static final String KEY_ASSCONNAMCUS = "asc_nam_cus";
        public static final String KEY_ASSCONSTA = "asc_sta";
        public static final String KEY_ASSCONDATTAS = "asc_dat_tas";
        public static final String KEY_ASSCONHOUTAS = "asc_hou_tas";
        public static final String KEY_ASSCONDATENTAS ="asc_dat_end_tas";
        public static final String KEY_ASSCONHOUENDTAS ="asc_hou_end_tas";
        public static final String KEY_ASSCONORI ="asc_ori";
        public static final String KEY_ASSCONEQU ="asc_equ";
        public static final String KEY_ASSCONTYP ="asc_typ";
        public static final String KEY_ASSCONTAS ="asc_tas";
        public static final String KEY_ASSCONANS ="asc_ans";
        public static final String KEY_ASSCONCODCUSTAS ="asc_cod_cus_tas";
        public static final String KEY_ASSCONSTATAS ="asc_sta_tas";
        public static final String KEY_ASSCONIDTAS ="asc_id_tas";
        public static final String KEY_ASSCONLOA ="asc_loa";

        /* @TABLE_APP_STOCK */
        public static final String TABLE_APP_STOCK = "app_stock";
        public static final String KEY_STOID = "ast_id";
        public static final String KEY_STOCODROU  = "ast_cod_rou";
        public static final String KEY_STOCODITE  = "ast_cod_ite";
        public static final String KEY_STOQUA  = "ast_qua";

        /* @TABLE_APP_TYPE_DOCUMENT */
        public static final String TABLE_APP_TYPE_DOCUMENT= "app_type_document";
        public static final String KEY_TYPDOCID = "atd_id";
        public static final String KEY_TYPDOCCOD =  "atd_cod";
        public static final String KEY_TYPDOCDES = "atd_des";
        public static final String KEY_TYPDOCSTA = "atd_sta";

        /* @TABLE_APP_DOCUMENT*/
        public static final String TABLE_APP_DOCUMENT = "app_document";
        public static final String KEY_DOCID = "adoc_id";
        public static final String KEY_DOCDOCTYP = "adoc_doc_typ";
        public static final String KEY_DOCROU = "adoc_rou";
        public static final String KEY_DOCDOCNAM = "adoc_doc_nam";
        public static final String KEY_DOCDOCSER = "adoc_doc_ser";
        public static final String KEY_DOCLOWLIM = "adoc_low_lim";
        public static final String KEY_DOCTOPLIM = "adoc_top_lim";
        public static final String KEY_DOCACTLIM = "adoc_act_lim";
        public static final String KEY_DOCDAT = "adoc_dat";
        public static final String KEY_DOCEMALEG = "adoc_ema_leg"; //Unknown
        public static final String KEY_DOCDECNUM = "adoc_dec_num"; //Unknown
        public static final String KEY_DOCSTA = "adoc_sta";
        public static final String KEY_DOCLASNUM = "adoc_las_num";

        /* @TABLE_SYN_ORDER */
        public static final String TABLE_APP_SYN_ORDER = "app_syn_order";
        public static final String KEY_SYNORDID = "aso_id";
        public static final String KEY_SYNORDTYP = "aso_typ";
        public static final String KEY_SYNORDCODCUS = "aso_cod_cus";
        public static final String KEY_SYNORDNAMCUS = "aso_nam_cus";
        public static final String KEY_SYNORDNUMDOC = "aso_num_doc";
        public static final String KEY_SYNORDNUM = "aso_num";
        public static final String KEY_SYNORDCODROU = "aso_cod_rou";
        public static final String KEY_SYNORDIDUSEAPP = "aso_id_use_app";
        public static final String KEY_SYNORDDATHOU = "aso_dat_hou";
        public static final String KEY_SYNORDRUC = "aso_ruc";
        public static final String KEY_SYNORDSTANUBFAC = "aso_sta_nub_fac";
        public static final String KEY_SYNORDOPEEXO = "aso_ope_exo";
        public static final String KEY_SYNORDOPETAX = "aso_ope_tax";
        public static final String KEY_SYNORDIGV = "aso_igv";
        public static final String KEY_SYNORDTOTPAY = "aso_tot_pay";
        public static final String KEY_SYNORDSTACHA = "aso_sta_cha";
        public static final String KEY_SYNORDLOA = "aso_loa";

        /* @TABLE_SYN_ORDER_ITEM */
        public static final String TABLE_APP_SYN_ORDER_ITEM = "app_syn_order_item";
        public static final String KEY_SYNORDITEID = "asoi_id";
        public static final String KEY_SYNORDITENUMDOC = "asoi_num_doc";
        public static final String KEY_SYNORDITEDATHOU = "asoi_dat_hou";
        public static final String KEY_SYNORDITECAT = "asoi_cat";
        public static final String KEY_SYNORDITECODITE = "asoi_cod_ite";
        public static final String KEY_SYNORDITEDESITE = "asoi_des_ite";
        public static final String KEY_SYNORDITEQUA = "asoi_qua";
        public static final String KEY_SYNORDITEPRI = "asoi_pri";
        public static final String KEY_SYNORDITEUNIMEA = "asoi_uni_mea";
        public static final String KEY_SYNORDITEIGV = "asoi_igv";
        public static final String KEY_SYNORDITETOTPAY = "asoi_tot_pay";
        public static final String KEY_SYNORDITESTA = "asoi_sta";
        public static final String KEY_SYNORDITESTACHA = "asoi_sta_cha";
        public static final String KEY_SYNORDITELOA = "asoi_loa";

        /* @TABLE_SYN_BREAK_STOCK */
        public static final String TABLE_APP_SYN_BREAK_STOCK = "app_syn_break_stock";
        public static final String KEY_SYNBRESTOID = "asbs_id";
        public static final String KEY_SYNBRESTOCOD = "asbs_cod";
        public static final String KEY_SYNBRESTOCODROU = "asbs_cod_rou";
        public static final String KEY_SYNBRESTOIDUSEAPP = "asbs_id_use_app";
        public static final String KEY_SYNBRESTOCODITE = "asbs_cod_ite";
        public static final String KEY_SYNBRESTONAMITE = "asbs_nam_ite";
        public static final String KEY_SYNBRESTODATHOU = "asbs_dat_hou";
        public static final String KEY_SYNBRESTOPRI = "asbs_pri";
        public static final String KEY_SYNBRESTONUMCUSPEN = "asbs_num_cus_pen";
        public static final String KEY_SYNBRESTOLOA = "asbs_loa";

        /* @TABLE_APP_PAPER */
        public static final String TABLE_APP_PAPER = "app_paper";
        public static final String KEY_PAPID = "ap_id";
        public static final String KEY_PAPCOD = "ap_cod";
        public static final String KEY_PAPCODSOC = "ap_cod_soc";
        public static final String KEY_PAPNAMSOC = "ap_nam_soc";
        public static final String KEY_PAPADDSOC = "ap_add_soc";
        public static final String KEY_PAPFIS = "ap_fis";          //RTN
        public static final String KEY_PAPCODSUC = "ap_cod_suc";
        public static final String KEY_PAPNAMSUC = "ap_nam_suc";
        public static final String KEY_PAPADDSUC = "ap_add_suc";
        public static final String KEY_PAPEMASOC = "ap_ema_soc";
        public static final String KEY_PAPPHOONESOC = "ap_pho_one_soc";
        public static final String KEY_PAPPHOTWOSOC = "ap_pho_two_soc";
        public static final String KEY_PAPHEAONESOC = "ap_hea_one_soc";
        public static final String KEY_PAPHEATWOSOC = "ap_hea_two_soc";
        public static final String KEY_PAPFOOONESOC = "ap_foo_one_soc";
        public static final String KEY_PAPFOOTWOSOC = "ap_foo_two_soc";
        public static final String KEY_PAPCODOFF = "ap_cod_off";
        public static final String KEY_PAPNAMOFF = "ap_nam_off";
        public static final String KEY_PAPSTA = "ap_sta";

        /* @TABLE_APP_SUGGESTED */
        public static final String TABLE_APP_SUGGESTED = "app_suggested";
        public static final String KEY_SUGID = "asu_id";
        public static final String KEY_SUGTYP = "asu_typ"; //VD (Venta Directa), PC (Pedido Cliente), PV (Pedido Vendedor)
        public static final String KEY_SUGCODCUS = "asu_cod_cus";
        public static final String KEY_SUGCODITE = "asu_cod_ite";
        public static final String KEY_SUGDESITE = "asu_des_ite";
        public static final String KEY_SUGCATITE = "asu_cat_ite";
        public static final String KEY_SUGQUAITE = "asu_qua_ite";
        public static final String KEY_SUGUNIMEA = "asu_uni_mea_ite"; /* Just in case */

        /* @TABLE_APP_CONFIGURATION */
        public static final String TABLE_APP_CONFIGURATION = "app_configuration";
        public static final String KEY_CONID = "aco_id";
        public static final String KEY_CONCUR = "aco_cur";             //"Currency": "S/. - Soles",
        public static final String KEY_CONUNIMEA = "aco_uni_mea";          // "UmWeight": "Lbs - Libras",
        public static final String KEY_CONPAPSIZ = "aco_pap_siz";

        /* @SQL */
        public AppUserSql usersql;
        public AppCustomerSql customersql;
        public AppSynchronizationSql synchronizationsql;
        public AppCategorySql categorysql;
        public AppItemSql itemsql;
        public AppPriceListSql pricelistsql;
        public AppDiscountListSql discountListsql;
        public AppPromoListSql promolistsql;
        public AppVisitSql visitSql;
        public AppSynVisitSql synvisitsql;
        public AppAssetControlSql assetcontrolsql;
        public AppStockSql stocksql;
        public AppTypeDocumentSql typedocumentsql;
        public AppDocumentSql documentsql;
        public AppSynOrderSql synordersql;
        public AppSynOrderItemSql synorderitemsql;
        public AppSynBreakStockSql synbreakstocksql;
        public AppPaperSql papersql;
        public AppSuggestedSql suggestedsql;
        public AppConfigurationSql configurationsql;


        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            usersql = new AppUserSql();
            customersql = new AppCustomerSql();
            synchronizationsql = new AppSynchronizationSql();
            categorysql = new AppCategorySql();
            itemsql = new AppItemSql();
            pricelistsql = new AppPriceListSql();
            discountListsql= new AppDiscountListSql();
            promolistsql = new AppPromoListSql();
            visitSql = new AppVisitSql();
            synvisitsql = new AppSynVisitSql();
            assetcontrolsql = new AppAssetControlSql();
            stocksql = new AppStockSql();
            typedocumentsql = new AppTypeDocumentSql();
            documentsql = new AppDocumentSql();
            synordersql = new AppSynOrderSql();
            synorderitemsql = new AppSynOrderItemSql();
            synbreakstocksql = new AppSynBreakStockSql();
            papersql = new AppPaperSql();
            suggestedsql = new AppSuggestedSql();
            configurationsql = new AppConfigurationSql();
        }
        public void onCreate(SQLiteDatabase db) {
            /* @TABLE_USER */
            String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_APP_USER + "("
                    + KEY_USEID + " INTEGER PRIMARY KEY," + KEY_USEUSE + " TEXT,"
                    + KEY_USEPAS + " TEXT,"  + KEY_USENAM + " TEXT," + KEY_USEEMA + " TEXT," + KEY_USEDAT + " TEXT,"
                    + KEY_USEROUCOD+ " TEXT," + KEY_USEROUNAM  + " TEXT," + KEY_USESALCEN + " TEXT,"
                    + KEY_USEROUTYP + " TEXT," + KEY_USELIMRET + " TEXT,"  + KEY_USESTA + " TEXT,"
                    + KEY_USELIMLTSLBS + " TEXT," + KEY_USEPENLTSLBS+ " TEXT,"  + KEY_USEPRORAN +" TEXT )";


            /* @TABLE_CUSTOMER */
            String CREATE_TABLE_CUSTOMER = "CREATE TABLE " + TABLE_APP_CUSTOMER + "("
                    + KEY_CUSID + " INTEGER PRIMARY KEY," + KEY_CUSCOD + " TEXT,"
                    + KEY_CUSNAM + " TEXT,"  + KEY_CUSRUC + " TEXT," + KEY_CUSARE + " TEXT,"
                    + KEY_CUSADD + " TEXT," + KEY_CUSCODCOU  + " TEXT," + KEY_CUSCODDEP + " TEXT,"
                    + KEY_CUSCODCIT + " TEXT," + KEY_CUSCODCOL + " TEXT," + KEY_CUSREP + " TEXT,"
                    + KEY_CUSDAY + " TEXT," + KEY_CUSPHO + " TEXT," + KEY_CUSMOBPHO + " TEXT,"
                    + KEY_CUSCODROU+ " TEXT," + KEY_CUSWEI+ " TEXT," + KEY_CUSLAT+ " TEXT," + KEY_CUSLON+ " TEXT,"
                    + KEY_CUSTYP + " TEXT," + KEY_CUSCLA+ " TEXT," + KEY_CUSPRI + " TEXT," + KEY_CUSPRO + " TEXT,"
                    + KEY_CUSCODBRA + " TEXT," + KEY_CUSCODOFF + " TEXT," + KEY_CUSNAMCIT + " TEXT," + KEY_CUSNAMCOL + " TEXT,"
                    + KEY_CUSNAMOFF + " TEXT," + KEY_CUSNAMROU + " TEXT," + KEY_CUSPRILISCOD + " TEXT," + KEY_CUSDISLISCOD + " TEXT,"
                    + KEY_CUSPROLISCOD + " TEXT," + KEY_CUSACT + " TEXT," + KEY_CUSSTA+  " TEXT,"
                    + KEY_CUSVIS + " TEXT," + KEY_CUSLOA +" TEXT )";

            /* @TABLE_SYNCHRONIZATION */
            String CREATE_TABLE_SYNCHRONIZATION = "CREATE TABLE " + TABLE_APP_SYNCHRONIZATION + "("
                    + KEY_SYNID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SYNCLANAM + " TEXT,"
                    + KEY_SYNDATHOUSTA + " TEXT,"  + KEY_SYNDATHOUEND + " TEXT," + KEY_SYNREGNUM + " TEXT,"
                    + KEY_SYNFAINUM+ " TEXT," + KEY_SYNSTA + " TEXT )";

            /* @TABLE_CATEGORY */
            String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_APP_CATEGORY + "("
                    + KEY_CATID + " INTEGER PRIMARY KEY," + KEY_CATCOD + " TEXT,"
                    + KEY_CATDES + " TEXT,"  + KEY_CATORD + " TEXT," + KEY_CATSTA + " TEXT )";

            /* @TABLE_ITEM */
            String CREATE_TABLE_ITEM = "CREATE TABLE " + TABLE_APP_ITEM + "("
                    + KEY_ITEID + " INTEGER PRIMARY KEY," + KEY_ITECATCOD  + " TEXT," + KEY_ITECOD + " TEXT,"
                    + KEY_ITEDES + " TEXT," + KEY_ITETAX + " TEXT," + KEY_ITEMEAUNICOD + " TEXT,"
                    + KEY_ITELITLBS + " TEXT," + KEY_ITEORD + " TEXT," + KEY_ITECATNAM + " TEXT,"
                    + KEY_ITEMEAUNINAM+ " TEXT," + KEY_ITESTA + " TEXT," + KEY_ITESTO + " TEXT," + KEY_ITEQUA + " TEXT,"
                    + KEY_ITETOT + " TEXT," + KEY_ITEPOS + " TEXT )";

            /* @TABLE_PRICE_LIST */
            String CREATE_TABLE_PRICE_LIST = "CREATE TABLE " + TABLE_APP_PRICE_LIST + "("
                    + KEY_PRILISID + " INTEGER PRIMARY KEY," + KEY_PRILISCOD + " TEXT,"
                    + KEY_PRILISITECOD + " TEXT,"  + KEY_PRILISPRI + " TEXT," + KEY_PRILISITEDES + " TEXT,"
                    + KEY_PRILISPRILISDES+ " TEXT," + KEY_PRILISSTA + " TEXT )";

            /* @TABLE_DISCOUNT_LIST */
            String CREATE_TABLE_DISCOUNT_LIST = "CREATE TABLE " + TABLE_APP_DISCOUNT_LIST + "("
                    + KEY_DISLISID + " INTEGER PRIMARY KEY," + KEY_DISLISCOD + " TEXT," + KEY_DISLISCODCUS + " TEXT,"
                    + KEY_DISLISCODCAT + " TEXT,"  + KEY_DISLISCODITE + " TEXT," + KEY_DISLISDISPER + " TEXT,"
                    + KEY_DISLISDATINI + " TEXT," + KEY_DISLISDATFIN  + " TEXT," + KEY_DISLISSTA + " TEXT )";

            /* @TABLE_PROMO_LIST */
            String CREATE_TABLE_PROMO_LIST = "CREATE TABLE " + TABLE_APP_PROMO_LIST + "("
                    + KEY_PROLISID + " INTEGER PRIMARY KEY," + KEY_PROLISCOD + " TEXT,"
                    + KEY_PROLISCODITEONE + " TEXT,"  + KEY_PROLISCATONE + " TEXT," + KEY_PROLISQUAONE + " TEXT,"
                    + KEY_PROLISUNIMEAONE + " TEXT," + KEY_PROLISCODITETWO + " TEXT," + KEY_PROLISCATTWO + " TEXT,"
                    + KEY_PROLISQUATWO  + " TEXT," + KEY_PROLISUNIMEATWO + " TEXT,"
                    + KEY_PROLISNAMITEONE + " TEXT," + KEY_PROLISNAMITETWO + " TEXT," + KEY_PROLISNAMUNIONE + " TEXT,"
                    + KEY_PROLISNAMUNITWO + " TEXT," + KEY_PROLISSTA + " TEXT )";


            /* @TABLE_VISIT_APP */
            String CREATE_TABLE_SYN_VISIT = "CREATE TABLE " + TABLE_APP_SYN_VISIT + "("
                    + KEY_VISAPPID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_VISAPPIDUSE + " TEXT,"
                    + KEY_VISAPPCODCUS + " TEXT,"  + KEY_VISAPPNAMCUS + " TEXT," + KEY_VISAPPCODVIS + " TEXT,"
                    + KEY_VISAPPNAMVIS + " TEXT," + KEY_VISAPPLATCUS  + " TEXT," + KEY_VISAPPLONCUS + " TEXT,"
                    + KEY_VISAPPLAT + " TEXT," + KEY_VISAPPLON + " TEXT," + KEY_VISAPPZON + " TEXT,"
                    + KEY_VISAPPRAT + " TEXT," + KEY_VISAPPDATHOU + " TEXT," + KEY_VISAPPNUMCUSPEN + " TEXT,"
                    + KEY_VISAPPLOA + " TEXT )";

            /* @TABLE_VISIT */
            String CREATE_TABLE_VISIT = "CREATE TABLE " + TABLE_APP_VISIT + "("
                    + KEY_VISID + " INTEGER PRIMARY KEY," + KEY_VISDES + " TEXT,"
                    + KEY_VISOFFCOD + " TEXT,"  + KEY_VISOFFNAM + " TEXT," + KEY_VISSTA + " TEXT )";

            /* @TABLE_ASSET_CONTROL */
            String CREATE_TABLE_ASSET_CONTROL = "CREATE TABLE " + TABLE_APP_ASSET_CONTROL + "("
                    + KEY_ASSCONID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ASSCONCOD + " TEXT,"
                    + KEY_ASSCONASS + " TEXT,"  + KEY_ASSCONDATASS + " TEXT," + KEY_ASSCONCODBRA + " TEXT,"
                    + KEY_ASSCONCODOFF + " TEXT," + KEY_ASSCONCODROU  + " TEXT," + KEY_ASSCONCODCUS + " TEXT,"
                    + KEY_ASSCONIDUSEAPP + " TEXT," + KEY_ASSCONNAMOFF + " TEXT," + KEY_ASSCONNAMBRA  + " TEXT,"
                    + KEY_ASSCONNAMROU + " TEXT," + KEY_ASSCONNAMCUS + " TEXT," + KEY_ASSCONSTA + " TEXT,"
                    + KEY_ASSCONDATTAS + " TEXT," +  KEY_ASSCONHOUTAS + " TEXT," + KEY_ASSCONDATENTAS + " TEXT,"
                    + KEY_ASSCONHOUENDTAS + " TEXT," + KEY_ASSCONORI + " TEXT," + KEY_ASSCONEQU + " TEXT,"
                    + KEY_ASSCONTYP + " TEXT," + KEY_ASSCONTAS + " TEXT," + KEY_ASSCONANS + " TEXT,"
                    + KEY_ASSCONCODCUSTAS + " TEXT," + KEY_ASSCONIDTAS + " TEXT," + KEY_ASSCONSTATAS + " TEXT,"
                    + KEY_ASSCONLOA+ " TEXT )";

            /* @TABLE_STOCK */
            String CREATE_TABLE_STOCK = "CREATE TABLE " + TABLE_APP_STOCK + "("
                    + KEY_STOID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_STOCODROU + " TEXT," + KEY_STOCODITE
                    + " TEXT,"  + KEY_STOQUA + " TEXT )";

            /* @TABLE_TYPE_DOCUMENT */
            String CREATE_TABLE_TYPE_DOCUMENT = "CREATE TABLE " + TABLE_APP_TYPE_DOCUMENT + "("
                    + KEY_TYPDOCID + " INTEGER PRIMARY KEY," + KEY_TYPDOCCOD + " TEXT," + KEY_TYPDOCDES
                    + " TEXT,"  + KEY_TYPDOCSTA + " TEXT )";

            /* @TABLE__DOCUMENT */
            String CREATE_TABLE_DOCUMENT = "CREATE TABLE " + TABLE_APP_DOCUMENT + "("
                    + KEY_DOCID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DOCDOCTYP + " TEXT,"
                    + KEY_DOCROU + " TEXT,"  + KEY_DOCDOCNAM + " TEXT," + KEY_DOCDOCSER + " TEXT,"
                    + KEY_DOCLOWLIM + " TEXT," + KEY_DOCTOPLIM  + " TEXT," + KEY_DOCACTLIM + " TEXT,"
                    + KEY_DOCDAT + " TEXT," + KEY_DOCEMALEG + " TEXT," + KEY_DOCDECNUM  + " TEXT,"
                    + KEY_DOCSTA + " TEXT," + KEY_DOCLASNUM + " TEXT )";

            /* @TABLE_SYN_ORDER */
            String CREATE_TABLE_SYN_ORDER = "CREATE TABLE " + TABLE_APP_SYN_ORDER + "("
                    + KEY_SYNORDID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SYNORDTYP + " TEXT,"
                    + KEY_SYNORDCODCUS + " TEXT,"  + KEY_SYNORDNAMCUS + " TEXT," + KEY_SYNORDNUMDOC + " TEXT,"
                    + KEY_SYNORDNUM + " TEXT," + KEY_SYNORDCODROU  + " TEXT," + KEY_SYNORDIDUSEAPP + " TEXT,"
                    + KEY_SYNORDDATHOU + " TEXT," + KEY_SYNORDRUC + " TEXT," + KEY_SYNORDSTANUBFAC  + " TEXT,"
                    + KEY_SYNORDOPEEXO + " TEXT," + KEY_SYNORDOPETAX + " TEXT," + KEY_SYNORDIGV + " TEXT,"
                    + KEY_SYNORDTOTPAY + " TEXT," +  KEY_SYNORDSTACHA + " TEXT," + KEY_SYNORDLOA + " TEXT )";

            /* @TABLE_SYN_ORDER_ITEM */
            String CREATE_TABLE_SYN_ORDER_ITEM = "CREATE TABLE " + TABLE_APP_SYN_ORDER_ITEM + "("
                    + KEY_SYNORDITEID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SYNORDITENUMDOC + " TEXT,"
                    + KEY_SYNORDITEDATHOU + " TEXT,"  + KEY_SYNORDITECAT + " TEXT," + KEY_SYNORDITECODITE + " TEXT,"
                    + KEY_SYNORDITEDESITE + " TEXT," + KEY_SYNORDITEQUA  + " TEXT," + KEY_SYNORDITEPRI + " TEXT,"
                    + KEY_SYNORDITEUNIMEA + " TEXT," + KEY_SYNORDITEIGV + " TEXT," + KEY_SYNORDITETOTPAY  + " TEXT,"
                    + KEY_SYNORDITESTA + " TEXT," + KEY_SYNORDITESTACHA + " TEXT," + KEY_SYNORDITELOA + " TEXT )";

            /* @TABLE_SYN_BREAK_STOCK */
            String CREATE_TABLE_SYN_BREAK_STOCK = "CREATE TABLE " + TABLE_APP_SYN_BREAK_STOCK + "("
                    + KEY_SYNBRESTOID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SYNBRESTOCOD + " TEXT,"
                    + KEY_SYNBRESTOCODROU + " TEXT,"  + KEY_SYNBRESTOIDUSEAPP + " TEXT," + KEY_SYNBRESTOCODITE + " TEXT,"
                    + KEY_SYNBRESTONAMITE + " TEXT," + KEY_SYNBRESTODATHOU  + " TEXT," + KEY_SYNBRESTOPRI + " TEXT,"
                    + KEY_SYNBRESTONUMCUSPEN + " TEXT," + KEY_SYNBRESTOLOA + " TEXT )";

            /* @TABLE_PROMO_LIST */
            String CREATE_TABLE_PAPER = "CREATE TABLE " + TABLE_APP_PAPER + "("
                    + KEY_PAPID + " INTEGER PRIMARY KEY," + KEY_PAPCOD + " TEXT,"
                    + KEY_PAPCODSOC + " TEXT,"  + KEY_PAPNAMSOC + " TEXT," + KEY_PAPADDSOC + " TEXT,"
                    + KEY_PAPFIS + " TEXT," + KEY_PAPCODSUC + " TEXT," + KEY_PAPNAMSUC + " TEXT,"
                    + KEY_PAPADDSUC  + " TEXT," + KEY_PAPEMASOC + " TEXT,"
                    + KEY_PAPPHOONESOC + " TEXT," + KEY_PAPPHOTWOSOC + " TEXT," + KEY_PAPHEAONESOC + " TEXT,"
                    + KEY_PAPHEATWOSOC + " TEXT," + KEY_PAPFOOONESOC + " TEXT," + KEY_PAPFOOTWOSOC + " TEXT,"
                    + KEY_PAPCODOFF + " TEXT," + KEY_PAPNAMOFF + " TEXT," + KEY_PAPSTA+ " TEXT )";

            String CREATE_TABLE_SUGGESTED = "CREATE TABLE " + TABLE_APP_SUGGESTED + "("
                    + KEY_SUGID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SUGTYP + " TEXT,"
                    + KEY_SUGCODCUS + " TEXT,"  + KEY_SUGCODITE + " TEXT," + KEY_SUGDESITE + " TEXT,"
                    + KEY_SUGCATITE + " TEXT," + KEY_SUGQUAITE  + " TEXT," + KEY_SUGUNIMEA + " TEXT )";

            /* @TABLE_SYN_BREAK_STOCK */
            String CREATE_TABLE_CONFIGURATION = "CREATE TABLE " + TABLE_APP_CONFIGURATION + "("
                    + KEY_CONID + " INTEGER PRIMARY KEY,"
                    + KEY_CONCUR + " TEXT," + KEY_CONUNIMEA + " TEXT," + KEY_CONPAPSIZ + " TEXT )";

            /* @EXECSQL_CREATE */
            db.execSQL(CREATE_TABLE_USER);
            db.execSQL(CREATE_TABLE_CUSTOMER);
            db.execSQL(CREATE_TABLE_SYNCHRONIZATION);
            db.execSQL(CREATE_TABLE_CATEGORY);
            db.execSQL(CREATE_TABLE_ITEM);
            db.execSQL(CREATE_TABLE_PRICE_LIST);
            db.execSQL(CREATE_TABLE_DISCOUNT_LIST);
            db.execSQL(CREATE_TABLE_PROMO_LIST);
            db.execSQL(CREATE_TABLE_VISIT);
            db.execSQL(CREATE_TABLE_SYN_VISIT);
            db.execSQL(CREATE_TABLE_ASSET_CONTROL);
            db.execSQL(CREATE_TABLE_STOCK);
            db.execSQL(CREATE_TABLE_TYPE_DOCUMENT);
            db.execSQL(CREATE_TABLE_DOCUMENT);
            db.execSQL(CREATE_TABLE_SYN_ORDER);
            db.execSQL(CREATE_TABLE_SYN_ORDER_ITEM);
            db.execSQL(CREATE_TABLE_SYN_BREAK_STOCK);
            db.execSQL(CREATE_TABLE_PAPER);
            db.execSQL(CREATE_TABLE_SUGGESTED);
            db.execSQL(CREATE_TABLE_CONFIGURATION);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            /* @EXECSQL_DROP */
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_USER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_CUSTOMER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_SYNCHRONIZATION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_CATEGORY);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_ITEM);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_PRICE_LIST);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_DISCOUNT_LIST);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_PROMO_LIST);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_VISIT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_SYN_VISIT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_ASSET_CONTROL);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_STOCK);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_TYPE_DOCUMENT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_DOCUMENT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_SYN_ORDER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_SYN_ORDER_ITEM);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_SYN_BREAK_STOCK);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_PAPER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_SUGGESTED);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_CONFIGURATION);


            onCreate(db);
        }
        public boolean checkDataBase(){
            File dbFile = new File(context.getDatabasePath(DATABASE_NAME).toString());
            return dbFile.exists();
        }
        public void deleteDataBase(){
            context.deleteDatabase(DATABASE_NAME);
        }


        /* @CLASS_USERSQL */
        public class AppUserSql {
            public AppUserSql() {	}
            public void deleteUser(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_USER,null,null);
                db.close();
            }
            public void addUser(UserClass user) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_USEID, user.getId());
                values.put(KEY_USEUSE, user.getUsername());
                values.put(KEY_USEPAS, user.getPassword());
                values.put(KEY_USENAM, user.getName());
                values.put(KEY_USEEMA, user.getEmail());
                values.put(KEY_USEDAT, user.getDate());

                values.put(KEY_USEROUCOD, user.getRouteCode());
                values.put(KEY_USEROUNAM, user.getRouteName());
                values.put(KEY_USESALCEN, user.getSalesCenter());
                values.put(KEY_USEROUTYP, user.getRouteType());
                values.put(KEY_USELIMRET, user.getLimitReturn());
                values.put(KEY_USESTA, user.getState());
                values.put(KEY_USELIMLTSLBS, user.getLimitLtsLbs());
                values.put(KEY_USEPENLTSLBS, user.getPendingLtsLbs());
                values.put(KEY_USEPRORAN, user.getProximityRange());


                db.insert(TABLE_APP_USER, null, values);
                db.close();
            }

            public boolean isRegisterUser(String sName, String sPassword) {
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_USER, new String[] { KEY_USEID, KEY_USEUSE, KEY_USEPAS, KEY_USENAM, KEY_USEEMA, KEY_USEDAT,
                                KEY_USEROUCOD, KEY_USEROUNAM, KEY_USESALCEN, KEY_USEROUTYP, KEY_USELIMRET, KEY_USESTA, KEY_USELIMLTSLBS,
                                KEY_USEPENLTSLBS, KEY_USEPRORAN}, KEY_USEUSE + "=?" + " and "+ KEY_USEPAS + "=?",
                        new String[] {sName, sPassword}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                if(cursor.getCount() == 1){
                    db.close();
                    return true;
                }else{
                    db.close();
                    return false;
                }
            }
            public UserClass getUser(int id) {
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_USER, new String[] { KEY_USEID, KEY_USEUSE, KEY_USEPAS, KEY_USENAM, KEY_USEEMA, KEY_USEDAT,
                                KEY_USEROUCOD, KEY_USEROUNAM, KEY_USESALCEN, KEY_USEROUTYP, KEY_USELIMRET, KEY_USESTA, KEY_USELIMLTSLBS,
                                KEY_USEPENLTSLBS, KEY_USEPRORAN}, KEY_USEID + "=?",
                        new String[] { String.valueOf(id) }, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                UserClass user = new UserClass(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(5), cursor.getString(6),
                        cursor.getString(7), cursor.getString(8), cursor.getString(9),
                        cursor.getString(10), cursor.getString(11), cursor.getString(12),
                        cursor.getString(13));
                db.close();
                return user;
            }
            public String getData(int nField, String sName) {
                String _date = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_USER, new String[] { KEY_USEID, KEY_USEUSE, KEY_USEPAS, KEY_USENAM, KEY_USEEMA, KEY_USEDAT,
                                KEY_USEROUCOD, KEY_USEROUNAM, KEY_USESALCEN, KEY_USEROUTYP, KEY_USELIMRET, KEY_USESTA, KEY_USELIMLTSLBS,
                                KEY_USEPENLTSLBS, KEY_USEPRORAN}, KEY_USEUSE + "=?",
                        new String[] {sName}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _date = cursor.getString(nField);
                db.close();
                return _date;
            }

            public String getAnyField(int nField) {
                String _date = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_USER, new String[] { KEY_USEID, KEY_USEUSE, KEY_USEPAS, KEY_USENAM, KEY_USEEMA, KEY_USEDAT,
                                KEY_USEROUCOD, KEY_USEROUNAM, KEY_USESALCEN, KEY_USEROUTYP, KEY_USELIMRET, KEY_USESTA, KEY_USELIMLTSLBS,
                                KEY_USEPENLTSLBS, KEY_USEPRORAN}, null,
                        null, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _date = cursor.getString(nField);
                db.close();
                return _date;
            }

            public ArrayList<UserClass> getUserData() {
                ArrayList<UserClass> userList = new ArrayList<UserClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_USER + " LIMIT 1";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        UserClass user = new UserClass();
                        user.setId(cursor.getInt(cursor.getColumnIndex(KEY_USEID)));
                        user.setUsername(cursor.getString(cursor.getColumnIndex(KEY_USEUSE)));
                        user.setPassword(cursor.getString(cursor.getColumnIndex(KEY_USEPAS)));
                        user.setName(cursor.getString(cursor.getColumnIndex(KEY_USENAM)));
                        user.setEmail(cursor.getString(cursor.getColumnIndex(KEY_USEEMA)));
                        user.setDate(cursor.getString(cursor.getColumnIndex(KEY_USEDAT)));
                        user.setRouteCode(cursor.getString(cursor.getColumnIndex(KEY_USEROUCOD)));
                        user.setRouteName(cursor.getString(cursor.getColumnIndex(KEY_USEROUNAM)));
                        user.setSalesCenter(cursor.getString(cursor.getColumnIndex(KEY_USESALCEN)));
                        user.setRouteType(cursor.getString(cursor.getColumnIndex(KEY_USEROUTYP)));
                        user.setLimitReturn(cursor.getString(cursor.getColumnIndex(KEY_USELIMRET)));
                        user.setState(cursor.getString(cursor.getColumnIndex(KEY_USESTA)));
                        user.setLimitLtsLbs(cursor.getString(cursor.getColumnIndex(KEY_USELIMLTSLBS)));
                        user.setPendingLtsLbs(cursor.getString(cursor.getColumnIndex(KEY_USEPENLTSLBS)));
                        user.setProximityRange(cursor.getString(cursor.getColumnIndex(KEY_USEPRORAN)));


                        userList.add(user);
                    } while (cursor.moveToNext());
                }
                db.close();
                return userList;
            }

            public int getId(String sName, String sPassword) {
                int _id=0;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_USER, new String[] { KEY_USEID, KEY_USEUSE, KEY_USEPAS, KEY_USENAM, KEY_USEEMA, KEY_USEDAT,
                                KEY_USEROUCOD, KEY_USEROUNAM, KEY_USESALCEN, KEY_USEROUTYP, KEY_USELIMRET, KEY_USESTA, KEY_USELIMLTSLBS,
                                KEY_USEPENLTSLBS, KEY_USEPRORAN}, KEY_USEUSE + "=?" + " and "+ KEY_USEPAS + "=?",
                        new String[] {sName, sPassword}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _id = cursor.getInt(0);
                db.close();
                return _id;
            }

            public BigDecimal getUsedReturn() {
                String returnUsed = "";
                String selectQuery = "SELECT au_user_lim_ret FROM " + TABLE_APP_USER;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        returnUsed = cursor.getString((cursor.getColumnIndex(KEY_USELIMRET)));
                    } while (cursor.moveToNext());
                }
                db.close();
                return new BigDecimal(returnUsed);
            }
            public void updateUsedReturn(String idUser, String usedAmount) {

                BigDecimal used = new BigDecimal(usedAmount);
                BigDecimal available = getUsedReturn();
                available = available.subtract(used);
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_USELIMRET, String.valueOf(available));
                db.update(TABLE_APP_USER, values, KEY_USEID + " = ?",new String[] { String.valueOf(idUser)});
                db.close();
            }

            public void updateField(String idUser, String field, String value) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(field, value);
                db.update(TABLE_APP_USER, values, KEY_USEID + " = ?",new String[] { String.valueOf(idUser)});
                db.close();
            }
        }

        public class AppCustomerSql{
            public AppCustomerSql() {
            }
            public void deleteCustomer(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_CUSTOMER,null, null);
                db.close();
            }


            public void deleteCustomerById(String id){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_CUSTOMER,KEY_CUSID + " = ?",new String[] { String.valueOf(id)});
                db.close();
            }

            public void deleteCustomeNotVisited(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_CUSTOMER, KEY_CUSVIS + " = ?",new String[] { String.valueOf("0")});
                db.close();
            }

            public int getPendingCustomerCount(String id){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_CUSTOMER + " WHERE ac_id!='" + id + "' AND ac_vis!='2'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();

                return result;
            }


            public boolean checkIfExists(String id){

                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_CUSTOMER + " WHERE ac_id='" + id + "'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();

                return result > 0;
            }

            public void updateCustomer(int id, CustomerClass customerClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put(KEY_CUSCOD,customerClass.getCode());
                values.put(KEY_CUSNAM,customerClass.getName());
                values.put(KEY_CUSRUC,customerClass.getRuc());
                values.put(KEY_CUSARE,customerClass.getArea());
                values.put(KEY_CUSADD,customerClass.getAddress());
                values.put(KEY_CUSCODCOU,customerClass.getCodeCountry());
                values.put(KEY_CUSCODDEP,customerClass.getCodeDepartment());
                values.put(KEY_CUSCODCIT,customerClass.getCodeCity());
                values.put(KEY_CUSCODCOL,customerClass.getCodeColony());
                values.put(KEY_CUSREP,customerClass.getRepresentative());
                values.put(KEY_CUSDAY,customerClass.getDay());
                values.put(KEY_CUSPHO,customerClass.getPhone());
                values.put(KEY_CUSMOBPHO,customerClass.getMobilePhone());
                values.put(KEY_CUSCODROU,customerClass.getCodeRoute());
                values.put(KEY_CUSWEI,customerClass.getWeight());
                values.put(KEY_CUSLAT,customerClass.getLatitude());
                values.put(KEY_CUSLON,customerClass.getLongitude());
                values.put(KEY_CUSTYP,customerClass.getType());
                values.put(KEY_CUSCLA,customerClass.get_class());
                values.put(KEY_CUSPRI,customerClass.getPriority());
                values.put(KEY_CUSPRO,customerClass.getPro());
                values.put(KEY_CUSCODBRA,customerClass.getCodeBranch());
                values.put(KEY_CUSCODOFF,customerClass.getCodeOffice());
                values.put(KEY_CUSNAMCIT,customerClass.getNameCity());
                values.put(KEY_CUSNAMCOL,customerClass.getNameColony());
                values.put(KEY_CUSNAMOFF,customerClass.getNameOffice());
                values.put(KEY_CUSNAMROU,customerClass.getNameRoute());

                values.put(KEY_CUSPRILISCOD, customerClass.getPriceListCode());
                values.put(KEY_CUSDISLISCOD, customerClass.getDiscountListCode());
                values.put(KEY_CUSPROLISCOD, customerClass.getPromoListCode());

                values.put(KEY_CUSACT,customerClass.getActive());
                values.put(KEY_CUSSTA,customerClass.getState());

                db.update(TABLE_APP_CUSTOMER, values, KEY_CUSID + " = ?",new String[] { String.valueOf(id)});
                db.close();
            }

            public void updateCustomerVisit(String id, String state){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_CUSVIS, state);
                db.update(TABLE_APP_CUSTOMER, values, KEY_CUSID + " = ?",new String[] {id});
                db.close();
            }

            public int countCustomers(){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_CUSTOMER,null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result;
            }

            public int countCustomersVisited(){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_CUSTOMER + " WHERE ac_vis='2'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result;
            }

            public ArrayList<CustomerClass> getCustomerDataByDay(String day){
                ArrayList<CustomerClass> curstomerClassList = new ArrayList<CustomerClass>();
                String selectQuery = "SELECT  * FROM " + TABLE_APP_CUSTOMER + " WHERE ac_day LIKE('%" + day + "%')";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()){
                    do {
                        CustomerClass item = new CustomerClass();
                        item.setId(cursor.getInt(cursor.getColumnIndex(KEY_CUSID)));
                        item.setCode(cursor.getString(cursor.getColumnIndex(KEY_CUSCOD)));
                        item.setName(cursor.getString(cursor.getColumnIndex(KEY_CUSNAM)));
                        item.setRuc(cursor.getString(cursor.getColumnIndex(KEY_CUSRUC)));
                        item.setArea(cursor.getString(cursor.getColumnIndex(KEY_CUSARE)));
                        item.setAddress(cursor.getString(cursor.getColumnIndex(KEY_CUSADD)));
                        item.setCodeCountry(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCOU)));
                        item.setCodeDepartment(cursor.getString(cursor.getColumnIndex(KEY_CUSCODDEP)));
                        item.setCodeCity(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCIT)));
                        item.setCodeColony(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCOL)));
                        item.setRepresentative(cursor.getString(cursor.getColumnIndex(KEY_CUSREP)));
                        item.setDay(cursor.getString(cursor.getColumnIndex(KEY_CUSDAY)));
                        item.setPhone(cursor.getString(cursor.getColumnIndex(KEY_CUSPHO)));
                        item.setMobilePhone(cursor.getString(cursor.getColumnIndex(KEY_CUSMOBPHO)));
                        item.setCodeRoute(cursor.getString(cursor.getColumnIndex(KEY_CUSCODROU)));
                        item.setWeight(cursor.getString(cursor.getColumnIndex(KEY_CUSWEI)));
                        item.setLatitude(cursor.getString(cursor.getColumnIndex(KEY_CUSLAT)));
                        item.setLongitude(cursor.getString(cursor.getColumnIndex(KEY_CUSLON)));
                        item.setType(cursor.getString(cursor.getColumnIndex(KEY_CUSTYP)));
                        item.set_class(cursor.getString(cursor.getColumnIndex(KEY_CUSCLA)));
                        item.setPriority(cursor.getString(cursor.getColumnIndex(KEY_CUSPRI)));
                        item.setPro(cursor.getString(cursor.getColumnIndex(KEY_CUSPRO)));
                        item.setCodeBranch(cursor.getString(cursor.getColumnIndex(KEY_CUSCODBRA)));
                        item.setCodeOffice(cursor.getString(cursor.getColumnIndex(KEY_CUSCODOFF)));
                        item.setNameCity(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMCIT)));
                        item.setNameColony(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMCOL)));
                        item.setNameOffice(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMOFF)));
                        item.setNameRoute(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMROU)));

                        item.setPriceListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSPRILISCOD)));
                        item.setDiscountListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSDISLISCOD)));
                        item.setPromoListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSPROLISCOD)));

                        item.setActive(cursor.getString(cursor.getColumnIndex(KEY_CUSACT)));
                        item.setState(cursor.getString(cursor.getColumnIndex(KEY_CUSSTA)));
                        item.setVisited(cursor.getString(cursor.getColumnIndex(KEY_CUSVIS)));
                        item.setLoad(cursor.getString(cursor.getColumnIndex(KEY_CUSLOA)));
                        curstomerClassList.add(item);
                    } while (cursor.moveToNext());
                }
                db.close();
                return curstomerClassList;
            }

            public ArrayList<CustomerClass> getCustomerDataNotByDay(String day){
                ArrayList<CustomerClass> curstomerClassList = new ArrayList<CustomerClass>();
                String selectQuery = "SELECT  * FROM " + TABLE_APP_CUSTOMER + " WHERE ac_day NOT LIKE('%" + day + "%')";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()){
                    do {
                        CustomerClass item = new CustomerClass();

                        item.setId(cursor.getInt(cursor.getColumnIndex(KEY_CUSID)));
                        item.setCode(cursor.getString(cursor.getColumnIndex(KEY_CUSCOD)));
                        item.setName(cursor.getString(cursor.getColumnIndex(KEY_CUSNAM)));
                        item.setRuc(cursor.getString(cursor.getColumnIndex(KEY_CUSRUC)));
                        item.setArea(cursor.getString(cursor.getColumnIndex(KEY_CUSARE)));
                        item.setAddress(cursor.getString(cursor.getColumnIndex(KEY_CUSADD)));
                        item.setCodeCountry(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCOU)));
                        item.setCodeDepartment(cursor.getString(cursor.getColumnIndex(KEY_CUSCODDEP)));
                        item.setCodeCity(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCIT)));
                        item.setCodeColony(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCOL)));
                        item.setRepresentative(cursor.getString(cursor.getColumnIndex(KEY_CUSREP)));
                        item.setDay(cursor.getString(cursor.getColumnIndex(KEY_CUSDAY)));
                        item.setPhone(cursor.getString(cursor.getColumnIndex(KEY_CUSPHO)));
                        item.setMobilePhone(cursor.getString(cursor.getColumnIndex(KEY_CUSMOBPHO)));
                        item.setCodeRoute(cursor.getString(cursor.getColumnIndex(KEY_CUSCODROU)));
                        item.setWeight(cursor.getString(cursor.getColumnIndex(KEY_CUSWEI)));
                        item.setLatitude(cursor.getString(cursor.getColumnIndex(KEY_CUSLAT)));
                        item.setLongitude(cursor.getString(cursor.getColumnIndex(KEY_CUSLON)));
                        item.setType(cursor.getString(cursor.getColumnIndex(KEY_CUSTYP)));
                        item.set_class(cursor.getString(cursor.getColumnIndex(KEY_CUSCLA)));
                        item.setPriority(cursor.getString(cursor.getColumnIndex(KEY_CUSPRI)));
                        item.setPro(cursor.getString(cursor.getColumnIndex(KEY_CUSPRO)));
                        item.setCodeBranch(cursor.getString(cursor.getColumnIndex(KEY_CUSCODBRA)));
                        item.setCodeOffice(cursor.getString(cursor.getColumnIndex(KEY_CUSCODOFF)));
                        item.setNameCity(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMCIT)));
                        item.setNameColony(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMCOL)));
                        item.setNameOffice(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMOFF)));
                        item.setNameRoute(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMROU)));

                        item.setPriceListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSPRILISCOD)));
                        item.setDiscountListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSDISLISCOD)));
                        item.setPromoListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSPROLISCOD)));

                        item.setActive(cursor.getString(cursor.getColumnIndex(KEY_CUSACT)));
                        item.setState(cursor.getString(cursor.getColumnIndex(KEY_CUSSTA)));
                        item.setVisited(cursor.getString(cursor.getColumnIndex(KEY_CUSVIS)));
                        item.setLoad(cursor.getString(cursor.getColumnIndex(KEY_CUSLOA)));
                        curstomerClassList.add(item);
                    } while (cursor.moveToNext());
                }
                db.close();
                return curstomerClassList;
            }

            //Not Visited (0)
            public ArrayList<CustomerClass> getCustomerVisitPending(){
                ArrayList<CustomerClass> curstomerClassList = new ArrayList<CustomerClass>();
                String selectQuery = "SELECT  * FROM " + TABLE_APP_CUSTOMER + " WHERE ac_vis='0'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()){
                    do {
                        CustomerClass item = new CustomerClass();

                        item.setId(cursor.getInt(cursor.getColumnIndex(KEY_CUSID)));
                        item.setCode(cursor.getString(cursor.getColumnIndex(KEY_CUSCOD)));
                        item.setName(cursor.getString(cursor.getColumnIndex(KEY_CUSNAM)));
                        item.setRuc(cursor.getString(cursor.getColumnIndex(KEY_CUSRUC)));
                        item.setArea(cursor.getString(cursor.getColumnIndex(KEY_CUSARE)));
                        item.setAddress(cursor.getString(cursor.getColumnIndex(KEY_CUSADD)));
                        item.setCodeCountry(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCOU)));
                        item.setCodeDepartment(cursor.getString(cursor.getColumnIndex(KEY_CUSCODDEP)));
                        item.setCodeCity(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCIT)));
                        item.setCodeColony(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCOL)));
                        item.setRepresentative(cursor.getString(cursor.getColumnIndex(KEY_CUSREP)));
                        item.setDay(cursor.getString(cursor.getColumnIndex(KEY_CUSDAY)));
                        item.setPhone(cursor.getString(cursor.getColumnIndex(KEY_CUSPHO)));
                        item.setMobilePhone(cursor.getString(cursor.getColumnIndex(KEY_CUSMOBPHO)));
                        item.setCodeRoute(cursor.getString(cursor.getColumnIndex(KEY_CUSCODROU)));
                        item.setWeight(cursor.getString(cursor.getColumnIndex(KEY_CUSWEI)));
                        item.setLatitude(cursor.getString(cursor.getColumnIndex(KEY_CUSLAT)));
                        item.setLongitude(cursor.getString(cursor.getColumnIndex(KEY_CUSLON)));
                        item.setType(cursor.getString(cursor.getColumnIndex(KEY_CUSTYP)));
                        item.set_class(cursor.getString(cursor.getColumnIndex(KEY_CUSCLA)));
                        item.setPriority(cursor.getString(cursor.getColumnIndex(KEY_CUSPRI)));
                        item.setPro(cursor.getString(cursor.getColumnIndex(KEY_CUSPRO)));
                        item.setCodeBranch(cursor.getString(cursor.getColumnIndex(KEY_CUSCODBRA)));
                        item.setCodeOffice(cursor.getString(cursor.getColumnIndex(KEY_CUSCODOFF)));
                        item.setNameCity(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMCIT)));
                        item.setNameColony(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMCOL)));
                        item.setNameOffice(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMOFF)));
                        item.setNameRoute(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMROU)));

                        item.setPriceListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSPRILISCOD)));
                        item.setDiscountListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSDISLISCOD)));
                        item.setPromoListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSPROLISCOD)));

                        item.setActive(cursor.getString(cursor.getColumnIndex(KEY_CUSACT)));
                        item.setState(cursor.getString(cursor.getColumnIndex(KEY_CUSSTA)));
                        item.setVisited(cursor.getString(cursor.getColumnIndex(KEY_CUSVIS)));
                        item.setLoad(cursor.getString(cursor.getColumnIndex(KEY_CUSLOA)));
                        curstomerClassList.add(item);
                    } while (cursor.moveToNext());
                }
                db.close();
                return curstomerClassList;
            }

            //Visiting now (1)
            public ArrayList<CustomerClass> getCustomerInVisit(){
                ArrayList<CustomerClass> curstomerClassList = new ArrayList<CustomerClass>();
                String selectQuery = "SELECT  * FROM " + TABLE_APP_CUSTOMER + " WHERE ac_vis='1'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()){
                    do {
                        CustomerClass item = new CustomerClass();

                        item.setId(cursor.getInt(cursor.getColumnIndex(KEY_CUSID)));
                        item.setCode(cursor.getString(cursor.getColumnIndex(KEY_CUSCOD)));
                        item.setName(cursor.getString(cursor.getColumnIndex(KEY_CUSNAM)));
                        item.setRuc(cursor.getString(cursor.getColumnIndex(KEY_CUSRUC)));
                        item.setArea(cursor.getString(cursor.getColumnIndex(KEY_CUSARE)));
                        item.setAddress(cursor.getString(cursor.getColumnIndex(KEY_CUSADD)));
                        item.setCodeCountry(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCOU)));
                        item.setCodeDepartment(cursor.getString(cursor.getColumnIndex(KEY_CUSCODDEP)));
                        item.setCodeCity(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCIT)));
                        item.setCodeColony(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCOL)));
                        item.setRepresentative(cursor.getString(cursor.getColumnIndex(KEY_CUSREP)));
                        item.setDay(cursor.getString(cursor.getColumnIndex(KEY_CUSDAY)));
                        item.setPhone(cursor.getString(cursor.getColumnIndex(KEY_CUSPHO)));
                        item.setMobilePhone(cursor.getString(cursor.getColumnIndex(KEY_CUSMOBPHO)));
                        item.setCodeRoute(cursor.getString(cursor.getColumnIndex(KEY_CUSCODROU)));
                        item.setWeight(cursor.getString(cursor.getColumnIndex(KEY_CUSWEI)));
                        item.setLatitude(cursor.getString(cursor.getColumnIndex(KEY_CUSLAT)));
                        item.setLongitude(cursor.getString(cursor.getColumnIndex(KEY_CUSLON)));
                        item.setType(cursor.getString(cursor.getColumnIndex(KEY_CUSTYP)));
                        item.set_class(cursor.getString(cursor.getColumnIndex(KEY_CUSCLA)));
                        item.setPriority(cursor.getString(cursor.getColumnIndex(KEY_CUSPRI)));
                        item.setPro(cursor.getString(cursor.getColumnIndex(KEY_CUSPRO)));
                        item.setCodeBranch(cursor.getString(cursor.getColumnIndex(KEY_CUSCODBRA)));
                        item.setCodeOffice(cursor.getString(cursor.getColumnIndex(KEY_CUSCODOFF)));
                        item.setNameCity(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMCIT)));
                        item.setNameColony(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMCOL)));
                        item.setNameOffice(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMOFF)));
                        item.setNameRoute(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMROU)));

                        item.setPriceListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSPRILISCOD)));
                        item.setDiscountListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSDISLISCOD)));
                        item.setPromoListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSPROLISCOD)));

                        item.setActive(cursor.getString(cursor.getColumnIndex(KEY_CUSACT)));
                        item.setState(cursor.getString(cursor.getColumnIndex(KEY_CUSSTA)));
                        item.setVisited(cursor.getString(cursor.getColumnIndex(KEY_CUSVIS)));
                        item.setLoad(cursor.getString(cursor.getColumnIndex(KEY_CUSLOA)));
                        curstomerClassList.add(item);
                    } while (cursor.moveToNext());
                }
                db.close();
                return curstomerClassList;
            }

            //Already visited (2)
            public ArrayList<CustomerClass> getCustomerVisited(){
                ArrayList<CustomerClass> curstomerClassList = new ArrayList<CustomerClass>();
                String selectQuery = "SELECT  * FROM " + TABLE_APP_CUSTOMER + " WHERE ac_vis='2'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()){
                    do {
                        CustomerClass item = new CustomerClass();

                        item.setId(cursor.getInt(cursor.getColumnIndex(KEY_CUSID)));
                        item.setCode(cursor.getString(cursor.getColumnIndex(KEY_CUSCOD)));
                        item.setName(cursor.getString(cursor.getColumnIndex(KEY_CUSNAM)));
                        item.setRuc(cursor.getString(cursor.getColumnIndex(KEY_CUSRUC)));
                        item.setArea(cursor.getString(cursor.getColumnIndex(KEY_CUSARE)));
                        item.setAddress(cursor.getString(cursor.getColumnIndex(KEY_CUSADD)));
                        item.setCodeCountry(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCOU)));
                        item.setCodeDepartment(cursor.getString(cursor.getColumnIndex(KEY_CUSCODDEP)));
                        item.setCodeCity(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCIT)));
                        item.setCodeColony(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCOL)));
                        item.setRepresentative(cursor.getString(cursor.getColumnIndex(KEY_CUSREP)));
                        item.setDay(cursor.getString(cursor.getColumnIndex(KEY_CUSDAY)));
                        item.setPhone(cursor.getString(cursor.getColumnIndex(KEY_CUSPHO)));
                        item.setMobilePhone(cursor.getString(cursor.getColumnIndex(KEY_CUSMOBPHO)));
                        item.setCodeRoute(cursor.getString(cursor.getColumnIndex(KEY_CUSCODROU)));
                        item.setWeight(cursor.getString(cursor.getColumnIndex(KEY_CUSWEI)));
                        item.setLatitude(cursor.getString(cursor.getColumnIndex(KEY_CUSLAT)));
                        item.setLongitude(cursor.getString(cursor.getColumnIndex(KEY_CUSLON)));
                        item.setType(cursor.getString(cursor.getColumnIndex(KEY_CUSTYP)));
                        item.set_class(cursor.getString(cursor.getColumnIndex(KEY_CUSCLA)));
                        item.setPriority(cursor.getString(cursor.getColumnIndex(KEY_CUSPRI)));
                        item.setPro(cursor.getString(cursor.getColumnIndex(KEY_CUSPRO)));
                        item.setCodeBranch(cursor.getString(cursor.getColumnIndex(KEY_CUSCODBRA)));
                        item.setCodeOffice(cursor.getString(cursor.getColumnIndex(KEY_CUSCODOFF)));
                        item.setNameCity(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMCIT)));
                        item.setNameColony(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMCOL)));
                        item.setNameOffice(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMOFF)));
                        item.setNameRoute(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMROU)));

                        item.setPriceListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSPRILISCOD)));
                        item.setDiscountListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSDISLISCOD)));
                        item.setPromoListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSPROLISCOD)));

                        item.setActive(cursor.getString(cursor.getColumnIndex(KEY_CUSACT)));
                        item.setState(cursor.getString(cursor.getColumnIndex(KEY_CUSSTA)));
                        item.setVisited(cursor.getString(cursor.getColumnIndex(KEY_CUSVIS)));
                        item.setLoad(cursor.getString(cursor.getColumnIndex(KEY_CUSLOA)));
                        curstomerClassList.add(item);
                    } while (cursor.moveToNext());
                }
                db.close();
                return curstomerClassList;
            }

            public ArrayList<CustomerClass> getPendingCustomer(){
                ArrayList<CustomerClass> curstomerClassList = new ArrayList<CustomerClass>();
                String selectQuery = "SELECT  * FROM " + TABLE_APP_CUSTOMER + " WHERE ac_loa='0'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()){
                    do {
                        CustomerClass item = new CustomerClass();

                        item.setId(cursor.getInt(cursor.getColumnIndex(KEY_CUSID)));
                        item.setCode(cursor.getString(cursor.getColumnIndex(KEY_CUSCOD)));
                        item.setName(cursor.getString(cursor.getColumnIndex(KEY_CUSNAM)));
                        item.setRuc(cursor.getString(cursor.getColumnIndex(KEY_CUSRUC)));
                        item.setArea(cursor.getString(cursor.getColumnIndex(KEY_CUSARE)));
                        item.setAddress(cursor.getString(cursor.getColumnIndex(KEY_CUSADD)));
                        item.setCodeCountry(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCOU)));
                        item.setCodeDepartment(cursor.getString(cursor.getColumnIndex(KEY_CUSCODDEP)));
                        item.setCodeCity(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCIT)));
                        item.setCodeColony(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCOL)));
                        item.setRepresentative(cursor.getString(cursor.getColumnIndex(KEY_CUSREP)));
                        item.setDay(cursor.getString(cursor.getColumnIndex(KEY_CUSDAY)));
                        item.setPhone(cursor.getString(cursor.getColumnIndex(KEY_CUSPHO)));
                        item.setMobilePhone(cursor.getString(cursor.getColumnIndex(KEY_CUSMOBPHO)));
                        item.setCodeRoute(cursor.getString(cursor.getColumnIndex(KEY_CUSCODROU)));
                        item.setWeight(cursor.getString(cursor.getColumnIndex(KEY_CUSWEI)));
                        item.setLatitude(cursor.getString(cursor.getColumnIndex(KEY_CUSLAT)));
                        item.setLongitude(cursor.getString(cursor.getColumnIndex(KEY_CUSLON)));
                        item.setType(cursor.getString(cursor.getColumnIndex(KEY_CUSTYP)));
                        item.set_class(cursor.getString(cursor.getColumnIndex(KEY_CUSCLA)));
                        item.setPriority(cursor.getString(cursor.getColumnIndex(KEY_CUSPRI)));
                        item.setPro(cursor.getString(cursor.getColumnIndex(KEY_CUSPRO)));
                        item.setCodeBranch(cursor.getString(cursor.getColumnIndex(KEY_CUSCODBRA)));
                        item.setCodeOffice(cursor.getString(cursor.getColumnIndex(KEY_CUSCODOFF)));
                        item.setNameCity(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMCIT)));
                        item.setNameColony(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMCOL)));
                        item.setNameOffice(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMOFF)));
                        item.setNameRoute(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMROU)));

                        item.setPriceListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSPRILISCOD)));
                        item.setDiscountListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSDISLISCOD)));
                        item.setPromoListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSPROLISCOD)));

                        item.setActive(cursor.getString(cursor.getColumnIndex(KEY_CUSACT)));
                        item.setState(cursor.getString(cursor.getColumnIndex(KEY_CUSSTA)));
                        item.setVisited(cursor.getString(cursor.getColumnIndex(KEY_CUSVIS)));
                        item.setLoad(cursor.getString(cursor.getColumnIndex(KEY_CUSLOA)));
                        curstomerClassList.add(item);
                    } while (cursor.moveToNext());
                }
                db.close();
                return curstomerClassList;
            }

            public void addCustomer(CustomerClass customerClass){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put(KEY_CUSID,customerClass.getId());
                values.put(KEY_CUSCOD,customerClass.getCode());
                values.put(KEY_CUSNAM,customerClass.getName());
                values.put(KEY_CUSRUC,customerClass.getRuc());
                values.put(KEY_CUSARE,customerClass.getArea());
                values.put(KEY_CUSADD,customerClass.getAddress());
                values.put(KEY_CUSCODCOU,customerClass.getCodeCountry());
                values.put(KEY_CUSCODDEP,customerClass.getCodeDepartment());
                values.put(KEY_CUSCODCIT,customerClass.getCodeCity());
                values.put(KEY_CUSCODCOL,customerClass.getCodeColony());
                values.put(KEY_CUSREP,customerClass.getRepresentative());
                values.put(KEY_CUSDAY,customerClass.getDay());
                values.put(KEY_CUSPHO,customerClass.getPhone());
                values.put(KEY_CUSMOBPHO,customerClass.getMobilePhone());
                values.put(KEY_CUSCODROU,customerClass.getCodeRoute());
                values.put(KEY_CUSWEI,customerClass.getWeight());
                values.put(KEY_CUSLAT,customerClass.getLatitude());
                values.put(KEY_CUSLON,customerClass.getLongitude());
                values.put(KEY_CUSTYP,customerClass.getType());
                values.put(KEY_CUSCLA,customerClass.get_class());
                values.put(KEY_CUSPRI,customerClass.getPriority());
                values.put(KEY_CUSPRO,customerClass.getPro());
                values.put(KEY_CUSCODBRA,customerClass.getCodeBranch());
                values.put(KEY_CUSCODOFF,customerClass.getCodeOffice());
                values.put(KEY_CUSNAMCIT,customerClass.getNameCity());
                values.put(KEY_CUSNAMCOL,customerClass.getNameColony());
                values.put(KEY_CUSNAMOFF,customerClass.getNameOffice());
                values.put(KEY_CUSNAMROU,customerClass.getNameRoute());

                values.put(KEY_CUSPRILISCOD, customerClass.getPriceListCode());
                values.put(KEY_CUSDISLISCOD, customerClass.getDiscountListCode());
                values.put(KEY_CUSPROLISCOD, customerClass.getPromoListCode());

                values.put(KEY_CUSACT,customerClass.getActive());
                values.put(KEY_CUSSTA,customerClass.getState());
                values.put(KEY_CUSVIS,customerClass.getVisited());
                values.put(KEY_CUSLOA,customerClass.getLoad());

                db.insert(TABLE_APP_CUSTOMER, null, values);
                db.close();
            }

            public ArrayList<CustomerClass> getCustomerData(){
                ArrayList<CustomerClass> curstomerClassList = new ArrayList<CustomerClass>();
                String selectQuery = "SELECT  * FROM " + TABLE_APP_CUSTOMER;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()){
                    do {
                        CustomerClass item = new CustomerClass();

                        item.setId(cursor.getInt(cursor.getColumnIndex(KEY_CUSID)));
                        item.setCode(cursor.getString(cursor.getColumnIndex(KEY_CUSCOD)));
                        item.setName(cursor.getString(cursor.getColumnIndex(KEY_CUSNAM)));
                        item.setRuc(cursor.getString(cursor.getColumnIndex(KEY_CUSRUC)));
                        item.setArea(cursor.getString(cursor.getColumnIndex(KEY_CUSARE)));
                        item.setAddress(cursor.getString(cursor.getColumnIndex(KEY_CUSADD)));
                        item.setCodeCountry(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCOU)));
                        item.setCodeDepartment(cursor.getString(cursor.getColumnIndex(KEY_CUSCODDEP)));
                        item.setCodeCity(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCIT)));
                        item.setCodeColony(cursor.getString(cursor.getColumnIndex(KEY_CUSCODCOL)));
                        item.setRepresentative(cursor.getString(cursor.getColumnIndex(KEY_CUSREP)));
                        item.setDay(cursor.getString(cursor.getColumnIndex(KEY_CUSDAY)));
                        item.setPhone(cursor.getString(cursor.getColumnIndex(KEY_CUSPHO)));
                        item.setMobilePhone(cursor.getString(cursor.getColumnIndex(KEY_CUSMOBPHO)));
                        item.setCodeRoute(cursor.getString(cursor.getColumnIndex(KEY_CUSCODROU)));
                        item.setWeight(cursor.getString(cursor.getColumnIndex(KEY_CUSWEI)));
                        item.setLatitude(cursor.getString(cursor.getColumnIndex(KEY_CUSLAT)));
                        item.setLongitude(cursor.getString(cursor.getColumnIndex(KEY_CUSLON)));
                        item.setType(cursor.getString(cursor.getColumnIndex(KEY_CUSTYP)));
                        item.set_class(cursor.getString(cursor.getColumnIndex(KEY_CUSCLA)));
                        item.setPriority(cursor.getString(cursor.getColumnIndex(KEY_CUSPRI)));
                        item.setPro(cursor.getString(cursor.getColumnIndex(KEY_CUSPRO)));
                        item.setCodeBranch(cursor.getString(cursor.getColumnIndex(KEY_CUSCODBRA)));
                        item.setCodeOffice(cursor.getString(cursor.getColumnIndex(KEY_CUSCODOFF)));
                        item.setNameCity(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMCIT)));
                        item.setNameColony(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMCOL)));
                        item.setNameOffice(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMOFF)));
                        item.setNameRoute(cursor.getString(cursor.getColumnIndex(KEY_CUSNAMROU)));

                        item.setPriceListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSPRILISCOD)));
                        item.setDiscountListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSDISLISCOD)));
                        item.setPromoListCode(cursor.getString(cursor.getColumnIndex(KEY_CUSPROLISCOD)));

                        item.setActive(cursor.getString(cursor.getColumnIndex(KEY_CUSACT)));
                        item.setState(cursor.getString(cursor.getColumnIndex(KEY_CUSSTA)));
                        item.setVisited(cursor.getString(cursor.getColumnIndex(KEY_CUSVIS)));
                        item.setLoad(cursor.getString(cursor.getColumnIndex(KEY_CUSLOA)));
                        curstomerClassList.add(item);
                    } while (cursor.moveToNext());
                }
                db.close();
                return curstomerClassList;
            }
            public CustomerClass getCustomer (int nId){

                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_CUSTOMER, new String[] {
                                KEY_CUSID, KEY_CUSCOD, KEY_CUSNAM,KEY_CUSRUC,KEY_CUSARE,KEY_CUSADD,KEY_CUSCODCOU,KEY_CUSCODDEP,
                                KEY_CUSCODCIT,KEY_CUSCODCOL,KEY_CUSREP,KEY_CUSDAY,KEY_CUSPHO,KEY_CUSMOBPHO,KEY_CUSCODROU,
                                KEY_CUSWEI, KEY_CUSLAT, KEY_CUSLON, KEY_CUSTYP, KEY_CUSCLA,KEY_CUSPRI, KEY_CUSPRO,KEY_CUSCODBRA,
                                KEY_CUSCODOFF, KEY_CUSNAMCIT,KEY_CUSNAMCOL, KEY_CUSNAMOFF, KEY_CUSNAMROU, KEY_CUSPRILISCOD,
                                KEY_CUSDISLISCOD, KEY_CUSPROLISCOD, KEY_CUSACT, KEY_CUSSTA, KEY_CUSVIS, KEY_CUSLOA}, KEY_CUSID + "=?",
                        new String[] { String.valueOf(nId) }, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                CustomerClass customerClass = new CustomerClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),
                        cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),
                        cursor.getString(11),cursor.getString(12),cursor.getString(13), cursor.getString(14), cursor.getString(15),
                        cursor.getString(16),cursor.getString(17),cursor.getString(18),cursor.getString(19),cursor.getString(20),
                        cursor.getString(21),cursor.getString(22), cursor.getString(23),cursor.getString(24),cursor.getString(25),
                        cursor.getString(26),cursor.getString(27),cursor.getString(28), cursor.getString(29), cursor.getString(30),
                        cursor.getString(31), cursor.getString(32), cursor.getString(33), cursor.getString(34));
                db.close();
                return customerClass;
            }

        }

        public class AppSynchronizationSql {
            public AppSynchronizationSql() {}

            public void deleteSynchronization(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_SYNCHRONIZATION,null,null);
                db.close();
            }

            public boolean checkIfExists(String classType){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_SYNCHRONIZATION + " WHERE as_cla_nam='" + classType + "'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result > 0;
            }

            public String getDateLastSyncStock() {
                String selectQuery = "SELECT * FROM " + TABLE_APP_SYNCHRONIZATION + " WHERE as_cla_nam='Stock'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if(cursor.moveToLast()){
                    return cursor.getString(cursor.getColumnIndex(KEY_SYNDATHOUEND));
                }
                db.close();
                return "";
            }

            public void addSynchronization(SynchronizationClass synchronizationClass) {

                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                //values.put(KEY_SYNID, synchronizationClass.getId());
                values.put(KEY_SYNCLANAM, synchronizationClass.getClassName());
                values.put(KEY_SYNDATHOUSTA, synchronizationClass.getDateHourStart());
                values.put(KEY_SYNDATHOUEND, synchronizationClass.getDateHourEnd());
                values.put(KEY_SYNREGNUM, synchronizationClass.getRegisterNumber());
                values.put(KEY_SYNFAINUM, synchronizationClass.getFailureNumber());
                values.put(KEY_SYNSTA, synchronizationClass.getState());

                db.insert(TABLE_APP_SYNCHRONIZATION, null, values);
                db.close();
            }


            public ArrayList<SynchronizationClass> getSynchronizationData() {
                ArrayList<SynchronizationClass> synchronizationClassList = new ArrayList<SynchronizationClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_SYNCHRONIZATION;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        SynchronizationClass synchronizationClass = new SynchronizationClass();
                        synchronizationClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_SYNID)));
                        synchronizationClass.setClassName(cursor.getString(cursor.getColumnIndex(KEY_SYNCLANAM)));
                        synchronizationClass.setDateHourStart(cursor.getString(cursor.getColumnIndex(KEY_SYNDATHOUSTA)));
                        synchronizationClass.setDateHourEnd(cursor.getString(cursor.getColumnIndex(KEY_SYNDATHOUEND)));
                        synchronizationClass.setRegisterNumber(cursor.getString(cursor.getColumnIndex(KEY_SYNREGNUM)));
                        synchronizationClass.setFailureNumber(cursor.getString(cursor.getColumnIndex(KEY_SYNFAINUM)));
                        synchronizationClass.setState(cursor.getString(cursor.getColumnIndex(KEY_SYNSTA)));

                        synchronizationClassList.add(synchronizationClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return synchronizationClassList;
            }



            public SynchronizationClass getSynchronization(String nId) {
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_SYNCHRONIZATION, new String[] {KEY_SYNID, KEY_SYNCLANAM, KEY_SYNDATHOUSTA, KEY_SYNDATHOUEND,
                                KEY_SYNREGNUM, KEY_SYNFAINUM, KEY_SYNSTA}, KEY_SYNID + "=?",
                        new String[] { String.valueOf(nId) }, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }

                SynchronizationClass synchronizationClass = new SynchronizationClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
                db.close();
                return synchronizationClass;
            }

            public String getData(int nField, String sName) {
                String _data = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_SYNCHRONIZATION, new String[] {KEY_SYNID, KEY_SYNCLANAM, KEY_SYNDATHOUSTA, KEY_SYNDATHOUEND,
                                KEY_SYNREGNUM, KEY_SYNFAINUM, KEY_SYNSTA}, KEY_SYNID + "=?",
                        new String[] {sName}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _data = cursor.getString(nField);
                db.close();
                return _data;
            }

        }

        public class AppCategorySql{
            public AppCategorySql() {
            }
            public void deleteCategory(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_CATEGORY,null,null);
                db.close();
            }

            public boolean checkIfExists(String id){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_CATEGORY + " WHERE aca_id='" + id + "'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result > 0;
            }

            public void addCategory(CategoryClass categoryClass) {

                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_CATID, categoryClass.getId());
                values.put(KEY_CATCOD, categoryClass.getCode());
                values.put(KEY_CATDES, categoryClass.getDescription());
                values.put(KEY_CATORD, categoryClass.getOrder());
                values.put(KEY_CATSTA, categoryClass.getState());
                db.insert(TABLE_APP_CATEGORY, null, values);
                db.close();
            }

            public void updateCategory(int id, CategoryClass categoryClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_CATID, categoryClass.getId());
                values.put(KEY_CATCOD, categoryClass.getCode());
                values.put(KEY_CATDES, categoryClass.getDescription());
                values.put(KEY_CATORD, categoryClass.getOrder());
                values.put(KEY_CATSTA, categoryClass.getState());
                db.update(TABLE_APP_CATEGORY, values, KEY_CATID + " = ?",new String[] { String.valueOf(id)});
                db.close();
            }


            public ArrayList<CategoryClass> getCategoryData() {
                ArrayList<CategoryClass> categoryClassList = new ArrayList<CategoryClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_CATEGORY;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        CategoryClass categoryClass = new CategoryClass();
                        categoryClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_CATID)));
                        categoryClass.setCode(cursor.getString(cursor.getColumnIndex(KEY_CATCOD)));
                        categoryClass.setDescription(cursor.getString(cursor.getColumnIndex(KEY_CATDES)));
                        categoryClass.setOrder(cursor.getString(cursor.getColumnIndex(KEY_CATORD)));
                        categoryClass.setState(cursor.getString(cursor.getColumnIndex(KEY_CATSTA)));
                        categoryClassList.add(categoryClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return categoryClassList;
            }


            public CategoryClass getCategory(String nId) {
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_CATEGORY, new String[] {KEY_CATID, KEY_CATCOD, KEY_CATDES, KEY_CATORD,
                                KEY_CATSTA}, KEY_CATID + "=?",
                        new String[] { String.valueOf(nId) }, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }

                CategoryClass categoryClass = new CategoryClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4));
                db.close();
                return categoryClass;
            }

            public String getData(int nField, String sName) {
                String _data = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_CATEGORY, new String[] {KEY_CATID, KEY_CATCOD, KEY_CATDES, KEY_CATORD,
                                KEY_CATSTA}, KEY_CATID + "=?",
                        new String[] {sName}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _data = cursor.getString(nField);
                db.close();
                return _data;
            }

        }

        public class AppItemSql{
            public AppItemSql() {
            }

            public void deleteItem(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_ITEM,null,null);
                db.close();
            }

            public void addItem(ItemClass itemClass) {

                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put(KEY_ITEID, itemClass.getId());
                values.put(KEY_ITECATCOD, itemClass.getCategoryCode());
                values.put(KEY_ITECOD, itemClass.getCode());
                values.put(KEY_ITEDES, itemClass.getDescription());
                values.put(KEY_ITETAX, itemClass.getTax());
                values.put(KEY_ITEMEAUNICOD, itemClass.getMeasureUnitCode());
                values.put(KEY_ITELITLBS, itemClass.getLitersLbs());
                values.put(KEY_ITEORD, itemClass.getOrder());
                values.put(KEY_ITECATNAM, itemClass.getCategoryName());
                values.put(KEY_ITEMEAUNINAM, itemClass.getMeasureUnitName());
                values.put(KEY_ITESTA, itemClass.getState());
                values.put(KEY_ITESTO, itemClass.getStock());
                values.put(KEY_ITEQUA, itemClass.getQuantity());
                values.put(KEY_ITETOT, itemClass.getTotal());
                values.put(KEY_ITEPOS, itemClass.getPosition());

                db.insert(TABLE_APP_ITEM, null, values);
                db.close();
            }

            public boolean checkIfExists(String id){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_ITEM + " WHERE ai_id='" + id + "'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result > 0;
            }

            public void updateItem(int id, ItemClass itemClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put(KEY_ITEID, itemClass.getId());
                values.put(KEY_ITECATCOD, itemClass.getCategoryCode());
                values.put(KEY_ITECOD, itemClass.getCode());
                values.put(KEY_ITEDES, itemClass.getDescription());
                values.put(KEY_ITETAX, itemClass.getTax());
                values.put(KEY_ITEMEAUNICOD, itemClass.getMeasureUnitCode());
                values.put(KEY_ITELITLBS, itemClass.getLitersLbs());
                values.put(KEY_ITEORD, itemClass.getOrder());
                values.put(KEY_ITECATNAM, itemClass.getCategoryName());
                values.put(KEY_ITEMEAUNINAM, itemClass.getMeasureUnitName());
                values.put(KEY_ITESTA, itemClass.getState());
                values.put(KEY_ITESTO, itemClass.getStock());
                values.put(KEY_ITEQUA, itemClass.getQuantity());
                values.put(KEY_ITETOT, itemClass.getTotal());
                values.put(KEY_ITEPOS, itemClass.getPosition());

                db.update(TABLE_APP_ITEM, values, KEY_ITEID + " = ?",new String[] { String.valueOf(id)});
                db.close();
            }

            public ArrayList<ItemClass> getItemData() {
                ArrayList<ItemClass> itemClassList = new ArrayList<ItemClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_ITEM;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        ItemClass itemClass = new ItemClass();
                        itemClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_ITEID)));
                        itemClass.setCategoryCode(cursor.getString(cursor.getColumnIndex(KEY_ITECATCOD)));
                        itemClass.setCode(cursor.getString(cursor.getColumnIndex(KEY_ITECOD)));
                        itemClass.setDescription(cursor.getString(cursor.getColumnIndex(KEY_ITEDES)));
                        itemClass.setTax(cursor.getString(cursor.getColumnIndex(KEY_ITETAX)));
                        itemClass.setMeasureUnitCode(cursor.getString(cursor.getColumnIndex(KEY_ITEMEAUNICOD)));
                        itemClass.setLitersLbs(cursor.getString(cursor.getColumnIndex(KEY_ITELITLBS)));
                        itemClass.setOrder(cursor.getString(cursor.getColumnIndex(KEY_ITEORD)));
                        itemClass.setCategoryName(cursor.getString(cursor.getColumnIndex(KEY_ITECATNAM)));
                        itemClass.setMeasureUnitName(cursor.getString(cursor.getColumnIndex(KEY_ITEMEAUNINAM)));
                        itemClass.setState(cursor.getString(cursor.getColumnIndex(KEY_ITESTA)));
                        itemClass.setStock(cursor.getString(cursor.getColumnIndex(KEY_ITESTO)));
                        itemClass.setQuantity(cursor.getString(cursor.getColumnIndex(KEY_ITEQUA)));
                        itemClass.setTotal(cursor.getString(cursor.getColumnIndex(KEY_ITETOT)));
                        itemClass.setPosition(cursor.getString(cursor.getColumnIndex(KEY_ITEPOS)));
                        itemClassList.add(itemClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return itemClassList;
            }

            public String getWeightByItemCode(String itemCode){
                String result = "";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT ai_lit_lbs FROM " + TABLE_APP_ITEM + " WHERE ai_cod='" + itemCode + "'",null);
                mCount.moveToFirst();
                result= mCount.getString(0);
                mCount.close();
                db.close();
                return result;
            }

            public String getTaxByItemCode(String itemCode){
                String result = "";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT ai_tax FROM " + TABLE_APP_ITEM + " WHERE ai_cod='" + itemCode + "'",null);
                mCount.moveToFirst();
                result= mCount.getString(0);
                mCount.close();
                db.close();
                return result;
            }

            public ItemClass getItem(String codeItem) {
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_ITEM, new String[] {KEY_ITEID, KEY_ITECATCOD, KEY_ITECOD, KEY_ITEDES,
                                KEY_ITETAX, KEY_ITEMEAUNICOD, KEY_ITELITLBS, KEY_ITEORD, KEY_ITECATNAM, KEY_ITEMEAUNINAM,
                                KEY_ITESTA, KEY_ITESTO, KEY_ITEQUA, KEY_ITETOT, KEY_ITEPOS}, KEY_ITECOD + "=?",
                        new String[] { String.valueOf(codeItem) }, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }

                ItemClass itemClass = new ItemClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7),
                        cursor.getString(8), cursor.getString(9), cursor.getString(10),cursor.getString(11), cursor.getString(12),
                        cursor.getString(13), cursor.getString(14));
                db.close();
                return itemClass;
            }

            public String getData(int nField, String sName) {
                String _data = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_ITEM, new String[] {KEY_ITEID, KEY_ITECATCOD, KEY_ITECOD, KEY_ITEDES,
                                KEY_ITETAX, KEY_ITEMEAUNICOD,
                                KEY_ITELITLBS, KEY_ITEORD, KEY_ITECATNAM, KEY_ITEMEAUNINAM, KEY_ITESTA, KEY_ITESTO,
                                KEY_ITEQUA, KEY_ITETOT, KEY_ITEPOS}, KEY_ITEID + "=?",
                        new String[] {sName}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _data = cursor.getString(nField);
                db.close();
                return _data;
            }


        }

        public class AppPriceListSql{
            public AppPriceListSql() {
            }
            public void deletePriceList(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_PRICE_LIST,null,null);
                db.close();
            }

            public void addPriceList(PriceListClass priceListClass) {

                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_PRILISID, priceListClass.getId());
                values.put(KEY_PRILISCOD, priceListClass.getCode());
                values.put(KEY_PRILISITECOD, priceListClass.getItemCode());
                values.put(KEY_PRILISPRI, priceListClass.getPrice());
                values.put(KEY_PRILISITEDES, priceListClass.getItemDescription());
                values.put(KEY_PRILISPRILISDES, priceListClass.getPriceListDescription());
                values.put(KEY_PRILISSTA, priceListClass.getState());
                db.insert(TABLE_APP_PRICE_LIST, null, values);
                db.close();
            }

            public boolean checkIfExists(String id){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_PRICE_LIST + " WHERE apl_id='" + id + "'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result > 0;
            }

            public void updatePriceList(int id, PriceListClass priceListClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put(KEY_PRILISID, priceListClass.getId());
                values.put(KEY_PRILISCOD, priceListClass.getCode());
                values.put(KEY_PRILISITECOD, priceListClass.getItemCode());
                values.put(KEY_PRILISPRI, priceListClass.getPrice());
                values.put(KEY_PRILISITEDES, priceListClass.getItemDescription());
                values.put(KEY_PRILISPRILISDES, priceListClass.getPriceListDescription());
                values.put(KEY_PRILISSTA, priceListClass.getState());

                db.update(TABLE_APP_PRICE_LIST, values, KEY_PRILISID + " = ?",new String[] { String.valueOf(id)});
                db.close();
            }

            public String getPriceByItemCode(String itemCode, String priceListCode){
                String result = "";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT apl_pri FROM " + TABLE_APP_PRICE_LIST + " WHERE apl_cod='" + priceListCode + "' AND apl_ite_cod='" + itemCode+ "'",null);
                mCount.moveToFirst();
                result= mCount.getString(0);
                mCount.close();
                db.close();
                return result;
            }

            public ArrayList<ItemClass> getItemsByPriceList(String priceListCode) {
                ArrayList<ItemClass> itemClassList= new ArrayList<ItemClass>();

                String selectQuery = "SELECT app_item.ai_id, " +
                        "app_item.ai_cat_cod, app_item.ai_cod, app_item.ai_des, app_item.ai_tax, " +
                        "app_item.ai_mea_uni_cod, app_item.ai_lit_lbs, app_item.ai_ord, app_item.ai_sta, " +
                        "app_item.ai_cat_nam, app_item.ai_mea_uni_nam, app_item.ai_sto, app_item.ai_qua, app_item.ai_tot, app_item.ai_pos " +
                        "FROM app_price_list, app_item, app_category " +
                        "WHERE app_price_list.apl_cod='" + priceListCode +"' " +
                        "AND app_price_list.apl_ite_cod=app_item.ai_cod " +
                        "AND app_item.ai_cat_cod=app_category.aca_cod " +
                        "ORDER BY app_category.aca_ord, app_item.ai_ord";

                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    int a = 0;
                    do {
                        ItemClass itemClass = new ItemClass();
                        itemClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_ITEID)));
                        itemClass.setCategoryCode(cursor.getString(cursor.getColumnIndex(KEY_ITECATCOD)));
                        itemClass.setCode(cursor.getString(cursor.getColumnIndex(KEY_ITECOD)));
                        itemClass.setDescription(cursor.getString(cursor.getColumnIndex(KEY_ITEDES)));
                        itemClass.setTax(cursor.getString(cursor.getColumnIndex(KEY_ITETAX)));
                        itemClass.setMeasureUnitCode(cursor.getString(cursor.getColumnIndex(KEY_ITEMEAUNICOD)));
                        itemClass.setLitersLbs(cursor.getString(cursor.getColumnIndex(KEY_ITELITLBS)));
                        itemClass.setOrder(cursor.getString(cursor.getColumnIndex(KEY_ITEORD)));
                        itemClass.setCategoryName(cursor.getString(cursor.getColumnIndex(KEY_ITECATNAM)));
                        itemClass.setMeasureUnitName(cursor.getString(cursor.getColumnIndex(KEY_ITEMEAUNINAM)));
                        itemClass.setState(cursor.getString(cursor.getColumnIndex(KEY_ITESTA)));
                        itemClass.setQuantity(cursor.getString(cursor.getColumnIndex(KEY_ITEQUA)));
                        itemClass.setTotal(cursor.getString(cursor.getColumnIndex(KEY_ITETOT)));
                        itemClass.setPosition(String.valueOf(a));
                        itemClass.setStock(cursor.getString(cursor.getColumnIndex(KEY_ITESTO)));

                        itemClassList.add(itemClass);
                        a++;
                    } while (cursor.moveToNext());
                }
                cursor.close();
                db.close();
                return itemClassList;
            }


            public ArrayList<PriceListClass> getPriceListData() {
                ArrayList<PriceListClass> priceListClassList = new ArrayList<PriceListClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_PRICE_LIST;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        PriceListClass priceListClass = new PriceListClass();
                        priceListClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_PRILISID)));
                        priceListClass.setCode(cursor.getString(cursor.getColumnIndex(KEY_PRILISCOD)));
                        priceListClass.setItemCode(cursor.getString(cursor.getColumnIndex(KEY_PRILISITECOD)));
                        priceListClass.setPrice(cursor.getString(cursor.getColumnIndex(KEY_PRILISPRI)));
                        priceListClass.setItemDescription(cursor.getString(cursor.getColumnIndex(KEY_PRILISITEDES)));
                        priceListClass.setPriceListDescription(cursor.getString(cursor.getColumnIndex(KEY_PRILISPRILISDES)));
                        priceListClass.setState(cursor.getString(cursor.getColumnIndex(KEY_PRILISSTA)));

                        priceListClassList.add(priceListClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return priceListClassList;
            }


            public PriceListClass getPriceList(String nId) {
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_PRICE_LIST, new String[] {KEY_PRILISID, KEY_PRILISCOD, KEY_PRILISITECOD, KEY_PRILISPRI,
                                KEY_PRILISITEDES, KEY_PRILISPRILISDES, KEY_PRILISSTA}, KEY_PRILISID + "=?",
                        new String[] { String.valueOf(nId) }, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }

                PriceListClass priceListClass = new PriceListClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
                db.close();
                return priceListClass;
            }

            public String getData(int nField, String sName) {
                String _data = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_PRICE_LIST, new String[] {KEY_PRILISID, KEY_PRILISCOD, KEY_PRILISITECOD, KEY_PRILISPRI,
                                KEY_PRILISITEDES, KEY_PRILISPRILISDES, KEY_PRILISSTA}, KEY_PRILISID + "=?",
                        new String[] {sName}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _data = cursor.getString(nField);
                db.close();
                return _data;
            }
        }

        public class AppDiscountListSql{
            public AppDiscountListSql() {
            }
            public void deleteDiscountList(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_DISCOUNT_LIST,null,null);
                db.close();
            }

            public void addDiscountList(DiscountListClass discountListClass) {

                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_DISLISID, discountListClass.getId());
                values.put(KEY_DISLISCOD, discountListClass.getCode());
                values.put(KEY_DISLISCODCUS, discountListClass.getCodeCustomer());
                values.put(KEY_DISLISCODCAT, discountListClass.getCodeCategory());
                values.put(KEY_DISLISCODITE, discountListClass.getCodeItem());
                values.put(KEY_DISLISDISPER, discountListClass.getDiscountPercentage());
                values.put(KEY_DISLISDATINI, discountListClass.getDateInitial());
                values.put(KEY_DISLISDATFIN, discountListClass.getDateFinish());
                values.put(KEY_DISLISSTA, discountListClass.getState());
                db.insert(TABLE_APP_DISCOUNT_LIST, null, values);
                db.close();
            }

            public boolean checkIfExists(String id){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_DISCOUNT_LIST + " WHERE adl_id='" + id + "'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result > 0;
            }

            public void updateDiscountList(int id, DiscountListClass discountListClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put(KEY_DISLISID, discountListClass.getId());
                values.put(KEY_DISLISCOD, discountListClass.getCode());
                values.put(KEY_DISLISCODCUS, discountListClass.getCodeCustomer());
                values.put(KEY_DISLISCODCAT, discountListClass.getCodeCategory());
                values.put(KEY_DISLISCODITE, discountListClass.getCodeItem());
                values.put(KEY_DISLISDISPER, discountListClass.getDiscountPercentage());
                values.put(KEY_DISLISDATINI, discountListClass.getDateInitial());
                values.put(KEY_DISLISDATFIN, discountListClass.getDateFinish());
                values.put(KEY_DISLISSTA, discountListClass.getState());

                db.update(TABLE_APP_DISCOUNT_LIST, values, KEY_DISLISID + " = ?",new String[] { String.valueOf(id)});
                db.close();
            }



            public ArrayList<DiscountListClass> getDiscountListData() {
                ArrayList<DiscountListClass> discountListClassList = new ArrayList<DiscountListClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_PRICE_LIST;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        DiscountListClass discountListClass = new DiscountListClass();
                        discountListClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_DISLISID)));
                        discountListClass.setCode(cursor.getString(cursor.getColumnIndex(KEY_DISLISCOD)));
                        discountListClass.setCodeCustomer(cursor.getString(cursor.getColumnIndex(KEY_DISLISCODCUS)));
                        discountListClass.setCodeCategory(cursor.getString(cursor.getColumnIndex(KEY_DISLISCODCAT)));
                        discountListClass.setCodeItem(cursor.getString(cursor.getColumnIndex(KEY_DISLISCODITE)));
                        discountListClass.setDiscountPercentage(cursor.getString(cursor.getColumnIndex(KEY_DISLISDISPER)));
                        discountListClass.setDateInitial(cursor.getString(cursor.getColumnIndex(KEY_DISLISDATINI)));
                        discountListClass.setDateFinish(cursor.getString(cursor.getColumnIndex(KEY_DISLISDATFIN)));
                        discountListClass.setState(cursor.getString(cursor.getColumnIndex(KEY_DISLISSTA)));

                        discountListClassList.add(discountListClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return discountListClassList;
            }

            public DiscountListClass getDiscountList(String nId) {
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_DISCOUNT_LIST, new String[] {KEY_DISLISID, KEY_DISLISCOD, KEY_DISLISCODCUS, KEY_DISLISCODCAT,
                                KEY_DISLISCODITE, KEY_DISLISDISPER, KEY_DISLISDATINI, KEY_DISLISDATFIN, KEY_DISLISSTA}, KEY_DISLISID + "=?",
                        new String[] { String.valueOf(nId) }, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }

                DiscountListClass discountListClass = new DiscountListClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                        cursor.getString(7), cursor.getString(8));
                db.close();
                return discountListClass;
            }

            public String getData(int nField, String sName) {
                String _data = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_DISCOUNT_LIST, new String[] {KEY_DISLISID, KEY_DISLISCOD, KEY_DISLISCODCUS, KEY_DISLISCODCAT,
                                KEY_DISLISCODITE, KEY_DISLISDISPER, KEY_DISLISDATINI, KEY_DISLISDATFIN, KEY_DISLISSTA}, KEY_DISLISID + "=?",
                        new String[] {sName}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _data = cursor.getString(nField);
                db.close();
                return _data;
            }
        }

        public class AppPromoListSql{
            public AppPromoListSql() {}

            public void deletePromoList(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_PROMO_LIST,null,null);
                db.close();
            }

            public void addPromoList(PromoListClass promoListClass) {

                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_PROLISID, promoListClass.getId());
                values.put(KEY_PROLISCOD, promoListClass.getCode());
                values.put(KEY_PROLISCODITEONE, promoListClass.getCodeItemOne());
                values.put(KEY_PROLISQUAONE, promoListClass.getQuantityOne());
                values.put(KEY_PROLISUNIMEAONE, promoListClass.getUnitMeasureOne());
                values.put(KEY_PROLISCODITETWO, promoListClass.getCodeItemTwo());
                values.put(KEY_PROLISQUATWO, promoListClass.getQuantityTwo());
                values.put(KEY_PROLISUNIMEATWO, promoListClass.getUnitMeasureTwo());
                values.put(KEY_PROLISNAMITEONE, promoListClass.getNameItemOne());
                values.put(KEY_PROLISNAMITETWO, promoListClass.getNameItemTwo());
                values.put(KEY_PROLISNAMUNIONE, promoListClass.getNameUnitOne());
                values.put(KEY_PROLISNAMUNITWO, promoListClass.getNameUnitTwo());
                values.put(KEY_PROLISSTA, promoListClass.getState());
                values.put(KEY_PROLISCATONE, promoListClass.getCategoryOne());
                values.put(KEY_PROLISCATTWO, promoListClass.getCategoryTwo());

                db.insert(TABLE_APP_PROMO_LIST, null, values);
                db.close();
            }

            public boolean checkIfExists(String id){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_PROMO_LIST + " WHERE aprl_id='" + id + "'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result > 0;
            }

            public void updatePromoList(int id, PromoListClass promoListClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put(KEY_PROLISID, promoListClass.getId());
                values.put(KEY_PROLISCOD, promoListClass.getCode());
                values.put(KEY_PROLISCODITEONE, promoListClass.getCodeItemOne());
                values.put(KEY_PROLISQUAONE, promoListClass.getQuantityOne());
                values.put(KEY_PROLISUNIMEAONE, promoListClass.getUnitMeasureOne());
                values.put(KEY_PROLISCODITETWO, promoListClass.getCodeItemTwo());
                values.put(KEY_PROLISQUATWO, promoListClass.getQuantityTwo());
                values.put(KEY_PROLISUNIMEATWO, promoListClass.getUnitMeasureTwo());
                values.put(KEY_PROLISNAMITEONE, promoListClass.getNameItemOne());
                values.put(KEY_PROLISNAMITETWO, promoListClass.getNameItemTwo());
                values.put(KEY_PROLISNAMUNIONE, promoListClass.getNameUnitOne());
                values.put(KEY_PROLISNAMUNITWO, promoListClass.getNameUnitTwo());
                values.put(KEY_PROLISSTA, promoListClass.getState());
                values.put(KEY_PROLISCATONE, promoListClass.getCategoryOne());
                values.put(KEY_PROLISCATTWO, promoListClass.getCategoryTwo());

                db.update(TABLE_APP_PROMO_LIST, values, KEY_PROLISID + " = ?",new String[] { String.valueOf(id)});
                db.close();
            }

            public ArrayList<PromoListClass> getPromoListData() {
                ArrayList<PromoListClass> promoListClassList = new ArrayList<PromoListClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_PROMO_LIST;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        PromoListClass promoListClass = new PromoListClass();
                        promoListClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_PROLISID)));
                        promoListClass.setCode(cursor.getString(cursor.getColumnIndex(KEY_PROLISCOD)));
                        promoListClass.setCodeItemOne(cursor.getString(cursor.getColumnIndex(KEY_PROLISCODITEONE)));
                        promoListClass.setQuantityOne(cursor.getString(cursor.getColumnIndex(KEY_PROLISQUAONE)));
                        promoListClass.setUnitMeasureOne(cursor.getString(cursor.getColumnIndex(KEY_PROLISUNIMEAONE)));
                        promoListClass.setCodeItemTwo(cursor.getString(cursor.getColumnIndex(KEY_PROLISCODITETWO)));
                        promoListClass.setQuantityTwo(cursor.getString(cursor.getColumnIndex(KEY_PROLISQUATWO)));
                        promoListClass.setUnitMeasureTwo(cursor.getString(cursor.getColumnIndex(KEY_PROLISUNIMEATWO)));
                        promoListClass.setNameItemOne(cursor.getString(cursor.getColumnIndex(KEY_PROLISNAMITEONE)));
                        promoListClass.setNameItemTwo(cursor.getString(cursor.getColumnIndex(KEY_PROLISNAMITETWO)));
                        promoListClass.setNameUnitOne(cursor.getString(cursor.getColumnIndex(KEY_PROLISNAMUNIONE)));
                        promoListClass.setNameUnitTwo(cursor.getString(cursor.getColumnIndex(KEY_PROLISNAMUNITWO)));
                        promoListClass.setState(cursor.getString(cursor.getColumnIndex(KEY_PROLISSTA)));
                        promoListClass.setCategoryOne(cursor.getString(cursor.getColumnIndex(KEY_PROLISCATONE)));
                        promoListClass.setCategoryTwo(cursor.getString(cursor.getColumnIndex(KEY_PROLISCATTWO)));

                        promoListClassList.add(promoListClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return promoListClassList;
            }

            public ArrayList<SynOrderItemClass> getPromotion(String itemCode, int itemQuantity){

                int quantityPromoItems = 0;
                int quantityOne = 0;
                int quantityTwo = 0;

                ArrayList<SynOrderItemClass> promotionList = new ArrayList<SynOrderItemClass>();
                String selectQuery = "SELECT app_promo_list.aprl_id, app_promo_list.aprl_code, app_promo_list.aprl_cod_ite_one, " +
                        "app_promo_list.aprl_qua_one, app_promo_list.aprl_uni_mea_one, app_promo_list.aprl_cod_ite_two, " +
                        "app_promo_list.aprl_qua_two, app_promo_list.aprl_uni_mea_two, app_promo_list.aprl_nam_ite_one, " +
                        "app_promo_list.aprl_nam_ite_two, app_promo_list.aprl_nam_uni_one, app_promo_list.aprl_nam_uni_two, " +
                        "app_promo_list.aprl_sta, app_promo_list.aprl_cat_one, app_promo_list.aprl_cat_two " +
                        "FROM app_promo_list, app_item " +
                        "WHERE app_promo_list.aprl_cod_ite_one='" + itemCode + "'";

                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        quantityOne = Integer.parseInt(cursor.getString((cursor.getColumnIndex(KEY_PROLISQUAONE))).replace(".000", ""));
                        quantityTwo = Integer.parseInt(cursor.getString((cursor.getColumnIndex(KEY_PROLISQUATWO))).replace(".000", ""));
                        float reason = itemQuantity / quantityOne;  //Divido cuantos compro y cuantos me piden para realizar la promocion
                        int truncate  = (int) reason;             //Al resultado lo trunco a entero
                        quantityPromoItems = quantityTwo * truncate;             //Luego consigo la cantidad que le debo dar a mi cliente la cantidad del producto en promocion por tantas veces sea mi resultado

                        if (truncate>0){ //Obviamente solo entro aqui si me dio mayor a cero

                            SynOrderItemClass promotion = new SynOrderItemClass(
                                    0,
                                    "",
                                    "",
                                    cursor.getString((cursor.getColumnIndex(KEY_PROLISCATTWO))),
                                    cursor.getString((cursor.getColumnIndex(KEY_PROLISCODITETWO))),
                                    cursor.getString((cursor.getColumnIndex(KEY_PROLISNAMITETWO))) + " (P)",
                                    String.valueOf(quantityPromoItems),
                                    "0.00",             //Seteo todo normal como un objeto, pero lo chingo diciendole que el precio es cero
                                    cursor.getString(cursor.getColumnIndex(KEY_PROLISUNIMEATWO)),
                                    "0.00",                //Impuestos son cero tambien
                                    "0.00",             //Total iguales cero
                                    "",
                                    "",
                                    "0"); //Creo mi objeto normalito

                            promotionList.add(promotion);           //No se porque lo meto en un arraylist si lo podria deolvier todo tranqui

                        }
                    } while (cursor.moveToNext());
                }
                db.close();
                return promotionList;
            }

            public PromoListClass getPromoList(String nId) {
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_PROMO_LIST, new String[] {KEY_PROLISID, KEY_PROLISCOD, KEY_PROLISCODITEONE, KEY_PROLISCATONE, KEY_PROLISQUAONE,
                                KEY_PROLISUNIMEAONE, KEY_PROLISCODITETWO, KEY_PROLISCATTWO, KEY_PROLISQUATWO, KEY_PROLISUNIMEATWO, KEY_PROLISNAMITEONE,
                                KEY_PROLISNAMITETWO, KEY_PROLISNAMUNIONE, KEY_PROLISNAMUNITWO, KEY_PROLISSTA}, KEY_PROLISID + "=?",
                        new String[] { String.valueOf(nId) }, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }

                PromoListClass promoListClass= new PromoListClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                        cursor.getString(7), cursor.getString(8), cursor.getString(9),cursor.getString(10),
                        cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14));
                db.close();
                return promoListClass;
            }

            public String getData(int nField, String sName) {
                String _data = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_PROMO_LIST, new String[] {KEY_PROLISID, KEY_PROLISCOD, KEY_PROLISCODITEONE, KEY_PROLISCATONE, KEY_PROLISQUAONE,
                                KEY_PROLISUNIMEAONE, KEY_PROLISCODITETWO, KEY_PROLISCATTWO, KEY_PROLISQUATWO, KEY_PROLISUNIMEATWO, KEY_PROLISNAMITEONE,
                                KEY_PROLISNAMITETWO, KEY_PROLISNAMUNIONE, KEY_PROLISNAMUNITWO, KEY_PROLISSTA}, KEY_PROLISID + "=?",
                        new String[] {sName}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _data = cursor.getString(nField);
                db.close();
                return _data;
            }

        }

        public class AppVisitSql{
            public AppVisitSql() {}

            public void deleteVisit(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_VISIT,null,null);
                db.close();
            }

            public boolean checkIfExists(String id){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_VISIT + " WHERE av_id='" + id + "'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result > 0;
            }

            public void addVisit(VisitClass visitClass) {

                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_VISID, visitClass.getId());
                values.put(KEY_VISDES, visitClass.getDescription());
                values.put(KEY_VISOFFCOD, visitClass.getOfficeCode());
                values.put(KEY_VISOFFNAM, visitClass.getOfficeName());
                values.put(KEY_VISSTA, visitClass.getState());
                db.insert(TABLE_APP_VISIT, null, values);
                db.close();
            }

            public void updateVisit(int id, VisitClass visitClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_VISID, visitClass.getId());
                values.put(KEY_VISDES, visitClass.getDescription());
                values.put(KEY_VISOFFCOD, visitClass.getOfficeCode());
                values.put(KEY_VISOFFNAM, visitClass.getOfficeName());
                values.put(KEY_VISSTA, visitClass.getState());
                db.update(TABLE_APP_VISIT, values, KEY_VISID + " = ?",new String[] { String.valueOf(id)});
                db.close();
            }


            public ArrayList<VisitClass> getVisitData() {
                ArrayList<VisitClass> visitClassList = new ArrayList<VisitClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_VISIT;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        VisitClass visitClass = new VisitClass();
                        visitClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_VISID)));
                        visitClass.setDescription(cursor.getString(cursor.getColumnIndex(KEY_VISDES)));
                        visitClass.setOfficeCode(cursor.getString(cursor.getColumnIndex(KEY_VISOFFCOD)));
                        visitClass.setOfficeName(cursor.getString(cursor.getColumnIndex(KEY_VISOFFNAM)));
                        visitClass.setState(cursor.getString(cursor.getColumnIndex(KEY_VISSTA)));
                        visitClassList.add(visitClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return visitClassList;
            }


            public VisitClass getVisit(String nId) {
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_VISIT, new String[] {KEY_VISID, KEY_VISDES, KEY_VISOFFCOD, KEY_VISOFFNAM,
                                KEY_VISSTA}, KEY_VISID + "=?",
                        new String[] { String.valueOf(nId) }, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }

                VisitClass visitClass = new VisitClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4));
                db.close();
                return visitClass;
            }

            public String getData(int nField, String sName) {
                String _data = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_VISIT, new String[] {KEY_VISID, KEY_VISDES, KEY_VISOFFCOD, KEY_VISOFFNAM,
                                KEY_VISSTA}, KEY_VISID + "=?",
                        new String[] {sName}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _data = cursor.getString(nField);
                db.close();
                return _data;
            }

        }

        public class AppSynVisitSql {

            public AppSynVisitSql() {}

            public void deleteVisitApp(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_SYN_VISIT,null,null);
                db.close();
            }

            public void deleteVisitAppById(String id){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_SYN_VISIT, KEY_VISAPPID + " = ?", new String[] {id});
                db.close();
            }
            public boolean checkIfExists(String codeCustomer){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_SYN_VISIT + " WHERE ava_cod_cus='" + codeCustomer + "'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result > 0;
            }

            public int countPending(){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_SYN_VISIT + " WHERE ava_loa='0'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result;
            }

            public int countDone(){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_SYN_VISIT + " WHERE ava_loa='1'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result;
            }

            public void addVisitApp(SynVisitClass synVisitClass) {

                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                //values.put(KEY_VISAPPID, synVisitClass.getId());
                values.put(KEY_VISAPPIDUSE, synVisitClass.getIdUser());
                values.put(KEY_VISAPPCODCUS, synVisitClass.getCodeCustomer());
                values.put(KEY_VISAPPNAMCUS, synVisitClass.getNameCustomer());
                values.put(KEY_VISAPPCODVIS, synVisitClass.getCodeVisit());
                values.put(KEY_VISAPPNAMVIS, synVisitClass.getNameVisit());
                values.put(KEY_VISAPPLATCUS, synVisitClass.getLatitudeCustomer());
                values.put(KEY_VISAPPLONCUS, synVisitClass.getLongitudeCustomer());
                values.put(KEY_VISAPPLAT, synVisitClass.getLatitude());
                values.put(KEY_VISAPPLON, synVisitClass.getLongitude());
                values.put(KEY_VISAPPZON, synVisitClass.getZone());
                values.put(KEY_VISAPPRAT, synVisitClass.getRate());
                values.put(KEY_VISAPPDATHOU, synVisitClass.getDateHour());
                values.put(KEY_VISAPPNUMCUSPEN, synVisitClass.getNumberCustomerPending());
                values.put(KEY_VISAPPLOA, synVisitClass.getLoad());

                db.insert(TABLE_APP_SYN_VISIT, null, values);
                db.close();
            }


            public ArrayList<SynVisitClass> getVisitAppData() {
                ArrayList<SynVisitClass> synVisitClassList = new ArrayList<SynVisitClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_SYN_VISIT;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        SynVisitClass synVisitClass = new SynVisitClass();
                        synVisitClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_VISAPPID)));
                        synVisitClass.setIdUser(cursor.getString(cursor.getColumnIndex(KEY_VISAPPIDUSE)));
                        synVisitClass.setCodeCustomer(cursor.getString(cursor.getColumnIndex(KEY_VISAPPCODCUS)));
                        synVisitClass.setNameCustomer(cursor.getString(cursor.getColumnIndex(KEY_VISAPPNAMCUS)));
                        synVisitClass.setCodeVisit(cursor.getString(cursor.getColumnIndex(KEY_VISAPPCODVIS)));
                        synVisitClass.setNameVisit(cursor.getString(cursor.getColumnIndex(KEY_VISAPPNAMVIS)));
                        synVisitClass.setLatitudeCustomer(cursor.getString(cursor.getColumnIndex(KEY_VISAPPLATCUS)));
                        synVisitClass.setLongitudeCustomer(cursor.getString(cursor.getColumnIndex(KEY_VISAPPLONCUS)));
                        synVisitClass.setLatitude(cursor.getString(cursor.getColumnIndex(KEY_VISAPPLAT)));
                        synVisitClass.setLongitude(cursor.getString(cursor.getColumnIndex(KEY_VISAPPLON)));
                        synVisitClass.setZone(cursor.getString(cursor.getColumnIndex(KEY_VISAPPZON)));
                        synVisitClass.setRate(cursor.getString(cursor.getColumnIndex(KEY_VISAPPRAT)));
                        synVisitClass.setDateHour(cursor.getString(cursor.getColumnIndex(KEY_VISAPPDATHOU)));
                        synVisitClass.setNumberCustomerPending(cursor.getString(cursor.getColumnIndex(KEY_VISAPPNUMCUSPEN)));
                        synVisitClass.setLoad(cursor.getString(cursor.getColumnIndex(KEY_VISAPPLOA)));

                        synVisitClassList.add(synVisitClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return synVisitClassList;
            }

            public ArrayList<SynVisitClass> getVisitAppPendingData() {
                ArrayList<SynVisitClass> synVisitClassList = new ArrayList<SynVisitClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_SYN_VISIT + " WHERE ava_loa='0'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        SynVisitClass synVisitClass = new SynVisitClass();
                        synVisitClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_VISAPPID)));
                        synVisitClass.setIdUser(cursor.getString(cursor.getColumnIndex(KEY_VISAPPIDUSE)));
                        synVisitClass.setCodeCustomer(cursor.getString(cursor.getColumnIndex(KEY_VISAPPCODCUS)));
                        synVisitClass.setNameCustomer(cursor.getString(cursor.getColumnIndex(KEY_VISAPPNAMCUS)));
                        synVisitClass.setCodeVisit(cursor.getString(cursor.getColumnIndex(KEY_VISAPPCODVIS)));
                        synVisitClass.setNameVisit(cursor.getString(cursor.getColumnIndex(KEY_VISAPPNAMVIS)));
                        synVisitClass.setLatitudeCustomer(cursor.getString(cursor.getColumnIndex(KEY_VISAPPLATCUS)));
                        synVisitClass.setLongitudeCustomer(cursor.getString(cursor.getColumnIndex(KEY_VISAPPLONCUS)));
                        synVisitClass.setLatitude(cursor.getString(cursor.getColumnIndex(KEY_VISAPPLAT)));
                        synVisitClass.setLongitude(cursor.getString(cursor.getColumnIndex(KEY_VISAPPLON)));
                        synVisitClass.setZone(cursor.getString(cursor.getColumnIndex(KEY_VISAPPZON)));
                        synVisitClass.setRate(cursor.getString(cursor.getColumnIndex(KEY_VISAPPRAT)));
                        synVisitClass.setDateHour(cursor.getString(cursor.getColumnIndex(KEY_VISAPPDATHOU)));
                        synVisitClass.setNumberCustomerPending(cursor.getString(cursor.getColumnIndex(KEY_VISAPPNUMCUSPEN)));
                        synVisitClass.setLoad(cursor.getString(cursor.getColumnIndex(KEY_VISAPPLOA)));

                        synVisitClassList.add(synVisitClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return synVisitClassList;
            }

            public void updateLoadSynVisit(String id, String load) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_VISAPPLOA, load);
                db.update(TABLE_APP_SYN_VISIT, values, KEY_VISAPPID + " = ?",new String[] { String.valueOf(id)});
                db.close();
            }

            public SynVisitClass getVisitApp(String nId) {
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_SYN_VISIT, new String[] {KEY_VISAPPID, KEY_VISAPPIDUSE, KEY_VISAPPCODCUS, KEY_VISAPPNAMCUS,
                                KEY_VISAPPCODVIS, KEY_VISAPPNAMVIS, KEY_VISAPPLATCUS, KEY_VISAPPLONCUS, KEY_VISAPPLAT, KEY_VISAPPLON, KEY_VISAPPZON,
                                KEY_VISAPPRAT, KEY_VISAPPDATHOU, KEY_VISAPPNUMCUSPEN, KEY_VISAPPLOA }, KEY_VISAPPID + "=?",
                        new String[] { String.valueOf(nId) }, null, null, null, null);


                if (cursor != null){
                    cursor.moveToFirst();
                }

                SynVisitClass synVisitClass = new SynVisitClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7),
                        cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12),
                        cursor.getString(13), cursor.getString(14));
                db.close();
                return synVisitClass;
            }

            public String getData(int nField, String sName) {
                String _data = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_SYN_VISIT, new String[] {KEY_VISAPPID, KEY_VISAPPIDUSE, KEY_VISAPPCODCUS, KEY_VISAPPNAMCUS,
                                KEY_VISAPPCODVIS, KEY_VISAPPNAMVIS, KEY_VISAPPLATCUS, KEY_VISAPPLONCUS, KEY_VISAPPLAT, KEY_VISAPPLON, KEY_VISAPPZON,
                                KEY_VISAPPRAT, KEY_VISAPPDATHOU, KEY_VISAPPNUMCUSPEN, KEY_VISAPPLOA }, KEY_VISAPPID + "=?",
                        new String[] {sName}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _data = cursor.getString(nField);
                db.close();
                return _data;
            }

        }

        public class AppAssetControlSql{

            public AppAssetControlSql() {}

            public void deleteAssetControl(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_ASSET_CONTROL,null,null);
                db.close();
            }

            public void deleteAssetControlById(String id){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_ASSET_CONTROL,KEY_ASSCONID + " = ?",new String[] { String.valueOf(id)});
                db.close();
            }

            public void addAssetControl(AssetControlClass assetControlClass) {

                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();

                //values.put(KEY_ASSCONID, assetControlClass.getId());
                values.put(KEY_ASSCONCOD, assetControlClass.getCode());
                values.put(KEY_ASSCONASS, assetControlClass.getAssignment());
                values.put(KEY_ASSCONDATASS, assetControlClass.getDateAssign());
                values.put(KEY_ASSCONCODBRA, assetControlClass.getCodeBranch());
                values.put(KEY_ASSCONCODOFF, assetControlClass.getCodeOffice());
                values.put(KEY_ASSCONCODROU, assetControlClass.getCodeRoute());
                values.put(KEY_ASSCONCODCUS, assetControlClass.getCodeCustomer());
                values.put(KEY_ASSCONIDUSEAPP, assetControlClass.getIdUserApp());
                values.put(KEY_ASSCONNAMOFF, assetControlClass.getNameOffice());
                values.put(KEY_ASSCONNAMBRA, assetControlClass.getNameBranch());
                values.put(KEY_ASSCONNAMROU, assetControlClass.getNameRoute());
                values.put(KEY_ASSCONNAMCUS, assetControlClass.getNameCustomer());
                values.put(KEY_ASSCONSTA, assetControlClass.getState());

                values.put(KEY_ASSCONDATTAS, assetControlClass.getDateTask());
                values.put(KEY_ASSCONHOUTAS, assetControlClass.getHourTask());
                values.put(KEY_ASSCONDATENTAS, assetControlClass.getDateEndTask());
                values.put(KEY_ASSCONHOUENDTAS, assetControlClass.getHourEndTask());
                values.put(KEY_ASSCONORI, assetControlClass.getOrigin());
                values.put(KEY_ASSCONEQU, assetControlClass.getEquipment());
                values.put(KEY_ASSCONTYP, assetControlClass.getType());
                values.put(KEY_ASSCONTAS, assetControlClass.getTask());
                values.put(KEY_ASSCONANS, assetControlClass.getAnswer());
                values.put(KEY_ASSCONCODCUSTAS, assetControlClass.getCodeCustomerTask());
                values.put(KEY_ASSCONSTATAS, assetControlClass.getStateTask());
                values.put(KEY_ASSCONIDTAS, assetControlClass.getIdTask());
                values.put(KEY_ASSCONLOA, assetControlClass.getLoad());


                db.insert(TABLE_APP_ASSET_CONTROL, null, values);
                db.close();
            }

            public boolean checkIfExists(String code){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_ASSET_CONTROL + " WHERE asc_cod='" + code + "' AND asc_ori='W'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result > 0;
            }

            public void updateAssetControl(String id, AssetControlClass assetControlClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();

                //values.put(KEY_ASSCONID, assetControlClass.getId());
                values.put(KEY_ASSCONCOD, assetControlClass.getCode());
                values.put(KEY_ASSCONASS, assetControlClass.getAssignment());
                values.put(KEY_ASSCONDATASS, assetControlClass.getDateAssign());
                values.put(KEY_ASSCONCODBRA, assetControlClass.getCodeBranch());
                values.put(KEY_ASSCONCODOFF, assetControlClass.getCodeOffice());
                values.put(KEY_ASSCONCODROU, assetControlClass.getCodeRoute());
                values.put(KEY_ASSCONCODCUS, assetControlClass.getCodeCustomer());
                values.put(KEY_ASSCONIDUSEAPP, assetControlClass.getIdUserApp());
                values.put(KEY_ASSCONNAMOFF, assetControlClass.getNameOffice());
                values.put(KEY_ASSCONNAMBRA, assetControlClass.getNameBranch());
                values.put(KEY_ASSCONNAMROU, assetControlClass.getNameRoute());
                values.put(KEY_ASSCONNAMCUS, assetControlClass.getNameCustomer());
                values.put(KEY_ASSCONSTA, assetControlClass.getState());

                values.put(KEY_ASSCONDATTAS, assetControlClass.getDateTask());
                values.put(KEY_ASSCONHOUTAS, assetControlClass.getHourTask());
                values.put(KEY_ASSCONDATENTAS, assetControlClass.getDateEndTask());
                values.put(KEY_ASSCONHOUENDTAS, assetControlClass.getHourEndTask());
                values.put(KEY_ASSCONORI, assetControlClass.getOrigin());
                values.put(KEY_ASSCONEQU, assetControlClass.getEquipment());
                values.put(KEY_ASSCONTYP, assetControlClass.getType());
                values.put(KEY_ASSCONTAS, assetControlClass.getTask());
                values.put(KEY_ASSCONANS, assetControlClass.getAnswer());
                values.put(KEY_ASSCONCODCUSTAS, assetControlClass.getCodeCustomerTask());
                values.put(KEY_ASSCONSTATAS, assetControlClass.getStateTask());
                values.put(KEY_ASSCONIDTAS, assetControlClass.getIdTask());
                values.put(KEY_ASSCONLOA, assetControlClass.getLoad());


                db.update(TABLE_APP_ASSET_CONTROL, values, KEY_ASSCONID + " = ?",new String[] { String.valueOf(id)});
                db.close();
            }

            public void updateAnswerTaskById(int id, String answer, String stateTask) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_ASSCONANS, answer);
                values.put(KEY_ASSCONSTATAS, stateTask);
                db.update(TABLE_APP_ASSET_CONTROL, values, KEY_ASSCONID + " = ?",new String[] { String.valueOf(id)});
                db.close();
            }
            public void updateLoad(String id) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_ASSCONLOA, "1");
                db.update(TABLE_APP_ASSET_CONTROL, values, KEY_ASSCONID + " = ?",new String[] { String.valueOf(id)});
                db.close();
            }

            public ArrayList<AssetControlClass> getAssetControlData() {
                ArrayList<AssetControlClass> assetControlClassList = new ArrayList<AssetControlClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_ASSET_CONTROL + " WHERE asc_ori='W'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        AssetControlClass assetControlClass = new AssetControlClass();
                        assetControlClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_ASSCONID)));
                        assetControlClass.setCode(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCOD)));
                        assetControlClass.setAssignment(cursor.getString(cursor.getColumnIndex(KEY_ASSCONASS)));
                        assetControlClass.setDateAssign(cursor.getString(cursor.getColumnIndex(KEY_ASSCONDATASS)));
                        assetControlClass.setCodeBranch(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODBRA)));
                        assetControlClass.setCodeOffice(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODOFF)));
                        assetControlClass.setCodeRoute(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODROU)));
                        assetControlClass.setCodeCustomer(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODCUS)));
                        assetControlClass.setIdUserApp(cursor.getString(cursor.getColumnIndex(KEY_ASSCONIDUSEAPP)));
                        assetControlClass.setNameOffice(cursor.getString(cursor.getColumnIndex(KEY_ASSCONNAMOFF)));
                        assetControlClass.setNameBranch(cursor.getString(cursor.getColumnIndex(KEY_ASSCONNAMBRA)));
                        assetControlClass.setNameRoute(cursor.getString(cursor.getColumnIndex(KEY_ASSCONNAMROU)));
                        assetControlClass.setNameCustomer(cursor.getString(cursor.getColumnIndex(KEY_ASSCONNAMCUS)));
                        assetControlClass.setState(cursor.getString(cursor.getColumnIndex(KEY_ASSCONSTA)));

                        assetControlClass.setDateTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONDATTAS)));
                        assetControlClass.setHourTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONHOUTAS)));
                        assetControlClass.setDateEndTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONDATENTAS)));
                        assetControlClass.setHourEndTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONHOUENDTAS)));
                        assetControlClass.setOrigin(cursor.getString(cursor.getColumnIndex(KEY_ASSCONORI)));
                        assetControlClass.setEquipment(cursor.getString(cursor.getColumnIndex(KEY_ASSCONEQU)));
                        assetControlClass.setType(cursor.getString(cursor.getColumnIndex(KEY_ASSCONTYP)));
                        assetControlClass.setTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONTAS)));
                        assetControlClass.setAnswer(cursor.getString(cursor.getColumnIndex(KEY_ASSCONANS)));
                        assetControlClass.setCodeCustomerTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODCUSTAS)));
                        assetControlClass.setStateTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONSTATAS)));
                        assetControlClass.setIdTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONIDTAS)));
                        assetControlClass.setLoad(cursor.getString(cursor.getColumnIndex(KEY_ASSCONLOA)));


                        assetControlClassList.add(assetControlClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return assetControlClassList;
            }

            public ArrayList<AssetControlClass> getAssetControlDataByCustomer(String codeCustomer) {
                ArrayList<AssetControlClass> assetControlClassList = new ArrayList<AssetControlClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_ASSET_CONTROL + " WHERE asc_cod_cus='" + codeCustomer+ "'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        AssetControlClass assetControlClass = new AssetControlClass();
                        assetControlClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_ASSCONID)));
                        assetControlClass.setCode(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCOD)));
                        assetControlClass.setAssignment(cursor.getString(cursor.getColumnIndex(KEY_ASSCONASS)));
                        assetControlClass.setDateAssign(cursor.getString(cursor.getColumnIndex(KEY_ASSCONDATASS)));
                        assetControlClass.setCodeBranch(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODBRA)));
                        assetControlClass.setCodeOffice(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODOFF)));
                        assetControlClass.setCodeRoute(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODROU)));
                        assetControlClass.setCodeCustomer(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODCUS)));
                        assetControlClass.setIdUserApp(cursor.getString(cursor.getColumnIndex(KEY_ASSCONIDUSEAPP)));
                        assetControlClass.setNameOffice(cursor.getString(cursor.getColumnIndex(KEY_ASSCONNAMOFF)));
                        assetControlClass.setNameBranch(cursor.getString(cursor.getColumnIndex(KEY_ASSCONNAMBRA)));
                        assetControlClass.setNameRoute(cursor.getString(cursor.getColumnIndex(KEY_ASSCONNAMROU)));
                        assetControlClass.setNameCustomer(cursor.getString(cursor.getColumnIndex(KEY_ASSCONNAMCUS)));
                        assetControlClass.setState(cursor.getString(cursor.getColumnIndex(KEY_ASSCONSTA)));

                        assetControlClass.setDateTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONDATTAS)));
                        assetControlClass.setHourTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONHOUTAS)));
                        assetControlClass.setDateEndTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONDATENTAS)));
                        assetControlClass.setHourEndTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONHOUENDTAS)));
                        assetControlClass.setOrigin(cursor.getString(cursor.getColumnIndex(KEY_ASSCONORI)));
                        assetControlClass.setEquipment(cursor.getString(cursor.getColumnIndex(KEY_ASSCONEQU)));
                        assetControlClass.setType(cursor.getString(cursor.getColumnIndex(KEY_ASSCONTYP)));
                        assetControlClass.setTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONTAS)));
                        assetControlClass.setAnswer(cursor.getString(cursor.getColumnIndex(KEY_ASSCONANS)));
                        assetControlClass.setCodeCustomerTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODCUSTAS)));
                        assetControlClass.setStateTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONSTATAS)));
                        assetControlClass.setIdTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONIDTAS)));
                        assetControlClass.setLoad(cursor.getString(cursor.getColumnIndex(KEY_ASSCONLOA)));


                        assetControlClassList.add(assetControlClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return assetControlClassList;
            }

            public int countAssetControlApp(){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_ASSET_CONTROL + " WHERE asc_ori='M'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result;
            }

            public ArrayList<AssetControlClass> getAssetControlAppInstance() {
                ArrayList<AssetControlClass> assetControlClassList = new ArrayList<AssetControlClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_ASSET_CONTROL + " WHERE asc_ori='M' AND asc_typ='Instance'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        AssetControlClass assetControlClass = new AssetControlClass();
                        assetControlClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_ASSCONID)));
                        assetControlClass.setCode(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCOD)));
                        assetControlClass.setAssignment(cursor.getString(cursor.getColumnIndex(KEY_ASSCONASS)));
                        assetControlClass.setDateAssign(cursor.getString(cursor.getColumnIndex(KEY_ASSCONDATASS)));
                        assetControlClass.setCodeBranch(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODBRA)));
                        assetControlClass.setCodeOffice(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODOFF)));
                        assetControlClass.setCodeRoute(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODROU)));
                        assetControlClass.setCodeCustomer(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODCUS)));
                        assetControlClass.setIdUserApp(cursor.getString(cursor.getColumnIndex(KEY_ASSCONIDUSEAPP)));
                        assetControlClass.setNameOffice(cursor.getString(cursor.getColumnIndex(KEY_ASSCONNAMOFF)));
                        assetControlClass.setNameBranch(cursor.getString(cursor.getColumnIndex(KEY_ASSCONNAMBRA)));
                        assetControlClass.setNameRoute(cursor.getString(cursor.getColumnIndex(KEY_ASSCONNAMROU)));
                        assetControlClass.setNameCustomer(cursor.getString(cursor.getColumnIndex(KEY_ASSCONNAMCUS)));
                        assetControlClass.setState(cursor.getString(cursor.getColumnIndex(KEY_ASSCONSTA)));

                        assetControlClass.setDateTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONDATTAS)));
                        assetControlClass.setHourTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONHOUTAS)));
                        assetControlClass.setDateEndTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONDATENTAS)));
                        assetControlClass.setHourEndTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONHOUENDTAS)));
                        assetControlClass.setOrigin(cursor.getString(cursor.getColumnIndex(KEY_ASSCONORI)));
                        assetControlClass.setEquipment(cursor.getString(cursor.getColumnIndex(KEY_ASSCONEQU)));
                        assetControlClass.setType(cursor.getString(cursor.getColumnIndex(KEY_ASSCONTYP)));
                        assetControlClass.setTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONTAS)));
                        assetControlClass.setAnswer(cursor.getString(cursor.getColumnIndex(KEY_ASSCONANS)));
                        assetControlClass.setCodeCustomerTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODCUSTAS)));
                        assetControlClass.setStateTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONSTATAS)));
                        assetControlClass.setIdTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONIDTAS)));
                        assetControlClass.setLoad(cursor.getString(cursor.getColumnIndex(KEY_ASSCONLOA)));


                        assetControlClassList.add(assetControlClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return assetControlClassList;
            }

            public ArrayList<AssetControlClass> getAssetControlAppObservation() {
                ArrayList<AssetControlClass> assetControlClassList = new ArrayList<AssetControlClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_ASSET_CONTROL + " WHERE asc_ori='M' AND asc_typ='Observation'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        AssetControlClass assetControlClass = new AssetControlClass();
                        assetControlClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_ASSCONID)));
                        assetControlClass.setCode(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCOD)));
                        assetControlClass.setAssignment(cursor.getString(cursor.getColumnIndex(KEY_ASSCONASS)));
                        assetControlClass.setDateAssign(cursor.getString(cursor.getColumnIndex(KEY_ASSCONDATASS)));
                        assetControlClass.setCodeBranch(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODBRA)));
                        assetControlClass.setCodeOffice(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODOFF)));
                        assetControlClass.setCodeRoute(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODROU)));
                        assetControlClass.setCodeCustomer(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODCUS)));
                        assetControlClass.setIdUserApp(cursor.getString(cursor.getColumnIndex(KEY_ASSCONIDUSEAPP)));
                        assetControlClass.setNameOffice(cursor.getString(cursor.getColumnIndex(KEY_ASSCONNAMOFF)));
                        assetControlClass.setNameBranch(cursor.getString(cursor.getColumnIndex(KEY_ASSCONNAMBRA)));
                        assetControlClass.setNameRoute(cursor.getString(cursor.getColumnIndex(KEY_ASSCONNAMROU)));
                        assetControlClass.setNameCustomer(cursor.getString(cursor.getColumnIndex(KEY_ASSCONNAMCUS)));
                        assetControlClass.setState(cursor.getString(cursor.getColumnIndex(KEY_ASSCONSTA)));

                        assetControlClass.setDateTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONDATTAS)));
                        assetControlClass.setHourTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONHOUTAS)));
                        assetControlClass.setDateEndTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONDATENTAS)));
                        assetControlClass.setHourEndTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONHOUENDTAS)));
                        assetControlClass.setOrigin(cursor.getString(cursor.getColumnIndex(KEY_ASSCONORI)));
                        assetControlClass.setEquipment(cursor.getString(cursor.getColumnIndex(KEY_ASSCONEQU)));
                        assetControlClass.setType(cursor.getString(cursor.getColumnIndex(KEY_ASSCONTYP)));
                        assetControlClass.setTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONTAS)));
                        assetControlClass.setAnswer(cursor.getString(cursor.getColumnIndex(KEY_ASSCONANS)));
                        assetControlClass.setCodeCustomerTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODCUSTAS)));
                        assetControlClass.setStateTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONSTATAS)));
                        assetControlClass.setIdTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONIDTAS)));
                        assetControlClass.setLoad(cursor.getString(cursor.getColumnIndex(KEY_ASSCONLOA)));


                        assetControlClassList.add(assetControlClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return assetControlClassList;
            }

            public ArrayList<AssetControlClass> getAssetControlApp() {
                ArrayList<AssetControlClass> assetControlClassList = new ArrayList<AssetControlClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_ASSET_CONTROL + " WHERE asc_ori='M'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        AssetControlClass assetControlClass = new AssetControlClass();
                        assetControlClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_ASSCONID)));
                        assetControlClass.setCode(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCOD)));
                        assetControlClass.setAssignment(cursor.getString(cursor.getColumnIndex(KEY_ASSCONASS)));
                        assetControlClass.setDateAssign(cursor.getString(cursor.getColumnIndex(KEY_ASSCONDATASS)));
                        assetControlClass.setCodeBranch(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODBRA)));
                        assetControlClass.setCodeOffice(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODOFF)));
                        assetControlClass.setCodeRoute(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODROU)));
                        assetControlClass.setCodeCustomer(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODCUS)));
                        assetControlClass.setIdUserApp(cursor.getString(cursor.getColumnIndex(KEY_ASSCONIDUSEAPP)));
                        assetControlClass.setNameOffice(cursor.getString(cursor.getColumnIndex(KEY_ASSCONNAMOFF)));
                        assetControlClass.setNameBranch(cursor.getString(cursor.getColumnIndex(KEY_ASSCONNAMBRA)));
                        assetControlClass.setNameRoute(cursor.getString(cursor.getColumnIndex(KEY_ASSCONNAMROU)));
                        assetControlClass.setNameCustomer(cursor.getString(cursor.getColumnIndex(KEY_ASSCONNAMCUS)));
                        assetControlClass.setState(cursor.getString(cursor.getColumnIndex(KEY_ASSCONSTA)));

                        assetControlClass.setDateTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONDATTAS)));
                        assetControlClass.setHourTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONHOUTAS)));
                        assetControlClass.setDateEndTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONDATENTAS)));
                        assetControlClass.setHourEndTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONHOUENDTAS)));
                        assetControlClass.setOrigin(cursor.getString(cursor.getColumnIndex(KEY_ASSCONORI)));
                        assetControlClass.setEquipment(cursor.getString(cursor.getColumnIndex(KEY_ASSCONEQU)));
                        assetControlClass.setType(cursor.getString(cursor.getColumnIndex(KEY_ASSCONTYP)));
                        assetControlClass.setTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONTAS)));
                        assetControlClass.setAnswer(cursor.getString(cursor.getColumnIndex(KEY_ASSCONANS)));
                        assetControlClass.setCodeCustomerTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONCODCUSTAS)));
                        assetControlClass.setStateTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONSTATAS)));
                        assetControlClass.setIdTask(cursor.getString(cursor.getColumnIndex(KEY_ASSCONIDTAS)));
                        assetControlClass.setLoad(cursor.getString(cursor.getColumnIndex(KEY_ASSCONLOA)));


                        assetControlClassList.add(assetControlClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return assetControlClassList;
            }

            public AssetControlClass getAssetControl(String nId) {
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_ASSET_CONTROL, new String[] {KEY_ASSCONID, KEY_ASSCONCOD, KEY_ASSCONASS, KEY_ASSCONDATASS,
                                KEY_ASSCONCODBRA, KEY_ASSCONCODOFF, KEY_ASSCONCODROU, KEY_ASSCONCODCUS, KEY_ASSCONIDUSEAPP, KEY_ASSCONNAMOFF,
                                KEY_ASSCONNAMBRA, KEY_ASSCONNAMROU, KEY_ASSCONNAMCUS, KEY_ASSCONSTA, KEY_ASSCONDATTAS, KEY_ASSCONHOUTAS, KEY_ASSCONDATENTAS,
                                KEY_ASSCONHOUENDTAS, KEY_ASSCONORI, KEY_ASSCONEQU, KEY_ASSCONTYP, KEY_ASSCONTAS, KEY_ASSCONANS,
                                KEY_ASSCONCODCUSTAS, KEY_ASSCONSTATAS, KEY_ASSCONIDTAS, KEY_ASSCONLOA}, KEY_ASSCONID + "=?",
                        new String[] { String.valueOf(nId) }, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }

                AssetControlClass assetControlClass= new AssetControlClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                        cursor.getString(7), cursor.getString(8), cursor.getString(9),cursor.getString(10),
                        cursor.getString(11), cursor.getString(12), cursor.getString(13) ,cursor.getString(14),
                        cursor.getString(15), cursor.getString(16), cursor.getString(17), cursor.getString(18),
                        cursor.getString(19), cursor.getString(20), cursor.getString(21), cursor.getString(22),
                        cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26));
                db.close();
                return assetControlClass;
            }

            public String getData(int nField, String sName) {
                String _data = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_ASSET_CONTROL, new String[] {KEY_ASSCONID, KEY_ASSCONCOD, KEY_ASSCONASS, KEY_ASSCONDATASS,
                                KEY_ASSCONCODBRA, KEY_ASSCONCODOFF, KEY_ASSCONCODROU, KEY_ASSCONCODCUS, KEY_ASSCONIDUSEAPP, KEY_ASSCONNAMOFF,
                                KEY_ASSCONNAMBRA, KEY_ASSCONNAMROU, KEY_ASSCONNAMCUS, KEY_ASSCONSTA, KEY_ASSCONDATTAS, KEY_ASSCONHOUTAS, KEY_ASSCONDATENTAS,
                                KEY_ASSCONHOUENDTAS, KEY_ASSCONORI, KEY_ASSCONEQU, KEY_ASSCONTYP, KEY_ASSCONTAS, KEY_ASSCONANS,
                                KEY_ASSCONCODCUSTAS, KEY_ASSCONSTATAS, KEY_ASSCONIDTAS, KEY_ASSCONLOA}, KEY_ASSCONID + "=?",
                        new String[] {sName}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _data = cursor.getString(nField);
                db.close();
                return _data;
            }

        }

        public class AppStockSql{
            public AppStockSql() {}


            public void deleteStock(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_STOCK,null,null);
                db.close();
            }

            public void addStock(StockClass stockClass) {

                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                //values.put(KEY_STOID, stockClass.getId());
                values.put(KEY_STOCODROU, stockClass.getCodeRoute());
                values.put(KEY_STOCODITE, stockClass.getCodeItem());
                values.put(KEY_STOQUA, stockClass.getQuantity());
                db.insert(TABLE_APP_STOCK, null, values);
                db.close();
            }


            public boolean checkIfExists(String codeItem){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_STOCK + " WHERE ast_cod_ite='" + codeItem + "'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result > 0;
            }

            public void updateStock(String codeItem, StockClass stockClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();

                //values.put(KEY_STOID, stockClass.getId());
                values.put(KEY_STOCODROU, stockClass.getCodeRoute());
                values.put(KEY_STOCODITE, stockClass.getCodeItem());
                values.put(KEY_STOQUA, stockClass.getQuantity());

                db.update(TABLE_APP_STOCK, values, KEY_STOCODITE + " = ?",new String[] { String.valueOf(codeItem)});
                db.close();
            }

            public void updateStockByCodeItem(String codeItem, String n_quantity) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_STOQUA, n_quantity);
                db.update(TABLE_APP_STOCK, values, KEY_STOCODITE + " = ?",new String[] { String.valueOf(codeItem)});
                db.close();
            }


            public ArrayList<StockClass> getStockData() {
                ArrayList<StockClass> stockList = new ArrayList<StockClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_STOCK;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        StockClass stockClass= new StockClass();
                        stockClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_STOID)));
                        stockClass.setCodeRoute(cursor.getString(cursor.getColumnIndex(KEY_STOCODROU)));
                        stockClass.setCodeItem(cursor.getString(cursor.getColumnIndex(KEY_STOCODITE)));
                        stockClass.setQuantity(cursor.getString(cursor.getColumnIndex(KEY_STOQUA)));

                        stockList.add(stockClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return stockList;
            }

            public StockClass getStock(String codeItem) {
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_STOCK, new String[] {KEY_STOID, KEY_STOCODROU, KEY_STOCODITE, KEY_STOQUA},
                        KEY_STOCODITE + "=?",
                        new String[] { String.valueOf(codeItem) }, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }

                StockClass stockClass = new StockClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3));
                db.close();
                return stockClass;
            }

            public String getStockLeft(String codeItem){
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_STOCK, new String[] {KEY_STOID, KEY_STOCODROU, KEY_STOCODITE, KEY_STOQUA},
                        KEY_STOCODITE + "=?",
                        new String[] { String.valueOf(codeItem) }, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }

                StockClass stockClass = new StockClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3));
                db.close();
                return stockClass.getQuantity();
            }

            public String getData(int nField, String codeItem) {
                String _data = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_STOCK, new String[] {KEY_STOID, KEY_STOCODROU, KEY_STOCODITE, KEY_STOQUA},
                        KEY_STOCODITE + "=?",
                        new String[] {codeItem}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _data = cursor.getString(nField);
                db.close();
                return _data;
            }


        }

        public class AppTypeDocumentSql{
            public AppTypeDocumentSql() {}

            public void deleteTypeDocument(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_TYPE_DOCUMENT,null,null);
                db.close();
            }

            public void addTypeDocument(TypeDocumentClass typeDocumentClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_TYPDOCID, typeDocumentClass.getId());
                values.put(KEY_TYPDOCCOD, typeDocumentClass.getCode());
                values.put(KEY_TYPDOCDES, typeDocumentClass.getDescription());
                values.put(KEY_TYPDOCSTA, typeDocumentClass.getState());
                db.insert(TABLE_APP_TYPE_DOCUMENT, null, values);
                db.close();
            }

            public boolean checkIfExists(String code){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_TYPE_DOCUMENT + " WHERE atd_cod='" + code + "'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result > 0;
            }

            public void updateTypeDocument(String code, TypeDocumentClass typeDocumentClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_TYPDOCID, typeDocumentClass.getId());
                values.put(KEY_TYPDOCCOD, typeDocumentClass.getCode());
                values.put(KEY_TYPDOCDES, typeDocumentClass.getDescription());
                values.put(KEY_TYPDOCSTA, typeDocumentClass.getState());
                db.update(TABLE_APP_TYPE_DOCUMENT, values, KEY_TYPDOCCOD + " = ?",new String[] { String.valueOf(code)});
                db.close();
            }


            public ArrayList<TypeDocumentClass> getTypeDocumentData() {
                ArrayList<TypeDocumentClass> typeDocumentList = new ArrayList<TypeDocumentClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_TYPE_DOCUMENT;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        TypeDocumentClass typeDocumentClass= new TypeDocumentClass();
                        typeDocumentClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_TYPDOCID)));
                        typeDocumentClass.setCode(cursor.getString(cursor.getColumnIndex(KEY_TYPDOCCOD)));
                        typeDocumentClass.setDescription(cursor.getString(cursor.getColumnIndex(KEY_TYPDOCDES)));
                        typeDocumentClass.setState(cursor.getString(cursor.getColumnIndex(KEY_TYPDOCSTA)));

                        typeDocumentList.add(typeDocumentClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return typeDocumentList;
            }

            public TypeDocumentClass getTypeDocument(String code) {
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_TYPE_DOCUMENT, new String[] {KEY_TYPDOCID, KEY_TYPDOCCOD, KEY_TYPDOCDES, KEY_TYPDOCSTA},
                        KEY_TYPDOCCOD + "=?",
                        new String[] { String.valueOf(code) }, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                TypeDocumentClass typeDocumentClass = new TypeDocumentClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                db.close();
                return typeDocumentClass;
            }

            public String getData(int nField, String code) {
                String _data = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_TYPE_DOCUMENT, new String[] {KEY_TYPDOCID, KEY_TYPDOCCOD, KEY_TYPDOCDES, KEY_TYPDOCSTA},
                        KEY_TYPDOCCOD + "=?",
                        new String[] {code}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _data = cursor.getString(nField);
                db.close();
                return _data;
            }
        }

        public class AppDocumentSql{
            public AppDocumentSql() {}

            public void deleteDocument(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_DOCUMENT,null,null);
                db.close();
            }

            public void addDocument(DocumentClass documentClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                //values.put(KEY_DOCID, documentClass.getId());
                values.put(KEY_DOCDOCTYP, documentClass.getDocumentType());
                values.put(KEY_DOCROU, documentClass.getRoute());
                values.put(KEY_DOCDOCNAM, documentClass.getDocumentName());
                values.put(KEY_DOCDOCSER, documentClass.getDocumentSerial());
                values.put(KEY_DOCLOWLIM, documentClass.getLowLimit());
                values.put(KEY_DOCTOPLIM, documentClass.getTopLimit());
                values.put(KEY_DOCACTLIM, documentClass.getActualLimit());
                values.put(KEY_DOCDAT, documentClass.getDate());
                values.put(KEY_DOCEMALEG, documentClass.getEmaleg());
                values.put(KEY_DOCDECNUM, documentClass.getDecnum());
                values.put(KEY_DOCSTA, documentClass.getState());
                values.put(KEY_DOCLASNUM, documentClass.getLastNumber());

                db.insert(TABLE_APP_DOCUMENT, null, values);
                db.close();
            }

            public boolean checkIfExists(String type){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_DOCUMENT + " WHERE adoc_doc_typ='" + type + "'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result > 0;
            }

            public void updateDocument(String type, DocumentClass documentClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                //values.put(KEY_DOCID, documentClass.getId());
                values.put(KEY_DOCDOCTYP, documentClass.getDocumentType());
                values.put(KEY_DOCROU, documentClass.getRoute());
                values.put(KEY_DOCDOCNAM, documentClass.getDocumentName());
                values.put(KEY_DOCDOCSER, documentClass.getDocumentSerial());
                values.put(KEY_DOCLOWLIM, documentClass.getLowLimit());
                values.put(KEY_DOCTOPLIM, documentClass.getTopLimit());
                values.put(KEY_DOCACTLIM, documentClass.getActualLimit());
                values.put(KEY_DOCDAT, documentClass.getDate());
                values.put(KEY_DOCEMALEG, documentClass.getEmaleg());
                values.put(KEY_DOCDECNUM, documentClass.getDecnum());
                values.put(KEY_DOCSTA, documentClass.getState());
                values.put(KEY_DOCLASNUM, documentClass.getLastNumber());
                db.update(TABLE_APP_DOCUMENT, values, KEY_DOCDOCTYP + " = ?",new String[] { String.valueOf(type)});
                db.close();
            }


            public ArrayList<DocumentClass> getDocumentData() {
                ArrayList<DocumentClass> documentList = new ArrayList<DocumentClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_DOCUMENT;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        DocumentClass documentClass= new DocumentClass();
                        documentClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_DOCID)));
                        documentClass.setDocumentType(cursor.getString(cursor.getColumnIndex(KEY_DOCDOCTYP)));
                        documentClass.setRoute(cursor.getString(cursor.getColumnIndex(KEY_DOCROU)));
                        documentClass.setDocumentName(cursor.getString(cursor.getColumnIndex(KEY_DOCDOCNAM)));
                        documentClass.setDocumentSerial(cursor.getString(cursor.getColumnIndex(KEY_DOCDOCSER)));
                        documentClass.setLowLimit(cursor.getString(cursor.getColumnIndex(KEY_DOCLOWLIM)));
                        documentClass.setTopLimit(cursor.getString(cursor.getColumnIndex(KEY_DOCTOPLIM)));
                        documentClass.setActualLimit(cursor.getString(cursor.getColumnIndex(KEY_DOCACTLIM)));
                        documentClass.setDate(cursor.getString(cursor.getColumnIndex(KEY_DOCDAT)));
                        documentClass.setEmaleg(cursor.getString(cursor.getColumnIndex(KEY_DOCEMALEG)));
                        documentClass.setDecnum(cursor.getString(cursor.getColumnIndex(KEY_DOCDECNUM)));
                        documentClass.setState(cursor.getString(cursor.getColumnIndex(KEY_DOCSTA)));
                        documentClass.setLastNumber(cursor.getString(cursor.getColumnIndex(KEY_DOCLASNUM)));

                        documentList.add(documentClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return documentList;
            }

            public DocumentClass getDocument(String type) {
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_DOCUMENT, new String[] {KEY_DOCID, KEY_DOCDOCTYP, KEY_DOCROU, KEY_DOCDOCNAM,
                                KEY_DOCDOCSER, KEY_DOCLOWLIM, KEY_DOCTOPLIM , KEY_DOCACTLIM, KEY_DOCDAT, KEY_DOCEMALEG,
                                KEY_DOCDECNUM, KEY_DOCSTA, KEY_DOCLASNUM},
                        KEY_DOCDOCTYP + "=?",
                        new String[] { String.valueOf(type)}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                DocumentClass documentClass = new DocumentClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5),
                        cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9),
                        cursor.getString(10), cursor.getString(11), cursor.getString(12));

                db.close();
                return documentClass;
            }

            public String getData(int nField, String code) {
                String _data = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_DOCUMENT, new String[] {KEY_DOCID, KEY_DOCDOCTYP, KEY_DOCROU, KEY_DOCDOCNAM,
                                KEY_DOCDOCSER, KEY_DOCLOWLIM, KEY_DOCTOPLIM , KEY_DOCACTLIM, KEY_DOCDAT, KEY_DOCEMALEG,
                                KEY_DOCDECNUM, KEY_DOCSTA, KEY_DOCLASNUM},
                        KEY_DOCID + "=?",
                        new String[] {code}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _data = cursor.getString(nField);
                db.close();
                return _data;
            }


        }

        public class AppSynOrderSql{
            public AppSynOrderSql() {}

            public void deleteSynOrder(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_SYN_ORDER,null,null);
                db.close();
            }

            public void addSynOrder(SynOrderClass synOrderClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                //values.put(KEY_SYNORDID, synOrderClass.getId());
                values.put(KEY_SYNORDTYP, synOrderClass.getType());
                values.put(KEY_SYNORDCODCUS, synOrderClass.getCodeCustomer());
                values.put(KEY_SYNORDNAMCUS, synOrderClass.getNameCustomer());
                values.put(KEY_SYNORDNUMDOC, synOrderClass.getNumberDocument());
                values.put(KEY_SYNORDNUM, synOrderClass.getNumeration());
                values.put(KEY_SYNORDCODROU, synOrderClass.getCodeRoute());
                values.put(KEY_SYNORDIDUSEAPP, synOrderClass.getIdUserApp());
                values.put(KEY_SYNORDDATHOU, synOrderClass.getDateHour());
                values.put(KEY_SYNORDRUC, synOrderClass.getRuc());
                values.put(KEY_SYNORDSTANUBFAC, synOrderClass.getStateNubeFact());
                values.put(KEY_SYNORDOPEEXO, synOrderClass.getOperationExonerated());
                values.put(KEY_SYNORDOPETAX, synOrderClass.getOperationTaxed());
                values.put(KEY_SYNORDIGV, synOrderClass.getIgv());
                values.put(KEY_SYNORDTOTPAY, synOrderClass.getTotalPay());
                values.put(KEY_SYNORDSTACHA, synOrderClass.getStateCharge());
                values.put(KEY_SYNORDLOA, synOrderClass.getLoad());

                db.insert(TABLE_APP_SYN_ORDER, null, values);
                db.close();
            }

            public int countDocuments(String type){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_SYN_ORDER + " WHERE aso_typ='" + type +"'" ,null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result;
            }
            public int countPendingDocuments(String type){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_SYN_ORDER + " WHERE aso_typ='" + type +"' AND aso_loa='0'" ,null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result;
            }
            public int countDoneDocuments(String type){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_SYN_ORDER + " WHERE aso_typ='" + type +"' AND aso_loa='1'" ,null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result;
            }
            public String getLastOrderNumeration(String type) {
                ArrayList<SynOrderClass> synOrderList = new ArrayList<SynOrderClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_SYN_ORDER + " WHERE aso_typ='" +  type +"'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if(cursor.moveToLast()){
                    return cursor.getString(cursor.getColumnIndex(KEY_SYNORDNUM));
                }
                db.close();
                return null;
            }


            public boolean checkIfExists(String id){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_SYN_ORDER + " WHERE aso_id='" + id + "'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result > 0;
            }

            public void updateOrderLoad(String docNum) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_SYNORDLOA, "1");
                db.update(TABLE_APP_SYN_ORDER, values, KEY_SYNORDNUMDOC + " = ?",new String[] { String.valueOf(docNum)});
                db.close();
            }

            public ArrayList<SynOrderClass> getSynOrderData(String type) {
                ArrayList<SynOrderClass> synOrderList = new ArrayList<SynOrderClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_SYN_ORDER + " WHERE aso_typ='" + type + "'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        SynOrderClass synOrderClass= new SynOrderClass();
                        synOrderClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_SYNORDID)));
                        synOrderClass.setType(cursor.getString(cursor.getColumnIndex(KEY_SYNORDTYP)));
                        synOrderClass.setCodeCustomer(cursor.getString(cursor.getColumnIndex(KEY_SYNORDCODCUS)));
                        synOrderClass.setNameCustomer(cursor.getString(cursor.getColumnIndex(KEY_SYNORDNAMCUS)));
                        synOrderClass.setNumberDocument(cursor.getString(cursor.getColumnIndex(KEY_SYNORDNUMDOC)));
                        synOrderClass.setNumeration(cursor.getString(cursor.getColumnIndex(KEY_SYNORDNUM)));
                        synOrderClass.setCodeRoute(cursor.getString(cursor.getColumnIndex(KEY_SYNORDCODROU)));
                        synOrderClass.setIdUserApp(cursor.getString(cursor.getColumnIndex(KEY_SYNORDIDUSEAPP)));
                        synOrderClass.setDateHour(cursor.getString(cursor.getColumnIndex(KEY_SYNORDDATHOU)));
                        synOrderClass.setRuc(cursor.getString(cursor.getColumnIndex(KEY_SYNORDRUC)));
                        synOrderClass.setStateNubeFact(cursor.getString(cursor.getColumnIndex(KEY_SYNORDSTANUBFAC)));
                        synOrderClass.setOperationExonerated(cursor.getString(cursor.getColumnIndex(KEY_SYNORDOPEEXO)));
                        synOrderClass.setOperationTaxed(cursor.getString(cursor.getColumnIndex(KEY_SYNORDOPETAX)));
                        synOrderClass.setIgv(cursor.getString(cursor.getColumnIndex(KEY_SYNORDIGV)));
                        synOrderClass.setTotalPay(cursor.getString(cursor.getColumnIndex(KEY_SYNORDTOTPAY)));
                        synOrderClass.setStateCharge(cursor.getString(cursor.getColumnIndex(KEY_SYNORDSTACHA)));
                        synOrderClass.setLoad(cursor.getString(cursor.getColumnIndex(KEY_SYNORDLOA)));

                        synOrderList.add(synOrderClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return synOrderList;
            }

            public ArrayList<SynOrderClass> getPendingSynOrderData(String type) {
                ArrayList<SynOrderClass> synOrderList = new ArrayList<SynOrderClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_SYN_ORDER + " WHERE " + KEY_SYNORDLOA + "='0' AND aso_typ='" + type + "'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        SynOrderClass synOrderClass= new SynOrderClass();
                        synOrderClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_SYNORDID)));
                        synOrderClass.setType(cursor.getString(cursor.getColumnIndex(KEY_SYNORDTYP)));
                        synOrderClass.setCodeCustomer(cursor.getString(cursor.getColumnIndex(KEY_SYNORDCODCUS)));
                        synOrderClass.setNameCustomer(cursor.getString(cursor.getColumnIndex(KEY_SYNORDNAMCUS)));
                        synOrderClass.setNumberDocument(cursor.getString(cursor.getColumnIndex(KEY_SYNORDNUMDOC)));
                        synOrderClass.setNumeration(cursor.getString(cursor.getColumnIndex(KEY_SYNORDNUM)));
                        synOrderClass.setCodeRoute(cursor.getString(cursor.getColumnIndex(KEY_SYNORDCODROU)));
                        synOrderClass.setIdUserApp(cursor.getString(cursor.getColumnIndex(KEY_SYNORDIDUSEAPP)));
                        synOrderClass.setDateHour(cursor.getString(cursor.getColumnIndex(KEY_SYNORDDATHOU)));
                        synOrderClass.setRuc(cursor.getString(cursor.getColumnIndex(KEY_SYNORDRUC)));
                        synOrderClass.setStateNubeFact(cursor.getString(cursor.getColumnIndex(KEY_SYNORDSTANUBFAC)));
                        synOrderClass.setOperationExonerated(cursor.getString(cursor.getColumnIndex(KEY_SYNORDOPEEXO)));
                        synOrderClass.setOperationTaxed(cursor.getString(cursor.getColumnIndex(KEY_SYNORDOPETAX)));
                        synOrderClass.setIgv(cursor.getString(cursor.getColumnIndex(KEY_SYNORDIGV)));
                        synOrderClass.setTotalPay(cursor.getString(cursor.getColumnIndex(KEY_SYNORDTOTPAY)));
                        synOrderClass.setStateCharge(cursor.getString(cursor.getColumnIndex(KEY_SYNORDSTACHA)));
                        synOrderClass.setLoad(cursor.getString(cursor.getColumnIndex(KEY_SYNORDLOA)));

                        synOrderList.add(synOrderClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return synOrderList;
            }

            public String getDateLastSynOrderData(String type) {
                ArrayList<SynOrderClass> synOrderList = new ArrayList<SynOrderClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_SYN_ORDER + " WHERE aso_typ='" + type + "'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if(cursor.moveToLast()){
                    return cursor.getString(cursor.getColumnIndex(KEY_SYNORDDATHOU));
                }
                db.close();
                return "";
            }

            public ArrayList<SynOrderClass> getSynOrderDataSales() {
                ArrayList<SynOrderClass> synOrderList = new ArrayList<SynOrderClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_SYN_ORDER + " WHERE aso_typ='FAC' OR aso_typ='BOL'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        SynOrderClass synOrderClass= new SynOrderClass();
                        synOrderClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_SYNORDID)));
                        synOrderClass.setType(cursor.getString(cursor.getColumnIndex(KEY_SYNORDTYP)));
                        synOrderClass.setCodeCustomer(cursor.getString(cursor.getColumnIndex(KEY_SYNORDCODCUS)));
                        synOrderClass.setNameCustomer(cursor.getString(cursor.getColumnIndex(KEY_SYNORDNAMCUS)));
                        synOrderClass.setNumberDocument(cursor.getString(cursor.getColumnIndex(KEY_SYNORDNUMDOC)));
                        synOrderClass.setNumeration(cursor.getString(cursor.getColumnIndex(KEY_SYNORDNUM)));
                        synOrderClass.setCodeRoute(cursor.getString(cursor.getColumnIndex(KEY_SYNORDCODROU)));
                        synOrderClass.setIdUserApp(cursor.getString(cursor.getColumnIndex(KEY_SYNORDIDUSEAPP)));
                        synOrderClass.setDateHour(cursor.getString(cursor.getColumnIndex(KEY_SYNORDDATHOU)));
                        synOrderClass.setRuc(cursor.getString(cursor.getColumnIndex(KEY_SYNORDRUC)));
                        synOrderClass.setStateNubeFact(cursor.getString(cursor.getColumnIndex(KEY_SYNORDSTANUBFAC)));
                        synOrderClass.setOperationExonerated(cursor.getString(cursor.getColumnIndex(KEY_SYNORDOPEEXO)));
                        synOrderClass.setOperationTaxed(cursor.getString(cursor.getColumnIndex(KEY_SYNORDOPETAX)));
                        synOrderClass.setIgv(cursor.getString(cursor.getColumnIndex(KEY_SYNORDIGV)));
                        synOrderClass.setTotalPay(cursor.getString(cursor.getColumnIndex(KEY_SYNORDTOTPAY)));
                        synOrderClass.setStateCharge(cursor.getString(cursor.getColumnIndex(KEY_SYNORDSTACHA)));
                        synOrderClass.setLoad(cursor.getString(cursor.getColumnIndex(KEY_SYNORDLOA)));

                        synOrderList.add(synOrderClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return synOrderList;
            }

            public SynOrderClass getSynOrder(String numberDocument) {
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_SYN_ORDER, new String[] {KEY_SYNORDID, KEY_SYNORDTYP, KEY_SYNORDCODCUS, KEY_SYNORDNAMCUS,
                                KEY_SYNORDNUMDOC, KEY_SYNORDNUM, KEY_SYNORDCODROU , KEY_SYNORDIDUSEAPP, KEY_SYNORDDATHOU, KEY_SYNORDRUC,
                                KEY_SYNORDSTANUBFAC, KEY_SYNORDOPEEXO, KEY_SYNORDOPETAX, KEY_SYNORDIGV,
                                KEY_SYNORDTOTPAY, KEY_SYNORDSTACHA, KEY_SYNORDLOA},
                        KEY_SYNORDNUMDOC + "=?",
                        new String[] { String.valueOf(numberDocument)}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                SynOrderClass synOrderClass = new SynOrderClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),cursor.getString(5),cursor.getString(6), cursor.getString(7),
                        cursor.getString(8), cursor.getString(9),cursor.getString(10),cursor.getString(11),
                        cursor.getString(12), cursor.getString(13),cursor.getString(14),cursor.getString(15),
                        cursor.getString(16));
                db.close();
                return synOrderClass;
            }

            public String getData(int nField, String code) {
                String _data = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_SYN_ORDER, new String[] {KEY_SYNORDID, KEY_SYNORDTYP, KEY_SYNORDCODCUS, KEY_SYNORDNAMCUS,
                                KEY_SYNORDNUMDOC, KEY_SYNORDNUM, KEY_SYNORDCODROU , KEY_SYNORDIDUSEAPP, KEY_SYNORDDATHOU, KEY_SYNORDRUC,
                                KEY_SYNORDSTANUBFAC, KEY_SYNORDOPEEXO, KEY_SYNORDOPETAX, KEY_SYNORDIGV,
                                KEY_SYNORDTOTPAY, KEY_SYNORDSTACHA, KEY_SYNORDLOA},
                        KEY_SYNORDID + "=?",
                        new String[] {code}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _data = cursor.getString(nField);
                db.close();
                return _data;
            }


        }

        public class AppSynOrderItemSql{
            public AppSynOrderItemSql() {}

            public void deleteSynOrder(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_SYN_ORDER_ITEM,null,null);
                db.close();
            }

            public void addSynOrderItem(SynOrderItemClass synOrderItemClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                //values.put(KEY_SYNORDITEID, synOrderItemClass.getId());
                values.put(KEY_SYNORDITENUMDOC, synOrderItemClass.getNumberDocument());
                values.put(KEY_SYNORDITEDATHOU, synOrderItemClass.getDateHour());
                values.put(KEY_SYNORDITECAT, synOrderItemClass.getCategory());
                values.put(KEY_SYNORDITECODITE, synOrderItemClass.getCodeItem());
                values.put(KEY_SYNORDITEDESITE, synOrderItemClass.getDescriptionItem());
                values.put(KEY_SYNORDITEQUA, synOrderItemClass.getQuantity());
                values.put(KEY_SYNORDITEPRI, synOrderItemClass.getPrice());
                values.put(KEY_SYNORDITEUNIMEA, synOrderItemClass.getUnitMeasure());
                values.put(KEY_SYNORDITEIGV, synOrderItemClass.getIgv());
                values.put(KEY_SYNORDITETOTPAY, synOrderItemClass.getTotalPay());
                values.put(KEY_SYNORDITESTA, synOrderItemClass.getState());
                values.put(KEY_SYNORDITESTACHA, synOrderItemClass.getStateCharge());
                values.put(KEY_SYNORDITELOA, synOrderItemClass.getLoad());

                db.insert(TABLE_APP_SYN_ORDER_ITEM, null, values);
                db.close();
            }

            public boolean checkIfExists(String id){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_SYN_ORDER_ITEM + " WHERE asoi_id='" + id + "'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result > 0;
            }

            public void updateOrderLoad(String docNum) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_SYNORDITELOA, "1");
                db.update(TABLE_APP_SYN_ORDER_ITEM, values, KEY_SYNORDITENUMDOC + " = ?",new String[] { String.valueOf(docNum)});
                db.close();
            }

            public void updateSynOrderItem(String id, SynOrderItemClass synOrderItemClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                //values.put(KEY_SYNORDITEID, synOrderItemClass.getId());
                values.put(KEY_SYNORDITENUMDOC, synOrderItemClass.getNumberDocument());
                values.put(KEY_SYNORDITEDATHOU, synOrderItemClass.getDateHour());
                values.put(KEY_SYNORDITECAT, synOrderItemClass.getCategory());
                values.put(KEY_SYNORDITECODITE, synOrderItemClass.getCodeItem());
                values.put(KEY_SYNORDITEDESITE, synOrderItemClass.getDescriptionItem());
                values.put(KEY_SYNORDITEQUA, synOrderItemClass.getQuantity());
                values.put(KEY_SYNORDITEPRI, synOrderItemClass.getPrice());
                values.put(KEY_SYNORDITEUNIMEA, synOrderItemClass.getUnitMeasure());
                values.put(KEY_SYNORDITEIGV, synOrderItemClass.getIgv());
                values.put(KEY_SYNORDITETOTPAY, synOrderItemClass.getTotalPay());
                values.put(KEY_SYNORDITESTA, synOrderItemClass.getState());
                values.put(KEY_SYNORDITESTACHA, synOrderItemClass.getStateCharge());
                values.put(KEY_SYNORDITELOA, synOrderItemClass.getLoad());
                db.update(TABLE_APP_SYN_ORDER_ITEM, values, KEY_SYNORDITEID + " = ?",new String[] { String.valueOf(id)});
                db.close();
            }


            public ArrayList<SynOrderItemClass> getSynOrderItemData(String numberDocument) {
                ArrayList<SynOrderItemClass> synOrderItemList = new ArrayList<SynOrderItemClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_SYN_ORDER_ITEM + " WHERE " + KEY_SYNORDITENUMDOC + "='" +numberDocument + "'" ;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        SynOrderItemClass synOrderItemClass= new SynOrderItemClass();
                        synOrderItemClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_SYNORDITEID)));
                        synOrderItemClass.setNumberDocument(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITENUMDOC)));
                        synOrderItemClass.setDateHour(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITEDATHOU)));
                        synOrderItemClass.setCategory(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITECAT)));
                        synOrderItemClass.setCodeItem(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITECODITE)));
                        synOrderItemClass.setDescriptionItem(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITEDESITE)));
                        synOrderItemClass.setQuantity(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITEQUA)));
                        synOrderItemClass.setPrice(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITEPRI)));
                        synOrderItemClass.setUnitMeasure(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITEUNIMEA)));
                        synOrderItemClass.setIgv(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITEIGV)));
                        synOrderItemClass.setTotalPay(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITETOTPAY)));
                        synOrderItemClass.setState(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITESTA)));
                        synOrderItemClass.setStateCharge(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITESTACHA)));
                        synOrderItemClass.setLoad(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITELOA)));

                        synOrderItemList.add(synOrderItemClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return synOrderItemList;
            }

            public ArrayList<SynOrderItemClass> getPendingSynOrderItemData(String numberDocument) {
                ArrayList<SynOrderItemClass> synOrderItemList = new ArrayList<SynOrderItemClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_SYN_ORDER_ITEM + " WHERE " + KEY_SYNORDITELOA + "='0' AND " + KEY_SYNORDITENUMDOC +"='" +  numberDocument + "'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        SynOrderItemClass synOrderItemClass= new SynOrderItemClass();
                        synOrderItemClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_SYNORDITEID)));
                        synOrderItemClass.setNumberDocument(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITENUMDOC)));
                        synOrderItemClass.setDateHour(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITEDATHOU)));
                        synOrderItemClass.setCategory(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITECAT)));
                        synOrderItemClass.setCodeItem(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITECODITE)));
                        synOrderItemClass.setDescriptionItem(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITEDESITE)));
                        synOrderItemClass.setQuantity(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITEQUA)));
                        synOrderItemClass.setPrice(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITEPRI)));
                        synOrderItemClass.setUnitMeasure(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITEUNIMEA)));
                        synOrderItemClass.setIgv(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITEIGV)));
                        synOrderItemClass.setTotalPay(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITETOTPAY)));
                        synOrderItemClass.setState(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITESTA)));
                        synOrderItemClass.setStateCharge(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITESTACHA)));
                        synOrderItemClass.setLoad(cursor.getString(cursor.getColumnIndex(KEY_SYNORDITELOA)));

                        synOrderItemList.add(synOrderItemClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return synOrderItemList;
            }

            public SynOrderItemClass getSynOrderItem(String documentNumber) {
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_SYN_ORDER_ITEM, new String[] {KEY_SYNORDITEID, KEY_SYNORDITENUMDOC, KEY_SYNORDITEDATHOU, KEY_SYNORDITECAT,
                                KEY_SYNORDITECODITE, KEY_SYNORDITEDESITE, KEY_SYNORDITEQUA , KEY_SYNORDITEPRI, KEY_SYNORDITEUNIMEA, KEY_SYNORDITEIGV,
                                KEY_SYNORDITETOTPAY, KEY_SYNORDITESTA, KEY_SYNORDITESTACHA, KEY_SYNORDITELOA},
                        KEY_SYNORDITENUMDOC + "=?",
                        new String[] { String.valueOf(documentNumber)}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                SynOrderItemClass synOrderItemClass = new SynOrderItemClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5),
                        cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9),
                        cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13));

                db.close();
                return synOrderItemClass;
            }

            public String getData(int nField, String code) {
                String _data = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_SYN_ORDER_ITEM, new String[] {KEY_SYNORDITEID, KEY_SYNORDITENUMDOC, KEY_SYNORDITEDATHOU, KEY_SYNORDITECAT,
                                KEY_SYNORDITECODITE, KEY_SYNORDITEDESITE, KEY_SYNORDITEQUA , KEY_SYNORDITEPRI, KEY_SYNORDITEUNIMEA, KEY_SYNORDITEIGV,
                                KEY_SYNORDITETOTPAY, KEY_SYNORDITESTA, KEY_SYNORDITESTACHA, KEY_SYNORDITELOA},
                        KEY_SYNORDITEID + "=?",
                        new String[] {code}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _data = cursor.getString(nField);
                db.close();
                return _data;
            }

        }

        public class AppSynBreakStockSql{
            public AppSynBreakStockSql() {}

            public void deleteSynBreakStock(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_SYN_BREAK_STOCK,null,null);
                db.close();
            }


            public void addSynBreakStock(SynBreakStockClass synBreakStockClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                //values.put(KEY_SYNBRESTOID, synBreakStockClass.getId());
                values.put(KEY_SYNBRESTOCOD, synBreakStockClass.getCode());
                values.put(KEY_SYNBRESTOCODROU, synBreakStockClass.getCodeRoute());
                values.put(KEY_SYNBRESTOIDUSEAPP, synBreakStockClass.getIdUserApp());
                values.put(KEY_SYNBRESTOCODITE, synBreakStockClass.getCodeItem());
                values.put(KEY_SYNBRESTONAMITE, synBreakStockClass.getNameItem());
                values.put(KEY_SYNBRESTODATHOU, synBreakStockClass.getDateHour());
                values.put(KEY_SYNBRESTOPRI, synBreakStockClass.getPrice());
                values.put(KEY_SYNBRESTONUMCUSPEN, synBreakStockClass.getNumberCustomerPending());
                values.put(KEY_SYNBRESTOLOA, synBreakStockClass.getLoad());

                db.insert(TABLE_APP_SYN_BREAK_STOCK, null, values);
                db.close();
            }

            public boolean checkIfExists(String code){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_SYN_BREAK_STOCK + " WHERE asbs_cod='" + code + "'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result > 0;
            }

            public void updateSynBreakStock(String code, SynBreakStockClass synBreakStockClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                //values.put(KEY_SYNBRESTOID, synBreakStockClass.getId());
                values.put(KEY_SYNBRESTOCOD, synBreakStockClass.getCode());
                values.put(KEY_SYNBRESTOCODROU, synBreakStockClass.getCodeRoute());
                values.put(KEY_SYNBRESTOIDUSEAPP, synBreakStockClass.getIdUserApp());
                values.put(KEY_SYNBRESTOCODITE, synBreakStockClass.getCodeItem());
                values.put(KEY_SYNBRESTONAMITE, synBreakStockClass.getNameItem());
                values.put(KEY_SYNBRESTODATHOU, synBreakStockClass.getDateHour());
                values.put(KEY_SYNBRESTOPRI, synBreakStockClass.getPrice());
                values.put(KEY_SYNBRESTONUMCUSPEN, synBreakStockClass.getNumberCustomerPending());
                values.put(KEY_SYNBRESTOLOA, synBreakStockClass.getLoad());

                db.update(TABLE_APP_SYN_BREAK_STOCK, values, KEY_SYNBRESTOCOD + " = ?",new String[] { String.valueOf(code)});
                db.close();
            }


            public ArrayList<SynBreakStockClass> getSynBreakStockData() {
                ArrayList<SynBreakStockClass> breakStockList = new ArrayList<SynBreakStockClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_SYN_BREAK_STOCK;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        SynBreakStockClass synBreakStockClass= new SynBreakStockClass();
                        synBreakStockClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_SYNBRESTOID)));
                        synBreakStockClass.setCode(cursor.getString(cursor.getColumnIndex(KEY_SYNBRESTOCOD)));
                        synBreakStockClass.setCodeRoute(cursor.getString(cursor.getColumnIndex(KEY_SYNBRESTOCODROU)));
                        synBreakStockClass.setIdUserApp(cursor.getString(cursor.getColumnIndex(KEY_SYNBRESTOIDUSEAPP)));
                        synBreakStockClass.setCodeItem(cursor.getString(cursor.getColumnIndex(KEY_SYNBRESTOCODITE)));
                        synBreakStockClass.setNameItem(cursor.getString(cursor.getColumnIndex(KEY_SYNBRESTONAMITE)));
                        synBreakStockClass.setDateHour(cursor.getString(cursor.getColumnIndex(KEY_SYNBRESTODATHOU)));
                        synBreakStockClass.setPrice(cursor.getString(cursor.getColumnIndex(KEY_SYNBRESTOPRI)));
                        synBreakStockClass.setNumberCustomerPending(cursor.getString(cursor.getColumnIndex(KEY_SYNBRESTONUMCUSPEN)));
                        synBreakStockClass.setLoad(cursor.getString(cursor.getColumnIndex(KEY_SYNBRESTOLOA)));

                        breakStockList.add(synBreakStockClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return breakStockList;
            }

            public ArrayList<SynBreakStockClass> getPendingSynBreakStockData() {
                ArrayList<SynBreakStockClass> breakStockList = new ArrayList<SynBreakStockClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_SYN_BREAK_STOCK + " WHERE asbs_loa='0'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        SynBreakStockClass synBreakStockClass= new SynBreakStockClass();
                        synBreakStockClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_SYNBRESTOID)));
                        synBreakStockClass.setCode(cursor.getString(cursor.getColumnIndex(KEY_SYNBRESTOCOD)));
                        synBreakStockClass.setCodeRoute(cursor.getString(cursor.getColumnIndex(KEY_SYNBRESTOCODROU)));
                        synBreakStockClass.setIdUserApp(cursor.getString(cursor.getColumnIndex(KEY_SYNBRESTOIDUSEAPP)));
                        synBreakStockClass.setCodeItem(cursor.getString(cursor.getColumnIndex(KEY_SYNBRESTOCODITE)));
                        synBreakStockClass.setNameItem(cursor.getString(cursor.getColumnIndex(KEY_SYNBRESTONAMITE)));
                        synBreakStockClass.setDateHour(cursor.getString(cursor.getColumnIndex(KEY_SYNBRESTODATHOU)));
                        synBreakStockClass.setPrice(cursor.getString(cursor.getColumnIndex(KEY_SYNBRESTOPRI)));
                        synBreakStockClass.setNumberCustomerPending(cursor.getString(cursor.getColumnIndex(KEY_SYNBRESTONUMCUSPEN)));
                        synBreakStockClass.setLoad(cursor.getString(cursor.getColumnIndex(KEY_SYNBRESTOLOA)));

                        breakStockList.add(synBreakStockClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return breakStockList;
            }

            public int countPending(){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_SYN_BREAK_STOCK + " WHERE asbs_loa='0'" ,null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result;
            }
            public int countDone(){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_SYN_BREAK_STOCK + " WHERE asbs_loa='1'" ,null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result;
            }

            public void updateLoadSynBreakStock(String code, String load) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_SYNBRESTOLOA, load);
                db.update(TABLE_APP_SYN_BREAK_STOCK, values, KEY_SYNBRESTOCOD + " = ?",new String[] { String.valueOf(code)});
                db.close();
            }


            public SynBreakStockClass getSynBreakStock(String code) {
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_SYN_BREAK_STOCK, new String[] {KEY_SYNBRESTOID, KEY_SYNBRESTOCOD, KEY_SYNBRESTOCODROU,
                                KEY_SYNBRESTOIDUSEAPP, KEY_SYNBRESTOCODITE, KEY_SYNBRESTONAMITE, KEY_SYNBRESTODATHOU ,
                                KEY_SYNBRESTOPRI, KEY_SYNBRESTONUMCUSPEN, KEY_SYNBRESTOLOA},
                        KEY_SYNBRESTOCOD + "=?",
                        new String[] { String.valueOf(code)}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                SynBreakStockClass synBreakStockClass = new SynBreakStockClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5),
                        cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));

                db.close();
                return synBreakStockClass;
            }

            public String getData(int nField, String code) {
                String _data = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_SYN_BREAK_STOCK, new String[] {KEY_SYNBRESTOID, KEY_SYNBRESTOCOD, KEY_SYNBRESTOCODROU,
                                KEY_SYNBRESTOIDUSEAPP, KEY_SYNBRESTOCODITE, KEY_SYNBRESTONAMITE, KEY_SYNBRESTODATHOU ,
                                KEY_SYNBRESTOPRI, KEY_SYNBRESTONUMCUSPEN, KEY_SYNBRESTOLOA},
                        KEY_SYNBRESTOCOD + "=?",
                        new String[] {code}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _data = cursor.getString(nField);
                db.close();
                return _data;
            }

        }

        public class AppPaperSql{
            public AppPaperSql() {}

            public void deletePaper(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_PAPER,null,null);
                db.close();
            }

            public void addPaper(PaperClass paperClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_PAPID, paperClass.getId());
                values.put(KEY_PAPCOD, paperClass.getCode());
                values.put(KEY_PAPCODSOC, paperClass.getCodeSociety());
                values.put(KEY_PAPNAMSOC, paperClass.getNameSociety());
                values.put(KEY_PAPADDSOC, paperClass.getAddressSoc());
                values.put(KEY_PAPFIS, paperClass.getFis());
                values.put(KEY_PAPCODSUC, paperClass.getCodeSucursal());
                values.put(KEY_PAPNAMSUC, paperClass.getNameSucursal());
                values.put(KEY_PAPADDSUC, paperClass.getAddressSucursal());
                values.put(KEY_PAPEMASOC, paperClass.getEmailSociety());
                values.put(KEY_PAPPHOONESOC, paperClass.getPhoneOneSociety());
                values.put(KEY_PAPPHOTWOSOC, paperClass.getPhoneTwoSociety());
                values.put(KEY_PAPHEAONESOC, paperClass.getHeadOneSociety());
                values.put(KEY_PAPHEATWOSOC, paperClass.getHeadTwoSociety());
                values.put(KEY_PAPFOOONESOC, paperClass.getFooterOneSociety());
                values.put(KEY_PAPFOOTWOSOC, paperClass.getFooterTwoSociety());
                values.put(KEY_PAPCODOFF, paperClass.getCodeOffice());
                values.put(KEY_PAPNAMOFF, paperClass.getNameOffice());
                values.put(KEY_PAPSTA, paperClass.getState());
                db.insert(TABLE_APP_PAPER, null, values);
                db.close();

            }

            public boolean checkIfExists(String id){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_PAPER + " WHERE ap_id='" + id + "'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result > 0;
            }

            public void updatePaper(String id, PaperClass paperClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                //values.put(KEY_PAPID, paperClass.getId());
                values.put(KEY_PAPCOD, paperClass.getCode());
                values.put(KEY_PAPCODSOC, paperClass.getCodeSociety());
                values.put(KEY_PAPNAMSOC, paperClass.getNameSociety());
                values.put(KEY_PAPADDSOC, paperClass.getAddressSoc());
                values.put(KEY_PAPFIS, paperClass.getFis());
                values.put(KEY_PAPCODSUC, paperClass.getCodeSucursal());
                values.put(KEY_PAPNAMSUC, paperClass.getNameSucursal());
                values.put(KEY_PAPADDSUC, paperClass.getAddressSucursal());
                values.put(KEY_PAPEMASOC, paperClass.getEmailSociety());
                values.put(KEY_PAPPHOONESOC, paperClass.getPhoneOneSociety());
                values.put(KEY_PAPPHOTWOSOC, paperClass.getPhoneTwoSociety());
                values.put(KEY_PAPHEAONESOC, paperClass.getHeadOneSociety());
                values.put(KEY_PAPHEATWOSOC, paperClass.getHeadTwoSociety());
                values.put(KEY_PAPFOOONESOC, paperClass.getFooterOneSociety());
                values.put(KEY_PAPFOOTWOSOC, paperClass.getFooterTwoSociety());
                values.put(KEY_PAPCODOFF, paperClass.getCodeOffice());
                values.put(KEY_PAPNAMOFF, paperClass.getNameOffice());
                values.put(KEY_PAPSTA, paperClass.getState());

                db.update(TABLE_APP_PAPER, values, KEY_PAPID + " = ?",new String[] { String.valueOf(id)});
                db.close();
            }


            public ArrayList<PaperClass> getPaperData() {
                ArrayList<PaperClass> paperList = new ArrayList<PaperClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_PAPER;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        PaperClass paperClass= new PaperClass();
                        paperClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_PAPID)));
                        paperClass.setCode(cursor.getString(cursor.getColumnIndex(KEY_PAPCOD)));
                        paperClass.setCodeSociety(cursor.getString(cursor.getColumnIndex(KEY_PAPCODSOC)));
                        paperClass.setNameSociety(cursor.getString(cursor.getColumnIndex(KEY_PAPNAMSOC)));
                        paperClass.setAddressSoc(cursor.getString(cursor.getColumnIndex(KEY_PAPADDSOC)));
                        paperClass.setFis(cursor.getString(cursor.getColumnIndex(KEY_PAPFIS)));
                        paperClass.setCodeSucursal(cursor.getString(cursor.getColumnIndex(KEY_PAPCODSUC)));
                        paperClass.setNameSucursal(cursor.getString(cursor.getColumnIndex(KEY_PAPNAMSUC)));
                        paperClass.setAddressSucursal(cursor.getString(cursor.getColumnIndex(KEY_PAPADDSUC)));
                        paperClass.setEmailSociety(cursor.getString(cursor.getColumnIndex(KEY_PAPEMASOC)));
                        paperClass.setPhoneOneSociety(cursor.getString(cursor.getColumnIndex(KEY_PAPPHOONESOC)));
                        paperClass.setPhoneTwoSociety(cursor.getString(cursor.getColumnIndex(KEY_PAPPHOTWOSOC)));
                        paperClass.setHeadOneSociety(cursor.getString(cursor.getColumnIndex(KEY_PAPHEAONESOC)));
                        paperClass.setHeadTwoSociety(cursor.getString(cursor.getColumnIndex(KEY_PAPHEATWOSOC)));
                        paperClass.setFooterOneSociety(cursor.getString(cursor.getColumnIndex(KEY_PAPFOOONESOC)));
                        paperClass.setFooterTwoSociety(cursor.getString(cursor.getColumnIndex(KEY_PAPFOOTWOSOC)));
                        paperClass.setCodeOffice(cursor.getString(cursor.getColumnIndex(KEY_PAPCODOFF)));
                        paperClass.setNameOffice(cursor.getString(cursor.getColumnIndex(KEY_PAPNAMOFF)));
                        paperClass.setState(cursor.getString(cursor.getColumnIndex(KEY_PAPSTA)));

                        paperList.add(paperClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return paperList;
            }
            public ArrayList<PaperClass> getPaperDataByCustomer(String codeCustomer) {
                ArrayList<PaperClass> paperList = new ArrayList<PaperClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_PAPER + " WHERE ap_cod_soc='" + codeCustomer + "'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        PaperClass paperClass= new PaperClass();
                        paperClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_PAPID)));
                        paperClass.setCode(cursor.getString(cursor.getColumnIndex(KEY_PAPCOD)));
                        paperClass.setCodeSociety(cursor.getString(cursor.getColumnIndex(KEY_PAPCODSOC)));
                        paperClass.setNameSociety(cursor.getString(cursor.getColumnIndex(KEY_PAPNAMSOC)));
                        paperClass.setAddressSoc(cursor.getString(cursor.getColumnIndex(KEY_PAPADDSOC)));
                        paperClass.setFis(cursor.getString(cursor.getColumnIndex(KEY_PAPFIS)));
                        paperClass.setCodeSucursal(cursor.getString(cursor.getColumnIndex(KEY_PAPCODSUC)));
                        paperClass.setNameSucursal(cursor.getString(cursor.getColumnIndex(KEY_PAPNAMSUC)));
                        paperClass.setAddressSucursal(cursor.getString(cursor.getColumnIndex(KEY_PAPADDSUC)));
                        paperClass.setEmailSociety(cursor.getString(cursor.getColumnIndex(KEY_PAPEMASOC)));
                        paperClass.setPhoneOneSociety(cursor.getString(cursor.getColumnIndex(KEY_PAPPHOONESOC)));
                        paperClass.setPhoneTwoSociety(cursor.getString(cursor.getColumnIndex(KEY_PAPPHOTWOSOC)));
                        paperClass.setHeadOneSociety(cursor.getString(cursor.getColumnIndex(KEY_PAPHEAONESOC)));
                        paperClass.setHeadTwoSociety(cursor.getString(cursor.getColumnIndex(KEY_PAPHEATWOSOC)));
                        paperClass.setFooterOneSociety(cursor.getString(cursor.getColumnIndex(KEY_PAPFOOONESOC)));
                        paperClass.setFooterTwoSociety(cursor.getString(cursor.getColumnIndex(KEY_PAPFOOTWOSOC)));
                        paperClass.setCodeOffice(cursor.getString(cursor.getColumnIndex(KEY_PAPCODOFF)));
                        paperClass.setNameOffice(cursor.getString(cursor.getColumnIndex(KEY_PAPNAMOFF)));
                        paperClass.setState(cursor.getString(cursor.getColumnIndex(KEY_PAPSTA)));

                        paperList.add(paperClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return paperList;
            }

        }

        public class AppSuggestedSql{
            public AppSuggestedSql() {}

            public void deleteSuggested(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_SUGGESTED,null,null);
                db.close();
            }
            public void addSuggested(SuggestedClass suggestedClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                //values.put(KEY_SUGID, suggestedClass.getId());
                values.put(KEY_SUGTYP, suggestedClass.getType());
                values.put(KEY_SUGCODCUS, suggestedClass.getCodeCustomer());
                values.put(KEY_SUGCODITE, suggestedClass.getCodeItem());
                values.put(KEY_SUGDESITE, suggestedClass.getDescriptionItem());
                values.put(KEY_SUGCATITE, suggestedClass.getCategoryItem());
                values.put(KEY_SUGQUAITE, suggestedClass.getQuantityItem());
                values.put(KEY_SUGUNIMEA, suggestedClass.getUnitMeasureItem());
                db.insert(TABLE_APP_SUGGESTED, null, values);
                db.close();
            }
            public boolean checkIfExists(String codeCustomer, String codeItem, String type){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_SUGGESTED + " WHERE asu_cod_cus='" + codeCustomer + "' AND asu_cod_ite='" + codeItem  + "' AND asu_typ='" + type +"'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result > 0;
            }
            public void updateSuggested(String codeCustomer, String codeItem, String type, SuggestedClass suggestedClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                //values.put(KEY_SUGID, suggestedClass.getId());
                values.put(KEY_SUGTYP, suggestedClass.getType());
                values.put(KEY_SUGCODCUS, suggestedClass.getCodeCustomer());
                values.put(KEY_SUGCODITE, suggestedClass.getCodeItem());
                values.put(KEY_SUGDESITE, suggestedClass.getDescriptionItem());
                values.put(KEY_SUGCATITE, suggestedClass.getCategoryItem());
                values.put(KEY_SUGQUAITE, suggestedClass.getQuantityItem());
                values.put(KEY_SUGUNIMEA, suggestedClass.getUnitMeasureItem());
                db.update(TABLE_APP_SUGGESTED, values, KEY_SUGCODCUS + " = ?" + " AND " + KEY_SUGCODITE + " = ?" + " AND " + KEY_SUGTYP + " = ?",new String[] { String.valueOf(codeCustomer), String.valueOf(codeItem), String.valueOf(type)});
                db.close();
            }

            public String getSuggestedQuantityByItem(String codeItem, String codeCustomer, String type){
                ArrayList<SuggestedClass> suggestedList = new ArrayList<SuggestedClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_SUGGESTED + " WHERE asu_cod_ite='" + codeItem +"' AND asu_cod_cus='" + codeCustomer  +"' AND asu_typ='" + type +"'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                String result = "";
                if (cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(KEY_SUGQUAITE));
                }
                db.close();
                return result;
            }

            public ArrayList<SuggestedClass> getSuggestedData() {
                ArrayList<SuggestedClass> suggestedList = new ArrayList<SuggestedClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_SUGGESTED;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        SuggestedClass suggestedClass = new SuggestedClass();
                        suggestedClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_SUGID)));
                        suggestedClass.setType(cursor.getString(cursor.getColumnIndex(KEY_SUGTYP)));
                        suggestedClass.setCodeCustomer(cursor.getString(cursor.getColumnIndex(KEY_SUGCODCUS)));
                        suggestedClass.setCodeItem(cursor.getString(cursor.getColumnIndex(KEY_SUGCODITE)));
                        suggestedClass.setDescriptionItem(cursor.getString(cursor.getColumnIndex(KEY_SUGDESITE)));
                        suggestedClass.setCategoryItem(cursor.getString(cursor.getColumnIndex(KEY_SUGCATITE)));
                        suggestedClass.setQuantityItem(cursor.getString(cursor.getColumnIndex(KEY_SUGQUAITE)));
                        suggestedClass.setUnitMeasureItem(cursor.getString(cursor.getColumnIndex(KEY_SUGUNIMEA)));
                        suggestedList.add(suggestedClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return suggestedList;
            }
            public SuggestedClass getSuggested(String id) {
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_SUGGESTED, new String[] {KEY_SUGID, KEY_SUGTYP, KEY_SUGCODCUS,
                                KEY_SUGCODITE, KEY_SUGDESITE, KEY_SUGCATITE, KEY_SUGQUAITE, KEY_SUGUNIMEA},
                        KEY_SUGID + "=?",
                        new String[] { String.valueOf(id)}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                SuggestedClass suggestedClass = new SuggestedClass(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7));
                db.close();
                return suggestedClass;
            }
            public String getData(int nField, String code) {
                String _data = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_SUGGESTED, new String[] {KEY_SUGID, KEY_SUGTYP, KEY_SUGCODCUS,
                                KEY_SUGCODITE, KEY_SUGDESITE, KEY_SUGCATITE, KEY_SUGQUAITE ,
                                KEY_SUGUNIMEA},
                        KEY_SUGID + "=?",
                        new String[] {code}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _data = cursor.getString(nField);
                db.close();
                return _data;
            }
        }
        public class AppConfigurationSql{
            public AppConfigurationSql() {
            }

            public void deleteConfiguration(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_CONFIGURATION,null,null);
                db.close();
            }
            public void addConfiguration(ConfigurationClass configurationClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_CONID, configurationClass.getId());
                values.put(KEY_CONCUR, configurationClass.getCurrency());
                values.put(KEY_CONUNIMEA, configurationClass.getUnitMeasure());
                values.put(KEY_CONPAPSIZ, configurationClass.getPaperSize());

                db.insert(TABLE_APP_CONFIGURATION, null, values);
                db.close();
            }
            public boolean checkIfExists(String id){
                int result = 0;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_APP_CONFIGURATION + " WHERE aco_id='" + id + "'",null);
                mCount.moveToFirst();
                result= mCount.getInt(0);
                db.close();
                return result > 0;
            }
            public void updateConfiguration(String id, ConfigurationClass configurationClass) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_CONID, configurationClass.getId());
                values.put(KEY_CONCUR, configurationClass.getCurrency());
                values.put(KEY_CONUNIMEA, configurationClass.getUnitMeasure());
                values.put(KEY_CONPAPSIZ, configurationClass.getPaperSize());
                db.update(TABLE_APP_CONFIGURATION, values, KEY_CONID + " = ?",new String[] { String.valueOf(id)});
                db.close();
            }
            public ArrayList<ConfigurationClass> getConfigurationData() {
                ArrayList<ConfigurationClass> configurationList = new ArrayList<ConfigurationClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_CONFIGURATION;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        ConfigurationClass configurationClass = new ConfigurationClass();
                        configurationClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_CONID)));
                        configurationClass.setCurrency(cursor.getString(cursor.getColumnIndex(KEY_CONCUR)));
                        configurationClass.setUnitMeasure(cursor.getString(cursor.getColumnIndex(KEY_CONUNIMEA)));
                        configurationClass.setPaperSize(cursor.getString(cursor.getColumnIndex(KEY_CONPAPSIZ)));

                        configurationList.add(configurationClass);
                    } while (cursor.moveToNext());
                }
                db.close();
                return configurationList;
            }

            public ConfigurationClass getConfiguration(String id) {
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_CONFIGURATION, new String[] {KEY_CONID, KEY_CONCUR,
                                KEY_CONUNIMEA, KEY_CONPAPSIZ},
                        KEY_CONID + "=?",
                        new String[] { String.valueOf(id)}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                ConfigurationClass configurationClass= new ConfigurationClass(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3));
                db.close();
                return configurationClass;
            }
            //Teniendo en cuenta que solo habra una instancia del mismo
            public String getData(String type) {
                String _data = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_CONFIGURATION, new String[] {KEY_CONID, KEY_CONCUR,
                                KEY_CONUNIMEA, KEY_CONPAPSIZ},
                        null,
                        null, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                if(type.equals("Currency")){ _data = cursor.getString(1); }
                else if(type.equals("MeasureUnit")){ _data = cursor.getString(2); }
                else if(type.equals("PaperSize")){ _data = cursor.getString(3); }

                db.close();
                return _data;
            }
        }

    }

}