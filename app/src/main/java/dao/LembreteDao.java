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
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public LembreteDao (Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public SQLiteDatabase getDatabase() {
        if (sqLiteDatabase == null) {
            sqLiteDatabase = databaseHelper.getWritableDatabase();
        }

        return sqLiteDatabase;
    }

    public List<Lembrete> listarLembrete(){
        String[] projecao = {
                DatabaseHelper.LEMBRETE_ID,
                DatabaseHelper.LEMBRETE_DATA,
                DatabaseHelper.LEMBRETE_LIVRO,
        };

        Cursor cursor = getDatabase().query(DatabaseHelper.LEMBRETE_TABLE, projecao,
                null, null, null, null, null);

        List<Lembrete> lembretes = new ArrayList<Lembrete>();
        while (cursor.moveToNext()) {
            Lembrete lembrete = new Lembrete(
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LEMBRETE_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.LEMBRETE_DATA)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LEMBRETE_LIVRO))
            );
            lembretes.add(lembrete);
        }

        cursor.close();
        return lembretes;
    }

    public long salvarLembrete(Lembrete lembrete) {
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.LEMBRETE_DATA, lembrete.getDatahora());
        valores.put(DatabaseHelper.LEMBRETE_LIVRO, lembrete.getLivroId());

        return getDatabase().insert(DatabaseHelper.LEMBRETE_TABLE, null, valores);
    }

    public Lembrete buscarLembretePorId(int id) {
        String[] projecao = {
                DatabaseHelper.LEMBRETE_ID,
                DatabaseHelper.LEMBRETE_DATA,
                DatabaseHelper.LEMBRETE_LIVRO,
        };

        Cursor cursor = getDatabase().query(DatabaseHelper.LEMBRETE_ID, projecao, "id = ?",
                new String[]{Integer.toString(id)}, null, null, null);

        if (cursor.moveToNext()){
            Lembrete lembrete = new Lembrete(
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LEMBRETE_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.LEMBRETE_DATA)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LEMBRETE_LIVRO))
            );
            cursor.close();
            return lembrete;
        }

        return null;
    }

    public void close() {
        databaseHelper.close();
        sqLiteDatabase = null;
    }
}
