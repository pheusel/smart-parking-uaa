package dhbw.smapa.uaa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private long bookingId;

    private long parkingId;

    private String uid;

    private Date parkingStart;

    private Date parkingEnd;

    private Double invoiceAmount;

    private Double cost;

    private Boolean isPaid;

}
