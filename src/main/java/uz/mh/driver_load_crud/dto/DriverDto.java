package uz.mh.driver_load_crud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto {
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String password;
}
