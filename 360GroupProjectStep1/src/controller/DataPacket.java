package controller;

import java.time.ZonedDateTime;
import java.util.concurrent.ConcurrentHashMap;

public class DataPacket {


    private final ZonedDateTime eventTime;
    private final String sensor;
    private final ConcurrentHashMap<String, Double> data;

    public DataPacket(String sensor, ZonedDateTime eventTime, ConcurrentHashMap<String, Double> data) {
    	this.sensor = sensor;
        this.eventTime = eventTime;
        this.data = data;
    }
    
    // returns sensor type for the given data packet
    String getSensor() {
    	return sensor;
    }

    // return time when data packet was recorded
    ZonedDateTime getEventTime() {
        return eventTime;
    }

    // returns data contained in data packet
    ConcurrentHashMap<String, Double> getData() {
        return data;
    }
}
