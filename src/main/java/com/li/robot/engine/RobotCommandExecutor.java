package com.li.robot.engine;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.li.robot.enums.Direction;
import com.li.robot.model.Robot;

@Component
public class RobotCommandExecutor {

	private static final String[] SUPPORTED_CMDS = { "place", "left", "right", "report", "move" };

	@Autowired
	private Robot targetRobot;

	public Robot getTargetRobot() {
		return targetRobot;
	}

	public void setTargetRobot(Robot targetRobot) {
		this.targetRobot = targetRobot;
	}

	public void executeCommand(String name, String... args) {
		/*check if command is valid*/
		if (!isValidated(name, args)) {
			return;
		}
		/*execute command according to name*/
		switch (name) {
		case "place":
			this.targetRobot.place(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Direction.valueOf(args[2]));
			break;

		case "move":
			this.targetRobot.move();
			break;
		case "left":
			this.targetRobot.turnLeft();
			break;
		case "right":
			this.targetRobot.turnRight();
			break;
		case "report":
			this.targetRobot.report();
			break;
		default:
			break;
		}
	}

	
	/**
	 * Validate user inputs (this part should be achieved by javax validation api in real codes)
	 * */
	private boolean isValidated(String name, String... args) {
		if(name == null || name.isBlank() || name.isEmpty()) {
			System.err.println("Please enter a valid command.");
			return false;
		}
		/*verify if command is supported*/
		if (!Arrays.asList(SUPPORTED_CMDS).contains(name)) {
			System.err.println("Command not supported.");
			return false;
		}
		/*robot should be placed before executing other commands*/
		if (!"place".equals(name) && !this.targetRobot.isPlaced()) {
			System.err.println("Please place the robot on table first.");
			return false;
		}
		/*place command needs 3 arguments*/
		if (args != null && args.length != 3) {
			System.err.println("Command not supported.");
			return false;
		}
		/*place command needs 2 digits and a valid direction*/
		if(args.length == 3) {
			try {
				Integer.valueOf(args[0]);
				Integer.valueOf(args[1]);
				Direction.valueOf(args[2]);
			}catch(IllegalArgumentException e) {
				System.err.println("Args format not correct.");
				return false;
			}
		}
		return true;
	}

}
