/*
 * RainfallRate Test class for Weather Station TCSS 360 		
 *  
 * Class: TCSS 360
 * Professor: Kivanç A. DINCER
 * Assignment: #1 Weather Station
 * Due Date: 4/19/20
 * Year: Spring 2020
 * School: UW-Tacoma
 */

package computationsTests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import computations.RainfallRate;
import controller.Controller;
import controller.DataPacket;
import sensors.RainSensor;

/**
 * 
 * @author Gregory Hablutzel
 * @version 1.0
 * This class tests the RainfallRate class for the VantagePro2 Weather Station.
 */
class RainfallRateTest {

	/*
	 * Ensures that rain fall rate works for first 3 values generate by the rain sensor.
	 */
	@Test
	void testGeneratedValues() {
		RainSensor rain = new RainSensor(Controller.RAINFALL_FILE);
		
		rain.run(); // 0.01
		rain.run(); // 0.04
		rain.run(); // 0.0
		RainfallRate rainfallRate = new RainfallRate(Controller.RAINRATE_FILE, rain.getSet());		rainfallRate.run();
		Double[] rainfallGeneratedValues = {3.0};
		int i = 0;
		for (DataPacket<Double> dp : rainfallRate.getSet()) {
			if (Double.compare(dp.getValue(), rainfallGeneratedValues[i]) != 0) {
				fail("values dont match");
			}
				
			i++;
		}
	}
	
	/*
	 * Generates a DewPoint < -105, to check if its gets caught, and set to -105.
	 */
	@Test
	void testRainrateLessThan04() {
		RainSensor rain = new RainSensor(Controller.RAINFALL_FILE);

		RainfallRate rainfallRate = new RainfallRate(Controller.RAINRATE_FILE, rain.getSet());
		ZonedDateTime eventTime = ZonedDateTime.now();
		DataPacket<Double> rainDP = new DataPacket<Double>(eventTime, "rain", "rain", 0.00); // 0.03"
		rain.getSet().add(rainDP);
		
		rainfallRate.run(); 
		
		
		double rainRate = 0;
		assertTrue(Double.compare(rainfallRate.getSet().last().getValue(), rainRate) == 0);	
	}

	
	/*
	 * Generates a DewPoint < -105, to check if its gets caught, and set to -105.
	 */
	@Test
	void testRainrateGreaterThanMax() {
		RainSensor rain = new RainSensor(Controller.RAINFALL_FILE);

		RainfallRate rainfallRate = new RainfallRate(Controller.RAINRATE_FILE, rain.getSet());
		ZonedDateTime eventTime = ZonedDateTime.now();
		DataPacket<Double> rainDP = new DataPacket<Double>(eventTime, "rain", "rain", 900.0); // 0.03"
		rain.getSet().add(rainDP);
		
		rainfallRate.run();
		
		
		double rainRate = 30; 
		assertTrue(Double.compare(rainfallRate.getSet().last().getValue(), rainRate) == 0);

	
	}
	
	/*
	 * Triggers IllegalArgumentException for file parameter in constructor.
	 */
	@Test
	void testConstructorNullFileException() {
		  assertThrows(IllegalArgumentException.class,
		            ()->{
		        		new RainfallRate(null, new TreeSet<DataPacket<Double>>()); 
		            });
	}
	
	/*
	 * Triggers IllegalArgumentException if data set parameter is null in constructor.
	 */
	@Test
	void testConstructorNullSetInputException() {
		  assertThrows(IllegalArgumentException.class,
		            ()->{
		        		new RainfallRate(new File("test.txt"), null); 
		            });
	}
	
	/*
	 * Triggers IllegalArgumentException if data set has 0 elements inside calcRainRate() function.
	 */
	@Test
	void testCalcRainRateSetInputSizeException() {
		  assertThrows(IllegalArgumentException.class,
		            ()->{
		        		RainfallRate rain = new RainfallRate(new File("test.txt"), new TreeSet<DataPacket<Double>>()); 
		        		rain.calcRainRate();
		            });
	}
}
