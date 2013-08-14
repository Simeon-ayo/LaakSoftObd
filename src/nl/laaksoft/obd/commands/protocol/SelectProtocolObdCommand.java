/*
 * TODO put header
 */
package nl.laaksoft.obd.commands.protocol;

import nl.laaksoft.obd.commands.ObdCommand;
import nl.laaksoft.obd.enums.ObdProtocols;

/**
 * Select the protocol to use.
 */
public class SelectProtocolObdCommand extends ObdCommand {
	
	private final ObdProtocols _protocol;

	/**
	 * @param command
	 */
	public SelectProtocolObdCommand(ObdProtocols protocol) {
		super("AT SP " + protocol.getValue());
		_protocol = protocol;
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
		return "Select Protocol " + _protocol.name();
	}

}