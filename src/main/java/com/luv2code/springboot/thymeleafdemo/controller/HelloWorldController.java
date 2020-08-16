package com.luv2code.springboot.thymeleafdemo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@CrossOrigin("${RDS_HOSTNAME:localhost}")
public class HelloWorldController {

	
	private EmployeeService producer; 
	
	@Autowired
	public HelloWorldController(EmployeeService producer) {
		
		this.producer = producer;
	}
	

	
	
	@GetMapping("/hello-world/{name}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public StringResponse helloworld(@PathVariable String name){
		StringResponse hello= new StringResponse("welcome "+ name);
		return hello;
	}
	@GetMapping("/hello")
	public StringResponse hello(){
	
		return new StringResponse("Hello World");
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
	@PreAuthorize("hasAuthority('ADMIN')")
	public Employee GetProducer(@PathVariable int id) {
		return producer.findById(id);
		
	}
	
	
	@PutMapping("/Update/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Employee> SaveProducer(@PathVariable int id,
			@RequestBody Employee Employee1){
		
		Employee employee=  producer.save(Employee1);
	
		return new ResponseEntity<Employee>(employee,HttpStatus.OK);
		
	}
	@PostMapping("/Update")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> PostProducer(@RequestBody Employee proc){
		
		Employee Created=  producer.save(proc);
		
		URI url= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(Created.getId())
		.toUri();
		
		return ResponseEntity.created(url).build();
		
	}
	



	
}
