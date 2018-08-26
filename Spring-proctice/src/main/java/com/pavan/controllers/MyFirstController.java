package com.pavan.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.model.Course;

@RestController
//@RequestMapping("/demo")
public class MyFirstController {
	
	/*@RequestMapping(value = "/post", method = RequestMethod.POST)
	public ResponseEntity<Course> getRequest(@RequestBody  Course course) {
		
		System.out.println("course : "+course.toString());
		
		return new ResponseEntity<>(course, HttpStatus.ACCEPTED);
		
	}*/
	@RequestMapping(value="/")
	public String getText() {
		//System.out.println("sdfjkjf");
		return "test";
	}

}
