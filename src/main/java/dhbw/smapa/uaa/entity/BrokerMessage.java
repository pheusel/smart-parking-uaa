package dhbw.smapa.uaa.entity;

import lombok.Data;

import java.util.Date;

@Data
public class BrokerMessage {

    private Long parkingId;

    private Date timestamp;

    private String uid;

    private Boolean isFree;
}
