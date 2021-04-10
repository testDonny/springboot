package com.example.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.ex.Result;

import com.example.user.model.GraphicsCardAll;
import com.example.user.model.Payment;
import com.example.user.model.PersonalCenter;
import com.example.user.service.GraphicsService;
import com.example.user.service.PaymentService;
import com.example.user.service.UserService;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/coin/api")
public class api {

	/**
	 * Postman api
	 * 
	 * post 信箱註冊並發送驗證碼
	 * http://localhost:10084/coin/api/insertouser?password=12xxZZ11@&email=x33778899@gmail.com
	 * 
	 * post 信箱驗證碼檢查
	 * http://localhost:10084/coin/api/checkemail?email=x33778899@gmail.com&code=6133
	 * 
	 * post 手機發送驗證碼
	 * http://localhost:10084/coin/api/phoneverification?email=x33778899@gmail.com&phone=0911678753
	 * 
	 * post 手機驗證碼檢查
	 * http://localhost:10084/coin/api/phoneverificationcheck?phone=0911678753&code=6359
	 * 
	 * post 登入判斷
	 * http://localhost:10084/coin/api/loginjudgment?email=x33778899@gmail.com&password=12xxZZ11@
	 * 
	 * post 忘記密碼 信箱發送新的驗證碼
	 * http://localhost:10084/coin/api/forgotpasswordmailbox?email=x33778899@gmail.com
	 * 
	 * post 忘記密碼 手機發送新的驗證碼
	 * http://localhost:10084/coin/api/forgotpasswordmobilephoneverificationcheck?password=12xxZZ11@&phone=0911678753
	 * 
	 * put 忘記密碼 信箱輸入驗證碼 輸入新的密碼
	 * http://localhost:10084/coin/api/forgotpasswordemail?email=x33778899@gmail.com&password=12xxZZ11@32S&code=5693
	 * 
	 * put 忘記密碼 手機輸入驗證碼 輸入新的密碼
	 * http://localhost:10084/coin/api/forgotpasswordphone?password=12xx@@!!22X&phone=0911678753&code=6982
	 * 
	 * Get 使用者交易紀錄
	 * http://localhost:10084/coin/api/administratororderinspection?email=x33778899@gmail.com
	 * 
	 */

	@Autowired
	private UserService userService;

	@Autowired
	private GraphicsService graphicsService;

	@Autowired
	private PaymentService paymentService;

	@GetMapping(value = "/insergraphicscardall")
	public String insergraphicscardall(GraphicsCardAll graphicsCardAll) {

		GraphicsCardAll GraphicsCardAll = graphicsService.insert(graphicsCardAll);

		return "資料插入成功";
	}

