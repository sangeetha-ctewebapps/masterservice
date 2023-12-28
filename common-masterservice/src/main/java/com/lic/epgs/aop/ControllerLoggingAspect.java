package com.lic.epgs.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class ControllerLoggingAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerLoggingAspect.class);

	// AOP expression for which methods shall be intercepted

	@Around("execution(* com.lic.epgs.controller..*(..)))")
	// @Pointcut("execution(* com.lic.epgs.controller..*(..))) && !execution(*
	// com.lic.epgs.controller.LoadController..*(..))) ")
	public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
		ObjectMapper objM = new ObjectMapper();

		// Get intercepted method details
		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();

		final StopWatch stopWatch = new StopWatch();
		Object arrys[] = proceedingJoinPoint.getArgs();
		// Measure method execution time
		stopWatch.start();
		Object result = proceedingJoinPoint.proceed();
		stopWatch.stop();

		// Log method execution time
		String a = Arrays.toString(arrys);
		if (!a.contains("multipart") && !result.toString().contains("application/vnd.ms-excel")) {
			LOGGER.info("Execution Details - Class Name:" + className + ".class. Method Name:" + methodName
					+ "(). Time Taken:" + stopWatch.getTotalTimeMillis() + " ms" + ". Request Details: "
					+ objM.writeValueAsString(arrys) + " And Response is: " + objM.writeValueAsString(result));
		} else if (result.toString().contains("application/vnd.ms-excel")) {
			LOGGER.info("Execution Details - Class Name:" + className + ".class. Method Name:" + methodName
					+ "(). Time Taken:" + stopWatch.getTotalTimeMillis() + " ms" + ". Request Details: "
					+ objM.writeValueAsString(arrys));
		}

		return result;
	}

	@Around("execution(* com.lic.epgs.controller..*(..)))")
	public Object profileAllController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

		// Get intercepted method details
		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();

		final StopWatch stopWatch = new StopWatch();

		// Measure method execution time
		stopWatch.start();
		Object result = proceedingJoinPoint.proceed();
		stopWatch.stop();

		// Log method execution time
		LOGGER.info("Execution time of Controller" + className + "." + methodName + " :: "
				+ stopWatch.getTotalTimeMillis() + " ms");

		return result;
	}

	@Around("execution(* com.lic.epgs.serviceImpl..*(..)))")
	public Object profileAllServiceImpl(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

		// Get intercepted method details
		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();

		final StopWatch stopWatch = new StopWatch();

		// Measure method execution time
		stopWatch.start();
		Object result = proceedingJoinPoint.proceed();
		stopWatch.stop();

		// Log method execution time
		LOGGER.info("Execution time of Controller" + className + "." + methodName + " :: "
				+ stopWatch.getTotalTimeMillis() + " ms");

		return result;
	}
}
