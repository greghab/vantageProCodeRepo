package sensors;

import controller.Controller;
import sensors.AbstractSensor;

public class WindDirection extends AbstractSensor<Integer> implements Runnable {

	/** The type of sensor. **/
	private String sensorName = "Wind";
	
	/** The type of measurement for this sensor. **/
	private String measurementDescription = "wind direction";
	private static final int max = 360; // 360
	private static final int min = 1; // 1
	/**
	 * The WindSensor.java class constructor.
	 * 
	 * @param theOutputSet is the packet that contains the wind data.
	 * @param theFile is the file to write the data into.
	 * @param theCableLength is the length of the anemometer.
	 */
	
	public int calcWindDirection() {
		return  rand.nextInt(max + 1 - min) + min; // [1, 360]

	}
	/**
	 * Runs and add the data to controller() and to a text file.
	 */
	public void run() {
		super.run(Controller.WINDDIRECTION_SET, Controller.WINDDIRECTION_FILE, 0, calcWindDirection(), sensorName, measurementDescription);
	}
}
