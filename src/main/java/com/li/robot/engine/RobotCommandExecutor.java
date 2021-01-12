package com.li.robot.engine;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.li.robot.enums.Direction;
import com.li.robot.exception.RobotCommandValidationException;
import com.li.robot.exception.RobotFallingException;
import com.li.robot.model.Robot;

@Component
public class RobotCommandExecutor {

	private static final String[] SUPPORTED_CMDS = { "PLACE", "LEFT", "RIGHT", "REPORT", "MOVE" };

	@Autowired
	private Robot targetRobot;

	public Robot getTargetRobot() {
		return targetRobot;
	}

	public void setTargetRobot(Robot targetRobot) {
		this.targetRobot = targetRobot;
	}

	public void executeCommand(String name, String... args) throws RobotFallingException, RobotCommandValidationException {
		/* check if command is valid */
		validate(name, args);
		/* execute command according to name */
		name = name.toUpperCase();
		switch (name) {
		case "PLACE":
			this.targetRobot.place(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Direction.valueOf(args[2]));
			break;
		case "MOVE":
			this.targetRobot.move();
			break;
		case "LEFT":
			this.targetRobot.turnLeft();
			break;
		case "RIGHT":
			this.targetRobot.turnRight();
			break;
		case "REPORT":
			this.targetRobot.report();
			break;
		default:
			break;
		}
	}

	/**
	 * Validate user inputs (this part should be achieved by javax validation api in
	 * real codes)
	 */
	private void validate(String name, String... args) {
		name = name.toUpperCase();
		/* verify if command is supported */
		if (!Arrays.asList(SUPPORTED_CMDS).contains(name)) {
			throw new RobotCommandValidationException(name,args);
		}
		/* robot should be placed before executing other commands */
		if (!"PLACE".equals(name) && !this.targetRobot.isPlaced()) {
			throw new RobotCommandValidationException("Robot should be placed at first.");
		}
		/* validate place command */
		if ("PLACE".equals(name) && !Objects.isNull(args)) {
			if (args.length != 3) {
				throw new RobotCommandValidationException(name,args);
			} else {
				try {
					Integer.valueOf(args[0]);
					Integer.valueOf(args[1]);
					Direction.valueOf(args[2]);
				} catch (IllegalArgumentException e) {
					throw new RobotCommandValidationException(name,args);
				}
			}
		}
	}

}
