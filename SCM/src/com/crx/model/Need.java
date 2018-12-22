package com.crx.model;

public class Need {
   private int id;
   private Goods goods;
   private int num;
   private String stopdate;
   private String status;
   private String demo;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Goods getGoods() {
	return goods;
}
public void setGoods(Goods goods) {
	this.goods = goods;
}
public int getNum() {
	return num;
}
public void setNum(int num) {
	this.num = num;
}
public String getStopdate() {
	return stopdate;
}
public void setStopdate(String stopdate) {
	this.stopdate = stopdate;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getDemo() {
	return demo;
}
public void setDemo(String demo) {
	this.demo = demo;
}
public Need(int id, Goods goods, int num, String stopdate, String status, String demo) {
	super();
	this.id = id;
	this.goods = goods;
	this.num = num;
	this.stopdate = stopdate;
	this.status = status;
	this.demo = demo;
}
public Need() {
	super();
	// TODO Auto-generated constructor stub
}
   
   
}
