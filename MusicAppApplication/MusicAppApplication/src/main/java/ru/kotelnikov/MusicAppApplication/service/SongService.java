package ru.kotelnikov.MusicAppApplication.service;

import ru.kotelnikov.MusicAppApplication.dto.SongDto;
import ru.kotelnikov.MusicAppApplication.dto.SongMetricsDto;

import java.util.List;

import java.util.List;

public interface SongService {
    List<SongDto> getAllSongs();
    void saveSong(SongDto songDto);
    SongDto getSongById(Long id);
    void deleteSong(Long id);
    List<SongMetricsDto> getAllMetrics();
    void saveMetrics(SongMetricsDto metricsDto);
    SongMetricsDto getMetricsById(Long id);
    void deleteMetrics(Long id);
    double calculateBonus();
}
