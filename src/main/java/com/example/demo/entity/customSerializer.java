package com.example.demo.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.Arrays;

@JsonComponent
public class customSerializer extends JsonSerializer<Booked> {
    @Override
    public void serialize(Booked booked, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("expirationDate", booked.getExpirationDate().toString());
        jsonGenerator.writeStringField("ticketPrice", booked.getPrice().toPlainString());
        jsonGenerator.writeStringField("id", booked.getMovie().getId().toString());
        jsonGenerator.writeStringField("movieName", booked.getMovie().getMovieName());
        jsonGenerator.writeStringField("roomNr", booked.getMovie().getRoomNr().toString());
        jsonGenerator.writeStringField("totalRows", booked.getMovie().getTotalRows().toString());
        jsonGenerator.writeStringField("totalColumns", booked.getMovie().getTotalColumns().toString());
        jsonGenerator.writeStringField("seats", Arrays.toString(booked.getMovie().getSeats()));
        jsonGenerator.writeStringField("screeningDateTime", booked.getMovie().getScreeningDateTime().toString());
        jsonGenerator.writeEndObject();
    }
}
