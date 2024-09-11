package uz.mh.driver_load_crud.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import uz.mh.driver_load_crud.model.Load;
import uz.mh.driver_load_crud.projection.LoadProjection;
import uz.mh.driver_load_crud.service.TestService;

@RestController
@RequestMapping("/api")
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/loads")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find all loads")
    Mono<Page<Load>> findAllLoads(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size){
        return testService.getAll(page,size);
    }
    @GetMapping("/loadProjections")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find all loads")
    Mono<Page<LoadProjection>> findLoads(@RequestParam(name = "driverId") Long driverId, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size){
        return testService.fndAll(driverId,page,size);
    }
}
