package com.li.robot.features;

/**
 * Base class for user command executor. for applying template pattern.
 */
public abstract class CommandExecutor {

	public void executeCommand(String name, String... args) {
		name = name.toUpperCase();
		this.validate(name, args);
		this.execute(name, args);
		this.close();
	}
	
	/**
	 * Close the command executor, can be extended if need to release any resources.
	 * */
	public void close() {}

	abstract public void validate(String name, String... args);

	abstract public void execute(String name, String... args);
}
