package com.li.robot;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

import com.li.robot.engine.RobotCommandExecutor;
import com.li.robot.exception.RobotCommandValidationException;
import com.li.robot.exception.RobotFallingException;

@SpringBootApplication
public class RobotApplication implements CommandLineRunner {

	@Autowired
	private RobotCommandExecutor robotCommandExecutor;

	public static void main(String[] args) {
		SpringApplication.run(RobotApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//a scanner waiting for user input from system.in
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Please input commmand (enter quit to exit) :");
			String userInput = sc.nextLine();
			if (!StringUtils.hasLength(userInput)) {
				System.err.println("Please enter a valid command name.");
				continue;
			}
			if("quit".equals(userInput)) {
				System.out.println("Merci au revoir.");
				sc.close();
				return;
			}
			String[] userInputs = userInput.split(" ");
			String commandName = userInputs[0];
			String[] commandArgs = userInputs.length == 1 ? null : userInputs[1].split(",");
			try {
				this.robotCommandExecutor.executeCommand(commandName, commandArgs);
			}catch(RobotFallingException | RobotCommandValidationException e) {
				System.err.println(e.getMessage());
			}
			
		}
	}

}
