package com.jp.preauth.controller;

import java.security.Principal;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestContoller {
	
	
	
	@RequestMapping("/sayHello")
	public String sayHello(Principal princi,  @RequestHeader HttpHeaders headers, HttpServletRequest request, HttpServletResponse response){
		
		System.out.println(request.getSession().getAttribute("UserDetails"));
		
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(userDetails);
		
		
		//response.addCookie(new Cookie("foo", "bar"));

		
		request.getSession().setAttribute("UserDetails", userDetails);
		
		return "Hello World from preauth";
	}
	
	
}
