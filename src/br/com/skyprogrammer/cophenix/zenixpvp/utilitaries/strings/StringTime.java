package br.com.skyprogrammer.cophenix.zenixpvp.utilitaries.strings;

import java.util.concurrent.TimeUnit;

public class StringTime
{
    public static String fromLong(final Long longToGetFrom) {
        final long longOfTimeLefting = (longToGetFrom - System.currentTimeMillis()) / 1000L;
        final String stringToReturn = fromLong("", longOfTimeLefting);
        return stringToReturn;
    }
    
    private static String fromLong(String stringToReturn, final long longToGetFrom) {
        final long longOfDays = (int)TimeUnit.SECONDS.toDays(longToGetFrom);
        final long longOfHours = TimeUnit.SECONDS.toHours(longToGetFrom) - longOfDays * 24L;
        final long longOfMinutes = TimeUnit.SECONDS.toMinutes(longToGetFrom) - TimeUnit.SECONDS.toHours(longToGetFrom) * 60L;
        final long longOfSeconds = TimeUnit.SECONDS.toSeconds(longToGetFrom) - TimeUnit.SECONDS.toMinutes(longToGetFrom) * 60L;
        String stringFormatOfLongOfDaysValue = String.valueOf(longOfDays) + " dias ";
        String stringFormatOfLongOfHoursValue = String.valueOf(longOfHours) + " horas ";
        String stringFormatOfLongOfMinutesValue = String.valueOf(longOfMinutes) + " minutos ";
        String stringFormatOfLongOfSecondsValue = String.valueOf(longOfSeconds) + " segundos";
        if (longOfDays == 0L || longOfDays == 0L) {
            stringFormatOfLongOfDaysValue = "";
        }
        if (longOfHours == 0L || longOfHours == 0L) {
            stringFormatOfLongOfHoursValue = "";
        }
        if (longOfMinutes == 0L || longOfMinutes == 0L) {
            stringFormatOfLongOfMinutesValue = "";
        }
        if (longOfSeconds == 0L || longOfSeconds == 0L) {
            stringFormatOfLongOfSecondsValue = "";
        }
        stringToReturn = String.valueOf(stringFormatOfLongOfDaysValue) + stringFormatOfLongOfHoursValue + stringFormatOfLongOfMinutesValue + stringFormatOfLongOfSecondsValue;
        stringToReturn = stringToReturn.trim();
        if (stringToReturn.equalsIgnoreCase("")) {
            stringToReturn = "0 segundos";
        }
        return stringToReturn;
    }
}
