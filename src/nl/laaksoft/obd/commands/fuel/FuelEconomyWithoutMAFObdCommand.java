/*
 * TODO put header 
 */
package nl.laaksoft.obd.commands.fuel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import nl.laaksoft.obd.commands.ObdCommand;
import nl.laaksoft.obd.commands.SpeedObdCommand;
import nl.laaksoft.obd.commands.control.CommandEquivRatioObdCommand;
import nl.laaksoft.obd.commands.engine.EngineRPMObdCommand;
import nl.laaksoft.obd.commands.pressure.IntakeManifoldPressureObdCommand;
import nl.laaksoft.obd.commands.temperature.AirIntakeTemperatureObdCommand;

/**
 * TODO put description
 */
public class FuelEconomyWithoutMAFObdCommand extends ObdCommand {
	
	public static final double AIR_FUEL_RATIO = 14.64;
    public static final double FUEL_DENSITY_GRAMS_PER_LITER = 720.0;
	
	public FuelEconomyWithoutMAFObdCommand() {
		super("");
	}
	
	/**
	 * As it's a fake command, neither do we need to send request or read
	 * response.
	 */
	@Override
	public void run(InputStream in, OutputStream out) throws IOException,
			InterruptedException {
		// prepare variables
		EngineRPMObdCommand rpmCmd = new EngineRPMObdCommand();
		rpmCmd.run(in, out);
		rpmCmd.getFormattedResult();
		
        AirIntakeTemperatureObdCommand airTempCmd = new AirIntakeTemperatureObdCommand();
        airTempCmd.run(in, out);
        airTempCmd.getFormattedResult();
        
        SpeedObdCommand speedCmd = new SpeedObdCommand();
        speedCmd.run(in, out);
        speedCmd.getFormattedResult();
        
        CommandEquivRatioObdCommand equivCmd = new CommandEquivRatioObdCommand();
        equivCmd.run(in, out);
        equivCmd.getFormattedResult();
        
        IntakeManifoldPressureObdCommand pressCmd = new IntakeManifoldPressureObdCommand();
        pressCmd.run(in, out);
        pressCmd.getFormattedResult();
        
        double imap = rpmCmd.getRPM() * pressCmd.getMetricUnit() / airTempCmd.getKelvin();
//        double maf = (imap / 120) * (speedCmd.getMetricSpeed()/100)*()
        
	}

	@Override
	public String getFormattedResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
