package com.sfu.object;

public class UserDataObject {
	Integer _id;
	public UserDataObject(Integer _id, String objectName, Integer userId) {
		super();
		this._id = _id;
		this.objectName = objectName;
		this.userId = userId;
	}
	String objectName;
	Integer userId;
	
	public UserDataObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	

}
