/*
 * TODO put header 
 */
package nl.laaksoft.obd.commands.fuel;

import nl.laaksoft.obd.commands.ObdCommand;
import nl.laaksoft.obd.enums.AvailableCommandNames;

/**
 * TODO put description
 */
public class FuelConsumptionObdCommand extends ObdCommand {

	private float fuelRate = -1.0f;

	public FuelConsumptionObdCommand() {
		super("01 5E");
	}

	public FuelConsumptionObdCommand(ObdCommand other) {
		super(other);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.lighthouselabs.obd.commands.ObdCommand#getFormattedResult()
	 */
	@Override
	public String getFormattedResult() {
		if (!"NODATA".equals(getResult())) {
			// ignore first two bytes [hh hh] of the response
			int a = buffer.get(2);
			int b = buffer.get(3);
			fuelRate = (a * 256 + b) * 0.05f;
		}

		String res = String.format("%.1f%s", fuelRate, "");

		return res;
	}

	public float getLitersPerHour() {
		return fuelRate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.lighthouselabs.obd.commands.ObdCommand#getName()
	 */
	@Override
	public String getName() {
		return AvailableCommandNames.FUEL_CONSUMPTION.getValue();
	}

}
