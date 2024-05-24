package io.codelex.flightplanner;

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

@Repository
public class FlightRepository {

    volatile List<Flight> dataRepository = new ArrayList<>();
    volatile Long id = 0L;

    public synchronized void add(Flight flight){
        System.out.println("testing add" + flight.getId());
        dataRepository.add(flight);
    }

    public synchronized void delete(Long id){
        System.out.println("testing delete" + id);
        dataRepository.remove(id);
    }

    public void clear(){
        dataRepository.clear();
    }

    public synchronized Long getNewId(){
        id+=1;
        return id;
    }

    public boolean isExist(Flight flight){
        return dataRepository.contains(flight);
    }

    public Airport searchAirport(String search) {
        String searchUpper = search.toUpperCase().trim();
        for (Flight flight : dataRepository) {
            Airport from = flight.getFrom();
            if (matchesSearch(from, searchUpper)) {
                return from;
            }
            Airport to = flight.getTo();
            if (matchesSearch(to, searchUpper)) {
                return to;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
    }

    private boolean matchesSearch(Airport airport, String search) {
        return airport.getAirport().toUpperCase().contains(search) ||
                airport.getCity().toUpperCase().contains(search) ||
                airport.getCountry().toUpperCase().contains(search);
    }

    public List<Flight> searchFlights(SearchFlightsRequest request) {
        Airport from;
        Airport to;
        try {
            from = searchAirport(request.getFrom());
            to = searchAirport(request.getTo());
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
                return flight;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
    }

}
