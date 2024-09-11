package uz.mh.driver_load_crud.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.mh.driver_load_crud.dto.LoadDto;

import uz.mh.driver_load_crud.dto.UpdateLoadStatusDto;
import uz.mh.driver_load_crud.model.Load;
import uz.mh.driver_load_crud.projection.LoadProjection;
import uz.mh.driver_load_crud.service.LoadService;

@RestController
@RequestMapping("/api/loads")
public class LoadController {
    private final LoadService loadService;

    public LoadController(LoadService loadService) {
        this.loadService = loadService;
    }
    @GetMapping("/getLoad")
    @Operation(summary = "Find Load by id")
    Mono<ResponseEntity<ResponseEntity<Load>>> findLoadById(@RequestParam(name = "id") Long id){
        return loadService.findById(id)
                .map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/addLoad")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Load.")
    Mono<ResponseEntity<Load>> saveLoad(@io.swagger.v3.oas.annotations.parameters.RequestBody LoadDto loadDto) {
        return loadService.saveLoad(loadDto)
                .map(savedDriver -> ResponseEntity.status(HttpStatus.CREATED).body(savedDriver));
    }
    @PutMapping(value = "/updateLoad")
    Mono<String> updateLoad(@RequestBody Load load){
        return loadService.updateLoad(load);
    }


    @GetMapping("/getAllLoads")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find all loads")
    Flux<Load> findAllLoads(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size){
        return loadService.findAllLoads(page,size);
    }
    @GetMapping("/getLoadsByDriverId")
    @Operation(summary = "Search Load by driver id")
    Flux<LoadProjection> searchLoadByDriverId(@RequestParam(name = "driver_id") Long driverId,
                                              @RequestParam(name = "page") int page,
                                              @RequestParam(name = "size") int size){
        return loadService.searchByDriverId(driverId, page, size);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Load by id")
    Mono<Void> deleteLoadById(@PathVariable Long id){
        return loadService.deleteLoadById(id);
    }


    @PatchMapping("/updateStatus")
    Mono<String> updateStatus(@io.swagger.v3.oas.annotations.parameters.RequestBody UpdateLoadStatusDto loadStatusDto){
        return loadService.updateStatus(loadStatusDto);
    }
    @GetMapping("/getAllData")
    Mono<?> getAllData(@RequestParam(name = "driver_id") Long id,
                       @RequestParam(name = "page") int page,
                       @RequestParam(name = "size") int size){
        return loadService.getAllData(id, page, size);
    }
}
