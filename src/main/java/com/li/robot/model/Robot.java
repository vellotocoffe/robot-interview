package com.li.robot.model;

import org.springframework.stereotype.Component;

import com.li.robot.enums.Direction;
import com.li.robot.features.MyRobotV1;

@Component
public class Robot implements MyRobotV1 {

	private int xPosition;
	private int yPosition;
	private Direction direction;
	private boolean placed = false;
	private final static int MAX_POSITION = 4;
	private final static int MIN_POSITION = 0;

	public boolean isPlaced() {
		return this.placed;
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	private boolean isFalling(int position) {
		return position > MAX_POSITION || position < MIN_POSITION;
	}

	@Override
	public void turnLeft() {
		switch (this.direction) {
		case NORTH:
			this.direction = Direction.WEST;
			break;
		case SOUTH:
			this.direction = Direction.EAST;
			break;
		case WEST:
			this.direction = Direction.SOUTH;
			break;
		case EAST:
			this.direction = Direction.NORTH;
			break;
		default:
			break;
		}
	}

	@Override
	public void turnRight() {
		switch (this.direction) {
		case NORTH:
			this.direction = Direction.EAST;
			break;
		case SOUTH:
			this.direction = Direction.WEST;
			break;
		case WEST:
			this.direction = Direction.NORTH;
			break;
		case EAST:
			this.direction = Direction.SOUTH;
			break;
		default:
			break;
		}

	}

	@Override
	public void move() {
		switch (this.direction) {
		case NORTH:
			if (!isFalling(this.yPosition + 1)) {
				this.yPosition++;
			}
			break;
		case SOUTH:
			if (!isFalling(this.yPosition - 1)) {
				this.yPosition--;
			}
			break;
		case WEST:
			if (!isFalling(this.xPosition - 1)) {
				this.xPosition--;
			}
			break;
		case EAST:
			if (!isFalling(this.xPosition + 1)) {
				this.xPosition++;
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void report() {
		System.out.println(String.format("current position %s,%s, current direction %s", this.xPosition, this.yPosition,
				this.direction));
	}

	@Override
	public void place(int xPosition, int yPosition, Direction direction) {
		if(isFalling(xPosition) || isFalling(yPosition)) {
			System.err.println("Your robot is about falling, command ignored.");
			return;
		}
		this.placed = true;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.direction = direction;
	}

}
