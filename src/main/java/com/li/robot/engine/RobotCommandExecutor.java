package com.li.robot.engine;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.li.robot.enums.Direction;
import com.li.robot.enums.RobotCommand;
import com.li.robot.exception.RobotCommandValidationException;
import com.li.robot.exception.RobotFallingException;
import com.li.robot.model.Robot;

@Component
public class RobotCommandExecutor {

	@Autowired
	private Robot targetRobot;

	public Robot getTargetRobot() {
		return targetRobot;
	}

	public void setTargetRobot(Robot targetRobot) {
		this.targetRobot = targetRobot;
	}

	public void executeCommand(String name, String... args) throws RobotFallingException, RobotCommandValidationException {
		/* user can input lower case command name */
		name = name.toUpperCase();
		/* check if command is valid */
		validate(name, args);
		/* execute command according to name */
		switch (RobotCommand.valueOf(name)) {
			case PLACE:
				this.targetRobot.place(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Direction.valueOf(args[2]));
				break;
			case MOVE:
				this.targetRobot.move();
				break;
			case LEFT:
				this.targetRobot.turnLeft();
				break;
			case RIGHT:
				this.targetRobot.turnRight();
				break;
			case REPORT:
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
		/* verify if command is supported */
		if (!RobotCommand.isValidCommand(name)) {
			throw new RobotCommandValidationException(name,args);
		}
		/* robot should be placed before executing other commands */
		if (!RobotCommand.PLACE.toString().equals(name) && !this.targetRobot.isPlaced()) {
			throw new RobotCommandValidationException("Robot should be placed at first.");
		}
		/* validate place command */
		if (RobotCommand.PLACE.toString().equals(name) && !Objects.isNull(args)) {
			if (RobotCommand.PLACE.getArgsNum() != args.length ) {
				throw new RobotCommandValidationException(name,args);
			} else {
				String regex = "[0-9]+";
				Matcher arg1Matcher = Pattern.compile(regex).matcher(args[0]);
				Matcher arg2Matcher = Pattern.compile(regex).matcher(args[1]);
				Boolean isDirEnumStr = Direction.isValidDirection(args[2]);
				if(!arg1Matcher.matches() || !arg2Matcher.matches() || !isDirEnumStr) {
					throw new RobotCommandValidationException(name,args);
				}
			}
		}
	}

}
