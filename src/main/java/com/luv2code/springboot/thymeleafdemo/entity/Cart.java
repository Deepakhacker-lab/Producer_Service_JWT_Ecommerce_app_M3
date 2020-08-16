package com.luv2code.springboot.thymeleafdemo.entity;

public class Cart {
	
public Cart() {
	
}


private int uniqueCode;

public int getUnique() {
	return uniqueCode;
}

public void setUnique(int unique) {
	this.uniqueCode = unique;
}

private int quantity;

public int getQuantity() {
	return quantity;
	
}
public Cart(int uniqueCode, int quantity) {
	super();
	this.uniqueCode = uniqueCode;
	this.quantity = quantity;
}

public void setQuantity(int quantity) {
	this.quantity=quantity;
}




}
