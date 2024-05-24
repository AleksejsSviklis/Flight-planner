package io.codelex.flightplanner.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class SearchFlightsRequest {

    @NotBlank(message = "First airport must not be blank")
    @NotEmpty(message = "First airport must not be empty")
    @NotNull(message = "First airport must not be null")
    private String from;
    @NotBlank(message = "Second airport must not be blank")
    @NotEmpty(message = "Second airport must not be empty")
    @NotNull(message = "Second airport must not be null")
    private String to;
    @NotNull(message = "baseDate must not be null")
    @NotEmpty(message = "baseDate must not be empty")
    @NotNull(message = "baseDate must not be null")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String departureDate;

    public SearchFlightsRequest(String from, String to, String departureDate) {
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchFlightsRequest that = (SearchFlightsRequest) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(departureDate, that.departureDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, departureDate);
    }
}
