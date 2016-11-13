package com.indix.dbupdater.model;

public class ProductVo {
	
	private String productId;
	private String title;
	private String upcs;
	private String seller;
	private double price;
	private long timestamp;
	private long categoryId;
	private long storeId;
	public String getPid() {
		return productId;
	}
	public void setPid(String pid) {
		productId = pid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUpcs() {
		return upcs;
	}
	public void setUpcs(String upcs) {
		this.upcs = upcs;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public long getCategoryld() {
		return categoryId;
	}
	public void setCategoryld(long categoryld) {
		this.categoryId = categoryld;
	}
	public long getStoreld() {
		return storeId;
	}
	public void setStoreld(long storeld) {
		this.storeId = storeld;
	}

	@Override
	public String toString(){

		return "{ ProductId:"+productId
				+", Title: "+title
				+", upcs: "+upcs
				+", categoryId: "+categoryId
				+", storyId: "+storeId
				+", seller: "+seller
				+", timestamp: "+timestamp
				+", price: "+price+" }";
	}}
