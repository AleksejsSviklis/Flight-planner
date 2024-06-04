package io.codelex.flightplanner.Flight.InMemory;

import io.codelex.flightplanner.Flight.FlightServise;
import io.codelex.flightplanner.Model.*;
import org.apache.coyote.Request;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


public class FlightInMemoryService implements FlightServise {

    private final FlightInMemoryRepository flightInMemoryRepository;

    public FlightInMemoryService(FlightInMemoryRepository flightInMemoryRepository) {
        this.flightInMemoryRepository = flightInMemoryRepository;
    }

    @Override
    public void clear(){
        flightInMemoryRepository.clear();
    }

    @Override
    public void delete(Long id){
        flightInMemoryRepository.delete(id);
    }

    private Long getNewId() {
        return flightInMemoryRepository.getNewId();
    }

    @Override
    public List<Airport> searchAirport(String search){
        return flightInMemoryRepository.searchAirport(search);
    }

    @Override
    public PageResult searchFlights(SearchFlightsRequest request) {
        if (request.getFrom().equals(request.getTo())){
            throw new ResponseStatusException(HttpStatus.valueOf(400), "Cannot be the same airports");
        }
        List<Flight> flights = flightInMemoryRepository.searchFlights(request);
        return new PageResult(0, flights.size(),flights);
    }

    @Override
    public Flight add(AddFlightRequest request){
        Flight flight = request.toFlight(getNewId());
        if(flight.isAirportSame()){
            throw new ResponseStatusException(HttpStatus.valueOf(400), "Cannot be able to add the same airports");
        }
        if(flight.isDatesCorrect()){
            throw new ResponseStatusException(HttpStatus.valueOf(400), "Cannot be able to add strange dates");
        }
        if (flightInMemoryRepository.isExist(flight)){
            throw new ResponseStatusException(HttpStatus.valueOf(409), "Cannot be able to add same flight twice");
        }
        flightInMemoryRepository.add(flight);
        return flight;
    }

    @Override
    public Flight findFlightById(long id){
        return flightInMemoryRepository.findFlightById(id);
    }

}
