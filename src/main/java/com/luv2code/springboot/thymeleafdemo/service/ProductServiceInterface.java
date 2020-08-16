package com.luv2code.springboot.thymeleafdemo.service;

import java.util.List;

import com.luv2code.springboot.thymeleafdemo.entity.ProductDetailsDTO;

public interface ProductServiceInterface {

	public List<ProductDetailsDTO> GetAllProducts(List<ProductDetailsDTO> list);
}
