package dhbw.smapa.uaa.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BrokerMessage {

    private long parking_id;

    private Timestamp timestamp;

    private Long uid;

    private Boolean is_free;
}
