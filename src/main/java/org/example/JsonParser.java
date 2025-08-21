package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class JsonParser {

    ObjectMapper         objectMapper = new ObjectMapper();
    @Getter
    HashMap<String,Long> map = new HashMap<>();
    List<Double>         priceList = new ArrayList<>();
    @Getter
    double               difPrice;


    void fileToListOfPojos(String fileFolder) throws IOException {

        File                 file = new File(fileFolder);
        TicketList           ticketList = objectMapper.readValue(file, new TypeReference<>() {});
        DateTimeFormatter    dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        List<Ticket>         myList = ticketList.getTickets();       

        for (Ticket ticket : myList) {

            if (ticket.getOrigin().equals("VVO") && ticket.getDestination().equals("TLV")) {

                LocalDateTime departureDateTime = LocalDateTime.parse(ticket.getDeparture_date() + " " + this.correctTimeValue(ticket.getDeparture_time()), dateTimeFormatter);
                LocalDateTime arrivalDateTime = LocalDateTime.parse(ticket.getArrival_date() + " " + this.correctTimeValue(ticket.getArrival_time()), dateTimeFormatter);
                long          flightDuration = java.time.Duration.between(
                        departureDateTime, arrivalDateTime).toMinutes();

                if ( (!map.containsKey(ticket.getCarrier())) || (flightDuration < map.get(ticket.getCarrier())) ) {
                    map.put(ticket.getCarrier(),flightDuration);
                }

                priceList.add(ticket.getPrice());
            }
        }

        this.getPriceDifference();
    }

    private void getPriceDifference() {

        int count = priceList.size();

        if (count != 0) {
            double sum = priceList.stream().mapToDouble(Double::doubleValue).sum();
            double averagePrice = sum / count;
            Collections.sort(priceList);

            if (count % 2 == 0)
            {
                difPrice = averagePrice - (priceList.get(count / 2) + priceList.get(count / 2 - 1)) / 2;
            }
            else {
                difPrice = averagePrice - priceList.get((count)/2);
            }
        }
    };

    public String correctTimeValue(String jsonStringTime){

        return (jsonStringTime.length()<5)? "0" + jsonStringTime:jsonStringTime;

    }
}


