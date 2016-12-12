package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Lembrete;

/**
 * Created by thiagoyf on 12/12/16.
 */

public class LembreteDao {
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public LembreteDao (Context context){
        dataBaseHelper = new DataBaseHelper(context);
    }

    public SQLiteDatabase getDatabase() {
        if (sqLiteDatabase == null) {
            sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        }

        return sqLiteDatabase;
    }

    public List<Lembrete> listarLembrete(){
        String[] projecao = {
                DataBaseHelper.LEMBRETE_ID,
                DataBaseHelper.LEMBRETE_DATA,
                DataBaseHelper.LEMBRETE_LIVRO,
        };

        Cursor cursor = getDatabase().query(DataBaseHelper.LEMBRETE_TABLE, projecao,
                null, null, null, null, null);

        List<Lembrete> lembretes = new ArrayList<Lembrete>();
        while (cursor.moveToNext()) {
            Lembrete lembrete = new Lembrete(
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.LEMBRETE_ID)),
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.LEMBRETE_DATA)),
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.LEMBRETE_LIVRO))
            );
            lembretes.add(lembrete);
        }

        cursor.close();
        return lembretes;
    }

    public long salvarLembrete(Lembrete lembrete) {
        ContentValues valores = new ContentValues();
        valores.put(DataBaseHelper.LEMBRETE_ID, lembrete.getId());
        valores.put(DataBaseHelper.LEMBRETE_DATA, lembrete.getDatahora());
        valores.put(DataBaseHelper.LEMBRETE_LIVRO, lembrete.getLivroId());

        Integer lembreteId = new Integer(lembrete.getId());
        if (lembreteId != null) {
            return getDatabase().update(DataBaseHelper.LEMBRETE_ID, valores, " id = ?",
                    new String[]{Integer.toString(lembrete.getId())});
        }

        return getDatabase().insert(DataBaseHelper.LEMBRETE_ID, null, valores);
    }

    public Lembrete buscarLembretePorId(int id) {
        String[] projecao = {
                DataBaseHelper.LEMBRETE_ID,
                DataBaseHelper.LEMBRETE_DATA,
                DataBaseHelper.LEMBRETE_LIVRO,
        };

        Cursor cursor = getDatabase().query(DataBaseHelper.LEMBRETE_ID, projecao, "id = ?",
                new String[]{Integer.toString(id)}, null, null, null);

        if (cursor.moveToNext()){
            Lembrete lembrete = new Lembrete(
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.LEMBRETE_ID)),
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.LEMBRETE_DATA)),
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.LEMBRETE_LIVRO))
            );
            cursor.close();
            return lembrete;
        }

        return null;
    }

    public void close() {
        dataBaseHelper.close();
        sqLiteDatabase = null;
    }
}
