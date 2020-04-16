/**
 * 
 */
package sensorsTests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculations.RainfallRate;
import controller.Controller;
import controller.DataPacket;
import sensors.RainSensor;
import sensors.WindSensor;

/**
 * @author greghab
 *
 */
class RainSensorTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Controller con = new Controller();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}
	@Test
	void testGeneratedValues() {
		Double[] rainGeneratedValues = {0.43, 0.44 ,0.48};

		//ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		RainSensor rain = new RainSensor(Controller.rainfallSet, Controller.rainfallFile);
		rain.run();
		rain.run();
		rain.run();
		
		int i = 0;
		for (DataPacket<Double> dp : Controller.rainfallSet) {
			if (Double.compare(dp.getValue(), rainGeneratedValues[i]) != 0) {
				//System.out.println("dp.getValue() is " + dp.getValue() + ", rainGeneratedValues.get(i) is " + rainGeneratedValues.get(i));
				fail("values dont match");
			}
				
			i++;
		}
	}
    @Test
	void testFileNotFoundException() {
    	 assertThrows(FileNotFoundException.class,
    	            ()->{
    	            //do whatever you want to do here
    	            //ex : objectName.thisMethodShoulThrowNullPointerExceptionForNullParameter(null);
    	        		RainSensor rain = new RainSensor(Controller.rainfallSet, Paths.get("files/words.txt").toFile());
    	            });
		
		
	}

}
