package util;

import java.util.Calendar;

/**
 * Created by thiagoyf on 12/13/16.
 */

public class DataUtil {
    public static String[] formatDbPt(String data){
        String[] splitDataEHora = data.split(" ");
        String[] splitData = splitDataEHora[0].split("-");

        splitDataEHora[0] = splitData[2] + "-" + splitData[1] + "-" + splitData[0];
        return splitDataEHora;
    }

    public static String formatPtDb(String data, String hora){
        String[] splitData = data.split("-");
        return splitData[2] + "-" + splitData[1] + "-" + splitData[0] + " " + hora;
    }

    public static boolean dataFutura(String data, String hora){
        Calendar currentTime = Calendar.getInstance();
        currentTime.set(Calendar.SECOND, 0);
        currentTime.set(Calendar.MILLISECOND, 0);

        Calendar paramDate = stringToCalendar(data, hora);

        if (paramDate.after(currentTime)){
            return true;
        }

        return false;
    }

    public static Calendar stringToCalendar(String data, String hora){
        Calendar paramDate = Calendar.getInstance();

        String[] splitData = data.split("-");
        String[] splitHora = hora.split(":");

        int year = Integer.parseInt(splitData[2]);
        int month = Integer.parseInt(splitData[1]) - 1;
        int day = Integer.parseInt(splitData[0]);

        int hour = Integer.parseInt(splitHora[0]);
        int minute = Integer.parseInt(splitHora[1]);

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
