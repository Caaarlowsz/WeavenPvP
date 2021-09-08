package br.com.skyprogrammer.cophenix.zenixpvp.utilitaries.strings;

public class StringFormal
{
    public static String format(final String stringToFormat) {
        if (stringToFormat.length() == 0) {
            return stringToFormat;
        }
        final String localStringFormated = stringToFormat.substring(0, 1).toUpperCase();
        if (stringToFormat.length() == 1) {
            return localStringFormated;
        }
        final String restOfTheString = stringToFormat.substring(1);
        return String.valueOf(localStringFormated) + restOfTheString;
    }
}
