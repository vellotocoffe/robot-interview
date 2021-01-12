package com.li.robot.features;

import com.li.robot.enums.Direction;

public interface MyRobotV1 {
	/**
	 * place the robot at initial position on table.
	 * */
	public void place(int xPosition,int yPosition,Direction direction);
	/**
	 * print report.
	 * */
	public void report();
	/**
	 * move forward.
	 * */
	public void move();
	/**
	 * turn robot left according to current direction.
	 * */
	public void turnLeft();
	/**
	 * turn robot right according to current direction.
	 * */
	public void turnRight();
}
