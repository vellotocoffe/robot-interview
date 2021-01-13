package com.li.robot;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.li.robot.engine.RobotCommandExecutor;
import com.li.robot.enums.RobotCommand;
import com.li.robot.exception.RobotCommandValidationException;
import com.li.robot.exception.RobotFallingException;

@Component
public class RobotCommandRunner implements CommandLineRunner {

	private static final String SPACE = " ";

	@Autowired
	private RobotCommandExecutor robotCommandExecutor;

	@Value("${cmd.args.delimiter}")
	private String argsDelimiter;

	@Override
	public void run(String... args) throws Exception {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Please input commmand (enter quit to exit) :");
			String userInput = sc.nextLine();
			if (!StringUtils.hasLength(userInput)) {
				System.err.println("Please enter a valid command name.");
				continue;
			}
			if (RobotCommand.QUIT.toString().equals(userInput.toUpperCase())) {
				sc.close();
				return;
			}
			executeCommand(userInput);
		}
	}

	private void executeCommand(String userInput) {
		String[] userInputs = userInput.split(SPACE);
		String commandName = userInputs[0];
		String[] commandArgs = userInputs.length == 1 ? null : userInputs[1].split(argsDelimiter);
		try {
			this.robotCommandExecutor.executeCommand(commandName, commandArgs);
		} catch (RobotFallingException | RobotCommandValidationException e) {
			System.err.println(e.getMessage());
		}
	}
}
