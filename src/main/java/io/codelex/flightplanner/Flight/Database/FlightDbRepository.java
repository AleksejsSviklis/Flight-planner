package io.codelex.flightplanner.Flight.Database;

import io.codelex.flightplanner.Model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightDbRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f WHERE f.from.airportName LIKE %:from% OR f.to.airportName LIKE %:to%")
    List<Flight> searchFlights(@Param("from") String from, @Param("to") String to);
}
