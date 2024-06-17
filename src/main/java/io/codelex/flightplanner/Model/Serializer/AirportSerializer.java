package io.codelex.flightplanner.Model.Serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.codelex.flightplanner.Model.Airport;
import java.io.IOException;

public class AirportSerializer extends JsonSerializer<Airport> {

    @Override
    public void serialize(Airport airport, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("country",
                airport.getCountry().substring(0, 1).toUpperCase() +
                airport.getCountry().substring(1).toLowerCase()
        );
        jsonGenerator.writeStringField("city",
                airport.getCity().substring(0, 1).toUpperCase() +
                        airport.getCity().substring(1).toLowerCase()
        );
        jsonGenerator.writeStringField("airport",
                airport.getAirportName()
        );
        jsonGenerator.writeEndObject();
    }
}