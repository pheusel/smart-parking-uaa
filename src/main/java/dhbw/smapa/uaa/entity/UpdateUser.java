package dhbw.smapa.uaa.entity;

import lombok.Data;

@Data
public class UpdateUser {

    private String password;
    private Address address;
}
