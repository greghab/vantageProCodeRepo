/*
 * AbstractSensor class for Weather Station TCSS 360 	
 *  
 * Class: TCSS 360
 * Professor: Kivanç A. DINCER
 * Assignment: #1
 * Assignment Title: Weather Station
 * Year: Spring 2020
 * School: UW-Tacoma
 * Description:
 * 
 * Note, we are UNABLE to test for exceptions, because this abstract classes run() method is called within the Runnable threads run() methods.
 * All Runnable threads, by design, cannot throw or return exceptions, and thus we are unable to check for thrown exceptions in JUNIT.
 * 
 * For more information, see here: https://dev4devs.com/2016/06/21/java-how-to-create-threads-that-return-values/
 * 
 */
package sensors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.TreeSet;

import controller.Controller;
import controller.DataPacket;

public abstract class AbstractSensor<T> {

	// protected means that this class and any subclasses may access the property. 
	protected String sensorName = "Abstract Sensor";
	protected String measurement = "NULL";

    protected ZonedDateTime eventTime;
	protected long seed = 24; 
	// for each of time we create a new instance of one of the sensors, 
	// 	create a new random object, and initialize it with a seed to we can reproduce 
	// 	the values it generates later in our JUnit Tests 
	// 	(make sensor output pseudo-random and reproducible)
	protected Random rand = new Random(seed);
	//File f;
	//protected TreeSet<DataPacket<T>> outputSet;
	//protected File f;
	protected FileOutputStream fos;
	protected ObjectOutputStream oos;
	
	
	public double round (double value, int precision) {
	    int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}
	
	public void run(TreeSet<DataPacket<T>> outputSet, File f, T zeroValue, T value, String sensor, String measurementName) {
		//System.out.println("RainFallThread");

		try {
			fos = new FileOutputStream(f);
			//System.out.println("fos");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			oos = new ObjectOutputStream(fos);
			//System.out.println("oos");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		eventTime = ZonedDateTime.now();

		DataPacket<T> rdp = new DataPacket<T>(eventTime, sensor, measurementName, value);

		outputSet.add(rdp);
		 TreeSet<DataPacket<T>> rainSerialize = (TreeSet<DataPacket<T>>) 
				 outputSet.tailSet(new DataPacket<T>(ZonedDateTime
		          .now()
		          .minusSeconds(60), sensor, measurementName, zeroValue));

			try {
				oos.writeObject(rainSerialize);
				//System.out.println("oos2");

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				Controller.CON.<T>readSerializedData(f);
				oos.flush();
			    oos.close();
				//System.out.println("con");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("RainFallRateThread");
	}

}
