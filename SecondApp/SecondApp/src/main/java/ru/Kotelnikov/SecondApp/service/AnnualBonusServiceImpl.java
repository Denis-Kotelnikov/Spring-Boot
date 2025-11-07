package ru.Kotelnikov.SecondApp.service;

import org.springframework.stereotype.Service;
import ru.Kotelnikov.SecondApp.model.Position;

import java.time.LocalDate;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {
    @Override
    public double calculate(Position position, double salary, double bonus, int workDays) {
        int daysInYear = isLeapYear() ? 366 : 365;
        return salary * bonus * daysInYear * position.getPositionCoefficient() / workDays;
    }

    public double calculateQuarterlyBonus(Position position, double salary, double bonus, int workDays) {
        if (!position.isManager()) {
            throw new IllegalArgumentException("Квартальная премия доступна только для менеджеров.");
        }
        int daysInQuarter = 91; // Примерное количество дней в квартале
        return (salary * bonus * daysInQuarter * position.getPositionCoefficient()) / workDays;
    }

    private boolean isLeapYear() {
        int currentYear = LocalDate.now().getYear();
        return (currentYear % 4 == 0 && currentYear % 100 != 0) || (currentYear % 400 == 0);
    }
}