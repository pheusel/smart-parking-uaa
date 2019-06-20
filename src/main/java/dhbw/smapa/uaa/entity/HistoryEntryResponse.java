package dhbw.smapa.uaa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class HistoryEntryResponse {
    @NotNull
    private LocalDateTime   begin;

    @NotNull
    private LocalDateTime   end;

    @NotNull
    private Double          cost;

    @NotNull
    private Long            parkingId;

    private AppUser         parker;

    @NotNull
    private String          section;
}
