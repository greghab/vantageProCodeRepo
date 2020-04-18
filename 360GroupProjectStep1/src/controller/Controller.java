/*
 * Controller class for Weather Station TCSS 360 	
 *  
 * Class: TCSS 360
 * Professor: Kivanç A. DINCER
 * Assignment: #1 Weather Station
 * Due Date: 4/19/20
 * Year: Spring 2020
 * School: UW-Tacoma
 */
package controller;

import java.io.File;
import java.util.TreeSet;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import computations.DewPoint;
import computations.HeatIndex;
import computations.RainfallRate;
import computations.WindChill;
import console.Console;
import java.util.concurrent.ScheduledExecutorService;

import sensors.HumiditySensor;
import sensors.RainSensor;
import sensors.TemperatureSensor;
import sensors.WindDirection;
import sensors.WindSensor;

/**
 * 
 * @authors Gregory Hablutzel
 * This class represents the main controller box for the VantagePro2 Weather Station.
 * This box gets sensor input, and serializes the data to a controller via a wireless connection.
 * 
 * The Controller class operates and schedules all the asynchronous sensor threads.
 * It is these threads themselves who manipulate and serialize the data stored inside this Controller.
 *
 */
public class Controller {

	public static final int RAINFALL_UPDATE_INTERVAL =  1; // 20 to 24 seconds per specification.
	public static final int RAINRATE_UPDATE_INTERVAL = 1; // 20 to 24 seconds per specification.
	public static final int TEMP_UPDATE_INTERVAL = 1; // 10 to 12 seconds per specification.
	public static final int WINDCHILL_UPDATE_INTERVAL = 10; // 10 to 12 seconds per specification.
	public static final int WINDDIRECTION_UPDATE_INTERVAL = 1; // 2.5 to 3 seconds per specification.
	public static final int WINDSPEED_UPDATE_INTERVAL = 1; // 2.5 to 3 seconds per specification.
	public static final int HEATINDEX_UPDATE_INTERVAL = 5; // 10 to 12 seconds per specification.
	public static final int HUMIDITY_UPDATE_INTERVAL = 1; // 10 to 12 seconds per specification.
	public static final int DEWPOINT_UPDATE_INTERVAL = 5; // 10 to 12 seconds per specification.

	public static final int RATE_INITIAL_DELAY = 5; // delay to allow sensor data to populate before any rates are calculated on data.

	public static final int WINDSENSOR_LENGTH = 30;

	public static final TreeSet<DataPacket<Double>> RAINFALL_SET = new TreeSet<DataPacket<Double>>();
	public static final TreeSet<DataPacket<Double>> RAINRATE_SET = new TreeSet<DataPacket<Double>>();
	public static final TreeSet<DataPacket<Double>> TEMPERATURE_SET = new TreeSet<DataPacket<Double>>();
	public static final TreeSet<DataPacket<Integer>> HUMIDITY_SET = new TreeSet<DataPacket<Integer>>();
	public static final TreeSet<DataPacket<Integer>> WINDCHILL_SET = new TreeSet<DataPacket<Integer>>();
	public static final TreeSet<DataPacket<Integer>> WINDDIRECTION_SET = new TreeSet<DataPacket<Integer>>();
	public static final TreeSet<DataPacket<Integer>> WINDSPEED_SET = new TreeSet<DataPacket<Integer>>();
	public static final TreeSet<DataPacket<Integer>> DEWPOINT_SET = new TreeSet<DataPacket<Integer>>();
	public static final TreeSet<DataPacket<Integer>> HEATINDEX_SET = new TreeSet<DataPacket<Integer>>();

	
	public static final File RAINFALL_FILE = new File("rainfallSerializedOutput.txt");
	public static final File RAINRATE_FILE = new File("rainfallRateSerializedOutput.txt");
	public static final File TEMPERATURE_FILE = new File("temperatureSerializedOutput.txt");
	public static final File HUMIDITY_FILE = new File("humiditySerializedOutput.txt");
	public static final File WINDCHILL_FILE = new File("windChillSerializedOutput.txt");
	public static final File WINDDIRECTION_FILE = new File("windDirectionSerializedOutput.txt");
	public static final File WINDSPEED_FILE = new File("windSpeedSerializedOutput.txt");
	public static final File DEWPOINT_FILE = new File("dewpointSerializedOutput.txt");
	public static final File HEATINDEX_FILE = new File("heatIndexSerializedOutput.txt");

	public static final Console CON = new Console();

	public static void main(String[] args) throws Exception {

		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		
		TemperatureSensor temp = new TemperatureSensor();
		WindSensor windSpeed = new WindSensor(WINDSENSOR_LENGTH);
		WindDirection windDirection = new WindDirection();
		WindChill windChill = new WindChill();
		HumiditySensor humidity = new HumiditySensor();

		RainSensor rain = new RainSensor();
		RainfallRate rainfallRate = new RainfallRate();
		
		HeatIndex heatIndex = new HeatIndex();
		DewPoint dewPoint = new DewPoint();
	


		// throw a FileNotFoundException on purpose 
		//RainSensor rain = new RainSensor(rainfallSet, Paths.get("files/words.txt").toFile());

		
		//scheduledExecutorService.scheduleAtFixedRate(windSpeed, 0, 
		//		windSpeedUpdateInterval, TimeUnit.SECONDS);
		
		
		//scheduledExecutorService.scheduleAtFixedRate(rain, 0, RAINFALL_UPDATE_INTERVAL, TimeUnit.SECONDS);
		
		//scheduledExecutorService.scheduleAtFixedRate(temp, 0, TEMP_UPDATE_INTERVAL, TimeUnit.SECONDS);
		//temp.run();
		//scheduledExecutorService.scheduleAtFixedRate(windSpeed, 0, WINDSPEED_UPDATE_INTERVAL, TimeUnit.SECONDS);
		//windSpeed.run();
		scheduledExecutorService.scheduleAtFixedRate(windDirection, 0, WINDDIRECTION_UPDATE_INTERVAL, TimeUnit.SECONDS);
		//scheduledExecutorService.scheduleAtFixedRate(humidity, 0, HUMIDITY_UPDATE_INTERVAL, TimeUnit.SECONDS);
//		scheduledExecutorService.scheduleAtFixedRate(dewPoint, RATE_INITIAL_DELAY, DEWPOINT_UPDATE_INTERVAL, TimeUnit.SECONDS);

		//scheduledExecutorService.scheduleAtFixedRate(heatIndex, RATE_INITIAL_DELAY, HEATINDEX_UPDATE_INTERVAL, TimeUnit.SECONDS);
		//scheduledExecutorService.scheduleAtFixedRate(windChill, RATE_INITIAL_DELAY, WINDCHILL_UPDATE_INTERVAL, TimeUnit.SECONDS);
		//windChill.run();

		//scheduledExecutorService.scheduleAtFixedRate(rainfallRate, RATE_INITIAL_DELAY, RAINRATE_UPDATE_INTERVAL, TimeUnit.SECONDS);
	}
}
