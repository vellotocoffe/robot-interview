package com.li.robot.exception;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RobotCommandValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String commandName;
	private String[] commandArgs;

	public RobotCommandValidationException(String commandName, String[] commandArgs) {
		super(String.format("command not valid -> cmd : %s , args : %s", commandName,
				Objects.isNull(commandArgs) ? "N/A" : Stream.of(commandArgs).collect(Collectors.joining(","))));
		this.commandName = commandName;
		this.commandArgs = commandArgs;
	}
	
	public RobotCommandValidationException(String msg) {
		super(msg);
	}

}
