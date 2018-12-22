package com.crx.model;

public class Account {
  private int id;
  private Goods goods;
  private String time;
  private int num;
  private String type;
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
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}
public int getNum() {
	return num;
}
public void setNum(int num) {
	this.num = num;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public Account(int id, Goods goods, String time, int num, String type) {
	super();
	this.id = id;
	this.goods = goods;
	this.time = time;
	this.num = num;
	this.type = type;
}
public Account() {
	super();
	// TODO Auto-generated constructor stub
}
  
}
