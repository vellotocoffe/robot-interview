package com.li.robot;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.li.robot.engine.RobotCommandExecutor;

@SpringBootApplication
public class RobotApplication implements CommandLineRunner {

	@Autowired
	private RobotCommandExecutor robotCommandExecutor;

	public static void main(String[] args) {
		SpringApplication.run(RobotApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//a scanner waiting for user input via system.in
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Please input commmand (enter quit to exit) :");
			String userInput = sc.nextLine();
			if("quit".equals(userInput)) {
				System.out.println("Merci au revoir.");
				return;
			}
			String[] userInputs = userInput.split(" ");
			String commandName = userInputs[0];
			String[] commandArgs = userInputs.length == 1 ? null : userInputs[1].split(",");
			this.robotCommandExecutor.executeCommand(commandName, commandArgs);
		}

	}

}
