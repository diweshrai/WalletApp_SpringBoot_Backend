package com.example.demo.EnumData;

public enum CustomerType {

	FREE("Free"),
	BASIC("Basic"),
	PREMIUM("Premium");
	
	private String cusType;

	CustomerType(String cusType) {
		this.cusType=cusType;
	}

	public String getCusType() {
		return cusType;
	}

	public void setCusType(String cusType) {
		this.cusType = cusType;
	}

}
