package co.empathy.academy.JavaClient.utils;

public class StringDoubleConversion {

    public static double toDouble(String string) {
        if (string.trim().equals("\\N")) {
            return 0.0;
        }
        try {
            return Double.parseDouble(string.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}