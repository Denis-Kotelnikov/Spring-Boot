package ru.kotelnikov.MusicAppApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kotelnikov.MusicAppApplication.entity.Song;

public interface SongRepository extends JpaRepository<Song, Long> {
}