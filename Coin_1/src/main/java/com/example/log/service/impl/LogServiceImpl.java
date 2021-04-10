package com.example.log.service.impl;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.log.dao.LogRepository;
import com.example.log.model.Log;
import com.example.user.dao.UserRepository;
import com.example.user.model.Payment;
import com.example.user.model.PersonalCenter;
import com.example.user.model.Random;

import com.example.log.service.LogService;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogRepository logRepository ;

	@Autowired
	private LogTx logTx;

	/**
	 * 會員註冊 發送驗證信
	 */

	@Override
	public Log insert(Log log) {
		Log result=null;
		logRepository.save(log);

		result=logTx.create(log);
		// 容器

		return result;
	}




}
