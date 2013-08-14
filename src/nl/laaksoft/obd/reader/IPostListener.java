/*
 * TODO put header 
 */
package nl.laaksoft.obd.reader;

import nl.laaksoft.obd.reader.io.ObdCommandJob;

/**
 * TODO put description
 */
public interface IPostListener {

	void stateUpdate(ObdCommandJob job);
	
}