package computations;

import controller.Controller;
import sensors.AbstractSensor;

public class DewPoint extends AbstractSensor<Integer> implements Runnable {
	

	private String sensorName = "Dewpoint";
	private String measurementDescription = "dewpoint";
	private static final int MIN = -105; // -105F
	private static final int MAX = 130; // 130F
	
    /**
     * Calculates the dew point given an air temperature
     * @param airTemperature
     * @param relativeHumidity
     * @return
     */
//    public static double calculateDewPoint(double airTemperature, double relativeHumidity) {
//        double lnHumidity = Math.log(relativeHumidity / 100);
//        double tMult = 17.27 * airTemperature;
//        double tPlus = 237.3 + airTemperature;
//        return (237.3 * (lnHumidity + (tMult / tPlus))) / (17.27 - (lnHumidity + (tMult / tPlus))); 
//    }
	  public int calculateDewPoint() {
		  	double airTemperature = Controller.TEMPERATURE_SET.last().getValue(); // get most recent temp value
	        //System.out.println("temp is " + airTemperature);

		  	int relativeHumidity = Controller.HUMIDITY_SET.last().getValue(); // get most recent humidity value
	        //System.out.println("humidity is " + relativeHumidity);

		  //	double airTemperature = 68.0;
		  	//int relativeHumidity = 70;
	        double lnHumidity = Math.log(relativeHumidity / 100.0);
	        double tMult = 17.27 * airTemperature;
	        double tPlus = 237.3 + airTemperature;
	        double dewPoint = (237.3 * (lnHumidity + (tMult / tPlus))) / (17.27 - (lnHumidity + (tMult / tPlus))); 
	        
	        if (dewPoint > 130) {
	        	dewPoint = 130;
	        }
	        if (dewPoint < -105) {
	        	dewPoint = -105;
	        }
	        //System.out.println("dewpoint is " + dewPoint);
	        return (int) Math.round(dewPoint);
	    }
    
//    public static void main(String[] args) {
//        double d1 = calculateDewPoint(20, 70);
//        System.out.println("Dew point of 68 degrees and 70% humidity (should be ~62): " + d1);
//    }
//    
	/*
	 *  Arguments:
	 *   outputSet, outputFile, double or int zero value, randomOutputFunction, sensor name, measurement name.
	 */
	public void run() {
		super.run(Controller.DEWPOINT_SET, Controller.DEWPOINT_FILE, 0, calculateDewPoint(), sensorName, measurementDescription);
	}
}