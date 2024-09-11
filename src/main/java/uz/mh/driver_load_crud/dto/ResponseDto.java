package uz.mh.driver_load_crud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDto {
    private String message;
    private int code;
    public ResponseDto(String message){
        this.message = message;
    }
}
