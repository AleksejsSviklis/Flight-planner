package io.codelex.flightplanner.Flight.Database;

import io.codelex.flightplanner.Flight.FlightServise;
import io.codelex.flightplanner.Model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

public class FlightDbService implements FlightServise {

    private final FlightDbRepository flightDbRepository;
    private final AirportDbRepository airportDbRepository;

    public FlightDbService(FlightDbRepository flightDbRepository, AirportDbRepository airportDbRepository) {
        this.flightDbRepository = flightDbRepository;
        this.airportDbRepository = airportDbRepository;
    }

    @Override
    public void clear() {
        flightDbRepository.deleteAll();
        airportDbRepository.deleteAll();
    }

    @Override
    public void delete(Long id) {
        flightDbRepository.deleteById(id);
    }

    @Override
    public List<Airport> searchAirport(String search) {
        search = search.toUpperCase().trim();
        return airportDbRepository.searchAirports(search);
    }

    @Override
    public PageResult searchFlights(SearchFlightsRequest request) {

        if(request.getFrom().equals(request.getTo())){
            throw new ResponseStatusException(HttpStatus.valueOf(400), "Cannot be the same airports");
        }

        List<Flight> flights = flightDbRepository.searchFlights(request.getFrom(), request.getTo() , request.getDepartureDate());

        return new PageResult(0, flights.size(), flights);
    }

    @Override
    public Flight add(AddFlightRequest request) {
        Flight flight = request.toFlight(null);

        if(flight.isAirportSame()){
            throw new ResponseStatusException(HttpStatus.valueOf(400), "Cannot be able to add the same airports");
        }
        if(flight.isDatesCorrect()){
            throw new ResponseStatusException(HttpStatus.valueOf(400), "Cannot be able to add strange dates");
        }

        flight.setFrom(getOrCreate(flight.getFrom()));
        flight.setTo(getOrCreate(flight.getTo()));

        try {
            return flightDbRepository.save(flight);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.valueOf(409), "Cannot be able to add same flight twice");
        }

    }

    private Airport getOrCreate(Airport airport) {
        List<Airport> airports = airportDbRepository.findAirportByAirportName(airport.getAirportName());
        if (airports.isEmpty()) {
            return airportDbRepository.save(airport);
        } else {
            return airports.get(0);
        }
    }

    @Override
    public Flight findFlightById(long id) {
        return flightDbRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
