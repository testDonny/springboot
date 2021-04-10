package com.example.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.user.model.GraphicsCardAll;
import com.example.user.model.Payment;
import com.example.user.model.PersonalCenter;
import com.example.user.model.UserCard;
import com.example.user.service.GraphicsService;
import com.example.user.service.PaymentService;
import com.example.user.service.UserService;

@RestController
@RequestMapping("/coin/user")
public class MydeviceView {
	//首頁
	private final String index = "http://localhost:10084/coin/user/gotomyindex";
	// 註冊頁面
	private final String registered = "http://localhost:10084/coin/user/gotoregistered";
	// 登入畫面
	private final String signin = "http://localhost:10084/coin/user/gotosignin";
	// 設備畫面
	private final String mydevice = "http://localhost:10084/coin/user/gotomydevice";
	// 儲值
	private final String storedvalue = "http://localhost:10084/coin/user/gotostoredvalue";
	@Autowired
	private UserService userService;

	@Autowired
	private GraphicsService graphicsService;

	private PaymentService paymentService;


	/**
	 * 設備畫面 第一次進入產生session 以request的參數產生 第二次開始都為session
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */

	@GetMapping(value = "/gotomydevice")
	public ModelAndView gotoMyDevice(Model model, GraphicsCardAll graphicsCardAll, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView modelAndView = new ModelAndView("mydevice");

		List<GraphicsCardAll> graphicsCardAll_1 = graphicsService.selectAll();
		// 查出所有設備資料

		String cardName = graphicsCardAll.getName();
		// 選擇的顯示卡

		BigDecimal bigDecimal = new BigDecimal("0");

		BigDecimal bigDecimal1 = new BigDecimal("0");

		int powerwattage = 0;

		if (request.getSession().getAttribute("count") == null || cardName != null) {

			request.getSession().setAttribute("count", 0);

			int count = (Integer) request.getSession().getAttribute("count");

			/**
			 * 處理當前顯示卡 的所有資料 只有第一次 我的設備 資料為 首頁選擇參數
			 */

			for (int x = 1; x < 9; x++) {
				request.getSession().setAttribute("cards" + x, cardName);

				List<GraphicsCardAll> graphicsCardAlls = graphicsService
						.findByName((String) request.getSession().getAttribute("cards" + x));

				bigDecimal = bigDecimal.add(graphicsCardAlls.get(0).getElectricity8());

				bigDecimal1 = bigDecimal1.add(graphicsCardAlls.get(0).getPoolcomputingpower8());

				powerwattage += graphicsCardAlls.get(0).getPowerwattage();

			}
			request.getSession().setAttribute("powerwattage", powerwattage);
			// 顯示瓦數
			request.getSession().setAttribute("totalpower", bigDecimal);
			// 顯示卡電力
			request.getSession().setAttribute("Poolcomputingpower", bigDecimal1);
			// 顯示算力

			count += 1;

			request.getSession().setAttribute("count", count);
			// 判斷購買進度
		}

		model.addAttribute("names", graphicsCardAll_1);
		// 我是所有資料
		return modelAndView;

	}

	/**
	 * 移除設備
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */

