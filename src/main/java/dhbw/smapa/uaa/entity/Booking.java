package dhbw.smapa.uaa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "tbl_booking")
public class Booking implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookingId;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = Parking.class)
    @JoinColumn(name = "parking_id", nullable = false)
    private long parkingId;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = AppUser.class)
    @JoinColumn(name = "uid", nullable = false)
    private long uid;

    @NotNull
    private Timestamp parkingStart;

    private Timestamp parkingEnd;

    private Double parkingTime, invoiceAmount;

    private boolean isPaid;
}