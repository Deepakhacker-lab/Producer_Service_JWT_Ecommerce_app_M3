package com.luv2code.springboot.thymeleafdemo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDetails {

	@JsonProperty
	private String name;

	@JsonProperty
	private int unique;

	@JsonProperty
	private double price;

	@JsonProperty
	private int AvailableQuantity;

	@JsonProperty
	private int bookingQuantity;
	
	@JsonProperty
	private byte[] picByte;

	
	public byte[] getPicByte() {
		return picByte;
	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}

	public ProductDetails(){
		
	}
//	public ProductDetails(String name, int unique, double price, int availableQuantity, MultipartFile picByte) {
//		this.name = name;
//		this.unique = unique;
//		this.price = price;
//		AvailableQuantity = availableQuantity;
//		this.picByte = picByte;
//	}

	public int getBookingQuantity() {
		return bookingQuantity;
	}

	public void setBookingQuantity(int bookingQuantity) {
		this.bookingQuantity = bookingQuantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUnique() {
		return unique;
	}

	public void setUnique(int unique) {
		this.unique = unique;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAvailableQuantity() {
		return AvailableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		AvailableQuantity = availableQuantity;
	}


}
