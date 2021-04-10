package com.example.user.service;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.servlet.ModelAndView;
//
//import com.example.user.model.CardStatus;
import com.example.user.model.Payment;
import com.example.user.model.PersonalCenter;
//import com.example.user.model.Storedvalue;
import com.example.user.model.UserCard;

public interface UserService {
	/**
	 * 查詢所有
	 * 使用者
	 */
	List<PersonalCenter> selectAll();

	/**
	 * 註冊
	 * 
	 * @param personalCenter
	 * @return
	 */
	PersonalCenter insert(PersonalCenter personalCenter);

//	/**
//	 * email 驗證通過 狀態為通過驗證狀態
//	 **/
//	List<PersonalCenter> checkEmail(String email, String code);

	/**
	 * 信箱通過檢查 手機發送驗證碼
	 * 
	 * @param email
	 * @param code
	 * @param phone
	 * @return
	 */
	Integer updatePhoneCode(String email, String phone, String code);
	
	
	/**
	 * 重新發送驗證碼
	 */
	Integer reEmailCode(String email,String code);
	
	
	
	
	
	
	
	
	
	
//	Integer checkPhone(String email, String phone, String code);

//	Integer upDateEmail(String email);

//	/**
//	 * 手機發送驗證碼前 必須先通過信箱驗證
//	 * 
//	 * @param phone
//	 * @param email
//	 * @return
//	 */
//	List<PersonalCenter> checkPhone(String email);

//	/**
//	 * 驗證碼檢查
//	 * 
//	 * @param phone
//	 * @param code
//	 * @return
//	 */
//	List<PersonalCenter> checkPhone(String phone, String code);
//
//	Integer mobilePhoneInspectionCodeInspection(String email, String phone, String code);
	
	/**
	 * 驗證碼
	 * 手機狀態
	 * 通過修改
	 * @param email
	 * @param phone
	 * @param code
	 * @return
	 */
	Integer checkPhoneCode_1(String email);
	

	List<PersonalCenter> login(String email, String password);

	/**
	 * 登入 信箱未驗證
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	List<PersonalCenter> emailNotVerified(String email, String password);

	/**
	 * 登入 手機未通過 驗證
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	List<PersonalCenter> phoneIsNotVerified(String email, String password);

	/**
	 * 信箱密碼錯誤
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	List<PersonalCenter> lastCheckLogin(String email, String password);

//	/**
//	 * 忘記密碼 信箱發送檢查
//	 * 
//	 * @param email
//	 * @param password
//	 * @return
//	 */
//	List<PersonalCenter> forgotPasswordMailbox(String email);

//	/**
//	 * 忘記密碼 手機發送檢查
//	 * 
//	 * @param email
//	 * @param password
//	 * @return
//	 */
//	List<PersonalCenter> forgotPasswordPhone(String phone);

//	/**
//	 * 發送驗證碼 信箱
//	 * 
//	 * @param email
//	 * @param code
//	 * @return
//	 */
//	Integer forgotPasswordMailboxUpdate(String email, String code);

	/**
	 * 查詢信箱
	 * 
	 * @param phone
	 * @return
	 */
	PersonalCenter findByEmail(String email);

	/**
	 * 查詢手機
	 * 
	 * @param phone
	 * @return
	 */
	PersonalCenter findByPhone(String phone);

	/**
	 * 檢查信箱 及驗證碼是否正確
	 * 
	 * @param email
	 * @param code
	 * @return
	 */
	List<PersonalCenter> forgotYourPassword(String email, String code);

	/**
	 * 信箱讓你輸入新的密碼
	 * 
	 * @param email
	 * @param code
	 * @param password
	 * @return
	 */
	Integer forgotPasswordMailboxNewPassword(String email, String password);

//	/**
//	 * 手機驗證碼修改
//	 * 
//	 * @param phone
//	 * @param code
//	 * @return
//	 */
//	Integer forgotPasswordPhoneUpdate(String phone, String code);

	/**
	 * 手機驗證碼 並輸入新密碼
	 * 
	 * @param phone
	 * @param password
	 * @return
	 */
	Integer forgotPasswordMobilePhoneVerificationEnterNewPassword(String phone, String password);

	/**
	 * 檢手機驗證碼是否正確
	 * 
	 * @param phone
	 * @param code
	 * @return
	 */
	List<PersonalCenter> forgotPasswordMobilePhoneVerificationCheck(String phone, String code);

//	void sendVerificationLetter(String email);
//
//	void iwantaverificationcodeformyphone(String phone);

	/**
	 * 產生訂單
	 * 
	 * @param personalCenter
	 * @param payment
	 * @param request
	 * @param userCard
	 * @return
	 */
	void insertOrder(PersonalCenter personalCenter, Payment payment, HttpServletRequest request, UserCard userCard);

	/**
	 * 儲值紀錄
	 * 
	 * @param personalCenter
	 * @param storedvalue
	 * @param request
	 */
	public void storedValue(PersonalCenter personalCenter, Payment payment, HttpServletRequest request);

	List<PersonalCenter> sortresult(String email);
	/**
	 * 扣除此筆
	 * 交易的金額
	 * 
	 * @param email
	 * @param fishpondwallet
	 * @return
	 */
	Integer modifyAccountAmount(String email, int fishpondwallet);
	
	
	/**
	 * 手機發送
	 * 驗證碼
	 * 
	 * @param phone
	 */
	public void verificationcodeForMyPhone(String phone,String email);
	
	Integer codemyPhoneUpdate(String email_id, String phone, String code_phone);

	/**
	 * 手機驗證信 山竹
	 */
	Integer checkPhone(String email, String phone, String code);
	
	
	List<PersonalCenter> checkPhoneCode(String email,String code);
	
	/**
	 * 儲值驗證
	 * 通過
	 * @param id
	 * @param fishpondwallet
	 * @param time
	 * @return
	 */
	Integer storedValueOrder(String id,int fishpondwallet,long time);
	
	/**
	 * 儲值驗證
	 * 駁回
	 * @param id
	 * @return
	 */
	Integer rejectreview(String id);
	

}
