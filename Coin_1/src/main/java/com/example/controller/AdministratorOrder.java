package com.example.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.user.model.Payment;
import com.example.user.model.PersonalCenter;
import com.example.user.model.Verification;
import com.example.user.service.UserService;
import com.example.user.service.VerificationService;

import lombok.var;

import com.example.user.service.PaymentService;

@RestController
@RequestMapping("/coin/administrator")
public class AdministratorOrder {

	@Autowired
	private UserService userService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private VerificationService verificationService;

	/**
	 * 管理員查詢多筆
	 */
	private static String a = "http://localhost:10084/coin/administrator/administratororderinspection";
	/**
	 * 管理員查詢會員單筆
	 */
	private static String b = "http://localhost:10084/coin/administrator/queryasingle?email=????????????????";

	/**
	 * 管理員查詢所有帳單
	 */
	private static String c = "http://localhost:10084/coin/administrator/queryalluserorders";

	/**
	 * 查詢個人 使用者狀態
	 * 
	 * @return
	 */
	@GetMapping(value = "/queryasingle")
	public ModelAndView queryaSingle(PersonalCenter personalCenter) {
		ModelAndView modelAndView = null;

		try {
			PersonalCenter result = userService.findByEmail(personalCenter.getEmail());

			long mseconds = result.getRegistrationtime() / 1000;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			java.util.Date date = new Date(mseconds * 1000);
			String str = sdf.format(date);

			result.setRegistrationtime1(str);

			List<Verification> verification = verificationService.singleData(result.getEmail());

			result.getVerification().add(verification.get(0));

			if (verification.get(0).getStatusPhone() == 1 && verification.get(0).getStatusEmail() == 1) {
				result.setStatus("通過所有驗證");
			} else if (verification.get(0).getStatusPhone() == 0 && verification.get(0).getStatusEmail() == 1) {
				result.setStatus("手機未通過驗證");
			} else {
				result.setStatus("信箱未通過驗證");
			}

			modelAndView = new ModelAndView("administrator");

			modelAndView.getModel().put("result", result);
		} catch (Exception e) {
			modelAndView = new ModelAndView("error");

			modelAndView.getModel().put("result", "該使用者不存在");
		}

		return modelAndView;
	}

	/**
	 * 查詢所有 使用者狀態
	 * 
	 * @param request
	 * @return
	 */

	@GetMapping(value = "/administratororderinspection")
	public ModelAndView administratOrorderInspection(HttpServletRequest request) {

		List<PersonalCenter> result = userService.selectAll();

		List<Verification> verification = verificationService.selectAllData();

		for (int x = 0; x < result.size(); x++) {
			long mseconds = result.get(x).getRegistrationtime() / 1000;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			java.util.Date date = new Date(mseconds * 1000);
			String str = sdf.format(date);

			result.get(x).setRegistrationtime1(str);

			result.get(x).getVerification().add(verification.get(x));

			if (verification.get(x).getStatusPhone() == 1 && verification.get(x).getStatusEmail() == 1) {
				result.get(x).setStatus("通過所有驗證");
			} else if (verification.get(x).getStatusPhone() == 0 && verification.get(x).getStatusEmail() == 1) {
				result.get(x).setStatus("手機未通過驗證");
			} else {
				result.get(x).setStatus("信箱未通過驗證");
			}

		}

		ModelAndView modelAndView = new ModelAndView("administrator");
		modelAndView.getModel().put("result", result);

		return modelAndView;

	}

	/**
	 * 訂單儲值
	 * 通過審核
	 * @param personalCenter
	 * @param payment
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/approved")
	public ModelAndView approved(PersonalCenter personalCenter, Payment payment, HttpServletResponse response) {

		Date date = new Date();
		long mseconds = date.getTime();
		// 交易成功並且產生訂單時間
		Integer result = userService.storedValueOrder(payment.getId(), personalCenter.getFishpondwallet(), mseconds);

		try {
			response.sendRedirect(c);
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	@PostMapping(value = "/rejectreview")
	public ModelAndView rejectReview(PersonalCenter personalCenter, Payment payment, HttpServletResponse response) {

		// 交易駁回
		Integer result = userService.rejectreview(payment.getId());

		try {
			response.sendRedirect(c);
			return null;
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;

	}
	

	

	/**
	 * 管理員查詢所有訂單狀態
	 * 
	 * @param request
	 * @return
	 */

	@GetMapping(value = "/queryalluserorders")
	public ModelAndView queryAllUserOrders(HttpServletRequest request) {

		List<Payment> result = paymentService.select();

		for (int x = 0; x < result.size(); x++) {
			long mseconds = result.get(x).getConsumptiontime() / 1000;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			java.util.Date date = new Date(mseconds * 1000);
			String str = sdf.format(date);

			result.get(x).setTime(str);
			//訂單產生時間
			
			if(result.get(x).getProvincialapprovaltime()!=null) {
				long msecond = result.get(x).getProvincialapprovaltime() / 1000;
				java.util.Date dates = new Date(msecond * 1000);
				String strs = sdf.format(dates);
				result.get(x).setTimes(strs);
				//審核通過時間
			}
		

		

			System.out.println(result.get(x).getPersonalCenter().getEmail());

			if (result.get(x).getApprovalstatus().equals("0")) {
				result.get(x).setApprovalstatus("審核中");
			} else if (result.get(x).getApprovalstatus().equals("2")) {
				result.get(x).setApprovalstatus("審核不通過");
			}

			else {
				result.get(x).setApprovalstatus("已通過審核");
			}

			if (result.get(x).getStatus().equals("0")) {
				result.get(x).setStatus("購買");
				result.get(x).setAmountreceived(unAbs(result.get(x).getAmountreceived()));
			} else {
				result.get(x).setStatus("儲值");
			}
		}

		ModelAndView modelAndView = new ModelAndView("administratorcheckorder");
		modelAndView.getModel().put("result", result);
		return modelAndView;

	}

	public static int unAbs(int a) {
		return (a > 0) ? -a : a;
	}

}
