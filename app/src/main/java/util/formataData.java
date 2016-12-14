package util;

/**
 * Created by thiagoyf on 12/13/16.
 */

public class FormataData {
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
}
