package com.example.user.service.impl;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.user.dao.VerificationRepository;
import com.example.user.model.Verification;
import com.example.user.service.VerificationService;

@Service
public class VerificationServiceImpl implements VerificationService {

	@Autowired
	private VerificationRepository verificationRepository;

	@Override
	public List<Verification> checkEmail(String email, String code) {

		return verificationRepository.checkEmail(email, code);
	}

	@Override
	@Transactional
	public Integer Mailboxstatus(String email) {

		return verificationRepository.Mailboxstatus(email);
	}

	@Override
	public List<Verification> singleData(String email_id) {

		return verificationRepository.singleData(email_id);
	}

	@Override
	public List<Verification> selectAllData() {

		return verificationRepository.findAll();
	}

	@Override
	public Long selectTime(String email) {
	
		return verificationRepository.selectTime(email);
	}

}