	@PostMapping(value = "/clearthedata")
	public ModelAndView cardjudgment(Model model, HttpServletRequest request, HttpServletResponse response) {

		BigDecimal bigDecimal = new BigDecimal("0");
		BigDecimal bigDecimal1 = new BigDecimal("0");
		int powerwattage = 0;

		for (int x = 1; x < 9; x++) {
			if (request.getParameter("card").equals(String.valueOf(x))) {
				request.getSession().removeAttribute("cards" + x);
			}
		}

		for (int x = 1; x < 9; x++) {
			List<GraphicsCardAll> graphicsCardAlls = graphicsService
					.findByName((String) request.getSession().getAttribute("cards" + x));

			if (graphicsCardAlls.isEmpty()) {
				continue;
			} else {
				bigDecimal = bigDecimal.add(graphicsCardAlls.get(0).getElectricity8());
				powerwattage += graphicsCardAlls.get(0).getPowerwattage();

				bigDecimal1 = bigDecimal1.add(graphicsCardAlls.get(0).getPoolcomputingpower8());
			}

		}
		request.getSession().setAttribute("powerwattage", powerwattage);
		// 顯示瓦數
		request.getSession().setAttribute("totalpower", bigDecimal);
		// 顯示卡電力
		request.getSession().setAttribute("Poolcomputingpower", bigDecimal1);
		// 顯示算力

		try {
			response.sendRedirect(mydevice);
			return null;
		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 添加設備
	 * 
	 * @param request
	 * @param model
	 * @return
	 */

	@PostMapping(value = "/addthisdata")
	public ModelAndView addthisdata(HttpServletRequest request, Model model, HttpServletResponse response,
			GraphicsCardAll graphicsCardAll) {

		BigDecimal bigDecimal = new BigDecimal("0");
		BigDecimal bigDecimal1 = new BigDecimal("0");
		int powerwattage = 0;

		for (int x = 1; x < 9; x++) {
		
			if (request.getSession().getAttribute("cards" + x) == null) {
				request.getSession().setAttribute("cards" + x, request.getParameter("cards"));
				break;
			}
		}
		// 只進行一次

		for (int x = 1; x < 9; x++) {

			List<GraphicsCardAll> graphicsCardAlls = graphicsService
					.findByName((String) request.getSession().getAttribute("cards" + x));

			if (graphicsCardAlls.isEmpty()) {
				continue;
			} else {

				bigDecimal = bigDecimal.add(graphicsCardAlls.get(0).getElectricity8());
				powerwattage += graphicsCardAlls.get(0).getPowerwattage();

				bigDecimal1 = bigDecimal1.add(graphicsCardAlls.get(0).getPoolcomputingpower8());
			}

		}
		request.getSession().setAttribute("powerwattage", powerwattage);
		// 顯示瓦數
		request.getSession().setAttribute("totalpower", bigDecimal);
		// 顯示卡電力
		request.getSession().setAttribute("Poolcomputingpower", bigDecimal1);
		// 顯示算力

		try {
			response.sendRedirect(mydevice);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 訂單
	 * 
	 * @param request
	 * @return
	 */

	@PostMapping(value = "/sendanorder")
	public ModelAndView sendAnOrder(HttpServletRequest request, UserCard userCard) {

		ModelAndView modelAndView = new ModelAndView("order");
		modelAndView.getModel().put("powerwattage", request.getParameter("powerwattage"));

		BigDecimal bigDecimal = new BigDecimal("0");

		int prices = 0;
		for (int x = 1; x < 9; x++) {
			List<GraphicsCardAll> graphicsCardAlls = graphicsService.findByName(request.getParameter("card" + x));

			if (graphicsCardAlls.isEmpty()) {
				continue;
			} else {
				bigDecimal = bigDecimal.add(graphicsCardAlls.get(0).getDailytheoreticalreturn8());
				modelAndView.getModel().put("card" + x, graphicsCardAlls.get(0).getName());
				modelAndView.getModel().put("powerwattage" + x, graphicsCardAlls.get(0).getPowerwattage());
				modelAndView.getModel().put("dailytheoreticalreturn" + x,
						graphicsCardAlls.get(0).getDailytheoreticalreturn8());
				modelAndView.getModel().put("price" + x, graphicsCardAlls.get(0).getPrice());
			}

			prices += graphicsCardAlls.get(0).getPrice();
		}

		modelAndView.getModel().put("dailytheoreticalreturn", bigDecimal);
		modelAndView.getModel().put("prices", prices);
		return modelAndView;
	}

	/**
	 * 確定訂單
	 */
	@PostMapping(value = "/confirmorder")
	public String confirmOrder(HttpServletRequest request, Payment payment, PersonalCenter personalCenter,
			UserCard userCard, HttpServletResponse response) {

		String email = (String) request.getSession().getAttribute("email").toString();

		PersonalCenter p = userService.findByEmail(email);

		// 身上的錢
		Integer me = p.getFishpondwallet();
		// 要付的錢
		Integer pay = payment.getAmountreceived();

		if (me < pay) {
			try {
				response.sendRedirect(storedvalue);
				return null;
			} catch (IOException e) {

				e.printStackTrace();
			}
		} else {
			int result = me - pay;
			userService.modifyAccountAmount(email, result);
			/**
			 * 插入訂單資訊
			 */
			userService.insertOrder(personalCenter, payment, request, userCard);
		}

		return "訂單成立";
	}

}
