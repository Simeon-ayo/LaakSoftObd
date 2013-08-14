/*
 * TODO put header 
 */
package nl.laaksoft.obd.reader;

import nl.laaksoft.obd.reader.io.ObdCommandJob;

/**
 * TODO put description
 */
public interface IPostMonitor {
	void setListener(IPostListener callback);

	boolean isRunning();

	void executeQueue();
	
	void addJobToQueue(ObdCommandJob job);
}