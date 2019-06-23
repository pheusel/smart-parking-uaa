package dhbw.smapa.uaa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Diese Klasse repräsentiert einen einzelnen Parkplatz. Jeder Parkplatz erhält eine
 * eindeutige ID, die 'parkingId'. Außerdem beinhaltet ein Parkplatz-Objekt folgende
 * Informationen:
 * Mit 'parkingAreaId' wird der Bereich festgelegt, in dem sich der Parkplatz
 * befindet (z.B. für einen von mehreren Parkplätzen in einer Stadt oder für ein
 * Parkdeck in einem Parkhaus).
 * Mit 'section' wird der Betreiber festgelegt. So können z.B. verschiedene Betreiber
 * dasselbe System nutzen und evtl sogar denselben Kundenstamm nutzen (Kooperation).
 * Die Variable 'paid' gibt an, ob der Parkplatz aktuell bezahlt ist. Sie ist null,
 * falls der Parkplatz nicht besetzt ist.
 * Die Variable 'paidAmount' gibt an, wieviel der Nutzer für den Parkplatz bezahlt hat.
 * Die Variablen 'parker' und 'beginning' können auch null sein,
 * falls der Parkplatz nicht von einem registrierten Kunden belegt ist.
 * Die Variable 'auto' kann in einem weiteren Schritt eingeführt werden, damit ein
 * Kunde mehrere Fahrzeuge verwenden kann und immer sieht, welches Auto wo parkt.
 */
@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "tbl_parking")
public class Parking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long parkingId;

    @NotNull
    private String area;

    @NotNull
    private Boolean isFree;

    @NotNull
    private Double latitude, longitude;
}
