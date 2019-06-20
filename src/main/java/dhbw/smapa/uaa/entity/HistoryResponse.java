package dhbw.smapa.uaa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class HistoryResponse {
    private List<HistoryEntryResponse> history;
}