	/**
	 * 插入目前的使用者資料
	 */
	@PostMapping(value = "/insertouser")
	public ResponseEntity<PersonalCenter> insertthisinformation(PersonalCenter personalCenter) {

		ResponseEntity<PersonalCenter> result = null;

		try {
			PersonalCenter personalCenters = userService.insert(personalCenter);

			if (personalCenters != null) {
				result = ResponseEntity.ok().body(personalCenters);

			} else {
				result = ResponseEntity.notFound().build();
			}
		} catch (Exception e) {

		}

		// 插入資料
		return result;

	}

//	@PostMapping(value = "/checkemail")
//	public ResponseEntity<List<PersonalCenter>> checkemail(String email, String code) {
//		ResponseEntity<List<PersonalCenter>> result = null;
//
//		try {
//
//			List<PersonalCenter> personalCenters = userService.checkEmail(email, code);
//
//			if (personalCenters.isEmpty()) {
//				result = ResponseEntity.notFound().build();
//
//			} else {
//				Integer update = userService.upDateEmail(email);
//				result = ResponseEntity.ok().body(personalCenters);
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return result;
//
//	}

//	@PostMapping(value = "/phoneverification")
//	public ResponseEntity<List<PersonalCenter>> phoneVerification(PersonalCenter personalCenter) {
//
//		/**
//		 * 判斷信箱是否存在 發送驗證碼
//		 */
//		ResponseEntity<List<PersonalCenter>> result = null;
//		List<PersonalCenter> personalCenters = userService.checkPhone(personalCenter.getEmail());
//		Integer test = null;
//
//		try {
//			if (personalCenters.isEmpty()) {
//				result = ResponseEntity.notFound().build();
//			} else {
//				// 手機發送驗證碼
//				test = userService.checkPhone(personalCenters.get(0).getEmail(), personalCenter.getPhone(), "");
//				personalCenters = userService.checkPhone(personalCenters.get(0).getEmail());
//				// 修改驗證碼狀態得到驗證碼
//				result = ResponseEntity.ok().body(personalCenters);
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return result;
//
//	}

//	/**
//	 * 手機驗證碼檢查
//	 * 
//	 * @param phone
//	 * @param code
//	 * @return
//	 */
//	@PostMapping(value = "/phoneverificationcheck")
//	public ResponseEntity<List<PersonalCenter>> phoneverificationcheck(String phone, String code) {
//		ResponseEntity<List<PersonalCenter>> result = null;
//
//		List<PersonalCenter> personalCenter = userService.checkPhone(phone, code);
//
//		if (personalCenter.isEmpty()) {
//			result = ResponseEntity.notFound().build();
//		} else {
//			Integer mobilePhoneInspectionCodeInspection = userService
//					.mobilePhoneInspectionCodeInspection(personalCenter.get(0).getEmail(), phone, code);
//			result = ResponseEntity.ok().body(personalCenter);
//		}
//		return result;
//
//	}

//	@PostMapping(value = "/loginjudgment")
//	public Result<HashMap<String, Object>> loginjudgment(String email, String password) {
////		 所有驗證通過
//		List<PersonalCenter> personalCenter = userService.login(email, password);
//		List<PersonalCenter> personalCenterss = null;
//
//		List<PersonalCenter> personalCenters = null;
//		HashMap<String, Object> INFO = null;
//		INFO = new HashMap<>();
//
//		if (personalCenter.isEmpty()) {
//			// 只有信箱通過驗證
//			personalCenterss = userService.phoneIsNotVerified(email, password);
//			if (personalCenterss.isEmpty() != true) {
//				// 信箱手機接未通過驗證
//				personalCenters = userService.emailNotVerified(email, password);
//
//				if (personalCenters.isEmpty() != true) {
//					INFO.put("data", "驗證失敗手機未通過驗證");
//				}
//
//			}
//
//		} else {
//			INFO.put("data", "驗證成功");
//		}
//		List<PersonalCenter> latsCheckLogin = userService.lastCheckLogin(email, password);
//		if (latsCheckLogin.isEmpty()) {
//			INFO.put("data", "信箱或密碼錯誤");
//
//		} else {
//			INFO.put("data", "驗證失敗手機及信箱接未通過驗證");
//		}
//		return Result.success(INFO);
//
//	}
//
//	@PostMapping(value = "/forgotpasswordmailbox")
//	public ResponseEntity<PersonalCenter> forgotPasswordMailbox(String email) {
//		PersonalCenter personalCenter = userService.findByEmail(email);
//		ResponseEntity result = null;
//
//		if (personalCenter != null) {
//			userService.forgotPasswordMailboxUpdate(email, "");
//			personalCenter = userService.findByEmail(email);
//			result = ResponseEntity.ok().body(personalCenter);
//		} else {
//			result = ResponseEntity.notFound().build();
//		}
//
//		return result;
//	}

