package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Lembrete;
import model.Livro;

/**
 * Created by thiagoyf on 12/12/16.
 */

public class LembreteDao {
    private static final int SALVAR = 1;
    private static final int ATUALIZAR = 2;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public LembreteDao(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public SQLiteDatabase getDatabase() {
        if (sqLiteDatabase == null) {
            sqLiteDatabase = databaseHelper.getWritableDatabase();
        }

        return sqLiteDatabase;
    }

    public List<Lembrete> listarLembrete() {
        String query = setJoinQuery() + " WHERE " + DatabaseHelper.LEMBRETE_ESTADO + " = 1";
        Cursor cursor = getDatabase().rawQuery(query, null);

        List<Lembrete> lembretes = new ArrayList<Lembrete>();
        while (cursor.moveToNext()) {
            Lembrete lembrete = getLembrete(cursor);
            lembretes.add(lembrete);
        }

        cursor.close();
        return lembretes;
    }

    public long salvarLembrete(Lembrete lembrete) {
        ContentValues valores = buildContentValues(SALVAR, lembrete);

        return getDatabase().insert(DatabaseHelper.LEMBRETE_TABLE, null, valores);
    }

    public long atualizarLembrete(Lembrete lembrete) {
        ContentValues valores = buildContentValues(ATUALIZAR, lembrete);

        return getDatabase().update(DatabaseHelper.LEMBRETE_TABLE, valores, DatabaseHelper
                .LEMBRETE_ID + " = ?", new String[]{String.valueOf(lembrete.getId())});
    }

    public Lembrete buscarLembretePorId(int id) {
        String query = setJoinQuery() + " WHERE " + DatabaseHelper.LEMBRETE_ID + " = " + id;
        Cursor cursor = getDatabase().rawQuery(query, null);

        if (cursor.moveToNext()) {
            Lembrete lembrete = getLembrete(cursor);
            cursor.close();
            return lembrete;
        }

        return null;
    }

    private ContentValues buildContentValues(int option, Lembrete lembrete){
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.LEMBRETE_DATA, lembrete.getDatahora());
        valores.put(DatabaseHelper.LEMBRETE_ESTADO, lembrete.getEstado());
        valores.put(DatabaseHelper.LEMBRETE_LIVRO, lembrete.getLivro().getId());
        valores.put(DatabaseHelper.LEMBRETE_REPETE, lembrete.getRepete());

        if (option == ATUALIZAR) {
            valores.put(DatabaseHelper.LEMBRETE_ID, lembrete.getId());
        }
        return valores;
    }

    private String setJoinQuery() {
        return "SELECT le." + DatabaseHelper.LEMBRETE_ID + ", le." + DatabaseHelper.LEMBRETE_REPETE
                + ", le." + DatabaseHelper.LEMBRETE_ESTADO + ", le." + DatabaseHelper.LEMBRETE_DATA
                + ", le." + DatabaseHelper.LEMBRETE_LIVRO +  ", li." + DatabaseHelper.LIVRO_FOTO
                + ", li." + DatabaseHelper.LIVRO_NOME + ", li." + DatabaseHelper.LIVRO_TOTAL_PAGINAS
                + " FROM "
                + DatabaseHelper.LEMBRETE_TABLE + " le INNER JOIN " + DatabaseHelper.LIVRO_TABLE
                + " li ON le." + DatabaseHelper.LEMBRETE_LIVRO + " = li." + DatabaseHelper.LIVRO_ID;
    }

    private Lembrete getLembrete(Cursor cursor) {
        Livro livro = new Livro(
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LEMBRETE_LIVRO)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.LIVRO_NOME)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LIVRO_TOTAL_PAGINAS)),
                cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.LIVRO_FOTO))
        );
        return new Lembrete(
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LEMBRETE_ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.LEMBRETE_DATA)),
                livro,
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LEMBRETE_ESTADO)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LEMBRETE_REPETE))
        );
    }

    public void close() {
        databaseHelper.close();
        sqLiteDatabase = null;
    }
}
