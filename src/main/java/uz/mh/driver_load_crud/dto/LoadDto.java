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
public class LoadDto {
    private String number;
    private Long driverId;
    private Status status;
    private String loadPickUpAddress;
    private String loadDeliveryAddress;
}
