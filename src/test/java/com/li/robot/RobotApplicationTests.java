package com.li.robot;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.li.robot.engine.RobotCommandExecutor;
import com.li.robot.enums.Direction;
import com.li.robot.exception.RobotCommandValidationException;
import com.li.robot.exception.RobotFallingException;
import com.li.robot.model.Robot;

class RobotApplicationTests {

	private RobotCommandExecutor commandExecutor;
	private Robot robot;

	@BeforeEach
	public void init() {
		commandExecutor = new RobotCommandExecutor();
		robot = new Robot();
		robot.setMaxPosition(4);
		robot.setMinPosition(0);
		commandExecutor.setTargetRobot(robot);
	}

	@Test
	void shouldThrowException_whenCommandNotSupported() {
		assertThrows(RobotCommandValidationException.class, () -> {
			commandExecutor.executeCommand("Antibes");
		});
	}

	@Test
	void shouldThrowException_ifRobotWillGetFallingByMoving() {
		commandExecutor.executeCommand("place", "0", "3", "WEST");
		assertThrows(RobotFallingException.class, () -> {
			commandExecutor.executeCommand("move");
		});
	}

	@Test
	void shouldThrowException_ifRobotWillGetFallingInitially() {
		assertThrows(RobotFallingException.class, () -> {
			commandExecutor.executeCommand("place", "5", "3", "EAST");
		});
	}

	@Test
	void shouldMoveToTargetPosition_ifCommandSeqIsValid() {
		commandExecutor.executeCommand("place", "1", "2", "EAST");
		commandExecutor.executeCommand("move");
		commandExecutor.executeCommand("move");
		commandExecutor.executeCommand("left");
		commandExecutor.executeCommand("move");
		assertTrue(commandExecutor.getTargetRobot().getxPosition() == 3);
		assertTrue(commandExecutor.getTargetRobot().getyPosition() == 3);
		assertTrue(commandExecutor.getTargetRobot().getDirection().equals(Direction.NORTH));
	}

}
