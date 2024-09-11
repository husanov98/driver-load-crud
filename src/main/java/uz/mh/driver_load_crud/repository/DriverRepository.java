package uz.mh.driver_load_crud.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.mh.driver_load_crud.model.Driver;

public interface DriverRepository extends ReactiveCrudRepository<Driver,Long> {
    Mono<Driver> findDriverByEmail(String title);
    Flux<Driver> findAllBy(Pageable pageable);
    Mono<Driver> findDriverByPhone(String phone);
    Mono<Driver> findByEmail(String email);

}
