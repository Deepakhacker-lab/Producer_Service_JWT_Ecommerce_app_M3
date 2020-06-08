package com.luv2code.springboot.thymeleafdemo.jwt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.annotation.Secured;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Secured("ROLE_ADMIN")
public class Swagger {
	
	public static final Contact DEFAULT_CONTACT =
			new Contact("Deepak", "sai", "Deepakp14@hotmail.com");
	
	  @SuppressWarnings("rawtypes")
	public static final ApiInfo API_Info = 
			  new ApiInfo("Producer Api Documentation", "{Producer Api Documentation", "1.0", "urn:tos",
	          DEFAULT_CONTACT, "Apache 2.0", 
	          "http://www.apache.org/licenses/LICENSE-2.0",
	          new ArrayList<VendorExtension>());

	private static final Set<String> Default_Application =
			new HashSet<String>(Arrays.asList("Application.json","Application.xml"));


	

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(API_Info).produces(Default_Application);
	}
}
