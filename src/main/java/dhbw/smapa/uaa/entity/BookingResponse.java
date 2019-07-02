package dhbw.smapa.uaa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private long bookingId;

    private long parkingId;

    private String uid;

    private Timestamp parkingStart;

    private Timestamp parkingEnd;

    private Double invoiceAmount;

    private Double cost;

    private Boolean isPaid;

}
