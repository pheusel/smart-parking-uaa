package dhbw.smapa.uaa.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BrokerMessage {

    private Long parkingId;

    private Timestamp timestamp;

    private Long uid;

    private Boolean isFree;
}
