package io.codelex.flightplanner;

import io.codelex.flightplanner.Model.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@RestController
@Validated
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService){
        this.flightService = flightService;
    }

    @PostMapping("/testing-api/clear")
    public void clear(){
        flightService.clear();
    }

    @GetMapping("/admin-api/flights/{id}")
    public Flight getFlight(@PathVariable("id") Long id){
        throw new ResponseStatusException(HttpStatus.valueOf(404));
    }

    @DeleteMapping("/admin-api/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFlight(@PathVariable("id") String id){
        flightService.delete(Long.valueOf(id));
    }

    @PutMapping("/admin-api/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@Valid @RequestBody AddFlightRequest request){
            Flight flight = request.toFlight(flightService.getNewId());
            flightService.add(flight);
            return flight;
    }

    @GetMapping("/api/airports")
    public List<Airport> searchAirports(@RequestParam("search") String search){
        return flightService.searchAirport(search);

    }

    @PostMapping("/api/flights/search")
    public PageResult searchFlights(@Valid @RequestBody SearchFlightsRequest request){
        return flightService.searchFlights(request);
    }

    @GetMapping("/api/flights/{id}")
    public Flight findFlightById(@PathVariable("id") String id){
        return flightService.findFlightById(Long.valueOf(id));
    }

}
