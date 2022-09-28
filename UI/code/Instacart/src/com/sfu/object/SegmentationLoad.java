package com.sfu.object;

public class SegmentationLoad {
	private String productId;
	public SegmentationLoad() {
		super();
		// TODO Auto-generated constructor stub
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
	public SegmentationLoad(String productId, String destinationBucket, String destinationObject) {
		super();
		this.productId = productId;
		this.destinationBucket = destinationBucket;
		this.destinationObject = destinationObject;
	}
	private String destinationBucket;
	private String destinationObject;

	
}
