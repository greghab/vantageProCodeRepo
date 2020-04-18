/**	
 * 
 */
package computationsTests;

import static org.junit.jupiter.api.Assertions.*;	

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import computations.RainfallRate;
import controller.Controller;
import controller.DataPacket;
import sensors.RainSensor;

/**
 * @author greghab
 *
 */
class RainfallRateTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		//Controller con = new Controller();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}
	@Test
	void testGeneratedValues() {
		RainSensor rain = new RainSensor();
		
		// values generate by rain.run() are: {0.43, 0.44 ,0.48};
		rain.run();
		rain.run();
		rain.run();
		
		RainfallRate rainfallRate = new RainfallRate();
		rainfallRate.run();
		
		Double[] rainfallGeneratedValues = {0.45}; // (average of {0.43, 0.44 ,0.48};
		int i = 0;
		for (DataPacket<Double> dp : Controller.RAINRATE_SET) {
			if (Double.compare(dp.getValue(), rainfallGeneratedValues[i]) != 0) {
				//System.out.println("dp.getValue() is " + dp.getValue() + ", rainGeneratedValues.get(i) is " + rainGeneratedValues.get(i));
				fail("values dont match");
			}
				
			i++;
		}
	}
}
