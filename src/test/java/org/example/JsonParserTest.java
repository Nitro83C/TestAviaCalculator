package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonParserTest {
    @Test
    public void testReadJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        //TicketList ticketList = objectMapper.readValue(new File("C:/Users/guram/IdeaProjects/TestAviaCalculator/tickets.json"), TicketList.class);
        TicketList ticketList = objectMapper.readValue(new File("src/main/resources/tickets.json"), TicketList.class);

        assertThat(ticketList.getTickets()).isNotEmpty();
        assertThat(ticketList.getTickets()).hasSize(12);
    }
}

