package com.example.log.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.log.dao.LogRepository;
import com.example.log.model.Log;
import com.example.user.dao.UserRepository;
import com.example.user.model.PersonalCenter;

@Service
public class LogTx {

	@Autowired
	private LogRepository logRepository;

	@Transactional
	public Log create(Log log) {
		return logRepository.save(log);
	}
}
