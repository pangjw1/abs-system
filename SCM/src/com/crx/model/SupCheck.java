package com.crx.model;

public class SupCheck {
   private int id;
  // private int sid;
   private Supplier sup;
   private String price;
   private String sample;
   private String sat;
   private String delivery;
   private String f_data;
   private String quality;
   private String wdate;
   public String getWdate() {
	return wdate;
}
public void setWdate(String wdate) {
	this.wdate = wdate;
}
private String demo;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
/*public int getSid() {
	return sid;
}
public void setSid(int sid) {
	this.sid = sid;
}*/

public String getPrice() {
	return price;
}
public Supplier getSup() {
	return sup;
}
public void setSup(Supplier sup) {
	this.sup = sup;
}
public void setPrice(String price) {
	this.price = price;
}
public String getSample() {
	return sample;
}
public void setSample(String sample) {
	this.sample = sample;
}
public String getSat() {
	return sat;
}
public void setSat(String sat) {
	this.sat = sat;
}
public String getDelivery() {
	return delivery;
}
public void setDelivery(String delivery) {
	this.delivery = delivery;
}
public String getF_data() {
	return f_data;
}
public void setF_data(String f_data) {
	this.f_data = f_data;
}
public String getQuality() {
	return quality;
}
public void setQuality(String quality) {
	this.quality = quality;
}
public String getDemo() {
	return demo;
}
public void setDemo(String demo) {
	this.demo = demo;
}
public SupCheck() {
	super();
	// TODO Auto-generated constructor stub
}
public SupCheck(int id, Supplier sup, String price, String sample, String sat, String delivery, String f_data,
		String quality,String wdate,String demo) {
	super();
	this.id = id;
	this.sup = sup;
	this.price = price;
	this.sample = sample;
	this.sat = sat;
	this.delivery = delivery;
	this.f_data = f_data;
	this.quality = quality;
	this.wdate=wdate;
	this.demo = demo;
}
   
}
