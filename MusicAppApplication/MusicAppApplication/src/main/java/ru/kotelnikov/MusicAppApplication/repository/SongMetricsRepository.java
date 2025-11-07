package ru.kotelnikov.MusicAppApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kotelnikov.MusicAppApplication.entity.SongMetrics;

public interface SongMetricsRepository extends JpaRepository<SongMetrics, Long> {
}
