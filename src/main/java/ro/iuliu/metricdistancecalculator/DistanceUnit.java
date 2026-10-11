package ro.iuliu.metricdistancecalculator;

public enum DistanceUnit {
    MM(1), CM(10), DM(100), M(1_000), KM(1_000_000);

    private final double millimetersPerUnit;

    DistanceUnit(double millimetersPerUnit) {
        this.millimetersPerUnit = millimetersPerUnit;
    }

    public double toMillimeters(double value) {
        return value * millimetersPerUnit;
    }

    public double fromMillimeters(double value) {
        return value / millimetersPerUnit;
    }
}