package com.sfu.object;

public class TrainInstacart {
	private int _id;
	private String statusName;
	private String trainId;
	public String getTrainId() {
		return trainId;
	}
	public void setTrainId(String trainId) {
		this.trainId = trainId;
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
	private String statusColor;
	@Override
	public String toString() {
		return "TrainInstacart [_id=" + _id + ", status=" + status + ", dataObject=" + dataObject + "]";
	}
	public TrainInstacart() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public int getStatus() {		
		return status;
		
	}
	public void setStatus(int status) {
		this.status = status;
		this.setStatusComponents(status);
	}
	public UserDataObject getDataObject() {
		return dataObject;
	}
	public void setDataObject(UserDataObject dataObject) {
		this.dataObject = dataObject;
	}
	public TrainInstacart(int _id, int status, UserDataObject dataObject, String trainId) {
		super();
		this._id = _id;
		this.status = status;
		this.dataObject = dataObject;
		this.trainId = trainId;
		this.setStatusComponents(status);
	}
	private int status;
	private UserDataObject dataObject;
	
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

}
