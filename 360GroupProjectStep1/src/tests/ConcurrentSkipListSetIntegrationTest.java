package tests;

import org.junit.Test;

import controller.DataPacket;
import controller.DataPacketSort;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

//import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConcurrentSkipListSetIntegrationTest {

    @Test
    public void givenThreadsProducingEvents_whenGetForEventsFromLastMinute_thenReturnThoseEventsInTheLockFreeWay() throws InterruptedException {
        //given
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        DataPacketSort dataPacketSort = new DataPacketSort();
        int numberOfThreads = 2;
        //when
        Runnable producer = () -> IntStream
          .rangeClosed(0, 100)
          .forEach(index -> dataPacketSort.acceptPacket(new DataPacket("temp", ZonedDateTime
            .now()
            .minusSeconds(index), new ConcurrentHashMap<String, Double>())));

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.execute(producer);
        }

        Thread.sleep(500);

        ConcurrentNavigableMap<ZonedDateTime, ConcurrentHashMap<String, Double>> eventsFromLastMinute = dataPacketSort.getEventsFromLastMinute();

        long eventsOlderThanOneMinute = eventsFromLastMinute
          .entrySet()
          .stream()
          .filter(e -> e
            .getKey()
            .isBefore(ZonedDateTime
              .now()
              .minusMinutes(1)))
          .count();
        assertEquals(eventsOlderThanOneMinute, 0);

        long eventYoungerThanOneMinute = eventsFromLastMinute
          .entrySet()
          .stream()
          .filter(e -> e
            .getKey()
            .isAfter(ZonedDateTime
              .now()
              .minusMinutes(1)))
          .count();

        //then
        assertTrue(eventYoungerThanOneMinute > 0);

        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    @Test
    public void givenThreadsProducingEvents_whenGetForEventsOlderThanOneMinute_thenReturnThoseEventsInTheLockFreeWay() throws InterruptedException {
        //given
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        DataPacketSort dataPacketSort = new DataPacketSort();
        int numberOfThreads = 2;
        //when
        Runnable producer = () -> IntStream
          .rangeClosed(0, 100)
          .forEach(index -> dataPacketSort.acceptPacket(
        		  new DataPacket("temp", ZonedDateTime
            .now()
            .minusSeconds(index), new ConcurrentHashMap<String, Double>())));

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.execute(producer);
        }

        Thread.sleep(500);

        ConcurrentNavigableMap<ZonedDateTime, ConcurrentHashMap<String, Double>> eventsFromLastMinute = dataPacketSort.getEventsOlderThatOneMinute();

        long eventsOlderThanOneMinute = eventsFromLastMinute
          .entrySet()
          .stream()
          .filter(e -> e
            .getKey()
            .isBefore(ZonedDateTime
              .now()
              .minusMinutes(1)))
          .count();
        assertTrue(eventsOlderThanOneMinute > 0);

        long eventYoungerThanOneMinute = eventsFromLastMinute
          .entrySet()
          .stream()
          .filter(e -> e
            .getKey()
            .isAfter(ZonedDateTime
              .now()
              .minusMinutes(1)))
          .count();

        //then
        assertEquals(eventYoungerThanOneMinute, 0);

        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
    }

}