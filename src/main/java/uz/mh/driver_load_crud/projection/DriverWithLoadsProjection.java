package uz.mh.driver_load_crud.projection;

import uz.mh.driver_load_crud.model.Load;

import java.util.List;

public interface DriverWithLoadsProjection {
    Long getId();
    String getFirstname();
    String getLastname();
    String getEmail();
    String getPhone();
    List<Load> getLoads();
}
