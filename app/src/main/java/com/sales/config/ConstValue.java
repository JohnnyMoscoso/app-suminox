package com.sales.config;

import com.sales.models.PaperClass;
import com.sales.models.SynOrderItemClass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConstValue {

	public static String MAIN_PREF = "CloudSales";

	public static String SITE_URL = "http://app7.pe/CloudSales";
	public static String JSON_LOGIN = SITE_URL + "/index.php?component=json&action=getUser";
	public static String JSON_CUSTOMER = SITE_URL + "/index.php?component=json&action=getCustomer";
	public static String JSON_CATEGORY = SITE_URL + "/index.php?component=json&action=getCategory";
	public static String JSON_ITEM = SITE_URL + "/index.php?component=json&action=getItem";
	public static String JSON_PRICE_LIST = SITE_URL + "/index.php?component=json&action=getPriceList";
	public static String JSON_VISIT = SITE_URL + "/index.php?component=json&action=getVisit";
	public static String JSON_DISCOUNT_LIST = SITE_URL + "/index.php?component=json&action=getDiscount";
	public static String JSON_PROMO_LIST = SITE_URL + "/index.php?component=json&action=getPromotion";
	public static String JSON_ASSET_CONTROL = SITE_URL + "/index.php?component=json&action=getAssetControl";
	public static String JSON_STOCK = SITE_URL + "/index.php?component=json&action=getRouteStock";
	public static String JSON_TYPE_DOCUMENT = SITE_URL + "/index.php?component=json&action=getTypeDocument";
	public static String JSON_DOCUMENT = SITE_URL + "/index.php?component=json&action=getappAdminDocument";
	public static String JSON_CHANGE_RETURN = SITE_URL + "/index.php?component=json&action=setReturnProduct";
	public static String JSON_TICKET = SITE_URL + "/index.php?component=json&action=getTicket";
	public static String JSON_SUGGESTED_SALE = SITE_URL + "/index.php?component=json&action=getSuggestedSale";
    public static String JSON_SUGGESTED_ORDER = SITE_URL + "/index.php?component=json&action=getSuggestedOrder";
	public static String JSON_SUGGESTED_CHARGE = SITE_URL + "/index.php?component=json&action=getSuggestedCharge";
	public static String JSON_CONFIGURATION = SITE_URL + "/index.php?component=json&action=getConfiguration";

	public static String JSON_CHANGE_PASSWORD = SITE_URL + "/index.php?component=json&action=setChangePassword";
	public static String JSON_SEND_CUSTOMER = SITE_URL + "/index.php?component=json&action=setCustomer";
	public static String JSON_SEND_VISIT_APP = SITE_URL + "/index.php?component=json&action=setVisitMade";
	public static String JSON_SEND_ASSET_CONTROL = SITE_URL + "/index.php?component=json&action=setAssetControl";
    public static String JSON_SEND_TASK = SITE_URL + "/index.php?component=json&action=updateAssetTask";
    public static String JSON_SEND_DOCUMENT = SITE_URL + "/index.php?component=json&action=setAppDocument";
    public static String JSON_SEND_STOCK_BREAK = SITE_URL + "/index.php?component=json&action=setStockBreak";

	//@GeneralUser
	public static String userId;
	public static String getUserId() {
		return userId;
	}
	public static void setUserId(String userId) {
		ConstValue.userId = userId;
	}

	public static String userName;
	public static String getUserName() { return userName; } public static void setUserName(String userName) { ConstValue.userName = userName; }

	public static String userPass;
	public static String getUserPass() {
		return userPass;
	}
	public static void setUserPass(String userPass) {
		ConstValue.userPass = userPass;
	}

	public static String userRouteCode;
	public static String getUserRouteCode() { return userRouteCode; } public static void setUserRouteCode(String userRouteCode) { ConstValue.userRouteCode = userRouteCode; }

	public static String userEmail;
	public static String getUserEmail() { return userEmail; } public static void setUserEmail(String userEmail) { ConstValue.userEmail = userEmail; }

	public static String company;
    public static String getUserCompany() { return company; }public static void setUserCompany(String company) { ConstValue.company = company; }

    public static String name;
    public static String getName() { return name; } public static void setName(String name) { ConstValue.name = name; }

    //@General Customer
    public static int customerId;
	public static int getCustomerId() { return customerId; }public static void setCustomerId(int customerId) { ConstValue.customerId = customerId; }

	public static String customerCode;
	public static String getCustomerCode() { return customerCode; } public static void setCustomerCode(String customerCode) { ConstValue.customerCode = customerCode; }

	//@General Asset Control
	public static int assetControlId;
	public static int getAssetControlId() { return assetControlId; } public static void setAssetControlId(int assetControlId) { ConstValue.assetControlId = assetControlId; }

	/** Config values */
	public static String currency; //Symbol
	public static String getCurrency() { return currency; } public static void setCurrency(String currency) { ConstValue.currency = currency; }

	public static String nameCurrency; //Name
	public static String getNameCurrency() { return nameCurrency; } public static void setNameCurrency(String nameCurrency) { ConstValue.nameCurrency = nameCurrency; }

	public static double proximateRange;
	public static double getProximateRange() { return proximateRange; }public static void setProximateRange(double proximateRange) { ConstValue.proximateRange = proximateRange; }

	public static String defaultPriceListCode;
	public static String getDefaultPriceListCode() { return defaultPriceListCode; } public static void setDefaultPriceListCode(String defaultPriceListCode) { ConstValue.defaultPriceListCode = defaultPriceListCode; }

	public static String unitMeasure;
	public static String getUnitMeasure() { return unitMeasure; } public static void setUnitMeasure(String unitMeasure) { ConstValue.unitMeasure = unitMeasure; }

	public static String limitLts;
	public static String getLimitLts() { return limitLts; } public static void setLimitLts(String limitLts) { ConstValue.limitLts = limitLts; }

	public static String pendingLimitLts;
	public static String getPendingLimitLts() { return pendingLimitLts; } public static void setPendingLimitLts(String pendingLimitLts) { ConstValue.pendingLimitLts = pendingLimitLts; }


	/** Static arraylist for item prices */
	public static ArrayList<String> itemPrices = new ArrayList<String>();
	public static void clearItemPrices(){ itemPrices.clear(); }
	public static void addItemPrices(ArrayList<String> prices){ itemPrices = prices; }
	public static ArrayList<String> getItemPrices(){
		ArrayList<String> prices = new ArrayList<String>();
		for(int i = 0; i < itemPrices.size(); i++){
			prices.add(itemPrices.get(i));
		}
		return prices;
	}

	/** Static arraylist for item taxes */
	public static ArrayList<String> itemTaxes = new ArrayList<String>();
	public static void clearItemTaxes(){ itemTaxes.clear(); }
	public static void addItemTaxes(ArrayList<String> taxes){ itemTaxes = taxes; }
	public static ArrayList<String> getItemTaxes(){
		ArrayList<String> taxes = new ArrayList<String>();
		for(int i = 0; i < itemTaxes.size(); i++){
			taxes.add(itemTaxes.get(i));
		}
		return taxes;
	}

	/** Static arraylist for item weight */
	public static ArrayList<String> itemWeight = new ArrayList<String>();
	public static void clearItemWeight(){ itemWeight.clear(); }
	public static void addItemWeight(ArrayList<String> weight){ itemWeight = weight; }
	public static ArrayList<String> getItemWeight(){
		ArrayList<String> weight = new ArrayList<String>();
		for(int i = 0; i < itemWeight.size(); i++){
			weight.add(itemWeight.get(i));
		}
		return weight;
	}

	/** items for an order or direct sale */
	public static ArrayList<HashMap<String, String>> changeReturnItems = new ArrayList<HashMap<String, String>>();
	public static void addChangeReturnItems(ArrayList<HashMap<String, String>> data){ changeReturnItems = data; }
	public static void clearChangeReturn(){ changeReturnItems.clear(); }
	public static  ArrayList<HashMap<String, String>> getChangeReturn(){
		ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < changeReturnItems.size(); i++) {
			HashMap<String, String> map = changeReturnItems.get(i);
			map.put("description", changeReturnItems.get(i).get("description"));
			map.put("color", changeReturnItems.get(i).get("color"));
			map.put("category", changeReturnItems.get(i).get("category"));
			map.put("price", changeReturnItems.get(i).get("price"));
			data.add(map);
		}
		return data;
	}

	/** items for an order or direct sale */
	public static ArrayList<HashMap<String, String>> orderItems = new ArrayList<HashMap<String, String>>();
	public static void addOrderItems(ArrayList<HashMap<String, String>> data){ orderItems = data; }
	public static void clearOrderItems(){ orderItems.clear(); }
	public static  ArrayList<HashMap<String, String>> getOrderItems(){
		ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < orderItems.size(); i++) {
			HashMap<String, String> map = orderItems.get(i);
			map.put("description", orderItems.get(i).get("description"));
			map.put("color", orderItems.get(i).get("color"));
			map.put("category", orderItems.get(i).get("category"));
			map.put("price", orderItems.get(i).get("price"));
			map.put("suggestedQuantity", orderItems.get(i).get("suggestedQuantity"));
			data.add(map);
		}
		return data;
	}


	/** Values for Order (Pedido de Carga) */
	public static ArrayList<SynOrderItemClass> synOrderItemList = new ArrayList<SynOrderItemClass>();
	public static void clearSynOrderItemList(){ synOrderItemList.clear();}
	public static void addSynOrderItemList(ArrayList<SynOrderItemClass> list){ synOrderItemList = list;}
	public static ArrayList<SynOrderItemClass> getSynOrderItemList(){
		ArrayList<SynOrderItemClass> list = new ArrayList<SynOrderItemClass>();
		for(int i = 0; i < synOrderItemList.size(); i++){
			list.add(
					new SynOrderItemClass(
							synOrderItemList.get(i).getId(), synOrderItemList.get(i).getNumberDocument(), synOrderItemList.get(i).getDateHour(), synOrderItemList.get(i).getCategory(),
							synOrderItemList.get(i).getCodeItem(), synOrderItemList.get(i).getDescriptionItem(), synOrderItemList.get(i).getQuantity(), synOrderItemList.get(i).getPrice(),
							synOrderItemList.get(i).getUnitMeasure(), synOrderItemList.get(i).getIgv(), synOrderItemList.get(i).getTotalPay(), synOrderItemList.get(i).getState(),
							synOrderItemList.get(i).getStateCharge(), synOrderItemList.get(i).getLoad()));
		}
		return list;
	}

	public static String documentNumber;
	public static String getDocumentNumber() { return documentNumber; } public static void setDocumentNumber(String documentNumber) { ConstValue.documentNumber = documentNumber; }

	public static String documentNumeration;
	public static String getDocumentNumeration() { return documentNumeration; } public static void setDocumentNumeration(String documentNumeration) { ConstValue.documentNumeration = documentNumeration; }

	public static String exemptAmount;
	public static String getExemptAmount() { return exemptAmount; } public static void setExemptAmount(String exemptAmount) { ConstValue.exemptAmount = exemptAmount; }

	public static String taxableAmount;
	public static String getTaxableAmount() { return taxableAmount; } public static void setTaxableAmount(String taxableAmount) { ConstValue.taxableAmount = taxableAmount; }

	public static String gravAmount;
	public static String getGravAmount() { return gravAmount; } public static void setGravAmount(String gravAmount) { ConstValue.gravAmount = gravAmount; }

	public static String total;
	public static String getTotal() { return total; } public static void setTotal(String total) { ConstValue.total = total; }

	public static String orderDateString;
	public static String getOrderDateString() { return orderDateString; } public static void setOrderDateString(String orderDateString) { ConstValue.orderDateString = orderDateString; }

	public static String numberDocumentSynOrder;
	public static String getNumberDocumentSynOrder() { return numberDocumentSynOrder; } public static void setNumberDocumentSynOrder(String numberDocumentSynOrder) { ConstValue.numberDocumentSynOrder = numberDocumentSynOrder; }

	public static String suggestedState;
	public static String getSuggestedState() { return suggestedState; } public static void setSuggestedState(String suggestedState) { ConstValue.suggestedState = suggestedState; }



	public static String paperSize;
	public static String getPaperSize() { return paperSize; } public static void setPaperSize(String paperSize) { ConstValue.paperSize = paperSize; }

	public static PaperClass paperConfiguration;
	public static void setPaperConfiguration(PaperClass loadPaper){
		paperConfiguration = loadPaper;
	}
	public static PaperClass getPaperConfiguration(){
		return new PaperClass(paperConfiguration.getId(), paperConfiguration.getCode(), paperConfiguration.getCodeSociety(), paperConfiguration.getNameSociety(),
				paperConfiguration.getAddressSoc(), paperConfiguration.getFis(), paperConfiguration.getCodeSucursal(), paperConfiguration.getNameSucursal(),
				paperConfiguration.getAddressSucursal(), paperConfiguration.getEmailSociety(), paperConfiguration.getPhoneOneSociety(), paperConfiguration.getPhoneTwoSociety(), paperConfiguration.getHeadOneSociety(),
				paperConfiguration.getHeadTwoSociety(), paperConfiguration.getFooterOneSociety(), paperConfiguration.getFooterTwoSociety(), paperConfiguration.getCodeOffice(), paperConfiguration.getNameOffice(), paperConfiguration.getState());
	}
	public static String dataPrint(String  _field){
		String data = "";
		if(_field == "sociedad"){
			data = paperConfiguration.getNameSociety();
		}
		if(_field == "RTN"){
			data = paperConfiguration.getFis();
		}
		if(_field == "address"){
			data = paperConfiguration.getAddressSoc();
		}
		if(_field == "sucursalName"){
			data = paperConfiguration.getNameSucursal();
		}
		if(_field == "sucursalAddress"){
			data = paperConfiguration.getAddressSucursal();
		}
		if(_field == "email"){
			data = paperConfiguration.getEmailSociety();
		}
		if(_field == "phone"){
			data = paperConfiguration.getPhoneOneSociety();
		}
		if(_field == "phoneTwo"){
			data = paperConfiguration.getPhoneTwoSociety();
		}
		if(_field == "headOne"){
			data = paperConfiguration.getHeadOneSociety();
			if(data.equals("")){data = "";}else{data=data+"\r\n";}
		}
		if(_field == "headTwo"){
			data = paperConfiguration.getHeadTwoSociety();
			if(data.equals("")){data = "";}else{data=data+"\r\n";}
		}
		if(_field == "footerOne"){
			data = paperConfiguration.getFooterOneSociety();
			if(data.equals("")){data = "";}else{data=data+"\r\n";}
		}
		if(_field == "footerTwo"){
			data = paperConfiguration.getFooterTwoSociety();
			if(data.equals("")){data = "";}else{data=data+"\r\n";}
		}
		return data;
	}

	/** Reprint value */
	public static String printStatus;
	public static String getPrintStatus() { return printStatus; } public static void setPrintStatus(String printStatus) { ConstValue.printStatus = printStatus; }

	/** Class Names for Pending Synchronization Fragment to Detail */
	public static String className;
	public static String getClassName() { return className; } public static void setClassName(String className) { ConstValue.className = className; }

	/** Fragment Sync values*/
    public static String checkedCustomers;
	public static String getCheckedCustomers() { return checkedCustomers; }public static void setCheckedCustomers(String checkedCustomers) { ConstValue.checkedCustomers = checkedCustomers; }

	public static String checkedItems;
	public static String getCheckedItems() { return checkedItems; } public static void setCheckedItems(String checkedItems) { ConstValue.checkedItems = checkedItems; }

	public static String checkedCategories;
	public static String getCheckedCategories() { return checkedCategories; } public static void setCheckedCategories(String checkedCategories) { ConstValue.checkedCategories = checkedCategories; }

	public static String checkedPriceList;
	public static String getCheckedPriceList() { return checkedPriceList; } public static void setCheckedPriceList(String checkedPriceList) { ConstValue.checkedPriceList = checkedPriceList; }

	public static String checkedDiscountList;
	public static String getCheckedDiscountList() {return checkedDiscountList; } public static void setCheckedDiscountList(String checkedDiscountList) {ConstValue.checkedDiscountList = checkedDiscountList; }

	public static String checkedPromoList;
	public static String getCheckedPromoList() { return checkedPromoList; } public static void setCheckedPromoList(String checkedPromoList) { ConstValue.checkedPromoList = checkedPromoList; }

	public static String checkedVisits;
    public static String getCheckedVisits() { return checkedVisits; } public static void setCheckedVisits(String checkedVisits) { ConstValue.checkedVisits = checkedVisits; }

    public static String checkedAssetControl;
	public static String getCheckedAssetControl() { return checkedAssetControl; } public static void setCheckedAssetControl(String checkedAssetControl) { ConstValue.checkedAssetControl = checkedAssetControl; }

	public static String checkedStock;
	public static String getCheckedStock() { return checkedStock; }public static void setCheckedStock(String checkedStock) { ConstValue.checkedStock = checkedStock; }

	public static String checkedDocuments;
	public static String getCheckedDocuments() { return checkedDocuments; } public static void setCheckedDocuments(String checkedDocuments) { ConstValue.checkedDocuments = checkedDocuments; }

	public static String checkedTickets;
	public static String getCheckedTickets() { return checkedTickets; } public static void setCheckedTickets(String checkedTickets) {ConstValue.checkedTickets = checkedTickets; }

	public static String checkedCatalogue;
	public static String getCheckedCatalogue() { return checkedCatalogue; } public static void setCheckedCatalogue(String checkedCatalogue) { ConstValue.checkedCatalogue = checkedCatalogue; }

	public static String checkedConfiguration;
	public static String getCheckedConfiguration() { return checkedConfiguration; } public static void setCheckedConfiguration(String checkedConfiguration) { ConstValue.checkedConfiguration = checkedConfiguration; }

	public static String inventarySearch = "0";
	public static String getInventarySearch() { return inventarySearch; } public static void setInventarySearch(String inventarySearch) { ConstValue.inventarySearch = inventarySearch; }
}
