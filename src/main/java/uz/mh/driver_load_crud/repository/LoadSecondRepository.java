package uz.mh.driver_load_crud.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.mh.driver_load_crud.model.Load;
import uz.mh.driver_load_crud.projection.LoadProjection;

@Repository
public interface LoadSecondRepository extends ReactiveSortingRepository<Load,Long> {
    Flux<Load> findAllBy(Pageable pageable);

    Mono<Long> count();

    @Query("select id, number, status, begin, ending from loads where driver_id =:driverId")
    Flux<LoadProjection> findByDriverId(Long driverId, Pageable pageable);
}
