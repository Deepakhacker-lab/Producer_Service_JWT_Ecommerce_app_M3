package com.luv2code.springboot.thymeleafdemo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.springframework.stereotype.Service;

import com.luv2code.springboot.thymeleafdemo.entity.ProductDetailsDTO;

@Service
public class ProductService implements ProductServiceInterface{
	
	

	
	public List<ProductDetailsDTO> GetAllProducts(List<ProductDetailsDTO> list){
		return  list.stream()
				.map(x-> new ProductDetailsDTO(x.getName(),x.getUnique(),x.getPrice(),x.getAvailableQuantity(),
						decompressBytes(x.getPicByte())))
				.collect(Collectors.toList());
		
		
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

}
