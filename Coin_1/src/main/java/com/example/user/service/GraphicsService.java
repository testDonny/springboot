package com.example.user.service;

import java.util.List;

import com.example.user.model.GraphicsCardAll;
import com.example.user.model.PersonalCenter;

public interface GraphicsService {

	GraphicsCardAll insert(GraphicsCardAll graphicsCardAll);

	List<GraphicsCardAll> selectAll();
	
	List<GraphicsCardAll> findByName(String name);

	List insert(List<String> list);
	
	GraphicsCardAll savedate(GraphicsCardAll graphicsCardAll);
	


}
