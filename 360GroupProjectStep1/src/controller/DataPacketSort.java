package controller;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class DataPacketSort {
	    private final ConcurrentSkipListMap<ZonedDateTime, ConcurrentHashMap<String, Double>> packets
	      = new ConcurrentSkipListMap<>(Comparator.comparingLong(value -> value.toInstant().toEpochMilli()));

	    public void acceptPacket(DataPacket packet) {
	        packets.put(packet.getEventTime(), packet.getData());
	    }

	    public ConcurrentNavigableMap<ZonedDateTime, ConcurrentHashMap<String, Double>> getPacketsFromLastMinute() throws InterruptedException {
	    	//Thread.sleep(2000);
	        return packets.tailMap(ZonedDateTime
	          .now()
	          .minusMinutes(1));
	    }

	    public ConcurrentNavigableMap<ZonedDateTime, ConcurrentHashMap<String, Double>> getPacketsOlderThatOneMinute() {
	        return packets.headMap(ZonedDateTime
	          .now()
	          .minusMinutes(1));
	    }
}
