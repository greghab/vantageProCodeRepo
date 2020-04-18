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
import sensors.HumiditySensor;
import sensors.RainSensor;
import sensors.WindDirection;
import sensors.WindSensor;

/**
 * @author greghab
 *
 */
class WindDirectionTest {

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
		int[] generatedValues = {236, 341, 217};

		//ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		WindDirection windDirection = new WindDirection();
		windDirection.run();
		windDirection.run();
		windDirection.run();
		
		int i = 0;
		for (DataPacket<Integer> dp : Controller.WINDDIRECTION_SET) {
			if (dp.getValue() != generatedValues[i]) {
				fail("values dont match");
			}
			i++;
		}
	}
}
