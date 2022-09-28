package com.sfu.object;

import java.util.List;

public class InstacartDataObject {
	int _id;
	public InstacartDataObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public UserDataObject getUserData() {
		return userData;
	}
	public void setUserData(UserDataObject userData) {
		this.userData = userData;
	}
	public DataObjectType getObjectType() {
		return objectType;
	}
	public void setObjectType(DataObjectType objectType) {
		this.objectType = objectType;
	}
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getBucketKey() {
		return bucketKey;
	}
	public void setBucketKey(String bucketKey) {
		this.bucketKey = bucketKey;
	}
	public InstacartDataObject(int _id, UserDataObject userData, DataObjectType objectType, String bucketName,
			String bucketKey) {
		super();
		this._id = _id;
		this.userData = userData;
		this.objectType = objectType;
		this.bucketName = bucketName;
		this.bucketKey = bucketKey;
	}
	UserDataObject userData;
	DataObjectType objectType;
	String bucketName;
	String bucketKey;
	List<InstacartFeatures> instacartFeatures;
	public List<InstacartFeatures> getInstacartFeatures() {
		return instacartFeatures;
	}
	public void setInstacartFeatures(List<InstacartFeatures> instacartFeatures) {
		this.instacartFeatures = instacartFeatures;
	}

	
}
