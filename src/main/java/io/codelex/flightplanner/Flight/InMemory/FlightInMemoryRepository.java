package io.codelex.flightplanner.Flight.InMemory;

import io.codelex.flightplanner.Model.Airport;
import io.codelex.flightplanner.Model.Flight;
import io.codelex.flightplanner.Model.SearchFlightsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class FlightInMemoryRepository {

    volatile List<Flight> dataRepository = new ArrayList<>();
    volatile AtomicLong id = new AtomicLong(0);

    public synchronized void add(Flight flight){
        dataRepository.add(flight);
    }

    public synchronized void delete(Long id){
        if (id != null || dataRepository.size() > id){
            dataRepository.remove(id);
        }
    }

    public void clear(){
        dataRepository.clear();
    }

    public synchronized Long getNewId(){
        return id.addAndGet(1);
    }

    public boolean isExist(Flight flight){
        return dataRepository.contains(flight);
    }

    public List<Airport> searchAirport(String search) {
        List<Airport> airports = new ArrayList<>();
        String searchUpper = search.toUpperCase().trim();
        for (Flight flight : dataRepository) {
            Airport from = flight.getFrom();
            if (matchesSearch(from, searchUpper)) {
                airports.add(from);
            }
            Airport to = flight.getTo();
            if (matchesSearch(to, searchUpper)) {
                airports.add(to);
            }
        }
        if (airports.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
        }
        return airports;
    }

    private boolean matchesSearch(Airport airport, String search) {
        return airport.getAirportName().contains(search) ||
                airport.getCity().contains(search) ||
                airport.getCountry().contains(search);
    }

    public List<Flight> searchFlights(SearchFlightsRequest request) {
        Airport from;
        Airport to;
        try {
            from = searchAirport(request.getFrom()).getFirst();
            to = searchAirport(request.getTo()).getFirst();
        } catch (ResponseStatusException e) {
            return Collections.emptyList();
        }
        LocalDate departureDate = LocalDate.parse(request.getDepartureDate());
        List<Flight> suitable = new ArrayList<>();
        for (Flight flight : dataRepository) {
            if (flight.getFrom().equals(from) && flight.getTo().equals(to) && flight.getDepartureTime().toLocalDate().isEqual(departureDate)) {
                suitable.add(flight);
            }

        }
        return suitable;
    }

    public Flight findFlightById(Long id){
        for (Flight flight : dataRepository){
            if (flight.getId().equals(id)){
                flight.getId();
                return flight;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
    }

}
