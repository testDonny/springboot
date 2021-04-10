package com.example.user.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.user.model.Payment;
import com.example.user.model.PersonalCenter;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, String>{


	@Modifying
	// hql
	@Query(nativeQuery = true, value = "select c.* from  payment c where c.email_id=?1")
	List<Payment> sortresult(String email);
	
	
	/**
	 * 儲值訂單
	 */
	@Modifying(clearAutomatically = true)
	@Query(nativeQuery = true, value = "update payment p set p.approvalstatus=1 where p.id=?1")
	Integer storedValueOrder(String id);
	

}
