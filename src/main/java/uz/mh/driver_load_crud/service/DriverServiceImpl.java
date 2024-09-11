package uz.mh.driver_load_crud.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.mh.driver_load_crud.dto.DriverDto;
import uz.mh.driver_load_crud.mapper.DriverMapper;
import uz.mh.driver_load_crud.model.Driver;
import uz.mh.driver_load_crud.repository.DriverRepository;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    public DriverServiceImpl(DriverRepository driverRepository, DriverMapper driverMapper) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
    }

    @Override
    public Mono<ResponseEntity<Driver>> findDriverById(Long id){
        return driverRepository.findById(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<Driver> searchDriverByEmail(String email) {
        return driverRepository.findDriverByEmail(email);
    }

    @Override
    public Flux<Driver> findAllDrivers(int page, int size) {
        return driverRepository.findAllBy(PageRequest.of(page, size));
    }

    @Override
    public Mono<String> saveDriver(DriverDto driverDto) {
        Driver driver = driverMapper.mapToDriver(driverDto);
        return driverRepository.findDriverByPhone(driverDto.getPhone())
                .flatMap(existingDriver -> Mono.just("Phone number is already taken"))
                .switchIfEmpty(driverRepository.findDriverByEmail(driverDto.getEmail())
                        .flatMap(existingDriver -> Mono.just("Email is already taken"))
                        .switchIfEmpty(driverRepository.save(driver).then(Mono.just("Driver created successfully"))));
    }

    @Override
    public Mono<String> updateDriver(Driver updatedDriver) {
//        return driverRepository.findById(updatedDriver.getId())
//                .flatMap(existingDriver -> {
//                    // Check if the new email or phone is already used by another driver
//                    return driverRepository.findByEmail(updatedDriver.getEmail())
//                            .filter(driver -> !driver.getId().equals(existingDriver.getId()))
//                            .switchIfEmpty(Mono.just(existingDriver))
//                            .flatMap(driver -> driverRepository.findByPhone(updatedDriver.getPhone())
//                                    .filter(phoneDriver -> !phoneDriver.getId().equals(existingDriver.getId()))
//                                    .switchIfEmpty(Mono.just(existingDriver))
//                                    .map(phoneDriver -> {
//                                        // Update the driver details
//                                        existingDriver.setEmail(updatedDriver.getEmail());
//                                        existingDriver.setPhone(updatedDriver.getPhone());
//                                        existingDriver.setPassword(updatedDriver.getPassword()); // Update other fields as needed
//                                        return existingDriver;
//                                    })
//                            );
//                })
//                .flatMap(driverRepository::save)
//                .onErrorResume(e -> Mono.error(new RuntimeException("Update failed: " + e.getMessage())));

        return driverRepository.findById(updatedDriver.getId())
                .flatMap(existingDriver -> driverRepository.save(updatedDriver)
                        .then(Mono.just("Driver updated successfully.")))
                .switchIfEmpty(Mono.just("Driver not found."));
    }

    @Override
    public Mono<String> deleteDriverById(Long id) {
        return driverRepository.deleteById(id)
                .flatMap(driver -> driverRepository.deleteById(id).thenReturn("Driver deleted successfully"))
                .switchIfEmpty(Mono.just("Driver not found with this id"));
    }

    @Override
    public Mono<String> isPresentByPhone(String phone) {
        Mono<Boolean> booleanMono = driverRepository.findDriverByPhone(phone).hasElement();
        return booleanMono.flatMap(exist ->{
            if (exist){
                return Mono.just("Yes");
            } else return Mono.just("No");
        });
    }

    @Override
    public Mono<Driver> findByEmail(String email) {
        return driverRepository.findByEmail(email);
    }
}
