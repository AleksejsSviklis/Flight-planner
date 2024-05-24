package io.codelex.flightplanner.Model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Objects;

public class Airport {
    @NotBlank(message = "Country must not be blank")
    @NotEmpty(message = "Country must not be empty")
    private String country;
    @NotBlank(message = "City must not be blank")
    @NotEmpty(message = "City must not be empty")
    private String city;
    @NotBlank(message = "Airport must not be blank")
    @NotEmpty(message = "Airport must not be empty")
    private String airport;

    public Airport(String country, String city, String airport) {
        this.country = country;
        this.city = city;
        this.airport = airport;
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
