package io.codelex.flightplanner.Flight.Database;

import io.codelex.flightplanner.Model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface FlightDbRepository extends JpaRepository<Flight, Long> {

    @Query(value = "SELECT f.* FROM FLIGHT f " +
            "JOIN AIRPORT from_airport ON f.from_id = from_airport.id " +
            "JOIN AIRPORT to_airport ON f.to_id = to_airport.id " +
            "WHERE from_airport.airport_name LIKE :from " +
            "AND to_airport.airport_name LIKE :to " +
            "AND FORMATDATETIME(f.departure_time, 'yyyy-MM-dd') LIKE :date", nativeQuery = true)
    List<Flight> searchFlights(@Param("from") String from,
                               @Param("to") String to,
                               @Param("date") String date);

}

