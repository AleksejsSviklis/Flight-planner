package io.codelex.flightplanner.Model.Serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.codelex.flightplanner.Model.Flight;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class FlightSerializer extends JsonSerializer<Flight> {

    @Override
    public void serialize(Flight flight, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", flight.getId());

        jsonGenerator.writeFieldName("from");
        serializers.defaultSerializeValue(flight.getFrom(), jsonGenerator);

        jsonGenerator.writeFieldName("to");
        serializers.defaultSerializeValue(flight.getTo(), jsonGenerator);

        jsonGenerator.writeStringField("carrier",
                flight.getCarrier().substring(0, 1).toUpperCase() +
                        flight.getCarrier().substring(1).toLowerCase()
        );
        jsonGenerator.writeStringField("departureTime", flight.getDepartureTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        jsonGenerator.writeStringField("arrivalTime", flight.getArrivalTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        jsonGenerator.writeEndObject();
    }
}