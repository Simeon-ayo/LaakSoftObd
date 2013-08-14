/*
 * TODO put header
 */
package nl.laaksoft.obd.commands.protocol;

import nl.laaksoft.obd.commands.ObdCommand;

/**
 * Turns off line-feed.
 */
public class LineFeedOffObdCommand extends ObdCommand {

	/**
	 * @param command
	 */
	public LineFeedOffObdCommand() {
		super("AT L0");
	}

	/**
	 * @param other
	 */
	public LineFeedOffObdCommand(ObdCommand other) {
		super(other);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.lighthouselabs.obd.commands.ObdCommand#getFormattedResult()
	 */
	@Override
	public String getFormattedResult() {
		return getResult();
	}

	@Override
	public String getName() {
		return "Line Feed Off";
	}

}