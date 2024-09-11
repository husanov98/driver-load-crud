package uz.mh.driver_load_crud.service;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import uz.mh.driver_load_crud.dto.DriverDto;
import uz.mh.driver_load_crud.model.Driver;
import uz.mh.driver_load_crud.repository.DriverRepository;

@Service
public class UserService {
    private final DriverRepository driverRepository;

    public UserService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }
//    @Override
    public Mono<Driver> findByUsername(String username) {
        return driverRepository.findByEmail(username);
    }

    public Mono<Driver> save(DriverDto user) {
        user.setPassword(user.getPassword());// Encrypt password before saving
        Driver driver = new Driver();
        driver.setPhone(user.getPhone());
        driver.setEmail(user.getEmail());
        driver.setLastname(user.getLastname());
        driver.setFirstname(user.getFirstname());
        driver.setPassword(user.getPassword());
        return driverRepository.save(driver);
    }


}
