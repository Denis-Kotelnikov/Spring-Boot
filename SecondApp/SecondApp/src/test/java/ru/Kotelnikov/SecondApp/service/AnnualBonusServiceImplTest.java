package ru.Kotelnikov.SecondApp.service;

import org.junit.jupiter.api.Test;
import ru.Kotelnikov.SecondApp.model.Position;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnnualBonusServiceImplTest {

    @Test
    void calculate() {
        Position position = Position.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;
        double result = new AnnualBonusServiceImpl().calculate(position, salary, bonus, workDays);
        double expected = 364693.8271604938;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void calculateQuarterlyBonus() {
        Position position = Position.MANAGER;
        double bonus = 1.5;
        int workDays = 60;
        double salary = 80000.00;
        double result = new AnnualBonusServiceImpl().calculateQuarterlyBonus(position, salary, bonus, workDays);
        double expected = 273600.0; // Рассчитано на основе примерных значений
        assertThat(result).isEqualTo(expected);

        // Тест на исключение для не-менеджера
        assertThrows(IllegalArgumentException.class, () -> {
            new AnnualBonusServiceImpl().calculateQuarterlyBonus(Position.HR, salary, bonus, workDays);
        });
    }
}
