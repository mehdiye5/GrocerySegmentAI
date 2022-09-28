package com.sfu.object;

import java.util.List;

public class OrderProductsLoad {
	private String s3Bucket;
	public OrderProductsLoad() {
		super();
		// TODO Auto-generated constructor stub
	}
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public List<AdditionalFeaturesLoad> getFeatureColumns() {
		return featureColumns;
	}
	public void setFeatureColumns(List<AdditionalFeaturesLoad> featureColumns) {
		this.featureColumns = featureColumns;
	}
	public OrderProductsLoad(String s3Bucket, String s3Object, int tenantId, String orderId, String productId,
			List<AdditionalFeaturesLoad> featureColumns) {
		super();
		this.s3Bucket = s3Bucket;
		this.s3Object = s3Object;
		this.tenantId = tenantId;
		this.orderId = orderId;
		this.productId = productId;
		this.featureColumns = featureColumns;
	}
	private String s3Object;
	private int tenantId;
	private String orderId;
	private String productId;
	private List<AdditionalFeaturesLoad> featureColumns;

}
