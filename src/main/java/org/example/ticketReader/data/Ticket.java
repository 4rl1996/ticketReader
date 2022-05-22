package org.example.ticketReader.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ticketReader.util.DateTimeUtil;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Ticket {

    private String origin;

    @JsonProperty("origin_name")
    private String originName;

    private String destination;

    @JsonProperty("destination_name")
    private String destinationName;

    @JsonProperty("departure_date")
    private String departureDate;

    @JsonProperty("departure_time")
    private String departureTime;

    @JsonProperty("arrival_date")
    private String arrivalDate;

    @JsonProperty("arrival_time")
    private String arrivalTime;

    private String carrier;

    private Integer stops;

    private Integer price;

    public Date getDepartureDate() {
        return DateTimeUtil.dateTimeParser(this.departureDate, this.departureTime);
    }

    public Date getArrivalDate() {
        return DateTimeUtil.dateTimeParser(this.arrivalDate, this.arrivalTime);
    }
}
