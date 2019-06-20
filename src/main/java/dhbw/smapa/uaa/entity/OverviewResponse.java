package dhbw.smapa.uaa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Diese Klasse ist für die Übersicht in der App gedacht.
 * Mit 'userId' kann überprüft werden, ob die richtigen Daten empfangen wurden.
 * Mit der Liste 'besetzteParkplaetze' können mehrere Dinge ermittelt werden:
 *      1.) Parkt der User aktuell?
 *      2.) Mit wie vielen Autos/Objekten parkt der User aktuell?
 *      3.) Welche Kosten entstehen durch die jeweiligen Parkvorgänge?
 *      4.) Seit wann parkt der User auf welchem Parkplatz?
 *      5.) Auf welchen Parkplätzen parkt der User aktuell?
 */
@AllArgsConstructor
@Data
public class OverviewResponse {

    private UserResponse            user;
    private List<ParkingResponse>   besetzteParkplaetze;
}
