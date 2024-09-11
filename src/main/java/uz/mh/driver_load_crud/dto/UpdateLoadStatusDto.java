package uz.mh.driver_load_crud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;
import uz.mh.driver_load_crud.Status;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ParameterObject
public class UpdateLoadStatusDto {
    private Long loadId;
    private Status status;
}
