package io.codelex.flightplanner.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.codelex.flightplanner.Model.Serializer.AirportSerializer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@JsonSerialize(using = AirportSerializer.class)
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Country must not be blank")
    private String country;
    @NotBlank(message = "City must not be blank")
    private String city;
    @NotBlank(message = "Airport name must not be blank")
    private String airportName;

    public Airport(String country, String city, String airportName) {
        this.country = country.toUpperCase().trim();
        this.city = city.toUpperCase().trim();
        this.airportName = airportName.toUpperCase().trim();
    }

    public Airport() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country.toUpperCase().trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city.toUpperCase().trim();
    }
    @JsonProperty("airport")
    public String getAirportName() {
        return airportName;
    }
    @JsonProperty("airport")
    public void setAirportName(String airport) {
        this.airportName = airport.toUpperCase().trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport1 = (Airport) o;
        return country.equalsIgnoreCase(airport1.country) &&
                city.equalsIgnoreCase(airport1.city) &&
                airportName.equalsIgnoreCase(airport1.airportName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, airportName);
    }

}
