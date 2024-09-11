package uz.mh.driver_load_crud.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uz.mh.driver_load_crud.dto.DriverDto;
import uz.mh.driver_load_crud.model.Driver;


@Component
public class DriverMapper {
    private final ModelMapper modelMapper = new ModelMapper();
    public Driver mapToDriver(DriverDto driverDto){
        return modelMapper.map(driverDto, Driver.class);
    }
}
