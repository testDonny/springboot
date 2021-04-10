package com.example.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cv4j.netdiscovery.core.Spider;
import com.cv4j.netdiscovery.core.SpiderEngine;
import com.cv4j.netdiscovery.extra.downloader.httpclient.HttpClientDownloader;
import com.example.user.model.GraphicsCardAll;
import com.example.user.model.PersonalCenter;
import com.example.user.model.Verification;
import com.example.user.service.GraphicsService;
import com.example.user.service.PaymentService;
import com.example.user.service.UserService;

import com.example.user.service.VerificationService;

import Parser.test;

@RestController
@RequestMapping("/coin/user")
public class Member {

	// 首頁讀資料
	private final String index = "http://localhost:10084/coin/user/gotomyindex";
	// 註冊頁面
	private final String registered = "http://localhost:10084/coin/user/gotoregistered";
	// 登入畫面
	private final String signin = "http://localhost:10084/coin/user/gotosignin";
	// 設備畫面
	private final String mydevice = "http://localhost:10084/coin/user/gotomydevice";
	// 註冊判斷
	private final String registeredmembers = "http://localhost:10084/coin/user/gotoregisteredmember";

	@Autowired
	private UserService userService;

	@Autowired
	private GraphicsService graphicsService;

	@Autowired
	private VerificationService verificationService;

	/**
	 * 首頁
	 */
	@GetMapping(value = "/gotomyindex")
	public ModelAndView phone(Model model, HttpServletRequest request) throws Exception {
		int money = 0;
		ModelAndView modelAndView = new ModelAndView("index");
		List<GraphicsCardAll> graphicsCardAll = graphicsService.selectAll();

		if (request.getSession().getAttribute("email") != null) {
			money = userService.findByEmail((String) request.getSession().getAttribute("email").toString())
					.getFishpondwallet();
		}

		model.addAttribute("names", graphicsCardAll);
		model.addAttribute("money", money);

		return modelAndView;
	}

//	@GetMapping(value = "/sss")
//	public SpiderEngine sss() {
//		// 目標任務的網頁地址，可以拷貝到瀏覽器去檢視
//		String url = "https://mining.btcfans.com/";
//
//		SpiderEngine engine = SpiderEngine.create();
//
//		long periodTime1 = 1000 * 5;
//
//		Spider spider1 = Spider.create().name("e_list").repeatRequest(periodTime1, url).parser(new test())
//				.downloader(new HttpClientDownloader()).initialDelay(periodTime1);
//
//		engine.addSpider(spider1);
//
//// 这一行要注意，通过接口可以获取访问爬虫容器内的状态
//		engine.runWithRepeat();
//
//		return null;
//	}

	/**
	 * 到登入畫面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/gotosignin")
	public ModelAndView signin(Model model, HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("signin");

		return modelAndView;
	}

	/**
	 * 到註冊畫面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/gotoregistered")
	public ModelAndView registered(Model model) {

		ModelAndView modelAndView = new ModelAndView("registered");

		return modelAndView;
	}

	/**
	 * 到個人中心 會判定有相對應的session 才能進入
	 * 
	 * @param model
	 * @param request
	 * @return
	 */

	@GetMapping(value = "/gotomyusers")
	public ModelAndView gotomyusers(Model model, HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("users");

		// 只有登入成功獲得session 才能進入這裡
		String email = (String) request.getSession().getAttribute("email").toString();

		PersonalCenter personalCenter = userService.findByEmail(email);

		model.addAttribute("names", personalCenter);

		return modelAndView;
	}

	/**
	 * 註冊判斷 信箱
	 * 
	 * @param personalCenter
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/registeredmembers")
	public ModelAndView registeredmember(PersonalCenter personalCenter, Model model, HttpServletResponse response,
			HttpServletRequest request) {
		PersonalCenter personalCenters = null;
		ModelAndView modelAndView = null;

		try {

			personalCenters = userService.findByEmail(personalCenter.getEmail());
			if (personalCenters == null) {

				// 註冊錯的可能性信箱唯一值不重複
				personalCenters = userService.insert(personalCenter);
				request.getSession().setAttribute("emailcode", personalCenter.getEmail());
				// 信箱
				response.sendRedirect(registeredmembers);
				return null;
			} else {
				response.sendRedirect(index);
				return null;
			}

		} catch (Exception e) {

		}

		return modelAndView;
	}

	/**
	 * 到信箱畫面
	 * 
	 * @param model
	 * @param personalCenter
	 * @return
	 */
	@GetMapping(value = "/gotoregisteredmember")
	public ModelAndView goregisteredmembers(Model model, PersonalCenter personalCenter, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("mailboxjudgment");
		modelAndView.getModel().put("email", request.getSession().getAttribute("emailcode"));

		
		/**
		 * 發送新的驗證碼的判斷
		 */
		if (request.getSession().getAttribute("emailcode_1") != null) {

			modelAndView.getModel().put("emailcode_1", "驗證碼已重新發送");
			request.getSession().removeAttribute("emailcode_1");
		}
		/**
		 * 
		 */
		return modelAndView;

	}

