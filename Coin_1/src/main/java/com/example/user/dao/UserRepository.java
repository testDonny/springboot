package com.example.user.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.user.model.Payment;
import com.example.user.model.PersonalCenter;
import com.example.user.model.Verification;

@Repository
public interface UserRepository
		extends JpaRepository<PersonalCenter, String>, JpaSpecificationExecutor<PersonalCenter> {

	/**
	 * 信箱 驗證碼 檢查
	 * 
	 * @param email
	 * @param code
	 * @return
	 */
	@Modifying
	// hql
	@Query(nativeQuery = true, value = "select p.*,e.* from personal_center p join verification e on e.code_email=?2 and e.email_id=?1")
	// 實體名稱非table名稱 //參數對應
	List<PersonalCenter> checkEmail(String email, String code);

	/**
	 * 修改信箱狀態
	 * 
	 * @param email
	 * @return
	 */
	@Modifying
	// hql
	@Query(nativeQuery = true, value = "update personal_center p join verification e  set e.status_email =1 where e.email_id=?1 ")
	// 實體名稱非table名稱 //參數對應
	Integer upDateEmail(String email);

	/**
	 * 手機驗證 送出驗證碼
	 */
	@Modifying(clearAutomatically = true)
	// hql
	@Query(nativeQuery = true, value = "update personal_center p join verification c set p.phone=?2 , c.code_phone=?3 where c.email_id=?1 and c.status_email=1")
	Integer updatePhoneCode(String email, String phone, String code);

	/**
	 * 手機驗證碼 檢查
	 */

	@Modifying(clearAutomatically = true)
	// hql
	@Query(nativeQuery = true, value = "select p.*,v.* from personal_center p join verification v on v.email_id=?1 and v.code_phone=?2")
	List<PersonalCenter> checkPhoneCode(String email, String code);

	/**
	 * 手機驗證 驗證碼修改狀態 為驗證通過
	 */
	@Modifying(clearAutomatically = true)
	// hql
	@Query(nativeQuery = true, value = "update personal_center p join verification c set c.status_phone=1 where c.email_id=?1 and c.status_email=1")
	Integer checkPhoneCode_1(String email);

//	/**
//	 * 手機驗證 必須通過信箱驗證
//	 */
//	@Modifying
//	// hql
//	@Query(nativeQuery = true, value = "select p.*,e.* from personal_center p join verification_email c where c.status_phone=1 and c.email_id=?1")
//	// 實體名稱非table名稱 //參數對應
//	List<PersonalCenter> checkPhone(String email);
//
//	/**
//	 * 手機驗證碼檢查
//	 * 
//	 * @param email
//	 * @return
//	 */
//	@Modifying
//	// hql
//	@Query(nativeQuery = true, value = "select p.*,e.* from personal_center p join verification e  where p.phone=?1 and e.code_phone=?2 and e.status_phone=0")
//	// 實體名稱非table名稱 //參數對應
//	List<PersonalCenter> checkPhone(String phone, String code);

//	/**
//	 * 手機驗證判斷是否成功
//	 */
//
//	@Modifying
//	// hql
//	@Query(nativeQuery = true, value = "update personal_center p join verification e set p.phone=?2, e.status_phone=1  where p.email=?1 and c.status_email=1 and e.status_phone=0 and e.code_phone=?3")
//	// 實體名稱非table名稱 //參數對應
//	Integer mobilePhoneInspectionCodeInspection(String email, String phone, String code);

	/**
	 * 信箱 密碼檢查 是否通過驗證
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	@Modifying
	// hql
	@Query(nativeQuery = true, value = "select p.*,e.* from personal_center p join verification e  where e.email_id=?1 and e.status_email=1 and e.status_phone=1 and p.password=?2")
	List<PersonalCenter> login(String email, String password);

	@Modifying
	// hql
	@Query(nativeQuery = true, value = "select p.*,e.* from personal_center p join verification e  where e.email_id=?1 and e.status_email=0 and e.status_phone=0 and p.password=?2")
	List<PersonalCenter> emailNotVerified(String email, String password);

	@Modifying
	// hql
	@Query(nativeQuery = true, value = "select p.*,e.* from personal_center p join verification e  where e.email_id=?1 and e.status_phone=0 and e.status_email=1 and p.password=?2")
	List<PersonalCenter> phoneIsNotVerified(String email, String password);

	@Modifying
	// hql
	@Query(nativeQuery = true, value = "select p.* from personal_center p  where p.email=?1 and p.password=?2")
	List<PersonalCenter> lastCheckLogin(String email, String password);

//	/**
//	 * 信箱 忘記密碼 發送新驗證碼 檢查
//	 * 
//	 * @param email
//	 * @param password
//	 * @return
//	 */
//	@Modifying
//	// hql
//	@Query(nativeQuery = true, value = "select p.* from personal_center p  where p.email=?1")
//	List<PersonalCenter> forgotPasswordMailbox(String email);
//
//	/**
//	 * 手機 忘記密碼 發送新驗證碼 檢查
//	 * 
//	 * @param email
//	 * @param password
//	 * @return
//	 */
//	@Modifying
//	// hql
//	@Query(nativeQuery = true, value = "select p.* from personal_center p  where p.phone=?1")
//	List<PersonalCenter> forgotPasswordPhone(String phone);
//
//	/**
//	 * 忘記密碼 信箱 發送驗證碼
//	 * 
//	 * @param email
//	 * @param phone
//	 * @param code
//	 * @return
//	 */
//	@Modifying(clearAutomatically = true)
//	// hql
//	@Query(nativeQuery = true, value = "update personal_center p join verification e set c.code=?2 where p.email=?1")
//	// 實體名稱非table名稱 //參數對應
//	Integer forgotPasswordMailboxUpdate(String email, String code);
//
//	/**
//	 * 忘記密碼 手機 發送驗證碼
//	 * 
//	 * @param email
//	 * @param phone
//	 * @param code
//	 * @return
//	 */
//	@Modifying(clearAutomatically = true)
//	// hql
//	@Query(nativeQuery = true, value = "update personal_center p join verification_phone e join verification_phone c set c.code=?2 where p.phone=?1")
//	// 實體名稱非table名稱 //參數對應
//	Integer forgotPasswordPhoneUpdate(String phone, String code);

	/**
	 * 查詢信箱
	 * 
	 * @param email
	 * @return
	 */
	PersonalCenter findByEmail(String email);

	/**
	 * 重新發送驗證碼
	 */
	@Modifying(clearAutomatically = true)
	// hql
	@Query(nativeQuery = true, value = "update personal_center p join verification v set v.code_email=?2 where v.email_id=?1")
	Integer reEmailCode(String email, String code);

//	/**
//	 * 排序
//	 * 
//	 * @param email
//	 * @param sort
//	 * @return
//	 */
//	PersonalCenter findByEmail(String email, Sort sort);

	/**
	 * 信箱通過驗證 發送驗證碼 手機
	 */
	@Modifying(clearAutomatically = true)
	// hql
	@Query(nativeQuery = true, value = "update personal_center p join verification v set v.code_phone=?3 where v.email_id=?1 and  p.phone=?2")
	Integer codemyPhoneUpdate(String email_id, String phone, String code_phone);

	/**
	 * 查詢手機
	 * 
	 * @param phone
	 * @return
	 */
	PersonalCenter findByPhone(String phone);

	/**
	 * 手機 驗證碼檢查
	 * 
	 * @param email
	 * @param code
	 * @return
	 */
	@Modifying
	// hql
	@Query(nativeQuery = true, value = "select p.* from personal_center p join verification c where p.phone=?1 and c.codePhone=?2")
	List<PersonalCenter> forgotPasswordMobilePhoneVerificationCheck(String phone, String code);

	/**
	 * 信箱 輸入新的密碼 輸入完驗證碼歸0 並設定條件code 至少必須輸入4碼 解決問題
	 * 
	 * @param email
	 * @param code
	 * @param password
	 * @return
	 */
	@Modifying(clearAutomatically = true)
	// hql
	@Query(nativeQuery = true, value = "update personal_center p join verification_email c set p.password=?2,c.code=0 where p.email=?1")
	Integer forgotPasswordMailboxNewPassword(String email, String password);

	/**
	 * 信箱 驗證碼檢查
	 * 
	 * @param email
	 * @param code
	 * @return
	 */
	@Modifying
	// hql
	@Query(nativeQuery = true, value = "select p.* from personal_center p join verification_email c where p.email=?1 and c.code=?2")
	List<PersonalCenter> forgotYourPassword(String email, String code);

	/**
	 * 輸入新的密碼及正確的手機驗證碼 輸入完驗證碼歸0 並設定條件code 至少必須輸入4碼 解決問題
	 * 
	 * @param phone
	 * @param code
	 * @param password
	 * @return
	 */
	@Modifying(clearAutomatically = true)
	// hql
	@Query(nativeQuery = true, value = "update personal_center p join verification_phone c set p.password=?2,c.code=0 where p.phone=?1 ")
	Integer forgotPasswordMobilePhoneVerificationEnterNewPassword(String phone, String password);

	/**
	 * 使用者查詢 自己的交易紀錄
	 * 
	 * @param email
	 * @return
	 */
	@Modifying
	// hql
	@Query(nativeQuery = true, value = "select p.* from personal_center p join payment c where c.email_id=?1")
	List<PersonalCenter> sortresult(String email);

	/**
	 * 扣除此筆 交易的金額
	 * 
	 * @param email
	 * @param fishpondwallet
	 * @return
	 */
	@Modifying(clearAutomatically = true)
	// hql
	@Query(nativeQuery = true, value = "update personal_center p set p.fishpondwallet=?2 where p.email=?1")
	Integer modifyAccountAmount(String email, int fishpondwallet);

	/**
	 * 儲值訂單 成功
	 */
	@Modifying(clearAutomatically = true)
	@Query(nativeQuery = true, value = "update personal_center p1 join payment p2 set p1.fishpondwallet=p1.fishpondwallet+?2,p2.approvalstatus=1,p2.provincialapprovaltime=?3 where p2.id=?1")
	Integer storedValueOrder(String id, int fishpondwallet, long time);

	/**
	 * 儲值訂單 駁回
	 */
	@Modifying(clearAutomatically = true)
	@Query(nativeQuery = true, value = "update personal_center p1 join payment p2 set p2.approvalstatus=2 where p2.id=?1")
	Integer rejectreview(String id);


}
