package io.codelex.flightplanner.Flight;

import io.codelex.flightplanner.Model.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Validated
public class FlightController {

    @Value("${app.storage-mode}")
    private String storageMode;

    private final FlightServise flightServise;

    public FlightController(FlightServise flightServise){
        this.flightServise = flightServise;
    }

    @PostMapping("/testing-api/clear")
    public void clear(){
        flightServise.clear();
    }

    @GetMapping("/admin-api/flights/{id}")
    public Flight getFlight(@PathVariable("id") Long id){
        throw new ResponseStatusException(HttpStatus.valueOf(404));
    }

    @DeleteMapping("/admin-api/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFlight(@PathVariable("id") String id){
        flightServise.delete(Long.valueOf(id));
    }

    @PutMapping("/admin-api/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@Valid @RequestBody AddFlightRequest request){
        return flightServise.add(request);
    }

    @GetMapping("/api/airports")
    public List<Airport> searchAirports(@RequestParam("search") String search){
        return flightServise.searchAirport(search);

    }

    @PostMapping("/api/flights/search")
    public PageResult searchFlights(@Valid @RequestBody SearchFlightsRequest request){
        return flightServise.searchFlights(request);
    }

    @GetMapping("/api/flights/{id}")
    public Flight findFlightById(@PathVariable("id") String id){
        return flightServise.findFlightById(Long.valueOf(id));
    }

}
