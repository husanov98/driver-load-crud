package uz.mh.driver_load_crud.projection;

import uz.mh.driver_load_crud.Status;

public interface LoadProjection {
    Long getId();
    String getNumber();
    Status getStatus();
    String getBegin();
    String getEnding();
}
