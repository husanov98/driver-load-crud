package uz.mh.driver_load_crud.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import uz.mh.driver_load_crud.Status;

@Table("loads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Load {
    @Id
    private Long id;
    private String number;
    @Column("driver_id")
    private Long driverId;
    @Column("status")
    private Status status;
    @Column("begin")
    private String loadPickUpAddress;
    @Column("ending")
    private String loadDeliveryAddress;

}
