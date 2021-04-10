package com.example.user.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.user.model.Payment;
import com.example.user.model.PersonalCenter;

public interface PaymentService {

	
	List<Payment> select();
	
	List<Payment> sortresult(String email);
	
	Integer storedValueOrder(String id);
}
