package uz.mh.driver_load_crud.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.mh.driver_load_crud.config.JWTUtil;
import uz.mh.driver_load_crud.dto.DriverDto;
import uz.mh.driver_load_crud.model.Driver;
import uz.mh.driver_load_crud.request.AuthRequest;
import uz.mh.driver_load_crud.response.AuthResponse;
import uz.mh.driver_load_crud.service.DriverService;
import uz.mh.driver_load_crud.service.UserService;

@RestController
@RequestMapping("/api")
public class DriverController {
    private final DriverService driverService;
    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public DriverController(DriverService driverService, JWTUtil jwtUtil, UserService userService, PasswordEncoder passwordEncoder) {
        this.driverService = driverService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public Mono<ResponseEntity<String>> signup(@RequestBody DriverDto driver) {
        // Encrypt password before saving
        driver.setPassword(driver.getPassword());
        return userService.save(driver)
                .map(savedUser -> ResponseEntity.ok("User signed up successfully"));
    }
    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest authRequest){
        return userService.findByUsername(authRequest.getEmail())
                .map(userDetails -> {
                    if (passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())){
                        System.out.println(userDetails.getPassword());
                        return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(authRequest.getEmail())));
                    }else {
                        throw new BadCredentialsException("Invalid email or password");
                    }
                }).switchIfEmpty(Mono.error(new BadCredentialsException("Invalid email or password")));
    }

    @GetMapping("/getDriver")
    @Operation(summary = "Find Driver by id")
    Mono<ResponseEntity<ResponseEntity<Driver>>> findDriverById(@RequestParam(name = "id") Long id){
        return driverService.findDriverById(id)
                .flatMap(existingDriver -> driverService.findDriverById(id))
                .map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/addDriver")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Driver.")
    Mono<ResponseEntity<?>> saveDriver(@RequestBody DriverDto driverDto) {
            return driverService.saveDriver(driverDto)
                    .map(savedDriver -> {
                        if (savedDriver.equals("Driver created successfully")){
                         return ResponseEntity.status(HttpStatus.CREATED).body(savedDriver);
                        }else {
                            return ResponseEntity.status(HttpStatus.CONFLICT).body(savedDriver);
                        }
                    });

    }

    @PutMapping("/updateDriver")
    Mono<ResponseEntity<String>> updateDriver(@RequestBody Driver driver){
        return driverService.updateDriver(driver)
                    .map(savedDriver -> {
                        if (savedDriver.equals("Driver updated successfully.")){
                            return ResponseEntity.status(HttpStatus.CREATED).body(savedDriver);
                        }else {
                            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(savedDriver);
                        }
                    });
    }

    @GetMapping("/getAllDrivers")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find all drivers")
    Flux<Driver> findAllDrivers(@RequestParam(name = "page") int page,
                                @RequestParam(name = "size") int size){
        return driverService.findAllDrivers(page, size);
    }
    @GetMapping("/getDriverByEmail")
    @Operation(summary = "Search Driver by email")
    Mono<ResponseEntity<Driver>> searchDriverByTitle(@RequestParam String email){
        return driverService.searchDriverByEmail(email)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Driver by id")
    Mono<String> deleteDriverById(@PathVariable Long id){
        return driverService.deleteDriverById(id);
    }
}
