package com.example.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.user.dao.UserCardRepository;
import com.example.user.model.UserCard;
import com.example.user.service.UserCardService;

@Service
public class UserCardServiceimpl implements UserCardService {

	@Autowired
	UserCardRepository userCardRepository;

	@Override
	public UserCard insert(UserCard userCard) {

		return userCardRepository.save(userCard);
	}

}
