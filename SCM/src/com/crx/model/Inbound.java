package com.crx.model;

public class Inbound {
private int id;
private Purchase pur;
private String indate;
private int num;
private String demo;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Purchase getPur() {
	return pur;
}
public void setPur(Purchase pur) {
	this.pur = pur;
}
public String getIndate() {
	return indate;
}
public void setIndate(String indate) {
	this.indate = indate;
}
public int getNum() {
	return num;
}
public void setNum(int num) {
	this.num = num;
}
public String getDemo() {
	return demo;
}
public void setDemo(String demo) {
	this.demo = demo;
}
public Inbound(int id, Purchase pur, String indate, int num, String demo) {
	super();
	this.id = id;
	this.pur = pur;
	this.indate = indate;
	this.num = num;
	this.demo = demo;
}
public Inbound() {
	super();
	// TODO Auto-generated constructor stub
}

}
