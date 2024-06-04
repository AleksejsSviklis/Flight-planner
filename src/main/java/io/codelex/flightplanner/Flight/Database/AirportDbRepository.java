package io.codelex.flightplanner.Flight.Database;

import io.codelex.flightplanner.Model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AirportDbRepository extends JpaRepository<Airport, Long> {

    @Query("SELECT a FROM Airport a WHERE a.airportName LIKE %:name%")
    List<Airport> findAirportByAirportName(@Param("name") String airportName);

    @Query("SELECT a FROM Airport a WHERE a.airportName LIKE %:search% OR a.country LIKE %:search% OR a.city LIKE %:search%")
    List<Airport> searchAirports(@Param("search") String search);

//    SELECT *
//    FROM airport
//    WHERE AIRPORT_NAME LIKE '%I%'
//    OR CITY LIKE '%I%'
//    OR COUNTRY LIKE '%I%'

}

