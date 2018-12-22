package com.crx.model;

public class Page {
	private int start;
	private int size;
	private String field;
	private String sort;
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public Page(int start, int size, String field, String sort) {
		super();
		this.start = start;
		this.size = size;
		this.field = field;
		this.sort = sort;
	}
	public Page() {
		super();
		// TODO Auto-generated constructor stub
	}

}
