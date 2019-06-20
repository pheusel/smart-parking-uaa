package dhbw.smapa.uaa.entity;

import lombok.Data;

@Data
public class UserResponse {

    private long userId;
    private String username;
    private Double monthlyCost;
    private AddressResponse addressResponse;
}