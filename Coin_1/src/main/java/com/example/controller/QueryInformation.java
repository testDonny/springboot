package com.example.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.user.model.Payment;
import com.example.user.service.PaymentService;
import com.example.user.service.UserService;

@RestController
@RequestMapping("/coin/user")
public class QueryInformation {

	@Autowired
	private UserService userService;

	@Autowired
	private PaymentService paymentService;

	/**
	 * 查詢使用者訂單狀態
	 * 
	 * @return
	 */
	@GetMapping(value = "/queryinformation")
	public ModelAndView queryinformation(HttpServletRequest request) {

		String email = (String) request.getSession().getAttribute("email").toString();

		List<Payment> result = paymentService.sortresult(email);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for (int x = 0; x < result.size(); x++) {
			long mseconds = result.get(x).getConsumptiontime() / 1000;
			//訂單產生時間
			if(result.get(x).getProvincialapprovaltime()!=null) {
			long msecondss = result.get(x).getProvincialapprovaltime()/ 1000;
			//訂單核准時間
			
			java.util.Date dates = new Date(msecondss * 1000);
			String strs = sdf.format(dates);
			result.get(x).setTimes(strs);
			}
			java.util.Date date = new Date(mseconds * 1000);
			//轉換成中文
			
			//轉換成中文
			String str = sdf.format(date);
			
		

			result.get(x).setTime(str);

			

			if (result.get(x).getApprovalstatus().equals("0")) {
				result.get(x).setApprovalstatus("審核中");
			} else {
				result.get(x).setApprovalstatus("已通過審核");
			}

			if (result.get(x).getStatus().equals("0")) {
				result.get(x).setStatus("購買");
				result.get(x).setAmountreceived(unAbs(result.get(x).getAmountreceived()));
			} else {
				result.get(x).setStatus("儲值");
			}
		}

		ModelAndView modelAndView = new ModelAndView("userorder");
		modelAndView.getModel().put("result", result);
		return modelAndView;

	}

	public static int unAbs(int a) {
		return (a > 0) ? -a : a;
	}

}
