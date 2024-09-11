package uz.mh.driver_load_crud.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uz.mh.driver_load_crud.dto.LoadDto;
import uz.mh.driver_load_crud.model.Load;

@Component
public class LoadMapper {
    private final ModelMapper modelMapper = new ModelMapper();
    public Load mapToLoad(LoadDto loadDto){
        return modelMapper.map(loadDto,Load.class);
    }
}
