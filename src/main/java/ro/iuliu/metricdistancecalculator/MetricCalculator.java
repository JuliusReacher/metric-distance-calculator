package ro.iuliu.metricdistancecalculator;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MetricCalculator {

    private static final Pattern TERM_PATTERN = Pattern.compile("\\s*(\\d+(?:[.,]\\d+)?)\\s*(km|mm|cm|dm|m)\\s*([+-])?");

    public double calculate(String expression, DistanceUnit outputUnit) {
        if (expression == null || expression.isBlank()) {
            throw new IllegalArgumentException("Expression must not be empty");
        }

        if (outputUnit == null) {
            throw new IllegalArgumentException("Output unit must not be null");
        }

        Matcher matcher = TERM_PATTERN.matcher(expression);
        int position = 0;
        double totalMillimeters = 0.0;
        double sign = 1.0;

        while (position < expression.length()) {
            matcher.region(position, expression.length());

            if (!matcher.lookingAt()) {
                throw new IllegalArgumentException("Invalid expression near: " + expression.substring(position));
            }

            double amount = Double.parseDouble(matcher.group(1).replace(',', '.'));
            DistanceUnit unit = DistanceUnit.valueOf(matcher.group(2).toUpperCase(Locale.ROOT));

            totalMillimeters += sign * unit.toMillimeters(amount);

            position = matcher.end();
            String operator = matcher.group(3);

            if (operator == null) {
                if (!expression.substring(position).isBlank()) {
                    throw new IllegalArgumentException("Invalid expression");
                }
                position = expression.length();
            } else {
                sign = operator.equals("+") ? 1.0 : -1.0;

                if (expression.substring(position).isBlank()) {
                    throw new IllegalArgumentException("Expression cannot end with an operator");
                }
            }
        }

        return outputUnit.fromMillimeters(totalMillimeters);
    }
}