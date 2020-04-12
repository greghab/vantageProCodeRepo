package sensors;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.TreeSet;

import controller.Controller;
import controller.DataPacket;

public class HumiditySensor extends AbstractSensor implements Runnable {
	private String sensorName = "Humidity";
	private String measurementString = "humidity";
	private static final int MIN_HUMIDITY = 0;
	private static final int MAX_HUMIDITY = 100;
	private static File f = new File("humiditySensorSerializedOutput.txt");
	
	@Override
	public void run() {
		try {
			fos = new FileOutputStream(f);
			oos = new ObjectOutputStream(fos);
		} catch (FileNotFoundException e) { 
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}
		eventTime = ZonedDateTime.now();
		DataPacket hdp = new DataPacket(eventTime, sensorName, measurementString, 
				generateHumidity());
		Controller.humiditySet.add(hdp);
		
		TreeSet<DataPacket> humiditySerialize = (TreeSet<DataPacket>) 
				 Controller.humiditySet.tailSet(new DataPacket(ZonedDateTime
		          .now()
		          .minusSeconds(60), sensorName, measurementString, 0.0));
	
			try {
				oos.writeObject(humiditySerialize);
				Controller.con.readSerializedData(f);
				oos.flush();
			    oos.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	}
	
	public double generateHumidity() { 
		double randomNumber = (rand.nextInt(MAX_HUMIDITY + 1 - MIN_HUMIDITY)
				+ MIN_HUMIDITY) / 100.0;  
		return randomNumber < 0.02 ? 0 : randomNumber;
	}

}
