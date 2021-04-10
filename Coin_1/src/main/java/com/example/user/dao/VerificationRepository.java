package com.example.user.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.user.model.Verification;



@Repository
public interface VerificationRepository extends JpaRepository<Verification, String> {

	/**
	 * 信箱驗證
	 * 狀態修改為
	 * 通過
	 */
	@Modifying(clearAutomatically = true)
	// hql
	@Query(nativeQuery = true, value = "update verification v set v.status_email=1 where email_id=?1 ")
	Integer Mailboxstatus(String email);
	
	/**
	 * 信箱驗證碼
	 * 檢查
	 */
	
	@Modifying(clearAutomatically = true)
	// hql
	@Query(nativeQuery = true, value = "select * from verification v where v.email_id=?1 and v.code_email=?2")
	List<Verification> checkEmail(String email,String code);
	

	
	
	/**
	 * 查詢狀態
	 * @param email_id
	 * @return
	 */
	@Modifying(clearAutomatically = true)
	// hql
	@Query(nativeQuery = true, value = "select * from verification v where v.email_id=?1")
	List<Verification> singleData(String email_id);
	
	/**
	 * 信箱驗證碼時間檢查
	 * @param email
	 * @return
	 */
	@Query(nativeQuery = true, value = "select email_time from verification v where v.email_id=?1")
	Long selectTime(String email);
	
	/**
	 * 信箱從新
	 * 發送驗證碼
	 */
	
	@Modifying(clearAutomatically = true)
	// hql
	@Query(nativeQuery = true, value = "update verification_email v set v.status_email=1 where email_id=?1 ")
	Integer resendTheVerificationCodeFromTheMailbox(String email);

	
}
