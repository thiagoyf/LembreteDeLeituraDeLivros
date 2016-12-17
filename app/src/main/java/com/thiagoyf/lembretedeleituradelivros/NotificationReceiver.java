package com.thiagoyf.lembretedeleituradelivros;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import model.Lembrete;
import model.Livro;

/**
 * Created by thiagoyf on 12/16/16.
 */

public class NotificationReceiver extends BroadcastReceiver {
    private Livro livro;
    private Lembrete lembrete;

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService
                (Context.NOTIFICATION_SERVICE);

        getExtras(intent);

        Intent mIntent = new Intent(context, Lembrete.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, lembrete.getId(),
                mIntent, PendingIntent.FLAG_UPDATE_CURRENT, intent.getExtras());

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.bookmark)
                .setContentTitle("Lembrete")
                .setContentText(lembrete.getLivro().getNome())
                .setAutoCancel(true);

        notificationManager.notify(lembrete.getId(), mBuilder.build());
    }

    private void getExtras(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            buildLivro(extras);
            buildLembrete(extras);
        }
    }

    private void buildLivro(Bundle extras) {
        livro = new Livro();
        livro.setId(extras.getInt("LIVRO_ID"));
        livro.setNome(extras.getString("LIVRO_NOME"));
        livro.setTotalPaginas(extras.getInt("LIVRO_TOTALPAGINAS"));
        livro.setFoto(extras.getByteArray("LIVRO_FOTO"));
    }

    private void buildLembrete(Bundle extras) {
        lembrete = new Lembrete();
        lembrete.setId(extras.getInt("LEMBRETE_ID"));
        lembrete.setDatahora(extras.getString("LEMBRETE_DATAHORA"));
        lembrete.setEstado(extras.getInt("LEMBRETE_ESTADO"));
        lembrete.setLivro(livro);
        lembrete.setRepete(extras.getInt("LEMBRETE_REPETE"));
    }
}
