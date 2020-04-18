package computations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.TreeSet;
import controller.Controller;
import controller.DataPacket;
import sensors.AbstractSensor;

public class RainfallRate extends AbstractSensor<Double> implements Runnable {
	private String sensor = "Rain";
	private String measurementString = "rain rate";
	
	// Take the last 60 seconds to analyze.
	private static final long PREV_DATA_LENGTH = 60;
	
	private static final int maxVal = 30; // 30"/hr
	private static final double minVal = 0.04; // 0.04"/hr

	public double calcRainRate() {
		// 0 or [0.4, 30]
		 TreeSet<DataPacket<Double>> inputSetTail = (TreeSet<DataPacket<Double>>) 
				 Controller.RAINFALL_SET.tailSet(new DataPacket<Double>(ZonedDateTime
		          .now()
		          .minusSeconds(PREV_DATA_LENGTH), sensor, measurementString, 0.0));

		 if (inputSetTail.size() == 0) {
			 throw new IllegalArgumentException("rain rate input set is empty!");
		 }
		 BigDecimal sumVal = new BigDecimal(0);
		 
		 for (DataPacket<Double> dp: inputSetTail) {
			 BigDecimal dpVal = new BigDecimal(dp.getValue());
			 sumVal = sumVal.add(dpVal);
		 }
		 BigDecimal inputSizeBig = new BigDecimal(inputSetTail.size());
	       BigDecimal rateBig = sumVal.divide(inputSizeBig, RoundingMode.HALF_UP);

		 double rate = rateBig.doubleValue();
		 rate = round(rate, 2);

		 if (rate < minVal) {
			 rate = 0;
		 }
		 if (rate > maxVal) {
			 rate = maxVal;
		 }
		 return rate;

//		double randomNumber = (rand.nextInt(maxVal + 1 - minVal) + minVal) /100.0 ;  
//		if (randomNumber < 0.04) {
//			randomNumber = 0;
//		}
//		return randomNumber;
	}
	
	
	/*
	 *  Arguments:
	 *   outputSet, outputFile, double or int zero value, randomOutputFunction, sensor name, measurement name.
	 */
	public void run() {
		super.run(Controller.RAINRATE_SET, Controller.RAINRATE_FILE, 0.0, calcRainRate(), sensor, measurementString);
	}
}
