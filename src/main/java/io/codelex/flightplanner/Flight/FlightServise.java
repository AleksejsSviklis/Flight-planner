package io.codelex.flightplanner.Flight;

import io.codelex.flightplanner.Model.*;

import java.util.List;

public interface FlightServise {

    void clear();

    void delete(Long id);

    List<Airport> searchAirport(String search);

    PageResult searchFlights(SearchFlightsRequest request);

    Flight add(AddFlightRequest request);

    Flight findFlightById(long id);

}
