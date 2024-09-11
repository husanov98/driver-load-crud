package uz.mh.driver_load_crud.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.mh.driver_load_crud.dto.DriverDto;
import uz.mh.driver_load_crud.model.Driver;


public interface DriverService {
    Mono<ResponseEntity<Driver>> findDriverById(Long id);
    Mono<Driver> searchDriverByEmail(String email);
    Flux<Driver> findAllDrivers(int page,int size);
    Mono<String> saveDriver(DriverDto driverDto);
    Mono<String> updateDriver(Driver driver);
    Mono<String> deleteDriverById(Long id);
    Mono<String> isPresentByPhone(String phone);
    Mono<Driver> findByEmail(String email);
}
