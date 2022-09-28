package com.sfu.object;

public class OrdersLoad {
	private String s3Bucket;
	public String getS3Bucket() {
		return s3Bucket;
	}
	public void setS3Bucket(String s3Bucket) {
		this.s3Bucket = s3Bucket;
	}
	public String getS3Object() {
		return s3Object;
	}
	public void setS3Object(String s3Object) {
		this.s3Object = s3Object;
	}
	public int getTenantId() {
		return tenantId;
	}
	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	private String s3Object;
	private int tenantId;
	private String userId;
	public OrdersLoad() {
		super();
		// TODO Auto-generated constructor stub
	}
	private String orderId;
	private String timestamp;
	public OrdersLoad(String s3Bucket, String s3Object, int tenantId, String userId, String orderId, String timestamp) {
		super();
		this.s3Bucket = s3Bucket;
		this.s3Object = s3Object;
		this.tenantId = tenantId;
		this.userId = userId;
		this.orderId = orderId;
		this.timestamp = timestamp;
	}
	

}
