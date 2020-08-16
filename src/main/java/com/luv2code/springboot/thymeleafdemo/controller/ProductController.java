package com.luv2code.springboot.thymeleafdemo.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.luv2code.springboot.thymeleafdemo.dao.UserDao;
import com.luv2code.springboot.thymeleafdemo.entity.Cart;
import com.luv2code.springboot.thymeleafdemo.entity.DAOUser;
import com.luv2code.springboot.thymeleafdemo.entity.ProductDetails;
import com.luv2code.springboot.thymeleafdemo.entity.ProductDetailsDTO;
import com.luv2code.springboot.thymeleafdemo.service.ProductServiceInterface;



@RestController
public class ProductController {
	
	@Value("${RDS_HOSTNAME:localhost}")
	private String host;
	
    @Autowired
    LoadBalancerClient loadBalancerClient;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProductServiceProxy productproxy;
	
	@Autowired
	private ProductServiceInterface product;
	
	@Autowired
	private UserDao user;

	
	@GetMapping("/products/getallproducts")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<ProductDetailsDTO> getAllProducts() {
		List<ProductDetailsDTO> pd= productproxy.retrieveExchangeValue();
		
		List<ProductDetailsDTO> list =product.GetAllProducts(pd);
		logger.info("Request for list has been successful");
		return list;
	}
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}
	
	
	@PostMapping(value="/products/add", consumes = "multipart/form-data")
	@ResponseBody

	public ResponseEntity<StringResponse>  addProduct(@RequestPart("file") MultipartFile file, @RequestPart("info") ProductDetails model) throws IOException {
		logger.info("Inside Controller");
		
		ServiceInstance serviceinstance = loadBalancerClient.choose("Product-Service");
		
			ProductDetailsDTO prod = new ProductDetailsDTO(model.getName(),
					model.getUnique(),
					model.getPrice(),
					model.getAvailableQuantity(),
					file.getBytes());
			logger.info("Inside Try catch "+ prod);
			HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    	
//		    HttpEntity<ProductDetailsDTO> entity = new  HttpEntity<ProductDetailsDTO>(prod,headers);
//		    ResponseEntity<StringResponse> ResponseEntity = new RestTemplate()
//		    		.postForEntity("http://localhost:8100/products/add", entity, StringResponse.class);
		    
		    //docker
		    HttpEntity<ProductDetailsDTO> entity = new  HttpEntity<ProductDetailsDTO>(prod,headers);
		    

		    ResponseEntity<StringResponse> Responseentity = new RestTemplate()
		    		.postForEntity("http://"+serviceinstance.getHost()+":"+serviceinstance.getPort()+"/products/add",
		    				entity, StringResponse.class);

		    return new ResponseEntity<>(Responseentity.getBody(),Responseentity.getStatusCode()); 		
		
		
		
	   
	} 
	
	// add to cart
	@PostMapping("/products/add/cart")
	public ResponseEntity<StringResponse> addToCart(@RequestBody List<ProductDetails> model) {
		
		ServiceInstance serviceinstance = loadBalancerClient.choose("Product-Service");
		List<Cart> obj = new ArrayList<>();
		String username;
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {

			username = ((UserDetails)principal).getUsername();

		} else {

		   username = principal.toString();

		}
		
		DAOUser daoUser = user.findByUsername(username);
		
		if(daoUser!=null) {
		}
		
		for(ProductDetails c1 : model) {
			obj.add(new Cart(c1.getUnique(), c1.getBookingQuantity()));

		}
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    	
	    HttpEntity<List<Cart>> entity = new  HttpEntity<List<Cart>>(obj,headers);

	    ResponseEntity<StringResponse> ResponseEntity = new RestTemplate()
	    		.postForEntity("http://"+serviceinstance.getHost()+":"+serviceinstance.getPort()+"/products/add/cart/{username}/{email}", 
	    				entity, StringResponse.class,username,daoUser.getEmail());

	    	return new ResponseEntity<>(ResponseEntity.getBody(), ResponseEntity.getStatusCode());
	}
	
// get cart list
	@GetMapping("/products/getallproducts/cart")
	public List<ProductDetails> getCartList() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username;
		if (principal instanceof UserDetails) {

			username = ((UserDetails)principal).getUsername();

		} else {

		   username = principal.toString();

		}
		
		DAOUser daoUser = user.findByUsername(username);
		
		List<ProductDetails> pd= productproxy.getCartList(daoUser.getEmail());
		
		
		
		logger.info("Request for list has been successful");
		return pd;
	}
	
}
