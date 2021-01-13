package com.li.robot.enums;

import java.util.stream.Stream;

public enum Direction {
	NORTH, SOUTH, EAST, WEST;

	public static boolean isValidDirection(String dirName) {
		return Stream.of(Direction.values()).map(Direction::toString).anyMatch(dirStr -> dirStr.equals(dirName));
	}
}
