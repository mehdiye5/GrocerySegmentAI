package com.sfu.object;

public class Segmentation {
	private int _id;
	private int status;
	private String segmentationName;
	public String getSegmentationName() {
		return segmentationName;
	}
	public void setSegmentationName(String segmentationName) {
		this.segmentationName = segmentationName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;	
		this.setStatusComponents(status);
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getStatusColor() {
		return statusColor;
	}
	public void setStatusColor(String statusColor) {
		this.statusColor = statusColor;
	}
	private String statusName;
	@Override
	public String toString() {
		return "Segmentation [_id=" + _id + ", status=" + status + ", segmentationName=" + segmentationName
				+ ", statusName=" + statusName + ", statusColor=" + statusColor + ", dataObject=" + dataObject
				+ ", productId=" + productId + ", destinationBucket=" + destinationBucket + ", destinationObject="
				+ destinationObject + ", responseId=" + responseId + "]";
	}
	private String statusColor;
	public Segmentation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public UserDataObject getDataObject() {
		return dataObject;
	}
	public void setDataObject(UserDataObject dataObject) {
		this.dataObject = dataObject;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getDestinationBucket() {
		return destinationBucket;
	}
	public void setDestinationBucket(String destinationBucket) {
		this.destinationBucket = destinationBucket;
	}
	public String getDestinationObject() {
		return destinationObject;
	}
	public void setDestinationObject(String destinationObject) {
		this.destinationObject = destinationObject;
	}
	public String getResponseId() {
		return responseId;
	}
	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}
	public Segmentation(int _id, UserDataObject dataObject, String productId, String destinationBucket,
			String destinationObject, String responseId, int status, String segmentationName) {
		super();
		this._id = _id;
		this.dataObject = dataObject;
		this.productId = productId;
		this.destinationBucket = destinationBucket;
		this.destinationObject = destinationObject;
		this.responseId = responseId;
		this.status = status;
		this.setStatusComponents(status);
		this.segmentationName = segmentationName;
	}
	
	public void setStatusComponents(int status) {
		if (status == 1) {
			this.statusName = "Not Initiated";
			this.statusColor = "";
		} else if (status == 2) {
			this.statusName = "Pending";
			this.statusColor = "warning";
		} else if (status == 3) {
			this.statusName = "Complete";
			this.statusColor = "success";
		} else {
			this.statusName = "Failed";
			this.statusColor = "danger";
		}
	}
	
	private UserDataObject dataObject;
	private String productId;
	private String destinationBucket;
	private String destinationObject;
	private String responseId;
}
