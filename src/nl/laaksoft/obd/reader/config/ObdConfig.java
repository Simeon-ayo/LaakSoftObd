/*
 * TODO put header
 */
package nl.laaksoft.obd.reader.config;

import java.util.ArrayList;

import nl.laaksoft.obd.commands.ObdCommand;
import nl.laaksoft.obd.commands.SpeedObdCommand;
import nl.laaksoft.obd.commands.control.CommandEquivRatioObdCommand;
import nl.laaksoft.obd.commands.control.DtcNumberObdCommand;
import nl.laaksoft.obd.commands.control.TimingAdvanceObdCommand;
import nl.laaksoft.obd.commands.control.TroubleCodesObdCommand;
import nl.laaksoft.obd.commands.engine.EngineLoadObdCommand;
import nl.laaksoft.obd.commands.engine.EngineRPMObdCommand;
import nl.laaksoft.obd.commands.engine.EngineRuntimeObdCommand;
import nl.laaksoft.obd.commands.engine.MassAirFlowObdCommand;
import nl.laaksoft.obd.commands.engine.ThrottlePositionObdCommand;
import nl.laaksoft.obd.commands.fuel.FindFuelTypeObdCommand;
import nl.laaksoft.obd.commands.fuel.FuelLevelObdCommand;
import nl.laaksoft.obd.commands.fuel.FuelTrimObdCommand;
import nl.laaksoft.obd.commands.pressure.BarometricPressureObdCommand;
import nl.laaksoft.obd.commands.pressure.FuelPressureObdCommand;
import nl.laaksoft.obd.commands.pressure.IntakeManifoldPressureObdCommand;
import nl.laaksoft.obd.commands.protocol.ObdResetCommand;
import nl.laaksoft.obd.commands.temperature.AirIntakeTemperatureObdCommand;
import nl.laaksoft.obd.commands.temperature.AmbientAirTemperatureObdCommand;
import nl.laaksoft.obd.commands.temperature.EngineCoolantTemperatureObdCommand;
import nl.laaksoft.obd.enums.FuelTrim;

/**
 * TODO put description
 */
public final class ObdConfig {

	public static ArrayList<ObdCommand> getCommands() {
		ArrayList<ObdCommand> cmds = new ArrayList<ObdCommand>();
		// Protocol
		cmds.add(new ObdResetCommand());

		// Control
		cmds.add(new CommandEquivRatioObdCommand());
		cmds.add(new DtcNumberObdCommand());
		cmds.add(new TimingAdvanceObdCommand());
		cmds.add(new TroubleCodesObdCommand(0));

		// Engine
		cmds.add(new EngineLoadObdCommand());
		cmds.add(new EngineRPMObdCommand());
		cmds.add(new EngineRuntimeObdCommand());
		cmds.add(new MassAirFlowObdCommand());

		// Fuel
		// cmds.add(new AverageFuelEconomyObdCommand());
		// cmds.add(new FuelEconomyObdCommand());
		// cmds.add(new FuelEconomyMAPObdCommand());
		// cmds.add(new FuelEconomyCommandedMAPObdCommand());
		cmds.add(new FindFuelTypeObdCommand());
		cmds.add(new FuelLevelObdCommand());
		cmds.add(new FuelTrimObdCommand(FuelTrim.LONG_TERM_BANK_1));
		cmds.add(new FuelTrimObdCommand(FuelTrim.LONG_TERM_BANK_2));
		cmds.add(new FuelTrimObdCommand(FuelTrim.SHORT_TERM_BANK_1));
		cmds.add(new FuelTrimObdCommand(FuelTrim.SHORT_TERM_BANK_2));

		// Pressure
		cmds.add(new BarometricPressureObdCommand());
		cmds.add(new FuelPressureObdCommand());
		cmds.add(new IntakeManifoldPressureObdCommand());

		// Temperature
		cmds.add(new AirIntakeTemperatureObdCommand());
		cmds.add(new AmbientAirTemperatureObdCommand());
		cmds.add(new EngineCoolantTemperatureObdCommand());

		// Misc
		cmds.add(new SpeedObdCommand());
		cmds.add(new ThrottlePositionObdCommand());

		return cmds;
	}

}