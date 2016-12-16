package util;

import java.util.Calendar;

/**
 * Created by thiagoyf on 12/13/16.
 */

public class DataUtil {
    public static String formatDataDbPt(String data){
        String[] splitDataHora = data.split(" ");
        String[] splitData = splitDataHora[0].split("-");

        return splitData[2] + "-" + splitData[1] + "-" + splitData[0];
    }

    public static String formatHoraDbPt(String data) {
        String[] splitDataEHora = data.split(" ");

        return splitDataEHora[1];
    }

    public static String formatPtDb(String data, String hora) {
        if (data == null) {
            return "0000-00-00 " + hora;
        }

        String[] splitData = data.split("-");

        if (hora == null) {
            return splitData[2] + "-" + splitData[1] + "-" + splitData[0] + " 00:00";
        }

        return splitData[2] + "-" + splitData[1] + "-" + splitData[0] + " " + hora;
    }

    public static boolean dataFutura(String data, String hora) {
        Calendar currentTime = Calendar.getInstance();
        currentTime.set(Calendar.SECOND, 0);
        currentTime.set(Calendar.MILLISECOND, 0);

        Calendar paramDate = stringToCalendar(data, hora);

        if (paramDate.after(currentTime)) {
            return true;
        }

        return false;
    }

    public static Calendar stringToCalendar(String data, String hora) {
        Calendar paramDate = Calendar.getInstance();

        int year = 0;
        int month = 0;
        int day = 0;

        if (data != null) {
            String[] splitData = data.split("-");

            year = Integer.parseInt(splitData[2]);
            month = Integer.parseInt(splitData[1]) - 1;
            day = Integer.parseInt(splitData[0]);
        }

        int hour = 0;
        int minute = 0;

        if (hora != null) {
            String[] splitHora = hora.split(":");

            hour = Integer.parseInt(splitHora[0]);
            minute = Integer.parseInt(splitHora[1]);
        }

        paramDate.set(Calendar.YEAR, year);
        paramDate.set(Calendar.MONTH, month);
        paramDate.set(Calendar.DAY_OF_MONTH, day);
        paramDate.set(Calendar.HOUR_OF_DAY, hour);
        paramDate.set(Calendar.MINUTE, minute);
        paramDate.set(Calendar.SECOND, 0);
        paramDate.set(Calendar.MILLISECOND, 0);

        return paramDate;
    }
}
