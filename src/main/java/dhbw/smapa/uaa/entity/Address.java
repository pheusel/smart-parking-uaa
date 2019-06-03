package dhbw.smapa.uaa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "tbl_address")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;

    @NotNull
    private String street;

    @NotNull
    private String houseNumber;

    @NotNull
    private int postalCode;

    @NotNull
    private String country;

    @OneToMany(mappedBy = "address", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AppUser> appUsers;
}
