package com.sales.models;

public class UserClass {

	public int id;
	public String username;
	public String password;
	public String name;
	public String email;
	public String date;

	public String routeCode;
	public String routeName;
	public String salesCenter;
	public String routeType;
	public String limitReturn;
	public String state;
	public String limitLtsLbs;
	public String pendingLtsLbs;
	public String proximityRange;


	public UserClass(){

	}

	public UserClass(int id, String username, String password, String name, String email, String date,
					 String routeCode, String routeName, String salesCenter, String routeType,
					 String limitReturn, String state, String limitLtsLbs, String pendingLtsLbs, String proximityRange) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.date = date;
		this.routeCode = routeCode;
		this.routeName = routeName;
		this.salesCenter = salesCenter;
		this.routeType = routeType;
		this.limitReturn = limitReturn;
		this.state = state;
		this.limitLtsLbs = limitLtsLbs;
		this.pendingLtsLbs = pendingLtsLbs;
		this.proximityRange = proximityRange;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getSalesCenter() {
		return salesCenter;
	}

	public void setSalesCenter(String salesCenter) {
		this.salesCenter = salesCenter;
	}

	public String getRouteType() {
		return routeType;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public String getLimitReturn() {
		return limitReturn;
	}

	public void setLimitReturn(String limitReturn) {
		this.limitReturn = limitReturn;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLimitLtsLbs() {
		return limitLtsLbs;
	}

	public void setLimitLtsLbs(String limitLtsLbs) {
		this.limitLtsLbs = limitLtsLbs;
	}

	public String getPendingLtsLbs() {
		return pendingLtsLbs;
	}

	public void setPendingLtsLbs(String pendingLtsLbs) {
		this.pendingLtsLbs = pendingLtsLbs;
	}

	public String getProximityRange() {
		return proximityRange;
	}

	public void setProximityRange(String proximityRange) {
		this.proximityRange = proximityRange;
	}
}
