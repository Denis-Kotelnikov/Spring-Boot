package ru.kotelnikov.MusicAppApplication.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SongMetricsDto {
    private Long id;
    @NotNull(message = "Song ID should not be empty")
    private Long songId;
    @Min(value = 0, message = "Revenue should not be negative")
    private Double revenue;
}
