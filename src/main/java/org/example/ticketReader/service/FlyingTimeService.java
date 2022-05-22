package org.example.ticketReader.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ticketReader.data.Ticket;
import org.example.ticketReader.data.TicketList;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class FlyingTimeService {

    public static void getResultForTicketList(String jsonFilePath){
        List<Ticket> tickets = readTicketList(jsonFilePath);
        getAverageFlyingTime(tickets);
        get90Percentile(tickets);
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

    private static void getAverageFlyingTime(List<Ticket> ticketList) {


        double averageFlyingTime = ticketList.stream().map(FlyingTimeService::getFlyingTimeInMinutes).reduce(Long::sum).get() / ticketList.size();
        int hours =  (int) averageFlyingTime / 60;
        double minutes = averageFlyingTime % 60;

        System.out.printf("Cреднее время полета между городами Владивосток и Тель-Авив составляет %s часов %s минут \n", hours, minutes);
    }

    private static void get90Percentile(List<Ticket> ticketList) {
        List<Long> flyingTimeInMinutesList = ticketList.stream()
            .map(FlyingTimeService::getFlyingTimeInMinutes)
            .sorted()
            .collect(Collectors.toList());

        int numberOfFlies = flyingTimeInMinutesList.size();

        int percentilePosition = (int) Math.ceil((90.0 / 100.0) * numberOfFlies);
        if (percentilePosition == numberOfFlies - 1) {
            double percentile = (flyingTimeInMinutesList.get(numberOfFlies - 1) + flyingTimeInMinutesList.get(numberOfFlies - 2)) / 2;
            int hours = (int) percentile / 60;
            double minutes = percentile % 60;
            System.out.printf("90-й процентиль времени полета между городами Владивосток и Тель-Авив %s часов %s минут \n", hours, minutes);
        } else {
            long percentile = flyingTimeInMinutesList.get(percentilePosition - 1);
            int hours = (int) percentile / 60;
            double minutes = percentile % 60;
            System.out.printf("90-й процентиль времени полета между городами Владивосток и Тель-Авив %s часов %s минут \n", hours, minutes);
        }
    }

    private static long getFlyingTimeInMinutes(Ticket ticket) {
        return Duration.between(ticket.getDepartureDate().toInstant(), ticket.getArrivalDate().toInstant()).toMinutes();
    }
}
