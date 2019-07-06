package dhbw.smapa.uaa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingResponse {
    private long parkingId;

    private Boolean isFree, isIdentified;

    private String area;

    private Date parkingStart;

    private Double price, latitude, longitude;
}
