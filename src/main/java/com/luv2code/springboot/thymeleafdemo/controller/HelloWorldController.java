package com.luv2code.springboot.thymeleafdemo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;


@RestController
@CrossOrigin("http://localhost:4200")
public class HelloWorldController {
	
	
	private EmployeeService producer; 
	
	@Autowired
	public HelloWorldController(EmployeeService producer) {
		
		this.producer = producer;
	}
	
	@GetMapping("/hello")
	public String hello(){
	
		return "Hello World";
	}
	
	@GetMapping("/hello-world/{name}")
	public String helloworld(@PathVariable String name){
		String hello= "welcome "+ name;
		return hello;
	}

	
	@GetMapping("/proc")
	public List<Employee> ProducerDetails(){
		
		List<Employee> theEmployees =producer.findAll();
		
		
		return theEmployees;
	}
	
	@DeleteMapping("/proc/{id}")
	public ResponseEntity<Void> DeleteProducer(@PathVariable int id){
		 producer.deleteById(id);
		 return ResponseEntity.noContent().build();
//		if(proc!=null) {
//			return ResponseEntity.noContent().build();
//		}
//		else
//		{
//			return ResponseEntity.notFound().build();
//		}
	}
	
	@GetMapping("/Update/{id}")
	public Employee GetProducer(@PathVariable int id) {
		return producer.findById(id);
		
	}
	
	
	@PutMapping("/Update/{id}")
	public ResponseEntity<Employee> SaveProducer(@PathVariable int id,
			@RequestBody Employee Employee1){
		
		Employee employee=  producer.save(Employee1);
	
		return new ResponseEntity<Employee>(employee,HttpStatus.OK);
		
	}
	@PostMapping("/Update")
	public ResponseEntity<Void> PostProducer(@RequestBody Employee proc){
		
		Employee Created=  producer.save(proc);
		
		URI url= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(Created.getId())
		.toUri();
		
		return ResponseEntity.created(url).build();
		
	}
	



	
}
