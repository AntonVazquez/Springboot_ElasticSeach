package co.empathy.academy.JavaClient.utils;

public class StringIntegerConversion {

    public static int toInt(String value) {
        if (value.trim().contentEquals("\\N")) {
            return 0;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}