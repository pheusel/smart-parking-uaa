package dhbw.smapa.uaa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTValidationResponse {

    private boolean validated;
}
