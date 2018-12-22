package com.crx.model;

public class Area {
  private int id;
  private Goods goods;
  private String name;
  private double minprice;
  private double maxprice;
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
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public double getMinprice() {
	return minprice;
}
public void setMinprice(double minprice) {
	this.minprice = minprice;
}
public double getMaxprice() {
	return maxprice;
}
public void setMaxprice(double maxprice) {
	this.maxprice = maxprice;
}
public String getDemo() {
	return demo;
}
public void setDemo(String demo) {
	this.demo = demo;
}
public Area(int id, Goods goods, String name, double minprice, double maxprice, String demo) {
	super();
	this.id = id;
	this.goods = goods;
	this.name = name;
	this.minprice = minprice;
	this.maxprice = maxprice;
	this.demo = demo;
}
public Area() {
	super();
	// TODO Auto-generated constructor stub
}
  
  
}
