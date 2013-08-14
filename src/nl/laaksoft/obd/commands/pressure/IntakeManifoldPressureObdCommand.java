/*
 * TODO put header
 */
package nl.laaksoft.obd.commands.pressure;

import nl.laaksoft.obd.enums.AvailableCommandNames;


/**
 * Intake Manifold Pressure
 */
public class IntakeManifoldPressureObdCommand extends PressureObdCommand {

	/**
	 * Default ctor.
	 */
	public IntakeManifoldPressureObdCommand() {
		super("01 0B");
	}

	/**
	 * Copy ctor.
	 * 
	 * @param other
	 */
	public IntakeManifoldPressureObdCommand(
			IntakeManifoldPressureObdCommand other) {
		super(other);
	}

	@Override
	public String getName() {
		return AvailableCommandNames.INTAKE_MANIFOLD_PRESSURE.getValue();
	}
	
}