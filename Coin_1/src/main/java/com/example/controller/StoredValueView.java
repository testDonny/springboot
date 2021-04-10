package com.example.controller;



import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.user.model.Payment;
import com.example.user.model.PersonalCenter;
import com.example.user.service.UserService;

@RestController
@RequestMapping("/coin/user")
public class StoredValueView {
	//首頁
	private final String index = "http://localhost:10084/coin/user/gotomyindex";

	@Autowired
	private UserService userService;

	/**
	 * 儲值 畫面
	 * 
	 * @return
	 */

	@GetMapping(value = "/gotostoredvalue")
	public ModelAndView gotostoredValue() {
		ModelAndView modelAndView = new ModelAndView("storedvalue");
		return modelAndView;
	}

	/**
	 * 儲值 判斷
	 * 
	 * @param personal
	 * @param payment
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/storedvalue")
	public PersonalCenter storedvalue(PersonalCenter personalCenter, Payment payment, HttpServletRequest request,HttpServletResponse response) {

		userService.storedValue(personalCenter, payment, request);
		
		try {
			response.sendRedirect(index);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	
	/**
	 * 提領 畫面
	 * 
	 * @return
	 */

	@GetMapping(value = "/gotowithdrawal")
	public ModelAndView gotowithdrawal() {
		ModelAndView modelAndView = new ModelAndView("withdrawal");
		return modelAndView;
	}
	

	/**
	 * 提領判斷
	 */
	@PostMapping(value = "/withdrawalmoney")
	public PersonalCenter withdrawalmoney(PersonalCenter personalCenter, Payment payment, HttpServletRequest request,HttpServletResponse response) {

		userService.storedValue(personalCenter, payment, request);
		
		try {
			response.sendRedirect(index);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
