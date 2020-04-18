
package computations;

import controller.Controller;
import sensors.AbstractSensor;

/**
 * Class for calculating the heat index given the relative humidity and temperature. This uses the  
 * @author Cade Reynoldson
 * @version 1.0
 * @date 4/12/2020
 */
public class HeatIndex  extends AbstractSensor<Integer> implements Runnable {
    

	private String sensorName = "HeatIndex";
	private String measurementDescription = "heat index";
	private static final int MIN = -40; // -40F
	private static final int MAX = 165; // -165F
	
    /**
     * Calculates the heat index given the relative humidity and temperature (in farenheit). 
     * @param relativeHumidity the relative humidity. NOTE: for 80% humidity, plug in 80, not 0.80 
     * @param temperature the temperature.
     * @return the heat index. 
     */
	
    public int calculateHeatIndex() {
    	double relativeHumidity = 50.0;
    	double temperature = 60.0;
        double heatIndex = simpleEquation(relativeHumidity, temperature);
        if (heatIndex >= 80.0) { 
            heatIndex = regressionEquation(relativeHumidity, temperature);
        }
        if (heatIndex > 165) {
        	heatIndex = 165;
        }
        if (heatIndex < -40) { 
        	heatIndex = -40;
        }
        return (int) Math.round(heatIndex);
    }
//    public static double calculateHeatIndex(double relativeHumidity, double temperature) {
//        double heatIndex = simpleEquation(relativeHumidity, temperature);
//        if (heatIndex >= 80.0) 
//            return regressionEquation(relativeHumidity, temperature);
//        else
//            return heatIndex;
//    }
    
    /**
     * Calculates the heat index in cases that the temperature is greater than 80 degrees.
     * @param relativeHumidity the relative humidity.
     * @param temperature the temperature.
     * @return the heat index.
     */
    private static double regressionEquation(double relativeHumidity, double temperature) {
        double temperatureSquared = temperature * temperature; //Save the temperature squared
        double relativeHumiditySquared = relativeHumidity * relativeHumidity; //Save the RH squared
        double heatIndex = 
                -42.379 
                + (2.04901523 * temperature) 
                + (10.14333127 * relativeHumidity)
                - (0.22475541 * temperature * relativeHumidity) 
                - (0.00683783 * temperatureSquared)
                - (0.05481717 * relativeHumiditySquared) 
                + (0.00122874 * temperatureSquared * relativeHumidity)
                + (0.00085282 * temperature * relativeHumiditySquared) 
                - (0.00000199 * temperatureSquared * relativeHumiditySquared);
        if ((relativeHumidity < 13) && (80 <= temperature) && (temperature <= 112)) { //if the relative humidity is less than 13% and temperature is between 80 and 112 degrees farenheit, make an adjustment. 
            double adjustment = ((13 - relativeHumidity) / 4) * Math.sqrt((17 - Math.abs(temperature - 95)) / 17);
            heatIndex -= adjustment;
        } else if ((relativeHumidity > 85) && (80 <= temperature) && (temperature <= 87)) { //if the relative humidity is greater than 85% and temp is between 80 and 87 degress, make an adjustment
            double adjustment = ((relativeHumidity - 85) / 10) * ((87 - temperature) / 5);
            heatIndex += adjustment;
        }
        return heatIndex;
    }
    
    /**
     * The simple equation for calculating the heat index. 
     * @param relativeHumidity the relative humidity. 
     * @param temperature the temperature.
     * @return the "simple" calculation of heat index.
     */
    private static double simpleEquation(double relativeHumidity, double temperature) {
        return 0.5 * (temperature + 61.0 + ((temperature - 68.0) * 1.2) + (relativeHumidity * 0.094));
    }
    
//    public static void main(String[] args) {
//        double t1 = calculateHeatIndex(50.0, 60.0);
//        System.out.println("Heat index of 50RH and 60F (Should be ~58):" + t1);
//        double t2 = calculateHeatIndex(86, 85);
//        System.out.println("Heat index of 86RH and 85F (Should be ~100):" + t2);
//        double t3 = calculateHeatIndex(10, 90);
//        System.out.println("Heat index of 10RH and 90F (Should be ~85):" + t3);
//    }
	/*
	 *  Arguments:
	 *   outputSet, outputFile, double or int zero value, randomOutputFunction, sensor name, measurement name.
	 */
	public void run() {
		super.run(Controller.HEATINDEX_SET, Controller.HEATINDEX_FILE, 0, calculateHeatIndex(), sensorName, measurementDescription);
	}
}