package com.bogaware.service.accounts.data;

public class BankTransactionLocation {
	private String address;
	private String city;
	private String state;
	private String zip;
	private Double lat;
	private Double lon;
	private String storeNumber;
	
	public BankTransactionLocation(String addLoc, String cityLoc, String stateLoc, String zipLoc, Double latLoc, Double lonLoc, String storeNum){
		address = addLoc;
		city = cityLoc;
		state = stateLoc;
		zip = zipLoc;
		lat = latLoc;
		lon = lonLoc;
		storeNumber = storeNum;
	}
	
	public String getAddress() {
	  return address;
	}
	
	public String getCity() {
	  return city;
	}
	
	public String getState() {
	  return state;
	}
	
	public String getZip() {
	  return zip;
	}
	
	public Double getLat() {
	  return lat;
	}
	
	public Double getLon() {
	  return lon;
	}
	
	public String getStoreNumber() {
	  return storeNumber;
	}
}
  
