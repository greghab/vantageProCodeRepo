package sensors;

import controller.Controller;

public class HumiditySensor extends AbstractSensor<Integer> implements Runnable {

	private String sensorName = "Humidity";
	private String measurementDescription = "humidity";
	private static final int MIN_HUMIDITY = 1;
	private static final int MAX_HUMIDITY = 100;
	
	public int generateHumidity() { 
		int randomNumber = rand.nextInt(MAX_HUMIDITY + 1 - MIN_HUMIDITY) + MIN_HUMIDITY; 
		return randomNumber;
	}
	
	/**
	 * Runs and add the data to controller() and to a text file.
	 */
	public void run() {
		super.run(Controller.HUMIDITY_SET, Controller.HUMIDITY_FILE, 0, generateHumidity(), sensorName, measurementDescription);
	}
}