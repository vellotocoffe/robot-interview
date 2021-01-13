package com.li.robot.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.li.robot.enums.Direction;
import com.li.robot.exception.RobotFallingException;
import com.li.robot.features.MyRobotV1;

@Component
public class Robot implements MyRobotV1 {

	@Value("${robot.max.position}")
	private  int maxPosition;
	@Value("${robot.min.position}")
	private  int minPosition;
	@Value("${robot.move.unit}")
	private int moveUnit;

	private int xPosition;
	private int yPosition;
	private Direction direction;
	private boolean placed = false;

	
	public int getMaxPosition() {
		return maxPosition;
	}

	public void setMaxPosition(int maxPosition) {
		this.maxPosition = maxPosition;
	}

	public int getMinPosition() {
		return minPosition;
	}

	public void setMinPosition(int minPosition) {
		this.minPosition = minPosition;
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
	
	public int getMoveUnit() {
		return moveUnit;
	}

	public void setMoveUnit(int moveUnit) {
		this.moveUnit = moveUnit;
	}

	public boolean isPlaced() {
		return this.placed;
	}

	public void init() {
		this.placed = false;
	}
	
	private boolean isFalling(int position) {
		return position > maxPosition || position < minPosition;
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
		int nextYPosition;
		int nextXPosition;
		switch (this.direction) {
			case NORTH:
				nextYPosition = this.yPosition + this.moveUnit;
				if (!isFalling(nextYPosition)) {
					this.yPosition = nextYPosition;
				} else {
					throw new RobotFallingException(this.xPosition, nextYPosition);
				}
				break;
			case SOUTH:
				nextYPosition = this.yPosition - this.moveUnit;
				if (!isFalling(nextYPosition)) {
					this.yPosition = nextYPosition;
				} else {
					throw new RobotFallingException(this.xPosition, nextYPosition);
				}
				break;
			case WEST:
				nextXPosition = this.xPosition - this.moveUnit;
				if (!isFalling(nextXPosition)) {
					this.xPosition = nextXPosition;
				} else {
					throw new RobotFallingException(nextXPosition, this.yPosition);
				}
				break;
			case EAST:
				nextXPosition = this.xPosition + this.moveUnit;
				if (!isFalling(nextXPosition)) {
					this.xPosition = nextXPosition;
				} else {
					throw new RobotFallingException(nextXPosition, this.yPosition);
				}
				break;
			default:
				break;
			}
	}

	@Override
	public void report() {
		System.out.println(String.format("%s,%s,%s", this.xPosition, this.yPosition, this.direction.name()));
	}

	@Override
	public void place(int xPosition, int yPosition, Direction direction) {
		if (isFalling(xPosition) || isFalling(yPosition)) {
			throw new RobotFallingException(xPosition, yPosition);
		}
		this.placed = true;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.direction = direction;
	}

}
