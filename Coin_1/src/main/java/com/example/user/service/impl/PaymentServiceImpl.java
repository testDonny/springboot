package com.example.user.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.user.dao.PaymentRepository;
import com.example.user.model.Payment;
import com.example.user.model.PersonalCenter;
import com.example.user.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{

	@Autowired
	private PaymentRepository paymentRepository;
	
	@Override
	public List<Payment> select() {

		return paymentRepository.findAll();
	}

	@Override
	public List<Payment> sortresult(String email) {

		return paymentRepository.sortresult(email);
	}

	/**
	 * 儲值訂單
	 */
	@Override
	@Transactional
	public
	Integer storedValueOrder(String id) {

		return paymentRepository.storedValueOrder(id);
	}





}
