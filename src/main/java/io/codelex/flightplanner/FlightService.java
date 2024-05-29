package io.codelex.flightplanner;

import io.codelex.flightplanner.Model.Airport;
import io.codelex.flightplanner.Model.Flight;
import io.codelex.flightplanner.Model.PageResult;
import io.codelex.flightplanner.Model.SearchFlightsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public void clear(){
        flightRepository.clear();
    }

    public void delete(Long id){
        flightRepository.delete(id);
    }

    public Long getNewId() {
        return flightRepository.getNewId();
    }

    public List<Airport> searchAirport(String search){
        return flightRepository.searchAirport(search);
    }

    public PageResult searchFlights(SearchFlightsRequest request) {
        if (request.getFrom().equals(request.getTo())){
            throw new ResponseStatusException(HttpStatus.valueOf(400), "Cannot be the same airports");
        }
        List<Flight> flights = flightRepository.searchFlights(request);
        return new PageResult(0, flights.size(),flights);
    }

    public void add(Flight flight){
        if(flight.isAirportSame()){
            throw new ResponseStatusException(HttpStatus.valueOf(400), "Cannot be able to add the same airports");
        }
        if(flight.isDatesCorrect()){
            throw new ResponseStatusException(HttpStatus.valueOf(400), "Cannot be able to add strange dates");
        }
        if (flightRepository.isExist(flight)){
            throw new ResponseStatusException(HttpStatus.valueOf(409), "Cannot be able to add same flight twice");
        }
        flightRepository.add(flight);
    }

    public Flight findFlightById(long id){
        return flightRepository.findFlightById(id);
    }

}
