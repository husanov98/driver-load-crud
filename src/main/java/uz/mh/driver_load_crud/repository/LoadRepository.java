package uz.mh.driver_load_crud.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import uz.mh.driver_load_crud.model.Load;
import uz.mh.driver_load_crud.projection.LoadProjection;
@Repository
public interface LoadRepository extends ReactiveCrudRepository<Load,Long> {
    @Query("select id, number, status, begin, ending from loads where driver_id =:driverId limit :size offset :page*2")
    Flux<LoadProjection> findByDriverId(Long driverId,int page,int size);
//    @Query(("select * from loads where driver_id =:id"))
//    Flux<Load> findByDriver(Long id,Pageable pageable);
    Flux<Load> findAllBy(Pageable pageable);
}
