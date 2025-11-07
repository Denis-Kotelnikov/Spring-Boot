package ru.kotelnikov.MySpringBoot2Dbase.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kotelnikov.MySpringBoot2Dbase.entity.Discipline;
import ru.kotelnikov.MySpringBoot2Dbase.service.DisciplineService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DisciplineController {
    @Autowired
    private DisciplineService disciplineService;

    @GetMapping("/disciplines")
    @Transactional
    public ResponseEntity<List<Discipline>> allDisciplines() {
        List<Discipline> allDisciplines = disciplineService.getAllDisciplines();
        return ResponseEntity.ok(allDisciplines);
    }

    @GetMapping("/disciplines/{id}")
    @Transactional
    public ResponseEntity<Discipline> getDiscipline(@PathVariable("id") int id) {
        Discipline discipline = disciplineService.getDiscipline(id);
        if (discipline != null) {
            return ResponseEntity.ok(discipline);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping ("/disciplines")
    @Transactional
    public ResponseEntity<Discipline> saveDiscipline(@RequestBody Discipline discipline) {
        Discipline savedDiscipline = disciplineService.saveDiscipline(discipline);
        if (savedDiscipline != null) {
            return ResponseEntity.ok(savedDiscipline);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping ("/disciplines/{id}")
    @Transactional
    public ResponseEntity<Discipline> updateDiscipline(@PathVariable("id") int id, @RequestBody Discipline discipline) {
        discipline.setId(id);
        Discipline updatedDiscipline = disciplineService.saveDiscipline(discipline);
        if (updatedDiscipline != null) {
            return ResponseEntity.ok(updatedDiscipline);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping ("/disciplines/{id}")
    @Transactional
    public ResponseEntity<String> deleteDiscipline(@PathVariable("id") int id) {
        Discipline discipline = disciplineService.getDiscipline(id);
        if (discipline != null) {
            disciplineService.deleteDiscipline(id);
            return ResponseEntity.ok("Discipline deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Discipline not found");
        }
    }
}
