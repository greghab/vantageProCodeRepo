/**	
 * 
 */
package computationsTests;

import static org.junit.jupiter.api.Assertions.*;	

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import computations.RainfallRate;
import computations.WindChill;
import controller.Controller;
import controller.DataPacket;
import sensors.RainSensor;
import sensors.TemperatureSensor;
import sensors.WindSensor;

/**
 * @author greghab
 *
 */
class WindChillTest {

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
		
		TemperatureSensor temp = new TemperatureSensor();
		WindSensor windSpeed = new WindSensor(Controller.WINDSENSOR_LENGTH);
		WindChill windChill = new WindChill();
		
		// values generate by rain.run() are: {0.43, 0.44 ,0.48};
		temp.run();
		windSpeed.run();
		windChill.run();
		
		if (Controller.WINDCHILL_SET.last().getValue() != 84) {
				fail("values dont match");
		}
	}
}
