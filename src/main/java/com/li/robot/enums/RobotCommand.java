package com.li.robot.enums;

import java.util.stream.Stream;

public enum RobotCommand {

	PLACE(3), LEFT(0), RIGHT(0), REPORT(0), MOVE(0), QUIT(0);

	private int argsNum;

	private RobotCommand(int argsNum) {
		this.argsNum = argsNum;
	}

	public int getArgsNum() {
		return argsNum;
	}

	public void setArgsNum(int argsNum) {
		this.argsNum = argsNum;
	}

	public static boolean isValidCommand(String commandName) {
		return Stream.of(RobotCommand.values()).map(RobotCommand::toString)
				.anyMatch(dirStr -> dirStr.equals(commandName));
	}
}
