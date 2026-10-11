package ro.iuliu.metricdistancecalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MetricCalculatorTest {

    private final MetricCalculator calculator = new MetricCalculator();

    @Test
    void calculatesTheAssignmentExample() {
        double result = calculator.calculate("10 cm + 1 m - 10 mm", DistanceUnit.MM);

        assertEquals(1090.0, result, 0.001);
    }

    @ParameterizedTest
    @CsvSource({"'1 m', CM, 100.0", "'1 km', M, 1000.0", "'5 dm', CM, 50.0", "'1 km - 500 m', KM, 0.5"})
    void convertsResultToRequestedUnit(String expression, DistanceUnit outputUnit, double expected) {

        double result = calculator.calculate(expression, outputUnit);

        assertEquals(expected, result, 0.001);
    }

    @Test
    void rejectsUnsupportedUnit() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate("10 miles", DistanceUnit.MM));
    }

    @Test
    void rejectsMultiplication() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate("10 cm * 2", DistanceUnit.MM));
    }

    @Test
    void rejectsBlankExpression() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate("   ", DistanceUnit.MM));
    }
}