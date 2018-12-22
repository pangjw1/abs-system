package com.crx.model;

public class Sale {
  private int id;
  private Goods goods;
  private int num;
  private String outdate;
  private double price;
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
public String getOutdate() {
	return outdate;
}
public void setOutdate(String outdate) {
	this.outdate = outdate;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public String getDemo() {
	return demo;
}
public void setDemo(String demo) {
	this.demo = demo;
}
public Sale(int id, Goods goods, int num, String outdate, double price, String demo) {
	super();
	this.id = id;
	this.goods = goods;
	this.num = num;
	this.outdate = outdate;
	this.price = price;
	this.demo = demo;
}
public Sale() {
	super();
	// TODO Auto-generated constructor stub
}
  
}
