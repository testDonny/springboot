package com.example.user.service.impl;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.user.dao.UserRepository;
//import com.example.user.model.CardStatus;
import com.example.user.model.Payment;
import com.example.user.model.PersonalCenter;
import com.example.user.model.Random;
//import com.example.user.model.Storedvalue;
import com.example.user.model.UserCard;
import com.example.user.model.Verification;
import com.example.user.service.UserService;

import lombok.var;

import com.example.user.dao.PaymentRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private UserTx userTx;

	/**
	 * 管理員查詢使用者
	 */
	@Override
	public List<PersonalCenter> selectAll() {

		return userRepository.findAll();
	}

	/**
	 * 會員註冊 發送驗證信 google
	 */

	@Override
	public PersonalCenter insert(PersonalCenter personalCenter) {
		PersonalCenter result = null;
		int check = check(personalCenter.getEmail(), personalCenter.getPassword());

		if (check == 1) {
			StringBuffer sbf = new StringBuffer();
			Random random = new Random();

			for (int x = 0; x <= 3; x++) {
				sbf.append(random.getRandom());
			}

			try {
				StringBuffer reqUrl = new StringBuffer();
				reqUrl.append(
						"https://script.google.com/macros/s/AKfycbyqAAshScl03QyuNqqH1HABZuPa3extGjXEa3GDWdS4fxByVfU/exec?");
				reqUrl.append("mail=" + personalCenter.getEmail());
				reqUrl.append("&code=" + sbf.toString());
				reqUrl.append("&CharsetURL=Big5");
				URL url = new URL(reqUrl.toString());
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setRequestMethod("GET");
				int code = urlConnection.getResponseCode();
			} catch (Exception e) {

			}

			
			
			
			PersonalCenter personalCenterContainer = new PersonalCenter();

			Set<Verification> verification = new HashSet<Verification>();
//			Set<VerificationPhone> verificationPhone = new HashSet<VerificationPhone>();

			// 先將東西放入容器

			Verification verification1 = new Verification();
//			VerificationPhone verificationPhone1 = new VerificationPhone();
			verification1.setCodeEmail(sbf.toString());
	
			
			Date dates = new Date();
			long mseconds = dates.getTime();
			mseconds+=60000;
			//現在時間加一分鐘驗證碼一分鐘發一次
			
			
			verification1.setEmailTime(mseconds);
			// 資料初始化放入

			verification.add(verification1);

			verification1.setPersonalCenter(personalCenterContainer);
			// 將資料放入最大的容器

			personalCenterContainer.setEmail(personalCenter.getEmail());

			personalCenterContainer.setVerification(verification);

			personalCenterContainer.setPassword(personalCenter.getPassword());

			personalCenterContainer.setWalletaddress(personalCenter.getWalletaddress());

			Date date = new Date();
			Long time = date.getTime();

			personalCenterContainer.setRegistrationtime(time);
			personalCenterContainer.getVerification().add(verification1);

			// 將所有東西放入最後要回傳的東西

			result = userTx.create(personalCenterContainer);

			System.out.println("驗證碼" + sbf.toString());
		}

		// 容器

		return result;
	}

	/**
	 * 信箱密碼 格式檢查
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	private int check(String email, String password) {
		try {
			if (email.trim().length() < 15 || email.trim().length() > 40) {
				return 0;
			} else if (password == null) {
				return 0;
			} else {

				if (password.trim().length() < 8 || password.trim().length() > 16) {
					return 0;
				} else {
					int count1 = 0;// 判斷大寫英文

					for (int x = 0; x < password.length(); x++) {
						char checkPassword = password.charAt(x);
						if (checkPassword >= 65 && checkPassword <= 90) {
							count1++;

						}
					}

					int count2 = 0; // 判斷小寫英文
					for (int x = 0; x < password.length(); x++) {
						char checkPassword = password.charAt(x);

						if (checkPassword >= 97 && checkPassword <= 122) {
							count2++;

						}
					}

					char specialSymbol[] = { '!', '@', '#', '$', '%', '^', '&', '*' };

					int count3 = 0; // 判斷特殊符號
					for (int x = 0; x < password.length(); x++) {
						char checkPassword = password.charAt(x);

						for (int y = 0; y < specialSymbol.length; y++) {

							if (checkPassword == specialSymbol[y]) {
								count3++;

							}
						}

					}

					int count4 = 0;
					char emptyString = ' ';

					for (int x = 0; x < password.length(); x++) {
						char checkPassword = password.charAt(x);

						if (checkPassword == emptyString) {
							count4++;
						}
					}

					if (count1 < 1 || count2 < 1 || count3 < 1 || count4 >= 1) {
						return 0;
					} else {
						return 1;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

	}

	/**
	 * 手機驗證信 山竹
	 */
	@Override
	@Transactional
	public Integer checkPhone(String email, String phone, String code) {
		StringBuffer sbf = new StringBuffer();
		try {
			Random random = new Random();

			for (int x = 0; x <= 3; x++) {
				sbf.append(random.getRandom());
				// 我要驗證碼亂數
			}

			StringBuffer reqUrl = new StringBuffer();
			reqUrl.append("http://smexpress.mitake.com.tw/SmSendGet.asp?");
			reqUrl.append("username=0970632144");
			reqUrl.append("&password=aaa123");
			reqUrl.append("&dstaddr=" + phone);
			reqUrl.append("&smbody=" + URLEncoder.encode("您的驗證碼為:" + sbf.toString(), "Big5"));
			reqUrl.append("&CharsetURL=Big5");
			URL url = new URL(reqUrl.toString());
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			int codes = urlConnection.getResponseCode();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userRepository.updatePhoneCode(email, phone, sbf.toString());
	}

//	@Override
//	@Transactional
//	public Integer mobilePhoneInspectionCodeInspection(String email, String phone, String code) {
//
//		return userRepository.mobilePhoneInspectionCodeInspection(email, phone, code);
//	}

	@Override
	public List<PersonalCenter> login(String email, String password) {

		return userRepository.login(email, password);
	}

	@Override
	public List<PersonalCenter> emailNotVerified(String email, String password) {

		return userRepository.emailNotVerified(email, password);
	}

	@Override
	public List<PersonalCenter> phoneIsNotVerified(String email, String password) {

		return userRepository.phoneIsNotVerified(email, password);
	}

	@Override
	public List<PersonalCenter> lastCheckLogin(String email, String password) {

		return userRepository.lastCheckLogin(email, password);
	}

	@Override
	public PersonalCenter findByEmail(String email) {

		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public Integer forgotPasswordMailboxNewPassword(String email, String password) {
		int check = check(email, password);
		if (check == 1) {
			userRepository.forgotPasswordMailboxNewPassword(email, password);
		} else {
			return 0;
		}
		return check;

	}

	@Override
	public List<PersonalCenter> forgotYourPassword(String email, String code) {
		if (code.length() < 4) {
			code = "00";
		}
		return userRepository.forgotYourPassword(email, code);

	}

	@Override
	public PersonalCenter findByPhone(String phone) {

		return userRepository.findByPhone(phone);
	}

	@Override
	@Transactional
	public Integer forgotPasswordMobilePhoneVerificationEnterNewPassword(String phone, String password) {
		int check = check("aaaaaaaaaaaaaaaaaaaa", password);

		if (check == 1) {
			userRepository.forgotPasswordMobilePhoneVerificationEnterNewPassword(phone, password);
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public List<PersonalCenter> forgotPasswordMobilePhoneVerificationCheck(String phone, String code) {

		return userRepository.forgotPasswordMobilePhoneVerificationCheck(phone, code);
	}

//	/**
//	 * 寄信
//	 */
//	@Override
//	public void sendVerificationLetter(String email) {
//		try {
//
//
//			
//			StringBuffer sbf = new StringBuffer();
//			Random random = new Random();
//
//			for (int x = 0; x <= 3; x++) {
//				sbf.append(random.getRandom());
//			}
//
//			StringBuffer reqUrl = new StringBuffer();
//			reqUrl.append(
//					"https://script.google.com/macros/s/AKfycbyqAAshScl03QyuNqqH1HABZuPa3extGjXEa3GDWdS4fxByVfU/exec?");
//			reqUrl.append("mail=" + email);
//			reqUrl.append("&code=" + sbf.toString());
//			reqUrl.append("&CharsetURL=Big5");
//			URL url = new URL(reqUrl.toString());
//			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//			urlConnection.setRequestMethod("GET");
//			int code = urlConnection.getResponseCode();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

	/**
	 * 手機驗證碼
	 */
	@Override
	@Transactional
	public void verificationcodeForMyPhone(String phone, String email) {
		StringBuffer sbf = new StringBuffer();
		try {

			Random random = new Random();

			for (int x = 0; x <= 3; x++) {
				sbf.append(random.getRandom());
				// 我要驗證碼亂數
			}

			StringBuffer reqUrl = new StringBuffer();
			reqUrl.append("http://smexpress.mitake.com.tw/SmSendGet.asp?");
			reqUrl.append("username=0970632144");
			reqUrl.append("&password=aaa123");
			reqUrl.append("&dstaddr=" + phone);
			reqUrl.append("&smbody=" + URLEncoder.encode("您的驗證碼為:" + sbf.toString(), "Big5"));
			reqUrl.append("&CharsetURL=Big5");
			URL url = new URL(reqUrl.toString());
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			int codes = urlConnection.getResponseCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		userRepository.codemyPhoneUpdate(phone, phone, sbf.toString());
	}

	/**
	 * 插入訂單資料
	 */
	@Override
	@Transactional
	public void insertOrder(PersonalCenter personalCenter, Payment payment, HttpServletRequest request,
			UserCard userCard) {
		payment.setStatus("0");
		payment.setApprovalstatus("1");
		Date date = new Date();
		Long time = date.getTime();
		payment.setConsumptiontime(time);
		payment.setProvincialapprovaltime(time);

		personalCenter = userRepository.findByEmail((String) request.getSession().getAttribute("email").toString());

		payment.setPersonalCenter(personalCenter);
		userCard.setPersonalCenter(personalCenter);
		personalCenter.getUsercard().add(userCard);
		personalCenter.getPayment().add(payment);
		userRepository.save(personalCenter);

	}

	/**
	 * 產生儲值訂單
	 */
	@Override
	public void storedValue(PersonalCenter personalCenter, Payment payment, HttpServletRequest request) {

		payment.setStatus("1");
		payment.setApprovalstatus("0");

		Date date = new Date();
		Long time = date.getTime();
		payment.setConsumptiontime(time);

		personalCenter = userRepository.findByEmail((String) request.getSession().getAttribute("email").toString());

		payment.setPersonalCenter(personalCenter);
		payment.setPaymentaddress(payment.getPaymentaddress());
		personalCenter.getPayment().add(payment);

		userRepository.save(personalCenter);
	}
	
	

//		payment.setStatus("1");
//		payment.setApprovalstatus("0");
//
//		Date date = new Date();
//		Long time = date.getTime();
//		payment.setConsumptiontime(time);
//
//		personalCenter = userRepository.findByEmail((String) request.getSession().getAttribute("email").toString());
//
//		payment.setPersonalCenter(personalCenter);
//		payment.setPaymentaddress(payment.getPaymentaddress());
//		personalCenter.getPayment().add(payment);
//
//		userRepository.save(personalCenter);


	/**
	 * 查詢自己的交易紀錄
	 */
	@Override
	public List<PersonalCenter> sortresult(String email) {

		return userRepository.sortresult(email);
	}

	/**
	 * 扣除此筆 交易的金額
	 * 
	 * @param email
	 * @param fishpondwallet
	 * @return
	 */
	@Override
	@Transactional
	public Integer modifyAccountAmount(String email, int fishpondwallet) {

		return userRepository.modifyAccountAmount(email, fishpondwallet);
	}

	@Override
	public Integer codemyPhoneUpdate(String email_id, String phone, String code_phone) {

		return userRepository.codemyPhoneUpdate(email_id, phone, code_phone);
	}

	@Override
	public Integer updatePhoneCode(String email, String phone, String code) {

		return userRepository.updatePhoneCode(email, phone, code);
	}

	/**
	 * 手檢驗證碼檢查
	 */
	@Override
	public List<PersonalCenter> checkPhoneCode(String email, String code) {
	
		return userRepository.checkPhoneCode(email, code);
	}

	/**
	 * 手機驗證
	 * 驗證碼修改狀態
	 * 為驗證通過
	 */
	@Override
	@Transactional
	public Integer checkPhoneCode_1(String email) {
		
		return userRepository.checkPhoneCode_1(email);
	}

	/**
	 * 儲值訂單
	 * 處理
	 */
	@Override
	@Transactional
	public Integer storedValueOrder(String id, int fishpondwallet,long time) {
	
		return userRepository.storedValueOrder(id, fishpondwallet,time);
	}

	/**
	 * 重新發送驗證碼
	 */
	@Override
	@Transactional
	
	public Integer reEmailCode(String email, String code) {
		
		try {

			StringBuffer sbf = new StringBuffer();
			Random random = new Random();

			for (int x = 0; x <= 3; x++) {
				sbf.append(random.getRandom());
			}

			StringBuffer reqUrl = new StringBuffer();
			reqUrl.append(
					"https://script.google.com/macros/s/AKfycbyqAAshScl03QyuNqqH1HABZuPa3extGjXEa3GDWdS4fxByVfU/exec?");
			reqUrl.append("mail=" + email);
			reqUrl.append("&code=" + sbf.toString());
			reqUrl.append("&CharsetURL=Big5");
			URL url = new URL(reqUrl.toString());
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			int codes = urlConnection.getResponseCode();
			
			code=sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		return userRepository.reEmailCode(email, code);
	}

	@Override
	@Transactional
	public Integer rejectreview(String id) {
	
		return userRepository.rejectreview(id);
	}



}
