package uz.mh.driver_load_crud.service;

import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.mh.driver_load_crud.dto.LoadDto;
import uz.mh.driver_load_crud.dto.UpdateLoadStatusDto;
import uz.mh.driver_load_crud.model.Load;
import uz.mh.driver_load_crud.projection.DriverProjection;
import uz.mh.driver_load_crud.projection.DriverWithLoadsProjection;
import uz.mh.driver_load_crud.projection.LoadProjection;
import uz.mh.driver_load_crud.repository.DriverRepository;
import uz.mh.driver_load_crud.repository.LoadRepository;

import java.util.List;

@Service
public class LoadServiceImpl implements LoadService {
    private final LoadRepository loadRepository;
    private final DriverRepository driverRepository;

    public LoadServiceImpl(LoadRepository loadRepository, DriverRepository driverRepository) {
        this.loadRepository = loadRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public Mono<ResponseEntity<Load>> findById(Long id) {
        return loadRepository.findById(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Flux<LoadProjection> searchByDriverId(Long driverId, int page, int size) {
        return loadRepository.findByDriverId(driverId,page,size);
    }

    @Override
    public Flux<Load> findAllLoads(int page, int size) {
        return loadRepository.findAllBy(PageRequest.of(page,size));
    }

    @Override
    public Mono<Load> saveLoad(LoadDto loadDto) {
        Load load = new Load();
        load.setNumber(loadDto.getNumber());
        load.setLoadDeliveryAddress(loadDto.getLoadDeliveryAddress());
        load.setLoadPickUpAddress(loadDto.getLoadPickUpAddress());
        load.setDriverId(loadDto.getDriverId());
        load.setStatus(loadDto.getStatus());
        return loadRepository.save(load);
    }

    @Override
    public Mono<Void> deleteLoadById(Long id) {
        return loadRepository.deleteById(id);
    }

    @Override
    public Mono<String> updateLoad(Load load) {
        return loadRepository.findById(load.getId())
                .flatMap(existingLoad -> loadRepository.save(load).then(Mono.just("Load updated successfully")))
                .switchIfEmpty(Mono.just("Load not found"));
    }

    @Override
    public Mono<String> updateStatus(UpdateLoadStatusDto loadStatusDto) {
        return loadRepository.findById(loadStatusDto.getLoadId())
                .flatMap(load -> {
                    load.setStatus(loadStatusDto.getStatus());
                    return loadRepository.save(load).then(Mono.just("Load status updated successfully"));
                }).switchIfEmpty(Mono.just("Load not found"));
    }

    @Override
    public Mono<DriverWithLoadsProjection> getAllData(Long driverId, int page, int size) {
        Flux<Load> all = loadRepository.findAllByDriverId(driverId, page, size);
        Mono<DriverProjection> driverMono = driverRepository.findDriverById(driverId);

        return driverMono.flatMap(driver -> all.collectList()
                .map(loads -> new DriverWithLoadsProjection() {
                    @Override
                    public Long getId() {
                        return driver.getId();
                    }
                    @Override
                    public String getFirstname() {
                        return driver.getFirstname();
                    }
                    @Override
                    public String getLastname() {
                        return driver.getLastname();
                    }
                    @Override
                    public String getEmail() {
                        return driver.getEmail();
                    }
                    @Override
                    public String getPhone() {
                        return driver.getPhone();
                    }
                    @Override
                    public List<Load> getLoads() {
                        return loads;
                    }
                })
        );
    }

}
