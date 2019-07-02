package dhbw.smapa.uaa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingResponse {
    private long parkingId;

    private Boolean isFree, isIdentified;

    private String area;

    private Double price, latitude, longitude;
}
