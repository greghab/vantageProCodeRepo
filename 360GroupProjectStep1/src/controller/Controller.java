package controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import console.Console;

import java.util.concurrent.ScheduledExecutorService; 

import sensors.TemperatureSensor;
import sensors.WindSensor;

// https://dev4devs.com/2016/06/21/java-how-to-create-threads-that-return-values/
// https://www.baeldung.com/java-runnable-callable

public class Controller {
	
	public static HashMap<Long, String> tempMap = new HashMap<Long, String>();
	public static HashMap<Long, String> windMap = new HashMap<Long, String>();

	public static HashMap<Long, String> testMap = new HashMap<Long, String>();
	
	private final ConcurrentSkipListMap<ZonedDateTime, String> events
    = new ConcurrentSkipListMap<>(Comparator.comparingLong(value -> value.toInstant().toEpochMilli()));


	public static File f;
	public static FileOutputStream fos;
	public static ObjectOutputStream oos;
	
	public static void main(String[] args) throws Exception {

		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		TemperatureSensor temp = new TemperatureSensor();
		WindSensor wind = new WindSensor(30);

		scheduledExecutorService.scheduleAtFixedRate(temp, 0, 3, TimeUnit.SECONDS);
		scheduledExecutorService.scheduleAtFixedRate(wind, 0, 1, TimeUnit.SECONDS);
		
		Save obj = new Save();
		obj.i = 4;
		f = new File("serializedOutput.txt");
		fos = new FileOutputStream(f);
		oos = new ObjectOutputStream(fos);
		oos.writeObject(obj);
		
		Console con = new Console();
		con.serialize();
	}
	
    ConcurrentNavigableMap<ZonedDateTime, String> getEventsFromLastMinute() throws InterruptedException {
    	Thread.sleep(2000);
        return events.tailMap(ZonedDateTime
          .now()
          .minusMinutes(1));
    }

    ConcurrentNavigableMap<ZonedDateTime, String> getEventsOlderThatOneMinute() {
        return events.headMap(ZonedDateTime
          .now()
          .minusMinutes(1));
    }
	
}

