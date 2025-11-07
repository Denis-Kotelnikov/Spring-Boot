package ru.Kotelnikov.SecondApp.service;

import org.springframework.stereotype.Service;
import ru.Kotelnikov.SecondApp.model.Position;
@Service
public interface AnnualBonusService {
    double calculate(Position position, double salary,double bonus, int workDays);
}