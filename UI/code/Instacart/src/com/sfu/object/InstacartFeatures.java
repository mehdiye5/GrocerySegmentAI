package com.sfu.object;

public class InstacartFeatures {
	private int _id;
	public InstacartFeatures() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public int getDataObject() {
		return dataObjectId;
	}
	public void setDataObject(int dataObject) {
		this.dataObjectId = dataObject;
	}
	public String getFeatureSource() {
		return featureSource;
	}
	public void setFeatureSource(String featureSource) {
		this.featureSource = featureSource;
	}
	public String getFeatureName() {
		return featureName;
	}
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	public InstacartFeatures(int _id, int dataObject, String featureSource, String featureName) {
		super();
		this._id = _id;
		this.dataObjectId = dataObject;
		this.featureSource = featureSource;
		this.featureName = featureName;
	}
	private int dataObjectId;
	private String featureSource;
	private String featureName;

}
