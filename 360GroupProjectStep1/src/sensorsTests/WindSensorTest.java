/**	
 * 
 */
package sensorsTests;

import static org.junit.jupiter.api.Assertions.*;	

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.DataPacket;
import sensors.RainSensor;
import sensors.WindSensor;

/**
 * @author greghab
 *
 */
class WindSensorTest {

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
	void testCalcMaxWindSpeed100() {
		WindSensor wind = new WindSensor(230);
		int maxWindSpeed = wind.getMaxWindSpeed();
		assertEquals(100, maxWindSpeed);
	}

	@Test
	void testCalcMaxWindSpeed135() {
		WindSensor wind = new WindSensor(1);
		int maxWindSpeed = wind.getMaxWindSpeed();
		assertEquals(135, maxWindSpeed);
	}
	
	@Test
	void testCalcMaxWindSpeedLessThanOrEqualToZero() {
		  assertThrows(IllegalArgumentException.class,
		            ()->{
		        		new WindSensor(-10);
		            });
	}
	@Test
	void testCalcMaxWindSpeedGreaterThan240() {
		  assertThrows(IllegalArgumentException.class,
		            ()->{
		        		new WindSensor(250);
		            });
	}
}
