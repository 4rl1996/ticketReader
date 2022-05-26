package org.example.ticketReader.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ticketReader.data.Ticket;
import org.example.ticketReader.data.TicketList;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlyingTimeService {

    public static void getResultForTicketList(String jsonFilePath) {

        List<Ticket> tickets = readTicketList(jsonFilePath);
        int numberOfFlies = tickets.size();

        getAverageFlyingTime(tickets, numberOfFlies);
        get90Percentile(tickets, numberOfFlies);
    }

    private static List<Ticket> readTicketList(String jsonFilePath) {

        TicketList ticketList;
        ObjectMapper mapper = new ObjectMapper();

        try {
            ticketList = mapper.readValue(Files.readAllBytes(Paths.get(jsonFilePath)), TicketList.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ticketList.getTickets();
    }

    private static void getAverageFlyingTime(List<Ticket> ticketList, int numberOfFlies) {

        double averageFlyingTime = Arrays.stream(ticketList.stream()
                .map(FlyingTimeService::getFlyingTimeInMinutes)
                .mapToLong(l -> l)
                .toArray())
            .average()
            .getAsDouble();

        System.out.printf("Average flight time between Vladivostok and Tel Aviv is %s hours %s minutes \n",
            getHours(averageFlyingTime), getMinutes(averageFlyingTime));
    }

    private static void get90Percentile(List<Ticket> ticketList, int numberOfFlies) {

        List<Long> flyingTimeInMinutesList = ticketList.stream()
            .map(FlyingTimeService::getFlyingTimeInMinutes)
            .sorted()
            .collect(Collectors.toList());

        int percentilePosition = (int) Math.ceil((90.0 / 100.0) * numberOfFlies);

        if (percentilePosition == numberOfFlies - 1) {
            double percentile = (flyingTimeInMinutesList.get(numberOfFlies - 1) + flyingTimeInMinutesList.get(numberOfFlies - 2)) / 2;
            System.out.printf("90th percentile of flight time between Vladivostok and Tel Aviv %s hours %s minutes \n",
                getHours(percentile), getMinutes(percentile));
        } else {
            long percentile = flyingTimeInMinutesList.get(percentilePosition - 1);
            System.out.printf("90th percentile of flight time between Vladivostok and Tel Aviv is %s hours %s minutes  \n",
                getHours(percentile), getMinutes(percentile));
        }
    }

    private static long getFlyingTimeInMinutes(Ticket ticket) {
        return Duration.between(ticket.getDepartureDate().toInstant(), ticket.getArrivalDate().toInstant()).toMinutes();
    }

    private static int getHours(double timeInMinutes) {
        return (int) timeInMinutes / 60;
    }

    private static double getMinutes(double timeInMinutes) {
        return timeInMinutes % 60;
    }
}
