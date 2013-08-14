/*
 * TODO put header
 */
package nl.laaksoft.obd.commands.protocol;

import nl.laaksoft.obd.commands.ObdCommand;

/**
 * This command will turn-off echo.
 */
public class EchoOffObdCommand extends ObdCommand {

	/**
	 * @param command
	 */
	public EchoOffObdCommand() {
		super("AT E0");
	}

	/**
	 * @param other
	 */
	public EchoOffObdCommand(ObdCommand other) {
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
		return "Echo Off";
	}

}