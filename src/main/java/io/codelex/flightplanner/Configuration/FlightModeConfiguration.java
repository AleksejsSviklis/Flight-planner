package io.codelex.flightplanner.Configuration;

import io.codelex.flightplanner.Flight.Database.AirportDbRepository;
import io.codelex.flightplanner.Flight.Database.FlightDbService;
import io.codelex.flightplanner.Flight.Database.FlightDbRepository;
import io.codelex.flightplanner.Flight.FlightServise;
import io.codelex.flightplanner.Flight.InMemory.FlightInMemoryRepository;
import io.codelex.flightplanner.Flight.InMemory.FlightInMemoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightModeConfiguration {

    @Value("${app.storage-mode}")
    private String storageMode;

    @Bean
    @ConditionalOnProperty(prefix = "app", name ="storage-mode", havingValue = "database")
    public FlightServise createFlightDbService(FlightDbRepository flightDbRepository,
                                               AirportDbRepository airportRepository) {
        return new FlightDbService(flightDbRepository, airportRepository);

    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "storage-mode", havingValue = "in-memory")
    public FlightServise createFlightInMemoryService(FlightInMemoryRepository flightInMemoryRepository) {
        return new FlightInMemoryService(flightInMemoryRepository);
    }
}
