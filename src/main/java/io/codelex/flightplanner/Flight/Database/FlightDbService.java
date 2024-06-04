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

        List<Flight> flights = flightDbRepository.searchFlights(request.getFrom(), request.getTo());
        for (Flight flight : flights) {
            if (!flight.getDepartureTime().toString().contains(request.getDepartureDate())) {
                flights.remove(flight);
            }
        }

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

        Airport from = flight.getFrom();
        List<Airport> airports = airportDbRepository.findAirportByAirportName(from.getAirportName());
        if (airports.isEmpty()) {
            airportDbRepository.save(from);
        }
        flight.setFrom(airportDbRepository.findAirportByAirportName(from.getAirportName()).getFirst());

        airports.clear();

        Airport to = flight.getTo();
        airports = airportDbRepository.findAirportByAirportName(to.getAirportName());
        if (airports.isEmpty()){
            airportDbRepository.save(to);
        }
        flight.setTo(airportDbRepository.findAirportByAirportName(to.getAirportName()).getFirst());

        try {
            flightDbRepository.save(flight);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.valueOf(409), "Cannot be able to add same flight twice");
        }

        return flightDbRepository.searchFlights(flight.getFrom().getAirportName(),
                flight.getTo().getAirportName())
                .stream()
                .filter(f -> f.equals(flight))
                .findFirst()
                .get();

    }

    @Override
    public Flight findFlightById(long id) {
        return flightDbRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
