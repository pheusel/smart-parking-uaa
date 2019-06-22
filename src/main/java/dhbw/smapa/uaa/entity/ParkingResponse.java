package dhbw.smapa.uaa.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Für weitere Infos: siehe Klasse 'Parking'.
 */
@Data
public class ParkingResponse {

    private long parkingId;
    private long parkingAreaId;
    private String section;
    private Boolean busy;
    private UserResponse parker;
    //private UserObjectResponse auto;
    private LocalDateTime beginning;
    private Double paidAmount;
}
