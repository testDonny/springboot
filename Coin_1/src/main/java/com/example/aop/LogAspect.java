//package com.example.aop;
//
//import java.lang.reflect.Modifier;
//import java.util.Enumeration;
//import javax.servlet.http.HttpServletRequest;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import com.example.log.model.Log;
//import com.example.log.service.LogService;
//import lombok.extern.slf4j.Slf4j;
//
//@Aspect
//@Component
//@Slf4j
//public class LogAspect {
//
//	@Autowired
//	private LogService logService;
////	
////	private StringBuilder stringBuilder;
//
//	private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);
//
//	// 定義一個切入點
//	@Pointcut("execution(* com.example.controller.*.*(..))")
//	public void pc1() {
//
//	}
//
//	// 前置通知
//	@Before(value = "pc1()")
//	public void before(JoinPoint jp) {
//		String name = jp.getSignature().getName();
////		System.out.println(name + "方法開始執行...");
//		Logger logger = LoggerFactory.getLogger(jp.getTarget().getClass().getName());
//
//		StringBuilder requestStr = new StringBuilder();
//		Object[] args = jp.getArgs();
//		if (args != null && args.length > 0) {
//			for (Object arg : args) {
//				requestStr.append(arg.toString());
//			}
//		}
//
//		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = attributes.getRequest();
//
//		// 記錄下請求內容
//
////		Log log=new Log();
////		log.setUrl(request.getRequestURL().toString());
////		log.setIp(request.getRemoteAddr());
////		log.setAfterModification(requestStr.toString());
////		log.setMethod(request.getMethod());
//////		System.out.println("url={}"+request.getRequestURL());
//////		System.out.println("method={}"+request.getMethod());
//////		System.out.println("ip={}"+request.getRemoteAddr());
//////		System.out.println("class_method={}"+jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName());
//////		System.out.println("args={}"+jp.getArgs().toString());
////		logService.insert(log);
//
////		System.out.println("目标方法所属类的简单类名:" + jp.getSignature().getDeclaringType().getSimpleName());
////		System.out.println("目标方法所属类的类名:" + jp.getSignature().getDeclaringTypeName());
////		System.out.println("目标方法声明类型:" + Modifier.toString(jp.getSignature().getModifiers()));
////
////		logger.info("start");
//
//	}
//
//	// 后置通知
//	@After(value = "pc1()")
//	public void after(JoinPoint jp) {
////		String name = jp.getSignature().getName();
////		System.out.println(name + "方法執行結束...");
////		Logger logger = LoggerFactory.getLogger(jp.getTarget().getClass().getName());
////		logger.info("end");
//
//	}
//
//	// 返回通知
//	@AfterReturning(value = "pc1()", returning = "result")
//	public void afterReturning(JoinPoint jp, Object result) {
//		String name = jp.getSignature().getName();
////		System.out.println(name + "方法返回值為：" + result);
//
//	}
//
//	// 異常通知
//	@AfterThrowing(value = "pc1()", throwing = "e")
//	public void afterThrowing(JoinPoint jp, Exception e) {
//		String name = jp.getSignature().getName();
////		System.out.println(name + "方法拋異常了，異常是：" + e.getMessage());
//	}
//
//	// 環繞通知
//	@Around("pc1()")
//	public Object around(ProceedingJoinPoint pjp) throws Throwable {
//
//		String name = pjp.getSignature().getName();
//		// 統計方法執行時間
//		long start = System.currentTimeMillis();
//		Object result = pjp.proceed();
//
//		return result;
//	}
//
//	public void saveLog(ProceedingJoinPoint joinPoint, long time) {
//
//		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = attributes.getRequest();
//
////        logger.info("url={}", request.getRequestURL());
////
////        logger.info("method={}", request.getMethod());
////
////        logger.info("ip={}", request.getRemoteAddr());
////
////        logger.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
////
////        logger.info("args={}", joinPoint.getArgs());
//
////        LogUser loguser =new LogUser();
////        StringBuilder sb=new StringBuilder();
//
//		Enumeration<String> enu = request.getParameterNames();
//		while (enu.hasMoreElements()) {
//
//			String paraName = enu.nextElement();
//
////			System.out.println(paraName + ": " + request.getParameter(paraName) + ",");
//
////            
////            if(paraName.equals("api_version")) {
//////            	 loguser.setVip_type(Integer.parseInt(request.getParameter(paraName)));
////            	 sb.append(paraName + ": " +request.getParameter(paraName)+" ");
////            }
////            if(paraName.equals("mine_info")) {
//////            	loguser.setMine_info(request.getParameter(paraName));
////            	sb.append(paraName + ": "+request.getParameter(paraName)+" ");
////            }
////            if(paraName.equals("state")) {
//////            	loguser.setState(Integer.parseInt(request.getParameter(paraName)));
////            	sb.append(paraName + ": "+request.getParameter(paraName)+" ");
////            }
////            if(paraName.equals("vip_type")) {
//////            	loguser.setState(Integer.parseInt(request.getParameter(paraName)));
////            	sb.append(paraName + ": "+request.getParameter(paraName));
////            }
////
////  
////        }
////        LogUser logusers=new LogUser();
////        
////        loguser.setCreated_time(logusers.getCreated_time());
////
////        loguser.setResult(sb.toString());
////        
////    	//StringBuilber 轉字串
////    	loguser.setUrl(request.getRequestURL().toString());
////    	
////    	loguser.setMethod(request.getMethod());
////    	
////    	loguser.setIp(request.getRemoteAddr());
////    	
//////    	loguser.setClass_method(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
////    	
////		logimple.save(loguser);
//
//		}
//
////    public void saveLog(ProceedingJoinPoint joinPoint, long time){
////        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
////        Method method = signature.getMethod();
////		SysLog sysLog = new SysLog();
////        Log logAnnotation = method.getAnnotation(Log.class);
////        if (logAnnotation != null) {
////            // 註解上的描述
////            sysLog.setOperation(logAnnotation.value());
////        }
////        // 請求的方法名
////        String className = joinPoint.getTarget().getClass().getName();
////        String methodName = signature.getName();
////        String username = null;
////        sysLog.setMethod(className + "." + methodName + "()");
////        // 請求的方法引數值
////        Object[] args = joinPoint.getArgs();
////        // 請求的方法引數名稱
////        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
////        String[] paramNames = u.getParameterNames(method);
////        if (args != null && paramNames != null) {
////            String params = "";
////            for (int i = 0; i < args.length; i++) {
////                params += "  " + paramNames[i] + ": " + args[i];
////            }
////            sysLog.setParams(params);
////        }
////        // 獲取request
////        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
////
////        // 設定IP地址
////        sysLog.setIp(AddressUtils.getIpAddr(request));
////        sysLog.setLocation(AddressUtils.getCityInfo(sysLog.getIp()));
////
////        //模擬一個使用者名稱
////        sysLog.setUsername("admin");
////        sysLog.setTime((int) time);
////        sysLogRepo.save(sysLog);
//	}
//
//}