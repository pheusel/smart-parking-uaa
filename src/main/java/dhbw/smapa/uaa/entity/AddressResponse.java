package dhbw.smapa.uaa.entity;

import lombok.Data;

@Data
public class AddressResponse {

    private long addressId;
    private String street;
    private String houseNumber;
    private int postalCode;
    private String city;
    private String country;
}