	@PutMapping(value = "/forgotpasswordemail")
	public ResponseEntity<List<PersonalCenter>> forgotPasswordEmail(String email, String code, String password) {

		List<PersonalCenter> personalCenter = userService.forgotYourPassword(email, code);

		ResponseEntity<List<PersonalCenter>> result = null;

		if (personalCenter.isEmpty() != true) {

			Integer results = userService.forgotPasswordMailboxNewPassword(email, password);
			if (results == 1) {
				result = ResponseEntity.ok().body(personalCenter);
			} else {
				result = ResponseEntity.notFound().build();
			}

		}
		return result;

	}

//	@PostMapping(value = "/forgotpasswordmobilephoneverificationcheck")
//	public ResponseEntity<PersonalCenter> forgotPasswordPhone(String phone) {
//		PersonalCenter personalCenter = userService.findByPhone(phone);
//
//		ResponseEntity<PersonalCenter> result = null;
//
//		if (personalCenter != null) {
//			Integer results = userService.forgotPasswordPhoneUpdate(phone, "");
//			personalCenter = userService.findByPhone(phone);
//			result = ResponseEntity.ok().body(personalCenter);
//		} else {
//			result = ResponseEntity.notFound().build();
//		}
//		return result;
//
//	}

	@PutMapping(value = "/forgotpasswordphone")
	public ResponseEntity<List<PersonalCenter>> mobileVerificationCodeCheck(String phone, String code,
			String password) {

		List<PersonalCenter> personalCenter = userService.forgotPasswordMobilePhoneVerificationCheck(phone, code);
		ResponseEntity<List<PersonalCenter>> result = null;
		if (personalCenter.isEmpty() != true) {
			Integer results = userService.forgotPasswordMobilePhoneVerificationEnterNewPassword(phone, password);

			result = ResponseEntity.ok().body(personalCenter);
		} else {
			result = ResponseEntity.notFound().build();
		}
		return result;

	}

	/**
	 * 查詢所有
	 * 使用者訂單狀態
	 * 
	 * @return
	 */
	
	@GetMapping(value = "/usertransaction")
	public ResponseEntity<List<Payment>> userTransaction(String email,HttpServletRequest request) {
		ResponseEntity<List<Payment>> result = null;
		List<Payment> payment=paymentService.select();
		
		
		for (int x = 0; x < payment.size(); x++) {
			long mseconds = payment.get(x).getConsumptiontime() / 1000;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			java.util.Date date = new Date(mseconds * 1000);
			String str = sdf.format(date);

			payment.get(x).setTime(str);

			payment.get(x).setTimes(str);

			if (payment.get(x).getApprovalstatus().equals("0")) {
				payment.get(x).setApprovalstatus("審核中");
			} else {
				payment.get(x).setApprovalstatus("已通過審核");
			}

			if (payment.get(x).getStatus().equals("0")) {
				payment.get(x).setStatus("購買");
				payment.get(x).setAmountreceived(unAbs(payment.get(x).getAmountreceived()));
			} else {
				payment.get(x).setStatus("儲值");
			}
		}
		result = ResponseEntity.ok().body(payment);
		

		return result;
	}
	

	/**
	 * 查詢使用
	 * 者訂單狀態
	 * 
	 * @return
	 */
	@GetMapping(value = "/queryinformation")
	public ResponseEntity<List<Payment>> administratororderinspection(String email) {


		ResponseEntity<List<Payment>> result = null;

		List<Payment> payment = paymentService.sortresult(email);

		for (int x = 0; x < payment.size(); x++) {
			long mseconds = payment.get(x).getConsumptiontime() / 1000;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			java.util.Date date = new Date(mseconds * 1000);
			String str = sdf.format(date);

			payment.get(x).setTime(str);

			payment.get(x).setTimes(str);

			if (payment.get(x).getApprovalstatus().equals("0")) {
				payment.get(x).setApprovalstatus("審核中");
			} else {
				payment.get(x).setApprovalstatus("已通過審核");
			}

			if (payment.get(x).getStatus().equals("0")) {
				payment.get(x).setStatus("購買");
				payment.get(x).setAmountreceived(unAbs(payment.get(x).getAmountreceived()));
			} else {
				payment.get(x).setStatus("儲值");
			}
		}

		result = ResponseEntity.ok().body(payment);
		return result;

	}

	public static int unAbs(int a) {
		return (a > 0) ? -a : a;
	}
}
