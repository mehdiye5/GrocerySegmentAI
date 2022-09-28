package com.sfu.object;

public class DataObjectType {
	Integer _id;
	public DataObjectType(Integer _id, String name) {
		super();
		this._id = _id;
		this.name = name;
	}
	String name;
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DataObjectType() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
