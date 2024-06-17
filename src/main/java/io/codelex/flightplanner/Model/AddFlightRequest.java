package io.codelex.flightplanner.Model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddFlightRequest {
    @Valid
    @NotNull(message = "From location must not be null")
    private Airport from;
    @Valid
    @NotNull(message = "To location must not be null")
    private Airport to;
    @NotBlank(message = "Carrier must not be empty")
    private String carrier;
    @NotBlank(message = "Departure time must not be empty")
    private String departureTime;
    @NotBlank(message = "Arrival time must not be empty")
    private String arrivalTime;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public AddFlightRequest(Airport from, Airport to, String carrier, String departureTime, String arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public String toString() {
        return "AddFlightRequest{" +
                "from=" + from +
                ", to=" + to +
                ", carrier='" + carrier + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", formatter=" + formatter +
                '}';
    }

    public Flight toFlight(Long id){
        if (id == null) {
            return new Flight(
                    from,
                    to,
                    carrier,
                    LocalDateTime.parse(departureTime, formatter),
                    LocalDateTime.parse(arrivalTime, formatter)
            );
        } else {
            return new Flight(
                    id,
                    from,
                    to,
                    carrier,
                    LocalDateTime.parse(departureTime, formatter),
                    LocalDateTime.parse(arrivalTime, formatter)
            );
        }
    }

}
