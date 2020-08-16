package com.luv2code.springboot.thymeleafdemo.controller;

import java.util.List;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.luv2code.springboot.thymeleafdemo.entity.ProductDetails;
import com.luv2code.springboot.thymeleafdemo.entity.ProductDetailsDTO;



@FeignClient(name="Product-Service")
//@RibbonClient(name="Product-Service")
//@FeignClient(name="zuulserver")
@LoadBalancerClient(name="Product-Service")
public interface ProductServiceProxy {
	
	@GetMapping("/GetAllProducts")
	public  List<ProductDetailsDTO> retrieveExchangeValue();

	@GetMapping("/GetAllProducts/cart/{email}")
	public List<ProductDetails> getCartList(@PathVariable String email);
	
}
