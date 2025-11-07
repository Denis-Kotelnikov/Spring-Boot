package ru.kotelnikov.MusicAppApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kotelnikov.MusicAppApplication.dto.SongDto;
import ru.kotelnikov.MusicAppApplication.dto.SongMetricsDto;
import ru.kotelnikov.MusicAppApplication.service.SongService;

import jakarta.validation.Valid;

@Controller
public class SongController {

    @Autowired
    private SongService songService;

    @GetMapping("/songs")
    public String listSongs(Model model) {
        model.addAttribute("songs", songService.getAllSongs());
        return "songs";
    }

    @GetMapping("/add-song")
    public String showAddSongForm(Model model) {
        model.addAttribute("song", new SongDto());
        return "add-song-form";
    }

    @PostMapping("/save-song")
    public String saveSong(@Valid @ModelAttribute("song") SongDto songDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("song", songDto);
            return "add-song-form";
        }
        songService.saveSong(songDto);
        return "redirect:/songs";
    }

    @GetMapping("/update-song")
    public String showUpdateSongForm(@RequestParam("songId") Long id, Model model) {
        SongDto songDto = songService.getSongById(id);
        model.addAttribute("song", songDto);
        return "add-song-form";
    }

    @GetMapping("/delete-song")
    public String deleteSong(@RequestParam("songId") Long id) {
        songService.deleteSong(id);
        return "redirect:/songs";
    }

    @GetMapping("/song-metrics")
    public String listMetrics(Model model) {
        model.addAttribute("metrics", songService.getAllMetrics());
        return "song-metrics";
    }

    @GetMapping("/add-metrics")
    public String showAddMetricsForm(Model model) {
        model.addAttribute("metrics", new SongMetricsDto());
        model.addAttribute("songs", songService.getAllSongs());
        return "add-metrics-form";
    }

    @PostMapping("/save-metrics")
    public String saveMetrics(@Valid @ModelAttribute("metrics") SongMetricsDto metricsDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("metrics", metricsDto);
            model.addAttribute("songs", songService.getAllSongs());
            return "add-metrics-form";
        }
        songService.saveMetrics(metricsDto);
        return "redirect:/song-metrics";
    }

    @GetMapping("/update-metrics")
    public String showUpdateMetricsForm(@RequestParam("metricsId") Long id, Model model) {
        SongMetricsDto metricsDto = songService.getMetricsById(id);
        model.addAttribute("metrics", metricsDto);
        model.addAttribute("songs", songService.getAllSongs());
        return "add-metrics-form";
    }

    @GetMapping("/delete-metrics")
    public String deleteMetrics(@RequestParam("metricsId") Long id) {
        songService.deleteMetrics(id);
        return "redirect:/song-metrics";
    }

    @GetMapping("/bonus")
    public String showBonus(Model model) {
        model.addAttribute("bonus", songService.calculateBonus());
        return "bonus";
    }
}