
package sensors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.util.TreeSet;

import controller.Controller;
import controller.DataPacket;

// https://stackoverflow.com/questions/2444019/how-do-i-generate-a-random-integer-between-min-and-max-in-java

// updateInterval: 60 seconds

public class TemperatureSensor extends AbstractSensor<Double> implements Runnable {
	

	private String sensorName = "Temperature";
	private String measurementDescription = "temperature";
	private static final int maxTemp = 1500; // 150.0F
	private static final int minTemp = -400; // -40.0F

	public double getTemp() {
		return  (rand.nextInt(maxTemp + 1 - minTemp) + minTemp)/10.0; // [-40.0, 150.0]
		
	}
	/*
	 *  Arguments:
	 *   outputSet, outputFile, double or int zero value, randomOutputFunction, sensor name, measurement name.
	 */
	public void run() {
		super.run(Controller.TEMPERATURE_SET, Controller.TEMPERATURE_FILE, 0.0, getTemp(), sensorName, measurementDescription);
	}
}