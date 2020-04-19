/*
 * RainfallRate class for Weather Station TCSS 360 		
 *  
 * Class: TCSS 360
 * Professor: Kivanç A. DINCER
 * Assignment: #1 Weather Station
 * Due Date: 4/19/20
 * Year: Spring 2020
 * School: UW-Tacoma
 */

package computations;

import java.io.File;	
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.TreeSet;
import controller.Controller;
import controller.DataPacket;
import sensors.AbstractSensor;

/**
 * 
 * @author Gregory Hablutzel
 * @version 1.0
 * This class represents the Rainfall Rate calculation for the VantagePro2 Weather Station.
 * RainfallRate calculates the rainfall rate given the past 60 seconds of rainfall data.
 */

public class RainfallRate extends AbstractSensor<Double> implements Runnable {
	private String sensor = "Rain";
	private String measurementString = "rain rate";
	
	// Take the last 60 seconds to analyze.
	private static final long PREV_DATA_LENGTH = 60;
	
	private static final int maxVal = 30; // 30"/hr
	private static final double minVal = 0.04; // 0.04"/hr

	TreeSet<DataPacket<Double>> rainDataSet;
	/**
	 * Constructs a RainfallRate object.
	 * @param file: output file.
	 * @param rainDataSet: input rain data.
	 * @throws IllegalArgumentException if file is null
	 * @throws IllegalArgumentException if tempDataSet is null
	 * @throws IllegalArgumentException if humidityDataaSet is null
	 */
	public RainfallRate (File file, TreeSet<DataPacket<Double>> rainDataSet) {
		if (file == null) {
			throw new IllegalArgumentException("file cannot be null");
		}
		if (rainDataSet == null) {
			throw new IllegalArgumentException("input set cannot be null");
		}
		 dataSet = new TreeSet<DataPacket<Double>>();
		 this.file = file;
		 this.rainDataSet = rainDataSet;
	}
	
	/**
	 * Calculates the rain rate (average) given the past 60 seconds of rain data.
	 * @return returns the rain rate.
	 */
	public double calcRainRate() {
		// 0 or [0.4, 30]
		 TreeSet<DataPacket<Double>> inputSetTail = (TreeSet<DataPacket<Double>>) 
				 rainDataSet.tailSet(new DataPacket<Double>(ZonedDateTime
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

		 double rate = rateBig.doubleValue() /20.0 * 3600.0 ; // have "/20 sec, want "/hr
		 
		 rate = round(rate, 2);

		 if (rate < minVal) {
			 rate = 0;
		 }
		 if (rate > maxVal) {
			 rate = maxVal;
		 }
		 return rate;
	}
	
	/**
	 * Method that executes in Runnable thread.
	 * Generates a new data point, adds it to the data set, and serializes last 60 seconds of data set to the Console.
	 */
	public void run() {
		super.run(dataSet, Controller.RAINRATE_FILE, 0.0, calcRainRate(), sensor, measurementString);
	}
}
