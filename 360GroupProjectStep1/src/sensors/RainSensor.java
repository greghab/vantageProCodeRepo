package sensors;

import controller.Controller;

// max rated accuraccy is rate for 10"/hr
// (99.99"/3600) = 0.002777"/sec max accurate rate (per measurement)
// (99.99"/3600) * 20 = 0.05555"/20sec max accurate rate (per measurement)
// Max rated daily rainfall is 99.99"
// (99.99"/3600) = 0.0227775"/sec max rate (per measurement)
// (99.99"/3600) * 20 = 0.5555"/20sec max rate (per measurement)


public class RainSensor extends AbstractSensor<Double> implements Runnable {


	private String sensorName = "Rain";
	private String measurementDescription = "rainfall";



	private static final int maxVal = 55; // 0.55"
	private static final int minVal = 0; // 0"


	public double getRain() {
		// [0, 99.99], resolution 0.01"
		double randomNumber = (rand.nextInt(maxVal + 1 - minVal) + minVal) / 100.0;  
		return randomNumber;
	}
	
	/*
	 *  Arguments:
	 *   outputSet, outputFile, double or int zero value, randomOutputFunction, sensor name, measurement name.
	 */
	public void run() {
		super.run(Controller.RAINFALL_SET, Controller.RAINFALL_FILE, 0.0, getRain(), sensorName, measurementDescription);
	}
}
