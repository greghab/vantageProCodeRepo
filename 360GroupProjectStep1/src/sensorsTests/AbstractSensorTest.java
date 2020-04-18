/**	
 * 
 */
package sensorsTests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.DataPacket;
import sensors.AbstractSensor;
import sensors.RainSensor;
import sensors.WindSensor;

/**
 * @author greghab
 *
 */
class AbstractSensorTest {

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
	void testRoundMethod() {

		AbstractSensor<Integer> instanceToTest = new AbstractSensorTestable();

		//ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		double val = 43.257;
		double roundedVal = instanceToTest.round(val, 1);
		System.out.println("roundedVal is " + roundedVal);
		if (Double.compare(roundedVal, 43.3) != 0) {
			fail("values dont match");
		}
	}
	/** This is needed, because we cannot construct abstract class directly */
	class AbstractSensorTestable extends AbstractSensor<Integer> {
	}
}
