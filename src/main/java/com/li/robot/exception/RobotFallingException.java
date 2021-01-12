package com.li.robot.exception;

public class RobotFallingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int illegalX;
	private int illegalY;

	public RobotFallingException() {
	}

	public RobotFallingException(int illegalX, int illegalY) {
		super(String.format("Command ignored : Robot will be falling at %s,%s", illegalX, illegalY));
		this.illegalX = illegalX;
		this.illegalY = illegalY;
	}

}
