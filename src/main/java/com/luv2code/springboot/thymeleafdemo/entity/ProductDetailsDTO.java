package com.luv2code.springboot.thymeleafdemo.entity;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ProductDetailsDTO {


		@Override
	public String toString() {
		return "ProductDetailsDTO [name=" + name + ", unique=" + unique + ", price=" + price + ", AvailableQuantity="
				+ AvailableQuantity + ", picByte=" + Arrays.toString(picByte) + "]";
	}

		@JsonProperty
		private String name;

		@JsonProperty
		private int unique;

		@JsonProperty
		private double price;

		@JsonProperty
		private int AvailableQuantity;

		@JsonProperty
		private byte[] picByte;



		public ProductDetailsDTO(String name, int unique, double price, int availableQuantity, byte[] picByte) {
			this.name = name;
			this.unique = unique;
			this.price = price;
			AvailableQuantity = availableQuantity;
			this.picByte = picByte;
		}

		public byte[] getPicByte() {
			return picByte;
		}

		public void setPicByte(byte[] picByte) {
			this.picByte = picByte;
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


