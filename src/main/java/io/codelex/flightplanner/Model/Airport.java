package io.codelex.flightplanner.Model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.codelex.flightplanner.Model.Serializer.AirportSerializer;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

@JsonSerialize(using = AirportSerializer.class)
public class Airport {
    @NotBlank(message = "Country must not be blank")
    private String country;
    @NotBlank(message = "City must not be blank")
    private String city;
    @NotBlank(message = "Airport must not be blank")
    private String airport;

    public Airport(String country, String city, String airport) {
        this.country = country.toUpperCase().trim();
        this.city = city.toUpperCase().trim();
        this.airport = airport.toUpperCase().trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport1 = (Airport) o;
        return country.equalsIgnoreCase(airport1.country) &&
                city.equalsIgnoreCase(airport1.city) &&
                airport.equalsIgnoreCase(airport1.airport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, airport);
    }

}
