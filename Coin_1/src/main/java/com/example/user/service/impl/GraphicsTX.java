package com.example.user.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.user.dao.GraphicsRepository;
import com.example.user.model.GraphicsCardAll;

@Service
public class GraphicsTX {

		@Autowired
	private  GraphicsRepository graphicsRepository;
		
		@Transactional
		public GraphicsCardAll create (GraphicsCardAll graphicsCardAll) {
			return graphicsRepository.save(graphicsCardAll);
			
		}
}
