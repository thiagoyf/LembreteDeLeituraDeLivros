package util;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;

import com.thiagoyf.lembretedeleituradelivros.MainActivity;
import com.thiagoyf.lembretedeleituradelivros.NotificationReceiver;

import java.util.Calendar;

import model.Lembrete;

/**
 * Created by thiagoyf on 12/16/16.
 */

public class ScheduleNotificationUtil {
    public static void setScheduleNotification(Context context, Lembrete lembrete, boolean set) {
        int hora = DataUtil.getHora(lembrete.getDatahora());
        int minute = DataUtil.getMinute(lembrete.getDatahora());

        AlarmManager alarmMgr;
        alarmMgr = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("LEMBRETE_ID", lembrete.getId());
        intent.putExtra("LEMBRETE_DATAHORA", lembrete.getDatahora());
        intent.putExtra("LEMBRETE_REPETE", lembrete.getRepete());
        intent.putExtra("LEMBRETE_ESTADO", lembrete.getEstado());
        intent.putExtra("LIVRO_ID", lembrete.getLivro().getId());
        intent.putExtra("LIVRO_NOME", lembrete.getLivro().getNome());
        intent.putExtra("LIVRO_TOTALPAGINAS", lembrete.getLivro().getTotalPaginas());
        intent.putExtra("LIVRO_FOTO", lembrete.getLivro().getFoto());

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, lembrete.getId(), intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        if (lembrete.getRepete() > 0 && lembrete.getRepete() < 8) {
            calendar.set(Calendar.DAY_OF_WEEK, lembrete.getRepete());
        }
        calendar.set(Calendar.HOUR, hora);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (set) {
            switch (lembrete.getRepete()) {
                case 0:
                    alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
                    break;
                case 8:
                    alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY, alarmIntent);
                    break;
                default:
                    alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY * 7, alarmIntent);
            }
        } else {
            alarmMgr.cancel(alarmIntent);
        }
    }
}
