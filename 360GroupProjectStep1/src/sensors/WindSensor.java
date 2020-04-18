/*
 * TCSS 360 - Spring 2020	
 * Instructor: Kivanc A. Dincer
 * Group Project 1
 * Due date: 19 April 2020
 */

package sensors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.ZonedDateTime;
import java.util.TreeSet;

import controller.Controller;
import controller.DataPacket;

/**
 * Display wind sensor's data based off the cable length.
 *
 * @author Cade Reynoldson
 * @author Daniel Machen
 * @author Egor Maksimenka
 * @author Gregory Hablutzel
 * @author Melinda Tran
 * @version 9 April 2020
 */

/*
 * 
    Cable Length, Anemometer: 40 feet (12 m) (included) 240 feet (73 m) (maximum recommended)
    Note: Maximum displayable wind decreases as the length of cable increases. At 140’ (42 m) of cable, 
    	the maximum wind speed displayed is 135 mph (60 m/s); at 240’ (73 m), the maximum wind speed 
    	displayed is 100 mph (34 m/s).
         - When calculating the max wind speed for our random input generator, we need to factor in the 
         cable length as a field.
         
         * 0 <= length < 140: 
 */
public class WindSensor extends AbstractSensor<Integer> implements Runnable{
	
	/** The type of sensor. **/
	private String sensorName = "Wind";
	
	/** The type of measurement for this sensor. **/
	private String measurementDescription = "wind speed";
	
	private int theCableLength;

	
	/** The max wind speed using cable length. **/
	private final int maxWindSpeed; // MPH
	
	/**
	 * The WindSensor.java class constructor.
	 * 
	 * @param theOutputSet is the packet that contains the wind data.
	 * @param theFile is the file to write the data into.
	 * @param theCableLength is the length of the anemometer.
	 */
	public WindSensor(int theCableLength) {
		super();
		this.theCableLength = theCableLength;
		maxWindSpeed = calcMaxWindSpeed();
	}
	
	/**
	 * calcMaxWindSpeed() calculate the max wind speed knowing that the 
	 * longer the cable length the shorter the max wind speed will be.
	 * 
	 * @param cableLength is the length of the anemometer.
	 * @return an illegal argument exception when  0 >= cableLength > 240 and a wind speed otherwise.
	 */
	public int calcMaxWindSpeed() {
		if (theCableLength <= 0) {
			throw new IllegalArgumentException("Cable Length cannot be <= 0 ft for wind sensor");
		} else if (theCableLength <= 140) {
			return 135;
		} else if (theCableLength <= 240) {
			return 100;
		} else {
			throw new IllegalArgumentException("Cable Length cannot be > 240ft for wind sensor");
		}
	}
	
	/**
	 * Generate a random integer for the wind speed with a range of [0, maxWindSpeed]
	 * @return
	 */
	public int getWindSpeed() {
		return rand.nextInt(maxWindSpeed + 1); // [0, maxWindSpeed]
	}
	/**
	 * Generate a random integer for the wind speed with a range of [0, maxWindSpeed]
	 * @return
	 */
	public int getMaxWindSpeed() {
		return maxWindSpeed;
	}
	

	/*
	 *  Arguments:
	 *   outputSet, outputFile, double or int zero value, randomOutputFunction, sensor name, measurement name.
	 */
	public void run() {
		super.run(Controller.WINDSPEED_SET, Controller.WINDSPEED_FILE, 0, getWindSpeed(), sensorName, measurementDescription);
	}
}
