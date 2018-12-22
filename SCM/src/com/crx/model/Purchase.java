package com.crx.model;

public class Purchase {
  private int id;
  private Goods good;
  private double price;
  private int num;
  private String purdate;
  private String status;
  private String bills;
  private String demo;

public Purchase(int id, Goods good, double price, int num, String purdate, String status, String bills, String demo) {
	super();
	this.id = id;
	this.good = good;
	this.price = price;
	this.num = num;
	this.purdate = purdate;
	this.status = status;
	this.bills = bills;
	this.demo = demo;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public Goods getGood() {
	return good;
}

public void setGood(Goods good) {
	this.good = good;
}

public double getPrice() {
	return price;
}

public void setPrice(double price) {
	this.price = price;
}

public int getNum() {
	return num;
}

public void setNum(int num) {
	this.num = num;
}

public String getPurdate() {
	return purdate;
}

public void setPurdate(String purdate) {
	this.purdate = purdate;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getBills() {
	return bills;
}

public void setBills(String bills) {
	this.bills = bills;
}

public String getDemo() {
	return demo;
}

public void setDemo(String demo) {
	this.demo = demo;
}

public Purchase() {
	super();
	// TODO Auto-generated constructor stub
}

}
