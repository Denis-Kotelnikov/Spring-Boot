package ru.kotelnikov.MusicAppApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.kotelnikov.MusicAppApplication.dto.SongDto;
import ru.kotelnikov.MusicAppApplication.dto.SongMetricsDto;
import ru.kotelnikov.MusicAppApplication.entity.Song;
import ru.kotelnikov.MusicAppApplication.entity.SongMetrics;
import ru.kotelnikov.MusicAppApplication.repository.SongRepository;
import ru.kotelnikov.MusicAppApplication.repository.SongMetricsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private SongMetricsRepository songMetricsRepository;

    @Override
    public List<SongDto> getAllSongs() {
        return songRepository.findAll().stream()
                .map(this::convertToSongDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveSong(SongDto songDto) {
        Song song = new Song();
        song.setId(songDto.getId());
        song.setTitle(songDto.getTitle());
        song.setArtist(songDto.getArtist());
        song.setGenre(songDto.getGenre());
        song.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        songRepository.save(song);
    }

    @Override
    public SongDto getSongById(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));
        return convertToSongDto(song);
    }

    @Override
    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

    @Override
    public List<SongMetricsDto> getAllMetrics() {
        return songMetricsRepository.findAll().stream()
                .map(this::convertToMetricsDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveMetrics(SongMetricsDto metricsDto) {
        SongMetrics metrics = new SongMetrics();
        metrics.setId(metricsDto.getId());
        metrics.setSongId(metricsDto.getSongId());
        metrics.setRevenue(metricsDto.getRevenue());
        songMetricsRepository.save(metrics);
    }

    @Override
    public SongMetricsDto getMetricsById(Long id) {
        SongMetrics metrics = songMetricsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Metrics not found"));
        return convertToMetricsDto(metrics);
    }

    @Override
    public void deleteMetrics(Long id) {
        songMetricsRepository.deleteById(id);
    }

    @Override
    public double calculateBonus() {
        return songMetricsRepository.findAll().stream()
                .mapToDouble(SongMetrics::getRevenue)
                .sum() * 0.1; // 10% от общего дохода
    }

    private SongDto convertToSongDto(Song song) {
        SongDto songDto = new SongDto();
        songDto.setId(song.getId());
        songDto.setTitle(song.getTitle());
        songDto.setArtist(song.getArtist());
        songDto.setGenre(song.getGenre());
        return songDto;
    }

    private SongMetricsDto convertToMetricsDto(SongMetrics metrics) {
        SongMetricsDto metricsDto = new SongMetricsDto();
        metricsDto.setId(metrics.getId());
        metricsDto.setSongId(metrics.getSongId());
        metricsDto.setRevenue(metrics.getRevenue());
        return metricsDto;
    }
}