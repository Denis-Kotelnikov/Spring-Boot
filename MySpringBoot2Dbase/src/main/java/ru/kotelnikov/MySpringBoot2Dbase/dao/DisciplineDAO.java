package ru.kotelnikov.MySpringBoot2Dbase.dao;

import ru.kotelnikov.MySpringBoot2Dbase.entity.Discipline;

import java.util.List;

public interface DisciplineDAO {
    List<Discipline> getAllDisciplines();
    Discipline saveDiscipline (Discipline discipline);
    Discipline getDiscipline(int id);
    void deleteDiscipline(int id);
}
