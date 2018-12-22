package com.crx.model;

public class Supplier {
  private int id;
  private String name;
  private String address;
  private String tel;
  private String email;
  private String fax;
  private String linkman;
  private String link_tel;
  private String status;
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
public String getTel() {
	return tel;
}
public void setTel(String tel) {
	this.tel = tel;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getFax() {
	return fax;
}
public void setFax(String fax) {
	this.fax = fax;
}
public String getLinkman() {
	return linkman;
}
public void setLinkman(String linkman) {
	this.linkman = linkman;
}
public String getLink_tel() {
	return link_tel;
}
public void setLink_tel(String link_tel) {
	this.link_tel = link_tel;
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
public Supplier() {
	super();
	// TODO Auto-generated constructor stub
}
public Supplier(int id, String name, String address, String tel, String email, String fax, String linkman,
		String link_tel, String status, String demo) {
	super();
	this.id = id;
	this.name = name;
	this.address = address;
	this.tel = tel;
	this.email = email;
	this.fax = fax;
	this.linkman = linkman;
	this.link_tel = link_tel;
	this.status = status;
	this.demo = demo;
}
  
  
}
