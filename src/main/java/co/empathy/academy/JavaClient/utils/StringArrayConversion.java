package co.empathy.academy.JavaClient.utils;

public class StringArrayConversion {

    public static String[] toArray(String string) {
        if (string.equals("\\N")) {
            return new String[0];
        }
        if (string.trim().equals("")) {
            return new String[0];
        }

        return string.split(",");
    }
}