package com.example.user.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.user.dao.UserRepository;
import com.example.user.model.PersonalCenter;

@Service
public class UserTx {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public PersonalCenter create(PersonalCenter personalCenter) {
		return userRepository.save(personalCenter);
	}
}
