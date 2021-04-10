package com.example.user.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.user.model.GraphicsCardAll;
import com.example.user.model.PersonalCenter;

@Repository
public interface GraphicsRepository extends JpaRepository<GraphicsCardAll, String> {

	
	List<GraphicsCardAll> findByName(String name);

	List save(List<String> list);


}
