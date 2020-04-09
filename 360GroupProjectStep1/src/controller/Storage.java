package controller;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListMap;

public class Storage {
	private final ConcurrentSkipListMap<ZonedDateTime, String> events
    = new ConcurrentSkipListMap<>(Comparator.comparingLong(value -> value.toInstant().toEpochMilli()));
}
