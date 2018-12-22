package com.crx.model;

public class Goods {
  private int id;
  private String name;
  private String address;
  private String size;
  private String batch;
  private String describe;
  private double price;
  private String status;
  private Supplier sup;
  private int store;
  private String demo;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getSize() {
	return size;
}
public void setSize(String size) {
	this.size = size;
}
public String getBatch() {
	return batch;
}
public void setBatch(String batch) {
	this.batch = batch;
}
public String getDescribe() {
	return describe;
}
public void setDescribe(String describe) {
	this.describe = describe;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public Supplier getSup() {
	return sup;
}
public void setSup(Supplier sup) {
	this.sup = sup;
}
public String getDemo() {
	return demo;
}
public void setDemo(String demo) {
	this.demo = demo;
}

public int getStore() {
	return store;
}
public void setStore(int store) {
	this.store = store;
}
public Goods(int id, String name, String address, String size, String batch, String describe, double price,
		String status, Supplier sup,int store, String demo) {
	super();
	this.id = id;
	this.name = name;
	this.address = address;
	this.size = size;
	this.batch = batch;
	this.describe = describe;
	this.price = price;
	this.status = status;
	this.sup = sup;
	this.store=store;
	this.demo = demo;
}
public Goods() {
	super();
	// TODO Auto-generated constructor stub
}
  
}
