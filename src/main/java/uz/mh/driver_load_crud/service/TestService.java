package uz.mh.driver_load_crud.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import uz.mh.driver_load_crud.model.Load;
import uz.mh.driver_load_crud.projection.LoadProjection;
import uz.mh.driver_load_crud.repository.LoadSecondRepository;

@Service
public class TestService {
    private final LoadSecondRepository loadSecondRepository;

    public TestService(LoadSecondRepository loadSecondRepository) {
        this.loadSecondRepository = loadSecondRepository;
    }
    public Mono<Page<Load>> getAll(int page,int size){
        return this.loadSecondRepository.findAllBy(PageRequest.of(page, size))
                .collectList()
                .zipWith(this.loadSecondRepository.count())
                .map(p -> new PageImpl<>(p.getT1(), PageRequest.of(page, size), p.getT2()));
    }
    public Mono<Page<LoadProjection>> fndAll(Long driverId, int page, int size){
        return this.loadSecondRepository.findByDriverId(driverId,PageRequest.of(page, size))
                .collectList()
                .zipWith(this.loadSecondRepository.count())
                .map(p -> new PageImpl<>(p.getT1(), PageRequest.of(page, size), p.getT2()));
    }
}
