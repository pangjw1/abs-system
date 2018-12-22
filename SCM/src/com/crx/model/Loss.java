package com.crx.model;

public class Loss {
  private int id;
  private Goods goods;
  private int num;
  private String lossdate;
  private String reason;
  private Purchase pur;
public Purchase getPur() {
	return pur;
}
public void setPur(Purchase pur) {
	this.pur = pur;
}
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
public String getLossdate() {
	return lossdate;
}
public void setLossdate(String lossdate) {
	this.lossdate = lossdate;
}
public String getReason() {
	return reason;
}
public void setReason(String reason) {
	this.reason = reason;
}
public Loss(int id, Goods goods, int num, String lossdate, String reason, Purchase pur) {
	super();
	this.id = id;
	this.goods = goods;
	this.num = num;
	this.lossdate = lossdate;
	this.reason = reason;
	this.pur = pur;
}
public Loss() {
	super();
	// TODO Auto-generated constructor stub
}

  
}
