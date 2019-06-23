package dhbw.smapa.uaa.entity;

import lombok.Data;

@Data
public class BrokerMessage {

    private long parking_id;

    private String time;

    private Long uid;

    private Boolean is_free;
}
