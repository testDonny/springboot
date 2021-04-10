package com.example.user.service;

import java.util.List;
import java.util.Set;

import com.example.user.model.Verification;


public interface VerificationService {

	
	Integer Mailboxstatus(String email);
	
	
	List<Verification> checkEmail(String email,String code);
	
	
	List<Verification> singleData(String email_id);
	
	List<Verification> selectAllData();
	
	Long selectTime(String email);
}
