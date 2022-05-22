package org.example.ticketReader;

import org.example.ticketReader.service.FlyingTimeService;

public class Main {

    public static void main(String[] args) {
        FlyingTimeService.getResultForTicketList(args[0]);
    }
}
