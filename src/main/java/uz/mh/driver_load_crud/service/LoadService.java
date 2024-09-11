package uz.mh.driver_load_crud.service;


import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.mh.driver_load_crud.dto.LoadDto;
import uz.mh.driver_load_crud.dto.UpdateLoadStatusDto;
import uz.mh.driver_load_crud.model.Load;
import uz.mh.driver_load_crud.projection.LoadProjection;

public interface LoadService {

    Mono<ResponseEntity<Load>> findById(Long id);
    Flux<LoadProjection> searchByDriverId(Long driverId, int page, int size);
    Flux<Load> findAllLoads(int page,int size);
    Mono<Load> saveLoad(LoadDto loadDto);
    Mono<Void> deleteLoadById(Long id);
    Mono<String> updateLoad(Load load);
    Mono<String> updateStatus(UpdateLoadStatusDto loadStatusDto);


}
