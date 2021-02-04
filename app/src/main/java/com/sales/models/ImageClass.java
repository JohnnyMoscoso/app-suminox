package com.sales.models;

public class ImageClass {

	public int id;
	public int idEvent;
	public String name;
	public String load;

	public ImageClass() {}

	public ImageClass(int _id, int _idEvent, String _name, String _load){
		this.id = _id;
		this.idEvent = _idEvent;
		this.name = _name;
		this.load = _load;

	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoad() {
		return load;
	}

	public void setLoad(String load) {
		this.load = load;
	}





}