	/**
	 * 重新發送 信箱驗證信
	 */
	@PostMapping(value = "/reemailcode")
	public ModelAndView reEmailCode(PersonalCenter personalCenter, Model model, HttpServletResponse response,
			HttpServletRequest request) {
		// 寄信
		Integer result = userService.reEmailCode(personalCenter.getEmail(), "");

		Long verificationTime  = verificationService.selectTime(personalCenter.getEmail());
		

//		java.util.Date date = new Date();
//
//		long mseconds = date.getTime() / 1000;
//
//		if(mseconds<verificationTime) {
//			System.out.println("驗證信發送要一分鐘");
//		}
//		
		
		
		try {
			request.getSession().setAttribute("emailcode_1", "ok");
			response.sendRedirect(registeredmembers);
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 信箱驗證碼判斷
	 * 
	 * @param email
	 * @param code
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/mailboxverification")
	public ModelAndView checkemail(String email, String code, HttpServletResponse response) {

		ModelAndView modelAndView = null;
		try {

			List<Verification> result = verificationService.checkEmail(email, code);

			if (result.isEmpty()) {
				modelAndView = new ModelAndView("mailboxjudgment");
				modelAndView.getModel().put("email", email);
				// 驗整碼驗證失敗
			} else {
				modelAndView = new ModelAndView("cellphone");
				Integer update = verificationService.Mailboxstatus(email);
				modelAndView.getModel().put("email", email);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return modelAndView;

	}

	/**
	 * 手機發送驗證碼
	 * 
	 * @param email
	 * @param code
	 * @param response
	 * @return
	 */

	@PostMapping(value = "/iwantaverificationcodeformyphone")
	public ModelAndView iwantaverificationcodeformyphone(String email, String phone, HttpServletResponse response,
			HttpServletRequest request) {

		Integer test = null;

		// 手機發送驗證碼
		test = userService.checkPhone(email, phone, "");

		userService.verificationcodeForMyPhone(phone, email);
		// 手機發送驗證碼

		// 修改驗證碼狀態得到驗證碼

		ModelAndView modelAndView = new ModelAndView("cellphone");
		modelAndView.getModel().put("email", email);
		modelAndView.getModel().put("phone", phone);
		modelAndView.getModel().put("result", "手機驗證碼已發送");
		return modelAndView;

	}

	/**
	 * 註冊最後的手機驗證碼驗證
	 * 
	 * @param phone
	 * @param code
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/mobilephoneverificationcodeverification")
	public ModelAndView mobilephoneverificationcodeverification(String phone, String code, String email,
			HttpServletResponse response) {

		// 手機驗證碼檢查
		List<PersonalCenter> checkPhoneCode = userService.checkPhoneCode(email, code);
		ModelAndView modelAndView = null;


		Integer result = null;

		if (checkPhoneCode.isEmpty()) {
			modelAndView = new ModelAndView("cellphone");
			modelAndView.getModel().put("email", email);
			modelAndView.getModel().put("error", "驗證碼錯誤");
		} else {
			result = userService.checkPhoneCode_1(email);
			try {
				response.sendRedirect(signin);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return modelAndView;

	}

	/**
	 * 登入狀態判斷
	 * 
	 * @param email
	 * @param code
	 * @return
	 */

	@PostMapping(value = "/loginjudgmentmodel")
	public ModelAndView loginjudgmentmodel(String email, String password, HttpServletRequest request,
			HttpServletResponse response) {
		// 所有驗證通過
		List<PersonalCenter> personalCenter = userService.login(email, password);

		System.out.println("全部通過" + personalCenter);
		List<PersonalCenter> personalCenterss = null;
		// 只有信箱通過驗證
		personalCenterss = userService.phoneIsNotVerified(email, password);
		System.out.println("只有信箱通過驗證" + personalCenterss);
		List<PersonalCenter> personalCenters = null;
		// 信箱手機接未通過驗證
		personalCenters = userService.emailNotVerified(email, password);
		System.out.println("信箱手機接未通過驗證" + personalCenters);
		List<PersonalCenter> latsCheckLogin = userService.lastCheckLogin(email, password);
		ModelAndView modelAndView = null;

		if (personalCenter.isEmpty() != true) {
			// 登入全都通過產生session
			request.getSession().setAttribute("email", email);
			// 信箱
			request.getSession().setAttribute("fishpondwallet", personalCenter.get(0).getFishpondwallet());
			// 錢包
			try {
				response.sendRedirect(index);
				return null;
			} catch (IOException e) {

				e.printStackTrace();
			}
		} else if (personalCenterss.isEmpty() != true) {
			modelAndView = new ModelAndView("cellphone");
			modelAndView.getModel().put("email", email);
		} else if (personalCenters.isEmpty() != true) {
			modelAndView = new ModelAndView("mailboxjudgment");
			modelAndView.getModel().put("email", email);

		} else if (latsCheckLogin.isEmpty()) {

			try {
				// 帳號或密碼錯誤
				response.sendRedirect(signin);
				return null;
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return modelAndView;

	}

	/**
	 * 登出畫面
	 * 
	 * @param request
	 * @return
	 */

	@GetMapping(value = "/logoutofthismailbox")
	public String checkemail(HttpServletRequest request, HttpServletResponse response) {
//		request.getSession().removeAttribute("email");

		request.getSession().invalidate();
		try {
			response.sendRedirect(index);
			return null;
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

}