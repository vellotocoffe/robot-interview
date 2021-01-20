package com.li.robot.aspect;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RobotAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private ConcurrentHashMap<String, Integer> cmdExecutionCounter = new ConcurrentHashMap<>();

	/**
	 * Advice counting valid command execution time per command.
	 */
	@AfterReturning(value = "execution(* com.li.robot.features.MyRobotV1.*(..))")
	public void afterReturningFromCmdExecution(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		Integer oldValue = cmdExecutionCounter.putIfAbsent(methodName, 1);
		if (oldValue != null) {
			cmdExecutionCounter.replace(methodName, oldValue + 1);
		}
	}

	/**
	 * Display command execution status at the end.
	 */
	@AfterReturning(value = "execution(* com.li.robot.features.CommandExecutor.close())")
	public void afterReturningFromExitCmdExecution(JoinPoint joinPoint) {
		String logMsg = this.cmdExecutionCounter.entrySet().stream()
				.map(entry -> String.format("Command %s is called %s times", entry.getKey(), entry.getValue()))
				.collect(Collectors.joining("\n"));
		logger.info("\n{}", logMsg);
	}

	/**
	 * Log any robot exception.
	 */
	@AfterThrowing(value = "execution(* com.li.robot.model.Robot.*(..))", throwing = "ex")
	public void afterThrowingException(JoinPoint joinPoint, Exception ex) {
		logger.error("Failed to execute : {}", ex.getMessage());
	}

}
