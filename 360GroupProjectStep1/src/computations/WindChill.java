package computations;

import controller.Controller;
import sensors.AbstractSensor;

public class WindChill extends AbstractSensor<Integer> implements Runnable {
    

	private String sensorName = "WindChill";
	private String measurementDescription = "wind chill";
	private static final int MIN = 135; // 135 MPH
	private static final int MAX = -110; // -110 MPH
	
    /**
     * Calculates the wind chill given a temperature (degrees farenheit) and a windspeed (miles per hour). Note, this  
     * @param temperature the temperature in farenheit. 
     * @param windspeed the windspeed in miles per hour.
     * @return the calculated wind chill.
     */
    public int calculateWindChill() {
    	double temperature = Controller.TEMPERATURE_SET.last().getValue(); // get most recent temp value
        //System.out.println("temp is " + airTemperature);

	  	int windspeed = Controller.WINDSPEED_SET.last().getValue(); // get most recent humidity value
    	//double temperature = 50.0;
    	//int windspeed = 10;
        if (windspeed < 3) {
            throw new IllegalArgumentException("Windspeed cannot be negative.");
        }
        double windChill = 35.74 + (0.6215 * temperature) - (35.75 * Math.pow(windspeed, 0.16)) + (0.4275 * temperature * Math.pow(windspeed, 0.16));
        
        if(windChill > 135) {
        	windChill = 135;
        }
        if (windChill < -110) {
        	windChill = -110;
        }
        return (int) Math.round(windChill);
    }
//    
//    public static void main(String[] args) {
//        double w1 = calculateWindChill(50, 10);
//        System.out.println("Wind chill for 50 degrees and 10 mph wind (Should be ~46): " + w1);
//    }
//    
    /**
	 * Runs and add the data to controller() and to a text file.
	 */
	public void run() {
		super.run(Controller.WINDCHILL_SET, Controller.WINDCHILL_FILE, 0, calculateWindChill(), sensorName, measurementDescription);
	}
}